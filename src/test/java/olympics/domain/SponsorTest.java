package olympics.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Sponsor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SponsorTest {

    public static final Long ID = 1L;
    public static final Long NEW_ID = 2L;

    public static final String NAME = "sponsorName";
    public static final String NEW_NAME = "newSponsorName";

    public static final String COUNTRY = "country";
    public static final String NEW_COUNTRY = "newCountry";

    private Sponsor sponsor;

    @Before
    public void setUp() {
        sponsor = new Sponsor(ID, NAME, COUNTRY);
    }

    @After
    public void tearDown() {
        sponsor = null;
    }

    @Test
    public void testGetID() {
        assertEquals("The IDs should be equal", ID, sponsor.getId());
    }

    @Test
    public void testSetID() {
        sponsor.setId(NEW_ID);
        assertEquals("The IDs should be equal", NEW_ID, sponsor.getId());
    }

    @Test
    public void getName() {
        assertEquals("The names should be equal", NAME, sponsor.getName());
    }

    @Test
    public void setName() {
        sponsor.setName(NEW_NAME);
        assertEquals("The names should be equal", NEW_NAME, sponsor.getName());
    }

    @Test
    public void getCountry() {
        assertEquals("The countries should be equal", COUNTRY, sponsor.getCountry());
    }

    @Test
    public void setCountry() {
        sponsor.setCountry(NEW_COUNTRY);
        assertEquals("The countries should be equal", NEW_COUNTRY, sponsor.getCountry());
    }

    @Test
    public void testSymmetricEquals() {
        final Sponsor newSponsor = new Sponsor(ID, NAME, COUNTRY);
        assertEquals("Two identical sponsors should be equal", newSponsor, sponsor);
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals("An sponsor should be equal to itself", sponsor, sponsor);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals("A non-null sponsor should not be equal to null", sponsor, null);
    }

    @Test
    public void testEqualDifferentClass() {
        assertNotEquals("An sponsor should not be equal to an object of another class", sponsor, ID);
    }

    @Test
    public void testEqualsDifferentId() {
        final Sponsor newSponsor = new Sponsor(NEW_ID, NAME, COUNTRY);
        assertNotEquals("Two sponsor with different IDs should not be equal", newSponsor, sponsor);
    }

    @Test
    public void testEqualsDifferentName() {
        final Sponsor newSponsor = new Sponsor(ID, NEW_NAME, COUNTRY);
        assertNotEquals("Two sponsor with different names should not be equal", newSponsor, sponsor);
    }

    @Test
    public void testEqualsDifferentCountry() {
        final Sponsor newSponsor = new Sponsor(ID, NAME, NEW_COUNTRY);
        assertNotEquals("Two sponsor with different countries should not be equal", newSponsor, sponsor);
    }

    @Test
    public void testHashCode() {
        final Sponsor newSponsor = new Sponsor(ID, NAME, COUNTRY);
        assertEquals("The hashcode of two identical sponsor should be the same", sponsor.hashCode(), newSponsor.hashCode());
    }

}