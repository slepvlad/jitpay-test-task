package eu.jitpay.testtask;

import eu.jitpay.testtask.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpsertUserTest extends AbstractControllerTest {
    private static final String BASE_PATH = "requests/user/";

    @Test
    @DisplayName("[Success] Create new user")
    public void createNewUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(readFileFromResources(BASE_PATH + "success_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("2e3b11b0-07a4-4873-8de5-d2ae2eab26b2"))
                .andExpect(jsonPath("$.email").value("alex.schmid@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.secondName").value("Schmid"))
                .andDo(MockMvcResultHandlers.print());

        var users = userRepository.findAll();

        assertEquals(1, users.size());
        assertEquals(UUID.fromString("2e3b11b0-07a4-4873-8de5-d2ae2eab26b2"), users.get(0).getUserId());
        assertEquals("alex.schmid@gmail.com", users.get(0).getEmail());
        assertEquals("Alex", users.get(0).getFirstName());
        assertEquals("Schmid", users.get(0).getSecondName());
    }

    @Test
    @DisplayName("[Success] Update existed user")
    public void updateExistedUserTest() throws Exception {

        User user = new User();
        user.setUserId(UUID.fromString("2e3b11b0-07a4-4873-8de5-d2ae2eab26b2"));
        user.setEmail("old.alex.schmid@gmail.com");
        user.setFirstName("Alex_old_name");
        user.setSecondName("Schmid_old_name");

        userRepository.save(user);

        assertEquals(1, userRepository.findAll().size());


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(readFileFromResources(BASE_PATH + "success_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("2e3b11b0-07a4-4873-8de5-d2ae2eab26b2"))
                .andExpect(jsonPath("$.email").value("alex.schmid@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.secondName").value("Schmid"))
                .andDo(MockMvcResultHandlers.print());

        var users = userRepository.findAll();

        assertEquals(1, users.size());
        assertEquals(UUID.fromString("2e3b11b0-07a4-4873-8de5-d2ae2eab26b2"), users.get(0).getUserId());
        assertEquals("alex.schmid@gmail.com", users.get(0).getEmail());
        assertEquals("Alex", users.get(0).getFirstName());
        assertEquals("Schmid", users.get(0).getSecondName());
    }

    @Test
    @DisplayName("[Fail] userId is null")
    public void userIdIsNullTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(readFileFromResources(BASE_PATH + "validation_user_id_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    @DisplayName("[Fail] Email is blank")
    public void emailIsBlankTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(readFileFromResources(BASE_PATH + "validation_email_blank_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    @DisplayName("[Fail] Email is wrong format")
    public void emailIsWrongFormatTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(readFileFromResources(BASE_PATH + "validation_email_wrong_format_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    @DisplayName("[Fail] FirstName is blank")
    public void firstNameIsBlankTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(readFileFromResources(BASE_PATH + "validation_firstname_blank_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    @DisplayName("[Fail] SecondName is blank")
    public void secondNameIsBlankTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(readFileFromResources(BASE_PATH + "validation_secondname_blank_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(0, userRepository.findAll().size());
    }
}
