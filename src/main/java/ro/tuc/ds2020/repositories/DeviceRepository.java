package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//interfata, in service implementam
public interface DeviceRepository extends JpaRepository<Device, UUID> {

    List<Device> findByName(String name);
    List<Device> findByAddress(String address);

}
