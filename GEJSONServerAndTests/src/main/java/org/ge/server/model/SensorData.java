// tag::sample[]
package org.ge.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class SensorData {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String name;

    @Min(0)
    @Max(1000)
    @NotNull
    private Long value;

    @Column(unique = true)
    @NotNull
    private Long time;
    private Boolean isOnline;

    public SensorData() {
    }

    public SensorData(String name, Long value, Long time, Boolean isOnline) {
        this.name = name;
        this.value = value;
        this.time = time;
        this.isOnline = isOnline;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", time=" + time +
                ", isOnline=" + isOnline +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getValue() {
        return value;
    }

    public Long getTime() {
        return time;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }
}

