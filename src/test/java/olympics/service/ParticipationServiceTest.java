package olympics.service;

import olympics.domain.AthleteTest;
import olympics.domain.CompetitionTest;
import olympics.domain.ParticipationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.validators.ParticipationValidator;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.repository.Repository;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;
import ro.ubb.olympics.service.ParticipationService;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParticipationServiceTest {

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

    public static final Participation FIRST_PARTICIPATION = new Participation(
        ParticipationTest.ID,
        FIRST_ATHLETE.getId(),
        FIRST_COMPETITION.getId(),
        ParticipationTest.RANK
    );

    public static final Participation SECOND_PARTICIPATION = new Participation(
        ParticipationTest.NEW_ID,
        SECOND_ATHLETE.getId(),
        SECOND_COMPETITION.getId(),
        ParticipationTest.NEW_RANK
    );

    public static final Participation THIRD_PARTICIPATION = new Participation(
        ParticipationTest.ID,
        SECOND_ATHLETE.getId(),
        SECOND_COMPETITION.getId(),
        ParticipationTest.NEW_RANK
    );

    private final Validator<Participation> participationValidator = new ParticipationValidator();
    private Repository<Long, Participation> participationRepository;
    private ParticipationService participationService;

    @Before
    public void setUp() {
        participationRepository = new InMemoryRepository<>(participationValidator);
        participationService = new ParticipationService(participationRepository);
    }

    @After
    public void tearDown() {
        participationRepository = null;
        participationService = null;
    }

    @Test
    public void testGetRepository() {
        assertEquals("The repositories should be equal", participationRepository, participationService.getRepository());
    }

    @Test
    public void testAddParticipation() {
        participationService.addParticipation(FIRST_PARTICIPATION);

        Optional<Participation> addedParticipation = participationRepository.findOne(FIRST_PARTICIPATION.getId());
        assertTrue("The added participation should be present in the repository", addedParticipation.isPresent());

        assertEquals(
            "The added participation should have the corresponding specified fields",
            FIRST_PARTICIPATION,
            addedParticipation.get()
        );
    }

    @Test
    public void testGetAllParticipations() {
        participationService.addParticipation(FIRST_PARTICIPATION);
        participationService.addParticipation(SECOND_PARTICIPATION);

        assertEquals(
            "All added participations should be present in the repository",
            Set.of(FIRST_PARTICIPATION, SECOND_PARTICIPATION),
            participationService.getAllParticipations()
        );
    }

    @Test
    public void testGetParticipationById() {
        participationService.addParticipation(FIRST_PARTICIPATION);

        Optional<Participation> addedParticipation = participationService.getParticipationById(FIRST_PARTICIPATION.getId());
        assertTrue("The added participation should be present in the repository", addedParticipation.isPresent());

        assertEquals(
            "The added participation should have the corresponding specified fields",
            FIRST_PARTICIPATION,
            addedParticipation.get()
        );
    }

    @Test
    public void testUpdateParticipation() {
        participationService.addParticipation(FIRST_PARTICIPATION);
        participationService.updateParticipation(THIRD_PARTICIPATION);

        Optional<Participation> updatedParticipation = participationRepository.findOne(FIRST_PARTICIPATION.getId());
        assertTrue("The updated participation should be present in the repository", updatedParticipation.isPresent());

        assertEquals(
            "The updated participation should have the corresponding specified fields",
            THIRD_PARTICIPATION,
            updatedParticipation.get()
        );
    }

    @Test
    public void testDeleteParticipation() {
        participationService.addParticipation(FIRST_PARTICIPATION);
        participationService.deleteParticipation(FIRST_PARTICIPATION.getId());

        Optional<Participation> deletedParticipation = participationRepository.findOne(FIRST_PARTICIPATION.getId());
        assertFalse("The deleted participation should not be present in the repository", deletedParticipation.isPresent());
    }

    @Test
    public void testFilterParticipationsByFirstName() {
        participationService.addParticipation(FIRST_PARTICIPATION);
        participationService.addParticipation(SECOND_PARTICIPATION);

        assertEquals(
            "The filtered participations should be the expected ones based on the athlete's first name",
            Set.of(FIRST_PARTICIPATION),
            participationService.filterParticipationsByPredicate(
                participation -> participation.getRank() == FIRST_PARTICIPATION.getRank()
            )
        );
    }

}