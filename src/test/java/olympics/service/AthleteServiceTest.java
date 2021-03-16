package olympics.service;

import olympics.domain.AthleteTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.validators.AthleteValidator;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.repository.Repository;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;
import ro.ubb.olympics.service.AthleteService;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AthleteServiceTest {

    public static final Athlete FIRST_ATHLETE = new Athlete(
        AthleteTest.ID,
        AthleteTest.FIRST_NAME,
        AthleteTest.LAST_NAME,
        AthleteTest.COUNTRY,
        AthleteTest.AGE
    );

    public static final Athlete SECOND_ATHLETE = new Athlete(
        AthleteTest.NEW_ID,
        AthleteTest.NEW_FIRST_NAME,
        AthleteTest.NEW_LAST_NAME,
        AthleteTest.NEW_COUNTRY,
        AthleteTest.NEW_AGE
    );

    public static final Athlete THIRD_ATHLETE = new Athlete(
        AthleteTest.ID,
        AthleteTest.NEW_FIRST_NAME,
        AthleteTest.NEW_LAST_NAME,
        AthleteTest.NEW_COUNTRY,
        AthleteTest.NEW_AGE
    );

    private final Validator<Athlete> athleteValidator = new AthleteValidator();
    private Repository<Long, Athlete> athleteRepository;
    private AthleteService athleteService;

    @Before
    public void setUp() {
        athleteRepository = new InMemoryRepository<>(athleteValidator);
        athleteService = new AthleteService(athleteRepository);
    }

    @After
    public void tearDown() {
        athleteRepository = null;
        athleteService = null;
    }

    @Test
    public void testGetRepository() {
        assertEquals("The repositories should be equal", athleteRepository, athleteService.getRepository());
    }

    @Test
    public void testAddAthlete() {
        athleteService.addAthlete(FIRST_ATHLETE);

        Optional<Athlete> addedAthlete = athleteRepository.findOne(FIRST_ATHLETE.getId());
        assertTrue("The added athlete should be present in the repository", addedAthlete.isPresent());

        assertEquals(
            "The added athlete should have the corresponding specified fields",
            FIRST_ATHLETE,
            addedAthlete.get()
        );
    }

    @Test
    public void testGetAllAthletes() {
        athleteService.addAthlete(FIRST_ATHLETE);
        athleteService.addAthlete(SECOND_ATHLETE);

        assertEquals(
            "All added athletes should be present in the repository",
            Set.of(FIRST_ATHLETE, SECOND_ATHLETE),
            athleteService.getAllAthletes()
        );
    }

    @Test
    public void testGetAthleteById() {
        athleteService.addAthlete(FIRST_ATHLETE);

        Optional<Athlete> addedAthlete = athleteService.getAthleteById(FIRST_ATHLETE.getId());
        assertTrue("The added athlete should be present in the repository", addedAthlete.isPresent());

        assertEquals(
            "The added athlete should have the corresponding specified fields",
            FIRST_ATHLETE,
            addedAthlete.get()
        );
    }

    @Test
    public void testUpdateAthlete() {
        athleteService.addAthlete(FIRST_ATHLETE);
        athleteService.updateAthlete(THIRD_ATHLETE);

        Optional<Athlete> updatedAthlete = athleteRepository.findOne(FIRST_ATHLETE.getId());
        assertTrue("The updated athlete should be present in the repository", updatedAthlete.isPresent());

        assertEquals(
            "The updated athlete should have the corresponding specified fields",
            THIRD_ATHLETE,
            updatedAthlete.get()
        );
    }

    @Test
    public void testDeleteAthlete() {
        athleteService.addAthlete(FIRST_ATHLETE);
        athleteService.deleteAthlete(FIRST_ATHLETE.getId());

        Optional<Athlete> deletedAthlete = athleteRepository.findOne(FIRST_ATHLETE.getId());
        assertFalse("The deleted athlete should not be present in the repository", deletedAthlete.isPresent());
    }

    @Test
    public void testFilterAthletesByFirstName() {
        athleteService.addAthlete(FIRST_ATHLETE);
        athleteService.addAthlete(SECOND_ATHLETE);

        assertEquals(
            "The filtered athletes should be the expected ones based on first name",
            Set.of(FIRST_ATHLETE),
            athleteService.filterAthletesByPredicate(
                athlete -> athlete.getFirstName().equals(FIRST_ATHLETE.getFirstName()))
        );
    }

}