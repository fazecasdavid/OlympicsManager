package olympics.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Competition;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CompetitionTest {

    public static final Long ID = 1L;
    public static final Long NEW_ID = 2L;

    public static final Date DATE = new Date(2000000000000L);
    public static final Date NEW_DATE = new Date(3000000000000L);

    public static final String LOCATION = "location";
    public static final String NEW_LOCATION = "newLocation";

    public static final String NAME = "name";
    public static final String NEW_NAME = "newName";

    public static final String DESCRIPTION = "description";
    public static final String NEW_DESCRIPTION = "newDescription";

    private Competition competition;

    @Before
    public void setUp() {
        competition = new Competition(ID, DATE, LOCATION, NAME, DESCRIPTION);
    }

    @After
    public void tearDown() {
        competition = null;
    }

    @Test
    public void testGetID() {
        assertEquals("The IDs should be equal", ID, competition.getId());
    }

    @Test
    public void testSetID() {
        competition.setId(NEW_ID);
        assertEquals("The IDs should be equal", NEW_ID, competition.getId());
    }

    @Test
    public void testGetDate() {
        assertEquals("The dates should be equal", DATE, competition.getDate());
    }

    @Test
    public void testSetDate() {
        competition.setDate(NEW_DATE);
        assertEquals("The dates should be equal", NEW_DATE, competition.getDate());
    }

    @Test
    public void testGetLocation() {
        assertEquals("The locations should be equal", LOCATION, competition.getLocation());
    }

    @Test
    public void testSetLocation() {
        competition.setLocation(NEW_LOCATION);
        assertEquals("The locations should be equal", NEW_LOCATION, competition.getLocation());
    }

    @Test
    public void testGetName() {
        assertEquals("The names should be equal", NAME, competition.getName());
    }

    @Test
    public void testSetName() {
        competition.setName(NEW_NAME);
        assertEquals("The names should be equal", NEW_NAME, competition.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("The descriptions should be equal", DESCRIPTION, competition.getDescription());
    }

    @Test
    public void testSetDescription() {
        competition.setDescription(NEW_DESCRIPTION);
        assertEquals("The descriptions should be equal", NEW_DESCRIPTION, competition.getDescription());
    }

    @Test
    public void testSymmetricEquals() {
        final Competition newCompetition = new Competition(ID, DATE, LOCATION, NAME, DESCRIPTION);
        assertEquals("Two identical competitions should be equal", newCompetition, competition);
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals("A competition should be equal to itself", competition, competition);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals("A non-null competition should not be equal to null", competition, null);
    }

    @Test
    public void testEqualsDifferentClass() {
        assertNotEquals("A competition should not be equal to an object of another class", competition, ID);
    }

    @Test
    public void testEqualsDifferentID() {
        final Competition newCompetition = new Competition(NEW_ID, DATE, LOCATION, NAME, DESCRIPTION);
        assertNotEquals("Two competitions with different IDs should not be equal", newCompetition, competition);
    }

    @Test
    public void testEqualsDifferentDate() {
        final Competition newCompetition = new Competition(ID, NEW_DATE, LOCATION, NAME, DESCRIPTION);
        assertNotEquals("Two competitions with different dates should not be equal", newCompetition, competition);
    }

    @Test
    public void testEqualsDifferentLocation() {
        final Competition newCompetition = new Competition(ID, DATE, NEW_LOCATION, NAME, DESCRIPTION);
        assertNotEquals("Two competitions with different locations should not be equal", newCompetition, competition);
    }

    @Test
    public void testEqualsDifferentName() {
        final Competition newCompetition = new Competition(ID, DATE, LOCATION, NEW_NAME, DESCRIPTION);
        assertNotEquals("Two competitions with different names should not be equal", newCompetition, competition);
    }

    @Test
    public void testEqualsDifferentDescription() {
        final Competition newCompetition = new Competition(ID, DATE, LOCATION, NAME, NEW_DESCRIPTION);
        assertNotEquals("Two competitions with different descriptions should not be equal", newCompetition, competition);
    }

    @Test
    public void testHashcode() {
        final Competition newCompetition = new Competition(ID, DATE, LOCATION, NAME, DESCRIPTION);
        assertEquals("The hashcode of two identical competitions should be the same", competition.hashCode(), newCompetition.hashCode());
    }

}