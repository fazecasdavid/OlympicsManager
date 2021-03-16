package ro.ubb.olympics.service;

import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service that handles repository operations with Competition objects.
 */
public class CompetitionService {

    private final Repository<Long, Competition> repository;

    /**
     * Constructor for the CompetitionService
     *
     * @param repository - the repository used by the service
     */
    public CompetitionService(final Repository<Long, Competition> repository) {
        this.repository = repository;
    }

    /**
     * Get the current repository
     *
     * @return the repository
     */
    public Repository<Long, Competition> getRepository() {
        return repository;
    }

    /**
     * Add a Competition
     *
     * @param competition - the competition to be added
     * @return the result of the add operation on the repository
     * @throws ValidatorException - if the validation is not passed
     */
    public Optional<Competition> addCompetition(final Competition competition) throws ValidatorException {
        return repository.save(competition);
    }

    /**
     * Get all the competitions
     *
     * @return a set containing all the the competitions
     */
    public Set<Competition> getAllCompetitions() {
        return StreamSupport
            .stream(repository.findAll().spliterator(), false)
            .collect(Collectors.toSet());
    }

    /**
     * Get the competition corresponding to a given id
     *
     * @param id - the id for which we new the competition
     * @return the competition we found with that id
     */
    public Optional<Competition> getCompetitionById(final Long id) {
        return repository.findOne(id);
    }

    /**
     * Update information about an competition
     *
     * @param competition new competition
     * @return the result of the update operation from the repository
     */
    public Optional<Competition> updateCompetition(final Competition competition) {
        return repository.update(competition);
    }

    /**
     * Delete a competition by its id
     *
     * @param id ~ the id of the competition we need to delete
     * @return the result of the delete operation from the repository
     */
    public Optional<Competition> deleteCompetition(final Long id) {
        return repository.delete(id);
    }


    /**
     * Filter all competitions by filter predicate
     *
     * @param filter ~ the filtering predicate
     * @return the filtered competitions
     */
    public Set<Competition> filterCompetitionsByPredicate(final Predicate<Competition> filter) {
        return StreamSupport
            .stream(repository.findAll().spliterator(), false)
            .filter(filter)
            .collect(Collectors.toSet());
    }

}