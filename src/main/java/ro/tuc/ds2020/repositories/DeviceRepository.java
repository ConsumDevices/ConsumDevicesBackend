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

    Optional<Device> findByName(String name);
    List<Device> findByAddress(String address);

    @Query(value = "SELECT d " +
            "FROM Device d " +
            "WHERE d.user.id = :id ")
    List<Device> findAllForClient(@Param("id") UUID id);

    @Query(value = "SELECT d " +
            "FROM Device d " +
            "WHERE d.user.id = :id AND d.name = :name")
    Optional<Device> findByNameAndUserID(@Param("name") String name, @Param("id") UUID id);

}
