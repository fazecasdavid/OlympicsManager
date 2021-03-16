package olympics.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.Sponsorship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SponsorshipTest {

    public static final Long ID = 1L;
    public static final Long NEW_ID = 2L;

    public static final int MONEY_CONTRIBUTION = 1;
    public static final int NEW_MONEY_CONTRIBUTION = 2;

    public static final Sponsor SPONSOR = new Sponsor(
        SponsorTest.ID,
        SponsorTest.NAME,
        SponsorTest.COUNTRY
    );

    public static final Sponsor NEW_SPONSOR = new Sponsor(
        SponsorTest.NEW_ID,
        SponsorTest.NEW_NAME,
        SponsorTest.NEW_COUNTRY
    );

    public static final Competition COMPETITION = new Competition(
        CompetitionTest.ID,
        CompetitionTest.DATE,
        CompetitionTest.LOCATION,
        CompetitionTest.NAME,
        CompetitionTest.DESCRIPTION
    );

    public static final Competition NEW_COMPETITION = new Competition(
        CompetitionTest.NEW_ID,
        CompetitionTest.NEW_DATE,
        CompetitionTest.NEW_LOCATION,
        CompetitionTest.NEW_NAME,
        CompetitionTest.NEW_DESCRIPTION
    );

    private Sponsorship sponsorship;

    @Before
    public void setUp() {
        sponsorship = new Sponsorship(ID, COMPETITION.getId(), SPONSOR.getId(), MONEY_CONTRIBUTION);
    }

    @After
    public void tearDown() {
        sponsorship = null;
    }

    @Test
    public void testGetID() {
        assertEquals("The IDs should be equal", ID, sponsorship.getId());
    }

    @Test
    public void testSetID() {
        sponsorship.setId(NEW_ID);
        assertEquals("The IDs should be equal", NEW_ID, sponsorship.getId());
    }

    @Test
    public void getCompetition() {
        assertEquals("The competitions should be equal", COMPETITION.getId(), sponsorship.getCompetitionId());
    }

    @Test
    public void setCompetition() {
        sponsorship.setCompetitionId(NEW_COMPETITION.getId());
        assertEquals("The competitions should be equal", NEW_COMPETITION.getId(), sponsorship.getCompetitionId());
    }

    @Test
    public void getSponsor() {
        assertEquals("The sponsorships should be equal", SPONSOR.getId(), sponsorship.getSponsorId());
    }

    @Test
    public void setSponsor() {
        sponsorship.setSponsorId(NEW_SPONSOR.getId());
        assertEquals("The sponsorships should be equal", NEW_SPONSOR.getId(), sponsorship.getSponsorId());
    }

    @Test
    public void testSymmetricEquals() {
        final Sponsorship newSponsorship = new Sponsorship(ID, COMPETITION.getId(), SPONSOR.getId(), MONEY_CONTRIBUTION);
        assertEquals("Two identical sponsorships should be equal", newSponsorship, sponsorship);
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals("A sponsorship should be equal to itself", sponsorship, sponsorship);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals("A non-null sponsorship should not be equal to null", sponsorship, null);
    }

    @Test
    public void testEqualDifferentClass() {
        assertNotEquals("A sponsorship should not be equal to an object of another class", sponsorship, ID);
    }

    @Test
    public void testEqualsDifferentId() {
        final Sponsorship newSponsorship = new Sponsorship(NEW_ID, COMPETITION.getId(), SPONSOR.getId(), MONEY_CONTRIBUTION);
        assertNotEquals("Two sponsorships with different IDs should not be equal", newSponsorship, sponsorship);
    }

    @Test
    public void testEqualsDifferentSponsor() {
        final Sponsorship newSponsorship = new Sponsorship(ID, COMPETITION.getId(), NEW_SPONSOR.getId(), MONEY_CONTRIBUTION);
        assertNotEquals("Two sponsorships with different sponsorships should not be equal", newSponsorship, sponsorship);
    }

    @Test
    public void testEqualsDifferentCompetition() {
        final Sponsorship newSponsorship = new Sponsorship(ID, NEW_COMPETITION.getId(), SPONSOR.getId(), MONEY_CONTRIBUTION);
        assertNotEquals("Two sponsorships with different competitions should not be equal", newSponsorship, sponsorship);
    }

    @Test
    public void testEqualsDifferentMoneyContribution() {
        final Sponsorship newSponsorship = new Sponsorship(ID, COMPETITION.getId(), SPONSOR.getId(), NEW_MONEY_CONTRIBUTION);
        assertNotEquals("Two sponsorships with different money contributions should not be equal", newSponsorship, sponsorship);
    }

    @Test
    public void testHashCode() {
        final Sponsorship newSponsorship = new Sponsorship(ID, COMPETITION.getId(), SPONSOR.getId(), MONEY_CONTRIBUTION);
        assertEquals("The hashcode of two identical sponsorships should be the same", sponsorship.hashCode(), newSponsorship.hashCode());
    }

}