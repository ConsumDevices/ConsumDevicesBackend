package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class Device implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    //@Type(type = "uuid-binary")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "maxHourlyConsumption", nullable = false)
    private float maxHourlyConsumption;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<DeviceConsumption> devicesConsumption;


    public Device() {
    }


    public Device(UUID id, String name, String description, String address, float maxHourlyConsumption, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.maxHourlyConsumption = maxHourlyConsumption;
        this.user = user;
    }

    public Device(String name, String description, String address, float maxHourlyConsumption, User user) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.maxHourlyConsumption = maxHourlyConsumption;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getMaxHourlyConsumption() {
        return maxHourlyConsumption;
    }

    public void setMaxHourlyConsumption(float maxHourlyConsumption) {
        this.maxHourlyConsumption = maxHourlyConsumption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DeviceConsumption> getDevicesConsumption() {
        return devicesConsumption;
    }

    public void setDevicesConsumption(List<DeviceConsumption> devicesConsumption) {
        this.devicesConsumption = devicesConsumption;
    }
}
