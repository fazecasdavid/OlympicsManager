package olympics.repository;

import olympics.domain.AthleteTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.validators.AthleteValidator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.Repository;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class InMemoryRepositoryTest {

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

    private static final Long INVALID_ID = 0L;

    public static final Athlete INVALID_ATHLETE = new Athlete(
        INVALID_ID,
        AthleteTest.FIRST_NAME,
        AthleteTest.LAST_NAME,
        AthleteTest.COUNTRY,
        AthleteTest.AGE
    );

    private final AthleteValidator athleteValidator = new AthleteValidator();
    private Repository<Long, Athlete> repository;

    @Before
    public void setUp() {
        repository = new InMemoryRepository<>(athleteValidator);
    }

    @After
    public void tearDown() {
        repository = null;
    }

    @Test
    public void testFindOneSuccess() {
        repository.save(FIRST_ATHLETE);
        final Optional<Athlete> athleteOptional = repository.findOne(FIRST_ATHLETE.getId());
        assertTrue(athleteOptional.isPresent());
        assertEquals(athleteOptional.get(), FIRST_ATHLETE);
    }

    @Test
    public void testFindOneFailure() {
        final Optional<Athlete> athleteOptional = repository.findOne(FIRST_ATHLETE.getId());
        assertFalse(athleteOptional.isPresent());
    }

    @Test
    public void testFindAll() {
        repository.save(FIRST_ATHLETE);
        repository.save(SECOND_ATHLETE);

        final Set<Athlete> athleteList = new HashSet<>();
        repository.findAll().forEach(athleteList::add);

        assertEquals(Set.of(FIRST_ATHLETE, SECOND_ATHLETE), athleteList);
    }

    @Test
    public void testSave() {
        final List<Athlete> beforeAthleteList = new ArrayList<>();
        repository.findAll().forEach(beforeAthleteList::add);

        assertTrue(beforeAthleteList.isEmpty());

        repository.save(FIRST_ATHLETE);

        final List<Athlete> afterAthleteList = new ArrayList<>();
        repository.findAll().forEach(afterAthleteList::add);

        assertFalse(afterAthleteList.isEmpty());
        assertEquals(Collections.singletonList(FIRST_ATHLETE), afterAthleteList);
    }

    @Test
    public void testSaveException() {
        assertThrows(
            ValidatorException.class,
            () -> repository.save(INVALID_ATHLETE)
        );
    }

    @Test
    public void testDelete() {
        repository.save(FIRST_ATHLETE);

        final List<Athlete> beforeAthleteList = new ArrayList<>();
        repository.findAll().forEach(beforeAthleteList::add);

        assertFalse(beforeAthleteList.isEmpty());
        assertEquals(Collections.singletonList(FIRST_ATHLETE), beforeAthleteList);

        repository.delete(FIRST_ATHLETE.getId());

        final List<Athlete> afterAthleteList = new ArrayList<>();
        repository.findAll().forEach(afterAthleteList::add);

        assertTrue(afterAthleteList.isEmpty());
    }

    @Test
    public void testUpdate() {
        repository.save(FIRST_ATHLETE);
        repository.update(THIRD_ATHLETE);

        final List<Athlete> athleteList = new ArrayList<>();
        repository.findAll().forEach(athleteList::add);

        assertEquals(Collections.singletonList(THIRD_ATHLETE), athleteList);
    }

    @Test
    public void testUpdateException() {
        assertThrows(
            ValidatorException.class,
            () -> repository.update(INVALID_ATHLETE)
        );
    }

}