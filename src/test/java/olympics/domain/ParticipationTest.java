package olympics.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Participation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ParticipationTest {

    public static final Long ID = 1L;
    public static final Long NEW_ID = 2L;

    public static final int RANK = 1;
    public static final int NEW_RANK = 2;

    public static final Athlete ATHLETE = new Athlete(
        AthleteTest.ID,
        AthleteTest.FIRST_NAME,
        AthleteTest.LAST_NAME,
        AthleteTest.COUNTRY,
        AthleteTest.AGE
    );

    public static final Competition COMPETITION = new Competition(
        CompetitionTest.ID,
        CompetitionTest.DATE,
        CompetitionTest.LOCATION,
        CompetitionTest.NAME,
        CompetitionTest.DESCRIPTION
    );

    private static final Athlete NEW_ATHLETE = new Athlete(
        AthleteTest.NEW_ID,
        AthleteTest.NEW_FIRST_NAME,
        AthleteTest.NEW_LAST_NAME,
        AthleteTest.NEW_COUNTRY,
        AthleteTest.NEW_AGE
    );

    private static final Competition NEW_COMPETITION = new Competition(
        CompetitionTest.NEW_ID,
        CompetitionTest.NEW_DATE,
        CompetitionTest.NEW_LOCATION,
        CompetitionTest.NEW_NAME,
        CompetitionTest.NEW_DESCRIPTION
    );

    private Participation participation;

    @Before
    public void setUp() {
        participation = new Participation(ID, ATHLETE.getId(), COMPETITION.getId(), RANK);
    }

    @After
    public void tearDown() {
        participation = null;
    }

    @Test
    public void testGetID() {
        assertEquals("The IDs should be equal", ID, participation.getId());
    }

    @Test
    public void testSetID() {
        participation.setId(NEW_ID);
        assertEquals("The IDs should be equal", NEW_ID, participation.getId());
    }

    @Test
    public void testGetAthlete() {
        assertEquals("The athletes should be equal", ATHLETE.getId(), participation.getAthleteId());
    }

    @Test
    public void testSetAthlete() {
        participation.setAthleteId(NEW_ATHLETE.getId());
        assertEquals("The athletes should be equal", NEW_ATHLETE.getId(), participation.getAthleteId());
    }

    @Test
    public void testGetCompetition() {
        assertEquals("The competitions should be equal", COMPETITION.getId(), participation.getCompetitionId());
    }

    @Test
    public void testSetCompetition() {
        participation.setCompetitionId(NEW_COMPETITION.getId());
        assertEquals("The competitions should be equal", NEW_COMPETITION.getId(), participation.getCompetitionId());
    }

    @Test
    public void testGetRank() {
        assertEquals("The ranks should be equal", RANK, participation.getRank());
    }

    @Test
    public void testSetRank() {
        participation.setRank(NEW_RANK);
        assertEquals("The ranks should be equal", NEW_RANK, participation.getRank());
    }

    @Test
    public void testSymmetricEquals() {
        final Participation newParticipation = new Participation(ID, ATHLETE.getId(), COMPETITION.getId(), RANK);
        assertEquals("Two identical participations should be equal", newParticipation, participation);
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals("A participation should be equal to itself", participation, participation);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals("A non-null participation should not be equal to null", participation, null);
    }

    @Test
    public void testEqualsDifferentClass() {
        assertNotEquals("A participation should not be equal to an object of another class", participation, ID);
    }

    @Test
    public void testEqualsDifferentID() {
        final Participation newParticipation = new Participation(NEW_ID, ATHLETE.getId(), COMPETITION.getId(), RANK);
        assertNotEquals("Two participations with different IDs should not be equal", newParticipation, participation);
    }

    @Test
    public void testEqualsDifferentAthlete() {
        final Participation newParticipation = new Participation(ID, NEW_ATHLETE.getId(), COMPETITION.getId(), RANK);
        assertNotEquals("Two participations with different athletes should not be equal", newParticipation, participation);
    }

    @Test
    public void testEqualsDifferentCompetition() {
        final Participation newParticipation = new Participation(ID, ATHLETE.getId(), NEW_COMPETITION.getId(), RANK);
        assertNotEquals("Two participations with different competitions should not be equal", newParticipation, participation);
    }

    @Test
    public void testEqualsDifferentRank() {
        final Participation newParticipation = new Participation(ID, ATHLETE.getId(), COMPETITION.getId(), NEW_RANK);
        assertNotEquals("Two participations with different ranks should not be equal", newParticipation, participation);
    }

    @Test
    public void testHashcode() {
        final Participation newParticipation = new Participation(ID, ATHLETE.getId(), COMPETITION.getId(), RANK);
        assertEquals("The hashcode of two identical participations should be the same", participation.hashCode(), newParticipation.hashCode());
    }

}