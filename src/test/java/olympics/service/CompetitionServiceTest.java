package olympics.service;

import olympics.domain.CompetitionTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.validators.CompetitionValidator;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.repository.Repository;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;
import ro.ubb.olympics.service.CompetitionService;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompetitionServiceTest {

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

    public static final Competition THIRD_COMPETITION = new Competition(
        CompetitionTest.ID,
        CompetitionTest.NEW_DATE,
        CompetitionTest.NEW_LOCATION,
        CompetitionTest.NEW_NAME,
        CompetitionTest.NEW_DESCRIPTION
    );

    private final Validator<Competition> competitionValidator = new CompetitionValidator();
    private Repository<Long, Competition> competitionRepository;
    private CompetitionService competitionService;

    @Before
    public void setUp() {
        competitionRepository = new InMemoryRepository<>(competitionValidator);
        competitionService = new CompetitionService(competitionRepository);
    }

    @After
    public void tearDown() {
        competitionRepository = null;
        competitionService = null;
    }

    @Test
    public void testGetRepository() {
        assertEquals("The repositories should be equal", competitionRepository, competitionService.getRepository());
    }

    @Test
    public void testAddCompetition() {
        competitionService.addCompetition(FIRST_COMPETITION);

        Optional<Competition> addedCompetition = competitionRepository.findOne(FIRST_COMPETITION.getId());
        assertTrue("The added competition should be present in the repository", addedCompetition.isPresent());

        assertEquals(
            "The added competition should have the corresponding specified fields",
            FIRST_COMPETITION,
            addedCompetition.get()
        );
    }

    @Test
    public void testGetAllCompetitions() {
        competitionService.addCompetition(FIRST_COMPETITION);
        competitionService.addCompetition(SECOND_COMPETITION);

        assertEquals(
            "All added competitions should be present in the repository",
            Set.of(FIRST_COMPETITION, SECOND_COMPETITION),
            competitionService.getAllCompetitions()
        );
    }

    @Test
    public void testGetCompetitionById() {
        competitionService.addCompetition(FIRST_COMPETITION);

        Optional<Competition> addedCompetition = competitionService.getCompetitionById(FIRST_COMPETITION.getId());
        assertTrue("The added competition should be present in the repository", addedCompetition.isPresent());

        assertEquals(
            "The added competition should have the corresponding specified fields",
            FIRST_COMPETITION,
            addedCompetition.get()
        );
    }

    @Test
    public void testUpdateCompetition() {
        competitionService.addCompetition(FIRST_COMPETITION);
        competitionService.updateCompetition(THIRD_COMPETITION);

        Optional<Competition> updatedCompetition = competitionRepository.findOne(FIRST_COMPETITION.getId());
        assertTrue("The updated competition should be present in the repository", updatedCompetition.isPresent());

        assertEquals(
            "The updated competition should have the corresponding specified fields",
            THIRD_COMPETITION,
            updatedCompetition.get()
        );
    }

    @Test
    public void testDeleteCompetition() {
        competitionService.addCompetition(FIRST_COMPETITION);
        competitionService.deleteCompetition(FIRST_COMPETITION.getId());

        Optional<Competition> deletedCompetition = competitionRepository.findOne(FIRST_COMPETITION.getId());
        assertFalse("The deleted competition should not be present in the repository", deletedCompetition.isPresent());
    }

    @Test
    public void testFilterCompetitionsByFirstName() {
        competitionService.addCompetition(FIRST_COMPETITION);
        competitionService.addCompetition(SECOND_COMPETITION);

        assertEquals(
            "The filtered competitions should be the expected ones based on name",
            Set.of(FIRST_COMPETITION),
            competitionService.filterCompetitionsByPredicate(
                competition -> competition.getName().equals(FIRST_COMPETITION.getName())
            )
        );
    }

}