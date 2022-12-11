package ro.tuc.ds2020.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorValuesDTO
{
    private UUID deviceId;
    private LocalDateTime date;
    private float value;



    public SensorValuesDTO(@JsonProperty("deviceId") UUID deviceId,
                           @JsonProperty("date") LocalDateTime date,
                           @JsonProperty("value") float value)
    {
        this.deviceId = deviceId;
        this.date = date;
        this.value = value;
    }



    @Override
    public String toString() {
        return "SensorValuesDTO{" +
                "deviceId='" + deviceId + '\'' +
                ", date='" + date + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
