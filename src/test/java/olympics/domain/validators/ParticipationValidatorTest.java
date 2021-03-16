package olympics.domain.validators;

import olympics.domain.AthleteTest;
import olympics.domain.CompetitionTest;
import olympics.domain.ParticipationTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.validators.ParticipationValidator;
import ro.ubb.olympics.domain.validators.ValidatorException;

public class ParticipationValidatorTest {

    private static final Long INVALID_ID = 0L;
    private static final int INVALID_RANK = 0;

    private final ParticipationValidator participationValidator = new ParticipationValidator();
    private Participation participation;

    @Before
    public void setUp() {
        participation = new Participation(
            ParticipationTest.ID,
            AthleteTest.ID,
            CompetitionTest.ID,
            ParticipationTest.RANK
        );
    }

    @After
    public void tearDown() {
        participation = null;
    }

    @Test
    public void testValidateNullObject() {
        Assert.assertThrows(
            "Should throw exception when validating null object",
            IllegalArgumentException.class,
            () -> participationValidator.validate(null)
        );
    }

    @Test
    public void testValidateInvalidRank() {
        participation.setRank(INVALID_RANK);
        Assert.assertThrows(
            "Should throw exception when rank is not strictly positive",
            ValidatorException.class,
            () -> participationValidator.validate(participation)
        );
    }

    @Test
    public void testValidateInvalidId() {
        participation.setId(INVALID_ID);
        Assert.assertThrows(
            "Should throw exception when id is not strictly positive",
            ValidatorException.class,
            () -> participationValidator.validate(participation)
        );
    }

    @Test
    public void testValidateValidObject() {
        participationValidator.validate(participation);
    }

}