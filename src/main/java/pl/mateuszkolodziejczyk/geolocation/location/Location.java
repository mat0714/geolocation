package pl.mateuszkolodziejczyk.geolocation.location;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @NotNull(message = "Device ID can't be null")
    private Long deviceId;

    @Range(min = -90, max = 90, message = "Latitude should be between -90 to 90")
    private BigDecimal latitude;

    @Range(min = -180, max = 180, message = "Longitude should be between -180 to 180")
    private BigDecimal longitude;
}
