package olympics.service;

import olympics.domain.CompetitionTest;
import olympics.domain.SponsorTest;
import olympics.domain.SponsorshipTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.Sponsorship;
import ro.ubb.olympics.domain.validators.SponsorshipValidator;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.repository.Repository;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;
import ro.ubb.olympics.service.SponsorshipService;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SponsorshipServiceTest {

    public static final Competition FIRST_COMPETITION = new Competition(
        CompetitionTest.ID,
        CompetitionTest.DATE,
        CompetitionTest.LOCATION,
        CompetitionTest.NAME,
        CompetitionTest.DESCRIPTION
    );

    public static final Competition SECOND_COMPETITION = new Competition(
        CompetitionTest.NEW_ID,
        CompetitionTest.NEW_DATE,
        CompetitionTest.NEW_LOCATION,
        CompetitionTest.NEW_NAME,
        CompetitionTest.NEW_DESCRIPTION
    );

    public static final Sponsor FIRST_SPONSOR = new Sponsor(
        SponsorTest.ID,
        SponsorTest.NAME,
        SponsorTest.COUNTRY
    );

    public static final Sponsor SECOND_SPONSOR = new Sponsor(
        SponsorTest.NEW_ID,
        SponsorTest.NEW_NAME,
        SponsorTest.NEW_COUNTRY
    );

    public static final Sponsorship FIRST_SPONSORSHIP = new Sponsorship(
        SponsorshipTest.ID,
        FIRST_COMPETITION.getId(),
        FIRST_SPONSOR.getId(),
        SponsorshipTest.MONEY_CONTRIBUTION
    );

    public static final Sponsorship SECOND_SPONSORSHIP = new Sponsorship(
        SponsorshipTest.NEW_ID,
        SECOND_COMPETITION.getId(),
        SECOND_SPONSOR.getId(),
        SponsorshipTest.NEW_MONEY_CONTRIBUTION
    );

    public static final Sponsorship THIRD_SPONSORSHIP = new Sponsorship(
        SponsorshipTest.ID,
        SECOND_COMPETITION.getId(),
        SECOND_SPONSOR.getId(),
        SponsorshipTest.NEW_MONEY_CONTRIBUTION
    );

    private final Validator<Sponsorship> sponsorshipValidator = new SponsorshipValidator();
    private Repository<Long, Sponsorship> sponsorshipRepository;
    private SponsorshipService sponsorshipService;

    @Before
    public void setUp() {
        sponsorshipRepository = new InMemoryRepository<>(sponsorshipValidator);
        sponsorshipService = new SponsorshipService(sponsorshipRepository);
    }

    @After
    public void tearDown() {
        sponsorshipRepository = null;
        sponsorshipService = null;
    }

    @Test
    public void testGetRepository() {
        assertEquals("The repositories should be equal", sponsorshipRepository, sponsorshipService.getRepository());
    }

    @Test
    public void testAddSponsorship() {
        sponsorshipService.addSponsorship(FIRST_SPONSORSHIP);

        Optional<Sponsorship> addedSponsorship = sponsorshipRepository.findOne(FIRST_SPONSORSHIP.getId());
        assertTrue("The added sponsorship should be present in the repository", addedSponsorship.isPresent());

        assertEquals(
            "The added sponsorship should have the corresponding specified fields",
            FIRST_SPONSORSHIP,
            addedSponsorship.get()
        );
    }

    @Test
    public void testGetAllSponsorships() {
        sponsorshipService.addSponsorship(FIRST_SPONSORSHIP);
        sponsorshipService.addSponsorship(SECOND_SPONSORSHIP);

        assertEquals(
            "All added sponsorships should be present in the repository",
            Set.of(FIRST_SPONSORSHIP, SECOND_SPONSORSHIP),
            sponsorshipService.getAllSponsorships()
        );
    }

    @Test
    public void testGetSponsorshipById() {
        sponsorshipService.addSponsorship(FIRST_SPONSORSHIP);

        Optional<Sponsorship> addedSponsorship = sponsorshipService.getSponsorshipById(FIRST_SPONSORSHIP.getId());
        assertTrue("The added sponsorship should be present in the repository", addedSponsorship.isPresent());

        assertEquals(
            "The added sponsorship should have the corresponding specified fields",
            FIRST_SPONSORSHIP,
            addedSponsorship.get()
        );
    }

    @Test
    public void testUpdateSponsorship() {
        sponsorshipService.addSponsorship(FIRST_SPONSORSHIP);
        sponsorshipService.updateSponsorship(THIRD_SPONSORSHIP);

        Optional<Sponsorship> updatedSponsorship = sponsorshipRepository.findOne(FIRST_SPONSORSHIP.getId());
        assertTrue("The updated sponsorship should be present in the repository", updatedSponsorship.isPresent());

        assertEquals(
            "The updated sponsorship should have the corresponding specified fields",
            THIRD_SPONSORSHIP,
            updatedSponsorship.get()
        );
    }

    @Test
    public void testDeleteSponsorship() {
        sponsorshipService.addSponsorship(FIRST_SPONSORSHIP);
        sponsorshipService.deleteSponsorship(FIRST_SPONSORSHIP.getId());

        Optional<Sponsorship> deletedSponsorship = sponsorshipRepository.findOne(FIRST_SPONSORSHIP.getId());
        assertFalse("The deleted sponsorship should not be present in the repository", deletedSponsorship.isPresent());
    }

    @Test
    public void testFilterSponsorshipsByFirstName() {
        sponsorshipService.addSponsorship(FIRST_SPONSORSHIP);
        sponsorshipService.addSponsorship(SECOND_SPONSORSHIP);

        assertEquals(
            "The filtered sponsorships should be the expected ones based on the money contribution",
            Set.of(FIRST_SPONSORSHIP),
            sponsorshipService.filterSponsorshipsByPredicate(
                sponsorship -> sponsorship.getMoneyContribution() == FIRST_SPONSORSHIP.getMoneyContribution()
            )
        );
    }

}