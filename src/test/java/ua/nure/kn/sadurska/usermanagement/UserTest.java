package ua.nure.kn.sadurska.usermanagement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringJoiner;

public class UserTest extends Assert {

    private static final String FIRST_NAME = "Zhanna";
    private static final String LAST_NAME = "Sadurska";

    private User testedUser;

    @Before
    public void setUp() {
        testedUser = new User();
    }

    @Test
    public void testGetFullName() {
        testedUser.setFirstName(FIRST_NAME);
        testedUser.setLastName(LAST_NAME);
        final String expectedFullName = new StringJoiner(", ").add(LAST_NAME).add(FIRST_NAME).toString();
        final String actualFullName = testedUser.getFullName();
        assertEquals(expectedFullName, actualFullName);
    }

    @Test
    public void testGetAge() throws ParseException {
        testedUser.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse("22/06/1986"));
        final int expectedAge = 32;
        final int actualAge = testedUser.getAge();
        assertEquals(expectedAge, actualAge);
    }
}
