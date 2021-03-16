package ro.ubb.olympics.service;

import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service that handles repository operations with Participation objects.
 *
 * @author Rares
 */
public class ParticipationService {

    private final Repository<Long, Participation> repository;

    /**
     * Constructor for the service
     *
     * @param repository ~ the repository to be used to store the entities
     */
    public ParticipationService(final Repository<Long, Participation> repository) {
        this.repository = repository;
    }

    /**
     * Getter for the repository
     *
     * @return Repository repository
     */
    public Repository<Long, Participation> getRepository() {
        return repository;
    }

    /**
     * Add a Participation
     *
     * @param participation ~ new Participation to be added
     * @return the result of the add operation on the repository
     * @throws ValidatorException if the Participation is not valid
     */
    public Optional<Participation> addParticipation(final Participation participation) throws ValidatorException {
        return repository.save(participation);
    }

    /**
     * @return all of the participations in the service
     */
    public Set<Participation> getAllParticipations() {
        return StreamSupport
            .stream(repository.findAll().spliterator(), false)
            .collect(Collectors.toSet());
    }

    /**
     * Get the Participation corresponding to a given id
     *
     * @param id ~ id of the participation to be searched for
     * @return the participation with the given id
     */
    public Optional<Participation> getParticipationById(final Long id) {
        return repository.findOne(id);
    }

    /**
     * Update the information of a given participation
     *
     * @param participation ~ with the updated information
     * @return the result of the update operation from the repository
     */
    public Optional<Participation> updateParticipation(final Participation participation) {
        return repository.update(participation);
    }

    /**
     * Delete a participation by its id
     *
     * @param id ~ the id of the participation to be deleted
     * @return the result of the delete operation from the repository
     */
    public Optional<Participation> deleteParticipation(final Long id) {
        return repository.delete(id);
    }


    /**
     * Filter all participations by filter predicate
     *
     * @param filter ~ the filter predicate
     * @return the set of all participations with the given rank
     */
    public Set<Participation> filterParticipationsByPredicate(final Predicate<Participation> filter) {
        return StreamSupport
            .stream(repository.findAll().spliterator(), false)
            .filter(filter)
            .collect(Collectors.toSet());
    }


}