package pl.mateuszkolodziejczyk.geolocation.location;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = LocationApi.class,
        excludeAutoConfiguration = {UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class})
class LocationApiTest {

    @MockBean
    LocationService locationService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldSaveLocation() throws Exception {
        Location location = new Location(
                200L, BigDecimal.valueOf(80), BigDecimal.valueOf(-140.5555));

        mockMvc.perform(post("/api/location")
                        .content(objectMapper.writeValueAsString(location))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotSaveLocationBecauseIdIsNull() throws Exception {
        Location location = new Location(
                null, BigDecimal.valueOf(-45), BigDecimal.valueOf(-140.5555));

        MvcResult mvcResult = mockMvc.perform(post("/api/location")
                        .content(objectMapper.writeValueAsString(location))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Map<String, List<String>> map = new ObjectMapper().readValue(contentAsString, new TypeReference<>() {});
        List<String> errors = map.get("errors");

        Assertions.assertEquals("Device ID can't be null", errors.get(0));
    }

    @Test
    void shouldNotSaveLocationBecauseLatitudeIsLowerThan90() throws Exception {
        Location location = new Location(
                200L, BigDecimal.valueOf(-91), BigDecimal.valueOf(-140.5555));

        MvcResult mvcResult = mockMvc.perform(post("/api/location")
                        .content(objectMapper.writeValueAsString(location))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Map<String, List<String>> map = new ObjectMapper().readValue(contentAsString, new TypeReference<>() {});
        List<String> errors = map.get("errors");

        Assertions.assertEquals("Latitude should be between -90 to 90", errors.get(0));
    }

    @Test
    void shouldNotSaveLocationBecauseLatitudeIsGreaterThan90() throws Exception {
        Location location = new Location(
                200L, BigDecimal.valueOf(91), BigDecimal.valueOf(-140.5555));

        MvcResult mvcResult = mockMvc.perform(post("/api/location")
                        .content(objectMapper.writeValueAsString(location))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Map<String, List<String>> map = new ObjectMapper().readValue(contentAsString, new TypeReference<>() {});
        List<String> errors = map.get("errors");

        Assertions.assertEquals("Latitude should be between -90 to 90", errors.get(0));
    }

    @Test
    void shouldNotSaveLocationBecauseLongitudeIsLowerThan180() throws Exception {
        Location location = new Location(
                200L, BigDecimal.valueOf(45), BigDecimal.valueOf(-181));

        MvcResult mvcResult = mockMvc.perform(post("/api/location")
                        .content(objectMapper.writeValueAsString(location))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Map<String, List<String>> map = new ObjectMapper().readValue(contentAsString, new TypeReference<>() {});
        List<String> errors = map.get("errors");

        Assertions.assertEquals("Longitude should be between -180 to 180", errors.get(0));
    }

    @Test
    void shouldNotSaveLocationBecauseLongitudeIsGreaterThan180() throws Exception {
        Location location = new Location(
                200L, BigDecimal.valueOf(45), BigDecimal.valueOf(181));

        MvcResult mvcResult = mockMvc.perform(post("/api/location")
                        .content(objectMapper.writeValueAsString(location))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Map<String, List<String>> map = new ObjectMapper().readValue(contentAsString, new TypeReference<>() {});
        List<String> errors = map.get("errors");

        Assertions.assertEquals("Longitude should be between -180 to 180", errors.get(0));
    }

    @Test
    void getAllLocations() throws Exception {
        Location location1 = new Location(
                201L, BigDecimal.valueOf(80), BigDecimal.valueOf(-140.5555));
        Location location2 = new Location(
                202L, BigDecimal.valueOf(44.5656), BigDecimal.valueOf(120.345123));
        List<Location> locations = List.of(location1, location2);
        BDDMockito.given(locationService.getAllLocations()).willReturn(locations);

        MvcResult mvcResult = mockMvc.perform(get("/api/location")).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Location> locationsResult = new ObjectMapper().readValue(contentAsString, new TypeReference<>() {});
        Assertions.assertEquals(2, locationsResult.size());
    }
}