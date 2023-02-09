package eu.jitpay.testtask;

import eu.jitpay.testtask.domain.User;
import eu.jitpay.testtask.repository.LocationRepository;
import eu.jitpay.testtask.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected LocationRepository locationRepository;
    @Autowired
    protected UserRepository userRepository;

    @AfterEach
    public void clean(){
        locationRepository.deleteAll();
        userRepository.deleteAll();
    }

    protected String readFileFromResources(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return FileUtils.readFileToString(file, "UTF-8");
    }

    protected User createDefaultUser(){
        User user = new User();
        user.setUserId(UUID.fromString("2e3b11b0-07a4-4873-8de5-d2ae2eab26b2"));
        user.setEmail("alex.schmid@gmail.com");
        user.setFirstName("Alex");
        user.setSecondName("Schmid");

        return userRepository.save(user);
    }
}
