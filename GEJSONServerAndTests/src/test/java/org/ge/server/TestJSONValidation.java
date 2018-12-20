package org.ge.server;

import org.ge.server.model.Data;
import org.ge.server.model.Error;
import org.ge.server.model.SensorData;
import org.ge.server.model.Status;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
public class TestJSONValidation {

    private static String uri = "http://localhost";

    private static RestTemplate restTemplate = new RestTemplate();

    static {
        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        };

        restTemplate.setErrorHandler(errorHandler);
    }

    @BeforeClass
    public static void deleteAllData() {
        Status status = restTemplate.getForObject(uri + "/deleteAll", Status.class);

        assertThat(status.getCode()).hasToString("ok");
        assertThat(status.getDescription()).hasToString("ok");
    }

    @Test
    public void submitValidSensorData() {
        Data data = new Data();
        data.addSensorData(new SensorData("sensor1", 500L, 1538041571928L, true));
        data.addSensorData(new SensorData("sensor2", 700L, 1538041571929L, false));
        data.addSensorData(new SensorData("sensor2", 500L, 1538041571930L, null));
        data.addSensorData(new SensorData("sensor4", 500L, 1538041571931L, true));

        ResponseEntity<Status> responseEntity = restTemplate.postForEntity(uri + "/post", data, Status.class);

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getCode()).hasToString("ok");
        assertThat(responseEntity.getBody().getDescription()).hasToString("ok");
    }

    @Test
    public void testTimeValueMustBeUnique() {
        Data data = new Data();
        data.addSensorData(new SensorData("sensor1", 500L, 10L, null));

        ResponseEntity<Status> responseEntity = restTemplate.postForEntity(uri + "/post", data, Status.class);

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getCode()).hasToString("ok");
        assertThat(responseEntity.getBody().getDescription()).hasToString("ok");

        ResponseEntity<Error> responseEntityError = restTemplate.postForEntity(uri + "/post", data, Error.class);
        assertThat(responseEntityError.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntityError.getBody().getMessage()).contains("ConstraintViolationException");
    }


    @Test
    public void testTooLargeSensorValue() {
        Data data = new Data();
        data.addSensorData(new SensorData("sensor1", 1500L, 200L, null));

        ResponseEntity<Error> responseEntity = restTemplate.postForEntity(uri + "/post", data, Error.class);

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessage()).hasToString("validation error");

        ResponseEntity<ArrayList<SensorData>> responseEntityCheck = getEntities(190L, 210L);

        assertThat(responseEntityCheck.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(responseEntityCheck.getBody().size()).isZero();

    }

    @Test
    public void testInvalidSensorName() {
        Data data = new Data();
        data.addSensorData(new SensorData("#sensor1#", 1500L, 300L, null));

        ResponseEntity<Error> responseEntity = restTemplate.postForEntity(uri + "/post", data, Error.class);

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessage()).hasToString("validation error");

        ResponseEntity<ArrayList<SensorData>> responseEntityCheck = getEntities(290L, 310L);

        assertThat(responseEntityCheck.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(responseEntityCheck.getBody().size()).isZero();
    }

    @Test
    public void testValueNotPresent() {
        Data data = new Data();
        data.addSensorData(new SensorData("sensor1", null, 400L, null));

        ResponseEntity<Error> responseEntity = restTemplate.postForEntity(uri + "/post", data, Error.class);

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessage()).hasToString("validation error");

        ResponseEntity<ArrayList<SensorData>> responseEntityCheck = getEntities(390L, 410L);

        assertThat(responseEntityCheck.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(responseEntityCheck.getBody().size()).isZero();
    }

    @Test
    public void testIsOnlineOptionalValue() {
        Data data = new Data();
        data.addSensorData(new SensorData("sensor1", 500L, 100L, null));
        data.addSensorData(new SensorData("sensor5", 100L, 101L, true));
        data.addSensorData(new SensorData("sensor10", 250L, 102L, false));

        ResponseEntity<Status> responseEntity = restTemplate.postForEntity(uri + "/post", data, Status.class);

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getCode()).hasToString("ok");
        assertThat(responseEntity.getBody().getDescription()).hasToString("ok");

        ResponseEntity<ArrayList<SensorData>> responseEntityCheck = getEntities(90L, 110L);

        assertThat(responseEntityCheck.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(responseEntityCheck.getBody()).hasSize(3);
        assertThat(responseEntityCheck.getBody()).extracting("name", "value", "time", "isOnline")
                .contains(
                        tuple("sensor1", 500L, 100L, null),
                        tuple("sensor5", 100L, 101L, Boolean.TRUE),
                        tuple("sensor10", 250L, 102L, Boolean.FALSE)
                );

    }


    private ResponseEntity<ArrayList<SensorData>> getEntities(Long from, Long to) {
        return restTemplate.exchange(uri + "/get?from={from}&to={to}", HttpMethod.GET,
                null, new ParameterizedTypeReference<ArrayList<SensorData>>() {
                }, from, to);
    }
}