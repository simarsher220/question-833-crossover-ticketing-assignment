package alfio.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {PasswordGenerator.class})
public class PasswordGeneratorTest {

    @Before
    public void setup() {

    }

    @Test
    public void getPassword_WhenDevModeEnabled() {
        System.setProperty("spring.profiles.active", "dev");
        String password = PasswordGenerator.generateRandomPassword();
        Assert.assertTrue(password.equals("abcd"));
    }

    @Test
    public void isValid_WhenPasswordIsNull() {
        Boolean isValid = PasswordGenerator.isValid(null);
        Assert.assertFalse(isValid);
    }

    @Test
    public void isValid_WhenPasswordIsLegit() {
        Boolean isValid = PasswordGenerator.isValid("Hello@1234");
        Assert.assertTrue(isValid);
    }
}
