package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.DeviceConsumption;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceConsumptionRepository extends JpaRepository<DeviceConsumption, UUID>{

    //Optional<Device> findByName(String name);
    List<DeviceConsumption> findByDate(LocalDateTime date);

    @Query(value = "SELECT d " +
            "FROM DeviceConsumption d " +
            "WHERE d.device.id = :deviceId ")
    List<DeviceConsumption> findByDeviceId(@Param("deviceId") UUID deviceId);
}
