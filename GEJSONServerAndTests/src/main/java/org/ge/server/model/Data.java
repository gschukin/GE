package org.ge.server.model;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class Data {
    List<@Valid SensorData> data;

    public Data() {
    }

    public Data(List<SensorData> data) {
        this.data = data;
    }

    public List<SensorData> getData() {
        return data;
    }

    public void setData(List<SensorData> data) {
        this.data = data;
    }

    public void addSensorData(SensorData sensorData) {
        if (data == null) {
            data = new ArrayList<>();
        }

        data.add(sensorData);
    }
}
