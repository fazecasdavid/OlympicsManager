package olympics.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Athlete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AthleteTest {

    public static final Long ID = 1L;
    public static final Long NEW_ID = 2L;

    public static final String FIRST_NAME = "firstName";
    public static final String NEW_FIRST_NAME = "newFirstName";

    public static final String LAST_NAME = "lastName";
    public static final String NEW_LAST_NAME = "newLastName";

    public static final String COUNTRY = "country";
    public static final String NEW_COUNTRY = "newCountry";

    public static final int AGE = 20;
    public static final int NEW_AGE = 21;

    private Athlete athlete;

    @Before
    public void setUp() {
        athlete = new Athlete(ID, FIRST_NAME, LAST_NAME, COUNTRY, AGE);
    }

    @After
    public void tearDown() {
        athlete = null;
    }

    @Test
    public void testGetID() {
        assertEquals("The IDs should be equal", ID, athlete.getId());
    }

    @Test
    public void testSetID() {
        athlete.setId(NEW_ID);
        assertEquals("The IDs should be equal", NEW_ID, athlete.getId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("The first names should be equal", FIRST_NAME, athlete.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        athlete.setFirstName(NEW_FIRST_NAME);
        assertEquals("The first names should be equal", NEW_FIRST_NAME, athlete.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("The last names should be equal", LAST_NAME, athlete.getLastName());
    }

    @Test
    public void testSetLastName() {
        athlete.setLastName(NEW_LAST_NAME);
        assertEquals("The last names should be equal", NEW_LAST_NAME, athlete.getLastName());
    }

    @Test
    public void testGetCountry() {
        assertEquals("The countries should be equal", COUNTRY, athlete.getCountry());
    }

    @Test
    public void testSetCountry() {
        athlete.setCountry(NEW_COUNTRY);
        assertEquals("The countries should be equal", NEW_COUNTRY, athlete.getCountry());
    }

    @Test
    public void testGetAge() {
        assertEquals("The ages should be equal", AGE, athlete.getAge());
    }

    @Test
    public void testSetAge() {
        athlete.setAge(NEW_AGE);
        assertEquals("The ages should be equal", NEW_AGE, athlete.getAge());
    }

    @Test
    public void testSymmetricEquals() {
        final Athlete newAthlete = new Athlete(ID, FIRST_NAME, LAST_NAME, COUNTRY, AGE);
        assertEquals("Two identical athletes should be equal", newAthlete, athlete);
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals("An athlete should be equal to itself", athlete, athlete);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals("A non-null athlete should not be equal to null", athlete, null);
    }

    @Test
    public void testEqualsDifferentClass() {
        assertNotEquals("An athlete should not be equal to an object of another class", athlete, ID);
    }

    @Test
    public void testEqualsDifferentID() {
        final Athlete newAthlete = new Athlete(NEW_ID, FIRST_NAME, LAST_NAME, COUNTRY, AGE);
        assertNotEquals("Two athletes with different IDs should not be equal", newAthlete, athlete);
    }

    @Test
    public void testEqualsDifferentFirstName() {
        final Athlete newAthlete = new Athlete(ID, NEW_FIRST_NAME, LAST_NAME, COUNTRY, AGE);
        assertNotEquals("Two athletes with different first names should not be equal", newAthlete, athlete);
    }

    @Test
    public void testEqualsDifferentLastName() {
        final Athlete newAthlete = new Athlete(ID, FIRST_NAME, NEW_LAST_NAME, COUNTRY, AGE);
        assertNotEquals("Two athletes with different last names should not be equal", newAthlete, athlete);
    }

    @Test
    public void testEqualsDifferentCountry() {
        final Athlete newAthlete = new Athlete(ID, FIRST_NAME, LAST_NAME, NEW_COUNTRY, AGE);
        assertNotEquals("Two athletes with different countries should not be equal", newAthlete, athlete);
    }

    @Test
    public void testEqualsDifferentAge() {
        final Athlete newAthlete = new Athlete(ID, FIRST_NAME, LAST_NAME, COUNTRY, NEW_AGE);
        assertNotEquals("Two athletes with different ages should not be equal", newAthlete, athlete);
    }

    @Test
    public void testHashcode() {
        final Athlete newAthlete = new Athlete(ID, FIRST_NAME, LAST_NAME, COUNTRY, AGE);
        assertEquals("The hashcode of two identical athletes should be the same", athlete.hashCode(), newAthlete.hashCode());
    }

}