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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetUserTest extends AbstractControllerTest {

    private static final String URL = "/api/v1/users/{userId}";


    @Test
    @DisplayName("[Success] Get user info")
    public void getUserInfoTest() throws Exception {
        var user = createDefaultUser();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL, user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId().toString()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(user.getSecondName()))
                .andExpect(jsonPath("$.location").doesNotExist())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[Success] Get user info with location")
    public void getUserInfoWithLocationTest() throws Exception {
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

        locationRepository.saveAll(List.of(location1, location2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL, user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId().toString()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(user.getSecondName()))
                .andExpect(jsonPath("$.location.latitude").value("11.540583401747602"))
                .andExpect(jsonPath("$.location.longitude").value("53.25742342295784"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[Fail] User does not exist")
    public void userDoesNotExistTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL, UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}
