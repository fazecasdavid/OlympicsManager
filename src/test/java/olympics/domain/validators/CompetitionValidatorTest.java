package olympics.domain.validators;

import olympics.domain.CompetitionTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.validators.CompetitionValidator;
import ro.ubb.olympics.domain.validators.ValidatorException;

public class CompetitionValidatorTest {

    private static final String EMPTY_STRING = "";
    private static final Long INVALID_ID = 0L;

    private final CompetitionValidator competitionValidator = new CompetitionValidator();
    private Competition competition;

    @Before
    public void setUp() {
        competition = new Competition(
            CompetitionTest.ID,
            CompetitionTest.DATE,
            CompetitionTest.LOCATION,
            CompetitionTest.NAME,
            CompetitionTest.DESCRIPTION
        );
    }

    @After
    public void tearDown() {
        competition = null;
    }

    @Test
    public void testValidateNullObject() {
        Assert.assertThrows(
            "Should throw exception when validating null object",
            IllegalArgumentException.class,
            () -> competitionValidator.validate(null)
        );
    }

    @Test
    public void testValidateInvalidLocation() {
        competition.setLocation(EMPTY_STRING);
        Assert.assertThrows(
            "Should throw exception when location is empty",
            ValidatorException.class,
            () -> competitionValidator.validate(competition)
        );
    }

    @Test
    public void testValidateInvalidName() {
        competition.setName(EMPTY_STRING);
        Assert.assertThrows(
            "Should throw exception when name is empty",
            ValidatorException.class,
            () -> competitionValidator.validate(competition)
        );
    }

    @Test
    public void testValidateInvalidDescription() {
        competition.setDescription(EMPTY_STRING);
        Assert.assertThrows(
            "Should throw exception when description is empty",
            ValidatorException.class,
            () -> competitionValidator.validate(competition)
        );
    }

    @Test
    public void testValidateInvalidId() {
        competition.setId(INVALID_ID);
        Assert.assertThrows(
            "Should throw exception when id is not strictly positive",
            ValidatorException.class,
            () -> competitionValidator.validate(competition)
        );
    }

    @Test
    public void testValidateValidObject() {
        competitionValidator.validate(competition);
    }

}