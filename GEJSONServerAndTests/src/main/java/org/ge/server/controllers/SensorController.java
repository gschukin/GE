package org.ge.server.controllers;

import org.ge.server.model.Data;
import org.ge.server.model.Error;
import org.ge.server.model.SensorData;
import org.ge.server.model.Status;
import org.ge.server.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SensorController {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @RequestMapping(path = "/post", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Status post(@RequestBody @Valid Data data) {
        sensorDataRepository.saveAll(data.getData());
        return new Status("ok", "ok");
    }

    @RequestMapping(path = "/deleteAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Status post() {
        sensorDataRepository.deleteAll();
        return new Status("ok", "ok");
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SensorData> get(@RequestParam Long from, @RequestParam Long to) {
        return sensorDataRepository.findByTimeGreaterThanEqualAndTimeLessThan(from, to);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error validationExceptionHandler(Exception e) {
        return new Error("validation error");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error dataIntegrityViolationExceptionExceptionHandler(Exception e) {
        return new Error(e.getMessage());
    }


}
