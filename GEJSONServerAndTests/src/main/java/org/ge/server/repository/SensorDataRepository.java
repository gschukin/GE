package org.ge.server.repository;

import org.ge.server.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    List<SensorData> findByTimeGreaterThanEqualAndTimeLessThan(Long startTime, Long endTime);
}
