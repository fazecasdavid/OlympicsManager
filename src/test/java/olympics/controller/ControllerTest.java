package olympics.controller;

import olympics.domain.AthleteTest;
import olympics.domain.CompetitionTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.Sponsorship;
import ro.ubb.olympics.domain.validators.AthleteValidator;
import ro.ubb.olympics.domain.validators.CompetitionValidator;
import ro.ubb.olympics.domain.validators.ParticipationValidator;
import ro.ubb.olympics.domain.validators.SponsorValidator;
import ro.ubb.olympics.domain.validators.SponsorshipValidator;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.Repository;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;
import ro.ubb.olympics.service.AthleteService;
import ro.ubb.olympics.service.CompetitionService;
import ro.ubb.olympics.service.FilterService;
import ro.ubb.olympics.service.ParticipationService;
import ro.ubb.olympics.service.ReportService;
import ro.ubb.olympics.service.SponsorService;
import ro.ubb.olympics.service.SponsorshipService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ControllerTest {

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 0L;

    private static final int VALID_RANK = 1;
    private static final int INVALID_RANK = 0;
    private static final int NEW_VALID_RANK = 2;

    private static final Long INVALID_ATHLETE_ID = 0L;
    private static final Long VALID_ATHLETE_ID = AthleteTest.ID;

    public static final Athlete ATHLETE = new Athlete(
        VALID_ATHLETE_ID,
        AthleteTest.FIRST_NAME,
        AthleteTest.LAST_NAME,
        AthleteTest.COUNTRY,
        AthleteTest.AGE
    );

    private static final Long INVALID_COMPETITION_ID = 0L;
    private static final Long VALID_COMPETITION_ID = CompetitionTest.ID;

    public static final Competition COMPETITION = new Competition(
        VALID_COMPETITION_ID,
        CompetitionTest.DATE,
        CompetitionTest.LOCATION,
        CompetitionTest.NAME,
        CompetitionTest.DESCRIPTION
    );

    private final Validator<Athlete> athleteValidator = new AthleteValidator();
    private final Validator<Competition> competitionValidator = new CompetitionValidator();
    private final Validator<Participation> participationValidator = new ParticipationValidator();
    private final Validator<Sponsor> sponsorValidator = new SponsorValidator();
    private final Validator<Sponsorship> sponsorshipValidator = new SponsorshipValidator();

    private Repository<Long, Athlete> athleteRepository;
    private Repository<Long, Competition> competitionRepository;
    private Repository<Long, Participation> participationRepository;
    private Repository<Long, Sponsor> sponsorRepository;
    private Repository<Long, Sponsorship> sponsorshipRepository;

    private AthleteService athleteService;
    private CompetitionService competitionService;
    private ParticipationService participationService;
    private SponsorService sponsorService;
    private SponsorshipService sponsorshipService;
    private ReportService reportService;
    private FilterService filterService;

    private Controller controller;

    @Before
    public void setUp() {
        athleteRepository = new InMemoryRepository<>(athleteValidator);
        competitionRepository = new InMemoryRepository<>(competitionValidator);
        participationRepository = new InMemoryRepository<>(participationValidator);
        sponsorRepository = new InMemoryRepository<>(sponsorValidator);
        sponsorshipRepository = new InMemoryRepository<>(sponsorshipValidator);

        athleteService = new AthleteService(athleteRepository);
        competitionService = new CompetitionService(competitionRepository);
        participationService = new ParticipationService(participationRepository);
        sponsorService = new SponsorService(sponsorRepository);
        sponsorshipService = new SponsorshipService(sponsorshipRepository);

        reportService = new ReportService(sponsorshipRepository, sponsorRepository, competitionRepository, participationRepository, athleteRepository);

        filterService = new FilterService(athleteService, competitionService, participationService, sponsorService, sponsorshipService);

        controller = new Controller(athleteService, competitionService, participationService, sponsorService, sponsorshipService, reportService, filterService);

        athleteRepository.save(ATHLETE);
        competitionRepository.save(COMPETITION);
    }

    @After
    public void tearDown() {
        athleteRepository = null;
        competitionRepository = null;
        participationRepository = null;

        athleteService = null;
        competitionService = null;
        participationService = null;

        controller = null;
    }

    @Test
    public void testGetAthleteService() {
        assertEquals("The athlete services should be equal", athleteService, controller.getAthleteService());
    }

    @Test
    public void testGetCompetitionService() {
        assertEquals("The competition services should be equal", competitionService, controller.getCompetitionService());
    }

    @Test
    public void testGetParticipationService() {
        assertEquals("The participation services should be equal", participationService, controller.getParticipationService());
    }

    @Test
    public void testAddParticipationNonExistentAthleteID() {
        assertThrows(
            "An exception should be signaled when trying to add a participation with a non-existent athlete ID",
            RuntimeException.class,
            () -> controller.addParticipation(VALID_ID, INVALID_ATHLETE_ID, VALID_COMPETITION_ID, VALID_RANK)
        );
    }

    @Test
    public void testAddParticipationNonExistentCompetitionID() {
        assertThrows(
            "An exception should be signaled when trying to add a participation with a non-existent competition ID",
            RuntimeException.class,
            () -> controller.addParticipation(VALID_ID, VALID_ATHLETE_ID, INVALID_COMPETITION_ID, VALID_RANK)
        );
    }

    @Test
    public void testAddParticipationInvalidParticipationFields() {
        assertThrows(
            "An exception should be signaled when trying to add a participation with invalid ID or rank",
            ValidatorException.class,
            () -> controller.addParticipation(INVALID_ID, VALID_ATHLETE_ID, VALID_COMPETITION_ID, INVALID_RANK)
        );
    }

    @Test
    public void testAddParticipationSuccessful() {
        controller.addParticipation(VALID_ID, VALID_ATHLETE_ID, VALID_COMPETITION_ID, VALID_RANK);

        Optional<Participation> addedParticipation = participationRepository.findOne(VALID_ID);
        assertTrue("The added participation should be present in the repository", addedParticipation.isPresent());

        assertEquals(
            "The added participation should have the corresponding specified fields",
            addedParticipation.get(),
            new Participation(
                VALID_ID,
                VALID_ATHLETE_ID,
                VALID_COMPETITION_ID,
                VALID_RANK
            )
        );
    }

    @Test
    public void testUpdateParticipationNonExistentAthleteID() {
        assertThrows(
            "An exception should be signaled when trying to update a participation with a non-existent athlete ID",
            RuntimeException.class,
            () -> controller.updateParticipation(VALID_ID, INVALID_ATHLETE_ID, VALID_COMPETITION_ID, VALID_RANK)
        );
    }

    @Test
    public void testUpdateParticipationNonExistentCompetitionID() {
        assertThrows(
            "An exception should be signaled when trying to update a participation with a non-existent competition ID",
            RuntimeException.class,
            () -> controller.updateParticipation(VALID_ID, VALID_ATHLETE_ID, INVALID_COMPETITION_ID, VALID_RANK)
        );
    }

    @Test
    public void testUpdateParticipationInvalidParticipationFields() {
        assertThrows(
            "An exception should be signaled when trying to update a participation with invalid ID or rank",
            ValidatorException.class,
            () -> controller.updateParticipation(INVALID_ID, VALID_ATHLETE_ID, VALID_COMPETITION_ID, INVALID_RANK)
        );
    }

    @Test
    public void testUpdateParticipationSuccessful() {
        controller.addParticipation(VALID_ID, VALID_ATHLETE_ID, VALID_COMPETITION_ID, VALID_RANK);

        controller.updateParticipation(VALID_ID, VALID_ATHLETE_ID, VALID_COMPETITION_ID, NEW_VALID_RANK);

        Optional<Participation> updatedParticipation = participationRepository.findOne(VALID_ID);
        assertTrue("The updated participation should be present in the repository", updatedParticipation.isPresent());

        assertEquals(
            "The updated participation should have the corresponding specified fields",
            updatedParticipation.get(),
            new Participation(
                VALID_ID,
                VALID_ATHLETE_ID,
                VALID_COMPETITION_ID,
                NEW_VALID_RANK
            )
        );
    }

}