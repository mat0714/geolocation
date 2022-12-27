package pl.mateuszkolodziejczyk.geolocation.location;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    LocationRepository locationRepository;
    @InjectMocks
    LocationService locationService;

    @AfterEach
    void tearDown() {
        locationRepository.deleteAll();
    }

    @Test
    void shouldSaveLocation() {
        // Given
        Location location = new Location(
                100L, BigDecimal.valueOf(80), BigDecimal.valueOf(-140.5555));
        // When
        locationService.saveLocation(location);
        // Then
        Mockito.verify(locationRepository, Mockito.times(1)).save(location);
    }

    @Test
    void shouldGetAllLocations() {
        // Given
        Location location1 = new Location(
                101L, BigDecimal.valueOf(80), BigDecimal.valueOf(-140.5555));
        Location location2 = new Location(
                102L, BigDecimal.valueOf(44.5656), BigDecimal.valueOf(120.345123));
        given(locationRepository.findAll()).willReturn(List.of(location1, location2));
        // When
        List<Location> allLocations = locationService.getAllLocations();
        // Then
        Assertions.assertEquals(2, allLocations.size());
    }
}