package ro.ubb.olympics.service;

import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service that handles repository operations with Athlete objects.
 */
public class AthleteService {

    private final Repository<Long, Athlete> repository;

    /**
     * Constructor for the Athlete service
     *
     * @param repository ~ the repository used by the service
     */
    public AthleteService(final Repository<Long, Athlete> repository) {
        this.repository = repository;
    }

    /**
     * Get the current repository
     *
     * @return the repository
     */
    public Repository<Long, Athlete> getRepository() {
        return repository;
    }

    /**
     * Add an athlete
     *
     * @param athlete ~ the new athlete to be added
     * @return the result of the add operation on the repository
     * @throws ValidatorException if the new athlete is not valid
     */
    public Optional<Athlete> addAthlete(final Athlete athlete) throws ValidatorException {
        return repository.save(athlete);
    }

    /**
     * Get all the athletes
     *
     * @return a set containing all the the athletes
     */
    public Set<Athlete> getAllAthletes() {
        return StreamSupport
            .stream(repository.findAll().spliterator(), false)
            .collect(Collectors.toSet());
    }

    /**
     * Get an athlete corresponding to a given id
     *
     * @param id ~ the id for which we need the athlete
     * @return the athlete we found with that id
     */
    public Optional<Athlete> getAthleteById(final Long id) {
        return repository.findOne(id);
    }

    /**
     * Update information about an athlete
     *
     * @param athlete the updated version of the athlete
     * @return the result of the update operation from the repository
     */
    public Optional<Athlete> updateAthlete(final Athlete athlete) {
        return repository.update(athlete);
    }

    /**
     * Delete an athlete by its id
     *
     * @param id ~ the id of the athlete we need to delete
     * @return the result of the delete operation from the repository
     */
    public Optional<Athlete> deleteAthlete(final Long id) {
        return repository.delete(id);
    }

    /**
     * Filter all athletes by filter predicate
     *
     * @param filter ~ the filtering predicate
     * @return the filtered athletes
     */
    public Set<Athlete> filterAthletesByPredicate(final Predicate<Athlete> filter) {
        return StreamSupport
            .stream(repository.findAll().spliterator(), false)
            .filter(filter)
            .collect(Collectors.toSet());
    }

}