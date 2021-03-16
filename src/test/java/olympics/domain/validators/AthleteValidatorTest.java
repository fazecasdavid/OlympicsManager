package olympics.domain.validators;

import olympics.domain.AthleteTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.validators.AthleteValidator;
import ro.ubb.olympics.domain.validators.ValidatorException;

public class AthleteValidatorTest {

    private static final String EMPTY_STRING = "";
    private static final Long INVALID_ID = 0L;
    private static final int INVALID_AGE = 0;

    private final AthleteValidator athleteValidator = new AthleteValidator();
    private Athlete athlete;

    @Before
    public void setUp() {
        athlete = new Athlete(
            AthleteTest.ID,
            AthleteTest.FIRST_NAME,
            AthleteTest.LAST_NAME,
            AthleteTest.COUNTRY,
            AthleteTest.AGE
        );
    }

    @After
    public void tearDown() {
        athlete = null;
    }

    @Test
    public void testValidateNullObject() {
        Assert.assertThrows(
            "Should throw exception when validating null object",
            IllegalArgumentException.class,
            () -> athleteValidator.validate(null)
        );
    }

    @Test
    public void testValidateInvalidFirstName() {
        athlete.setFirstName(EMPTY_STRING);
        Assert.assertThrows(
            "Should throw exception when first name is empty",
            ValidatorException.class,
            () -> athleteValidator.validate(athlete)
        );
    }

    @Test
    public void testValidateInvalidLastName() {
        athlete.setLastName(EMPTY_STRING);
        Assert.assertThrows(
            "Should throw exception when last name is empty",
            ValidatorException.class,
            () -> athleteValidator.validate(athlete)
        );
    }

    @Test
    public void testValidateInvalidCountry() {
        athlete.setCountry(EMPTY_STRING);
        Assert.assertThrows(
            "Should throw exception when country is empty",
            ValidatorException.class,
            () -> athleteValidator.validate(athlete)
        );
    }

    @Test
    public void testValidateInvalidAge() {
        athlete.setAge(INVALID_AGE);
        Assert.assertThrows(
            "Should throw exception when age is not strictly positive",
            ValidatorException.class,
            () -> athleteValidator.validate(athlete)
        );
    }

    @Test
    public void testValidateInvalidId() {
        athlete.setId(INVALID_ID);
        Assert.assertThrows(
            "Should throw exception when id is not strictly positive",
            ValidatorException.class,
            () -> athleteValidator.validate(athlete)
        );
    }

    @Test
    public void testValidateValidObject() {
        athleteValidator.validate(athlete);
    }

}