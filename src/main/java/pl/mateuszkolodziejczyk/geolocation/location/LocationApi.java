package pl.mateuszkolodziejczyk.geolocation.location;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/location")
@RequiredArgsConstructor
public class LocationApi {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Void> saveLocation(@RequestBody @Valid Location location) {
        locationService.saveLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public  ResponseEntity<List<Location>> getAllLocations() {
        List<Location> allLocations = locationService.getAllLocations();
        return ResponseEntity.status(HttpStatus.OK).body(allLocations);
    }
}
