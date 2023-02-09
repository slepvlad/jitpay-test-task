package eu.jitpay.testtask;

import eu.jitpay.testtask.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CreateLocationTest extends AbstractControllerTest {
    private static final String BASE_PATH = "requests/location/";

    @Test
    @DisplayName("[Success] Create new location")
    public void createNewLocationTest() throws Exception {
        User user = new User();
        user.setUserId(UUID.fromString("2e3b11b0-07a4-4873-8de5-d2ae2eab26b2"));
        user.setEmail("alex.schmid@gmail.com");
        user.setFirstName("Alex");
        user.setSecondName("Schmid");

        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/locations")
                        .content(readFileFromResources(BASE_PATH + "success_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        var locations = locationRepository.findAll();
        assertEquals(1, locations.size());
        assertEquals(UUID.fromString("2e3b11b0-07a4-4873-8de5-d2ae2eab26b2"), locations.get(0).getUserId());
        assertEquals(LocalDateTime.parse("2022-02-08T11:44:00.524"), locations.get(0).getCreatedOn());
        assertEquals(new BigDecimal("52.25742342295784"), locations.get(0).getLatitude());
        assertEquals(new BigDecimal("10.540583401747602"), locations.get(0).getLongitude());

    }

    @Test
    @DisplayName("[Fail] Create new location User doesn't exist")
    public void userNotExistTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/locations")
                        .content(readFileFromResources(BASE_PATH + "success_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Constraint violation"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("[Fail] UserId is null")
    public void userIdIsNullTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/locations")
                        .content(readFileFromResources(BASE_PATH + "validation_user_id_null_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("[Fail] CreatedOn is null")
    public void createdOnIsNullTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/locations")
                        .content(readFileFromResources(BASE_PATH + "validation_created_on_null_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("[Fail] Location is null")
    public void locationIsNullTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/locations")
                        .content(readFileFromResources(BASE_PATH + "validation_location_null_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
