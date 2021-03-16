package olympics.domain.validators;

import olympics.domain.CompetitionTest;
import olympics.domain.SponsorTest;
import olympics.domain.SponsorshipTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Sponsorship;
import ro.ubb.olympics.domain.validators.SponsorshipValidator;
import ro.ubb.olympics.domain.validators.ValidatorException;

public class SponsorshipValidatorTest {

    private static final Long INVALID_ID = 0L;
    private static final int INVALID_MONEY_CONTRIBUTION = 0;

    private final SponsorshipValidator sponsorshipValidator = new SponsorshipValidator();
    private Sponsorship sponsorship;

    @Before
    public void setUp() {
        sponsorship = new Sponsorship(
            SponsorshipTest.ID,
            CompetitionTest.ID,
            SponsorTest.ID,
            SponsorshipTest.MONEY_CONTRIBUTION
        );
    }

    @After
    public void tearDown() {
        sponsorship = null;
    }

    @Test
    public void testValidateNullObject() {
        Assert.assertThrows(
            "Should throw exception when validating null object",
            IllegalArgumentException.class,
            () -> sponsorshipValidator.validate(null)
        );
    }

    @Test
    public void testValidateInvalidMoneyContribution() {
        sponsorship.setMoneyContribution(INVALID_MONEY_CONTRIBUTION);
        Assert.assertThrows(
            "Should throw exception when money contribution is not strictly positive",
            ValidatorException.class,
            () -> sponsorshipValidator.validate(sponsorship)
        );
    }

    @Test
    public void testValidateInvalidId() {
        sponsorship.setId(INVALID_ID);
        Assert.assertThrows(
            "Should throw exception when id is not strictly positive",
            ValidatorException.class,
            () -> sponsorshipValidator.validate(sponsorship)
        );
    }

    @Test
    public void testValidateValidObject() {
        sponsorshipValidator.validate(sponsorship);
    }

}