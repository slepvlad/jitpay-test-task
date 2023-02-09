package eu.jitpay.testtask;

import eu.jitpay.testtask.domain.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class GetLocationsTest extends AbstractControllerTest {
    private static final String URL = "/api/v1/locations/user/{userId}";

    @Test
    @DisplayName("[Success] Get locations")
    public void getLocationsTest() throws Exception {
        var user = createDefaultUser();

        Location location1 = new Location();
        location1.setUserId(user.getUserId());
        location1.setCreatedOn(LocalDateTime.parse("2022-02-07T11:44:00.524"));
        location1.setLongitude(new BigDecimal("52.25742342295784"));
        location1.setLatitude(new BigDecimal("10.540583401747602"));

        Location location2 = new Location();
        location2.setUserId(user.getUserId());
        location2.setCreatedOn(LocalDateTime.parse("2022-02-09T11:44:00.524"));
        location2.setLongitude(new BigDecimal("53.25742342295784"));
        location2.setLatitude(new BigDecimal("11.540583401747602"));

        Location location3 = new Location();
        location3.setUserId(user.getUserId());
        location3.setCreatedOn(LocalDateTime.parse("2022-02-06T11:44:00.524"));
        location3.setLongitude(new BigDecimal("52.25742342295784"));
        location3.setLatitude(new BigDecimal("10.540583401747602"));

        locationRepository.saveAll(List.of(location1, location2, location3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL, user.getUserId())
                        .param("from", "2022-02-07T11:44:00.524")
                        .param("to", "2022-02-09T11:44:00.524")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId().toString()))
                .andExpect(jsonPath("$.locations").isArray())
                .andExpect(jsonPath("$.locations", hasSize(2)))
                .andExpect(jsonPath("$.locations[0].createdOn").value("2022-02-09T11:44:00.524"))
                .andExpect(jsonPath("$.locations[0].location.latitude").value("11.540583401747602"))
                .andExpect(jsonPath("$.locations[0].location.longitude").value("53.25742342295784"))
                .andExpect(jsonPath("$.locations[1].createdOn").value("2022-02-07T11:44:00.524"))
                .andExpect(jsonPath("$.locations[1].location.latitude").value("10.540583401747602"))
                .andExpect(jsonPath("$.locations[1].location.longitude").value("52.25742342295784"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("[Fail] Get locations User not exists")
    public void userNotExistsTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL, UUID.randomUUID())
                        .param("from", "2022-02-07T11:44:00.524")
                        .param("to", "2022-02-09T11:44:00.524")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[Fail] From date is After To Date")
    public void fromDateAfterToTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL, UUID.randomUUID())
                        .param("from", "2022-02-09T11:44:00.524")
                        .param("to", "2022-02-07T11:44:00.524")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Date And Time from is after Date And Time to"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[Fail] Time range is too long")
    public void timeRangeIsTooLongTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL, UUID.randomUUID())
                        .param("from", "2022-01-01T11:44:00.524")
                        .param("to", "2022-02-07T11:44:00.524")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Max time period is to long. Try to reduce time range"))
                .andDo(MockMvcResultHandlers.print());
    }
}
