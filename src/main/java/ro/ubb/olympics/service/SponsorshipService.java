package ro.ubb.olympics.service;


import ro.ubb.olympics.domain.Sponsorship;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service that handles repository operations with Sponsorship objects.
 */
public class SponsorshipService {

    private final Repository<Long, Sponsorship> repository;

    /**
     * Constructor for the Sponsorship service
     *
     * @param repository ~ the repository used by the service
     */
    public SponsorshipService(Repository<Long, Sponsorship> repository) {
        this.repository = repository;
    }

    /**
     * Get the current repository
     *
     * @return the repository
     */
    public Repository<Long, Sponsorship> getRepository() {
        return repository;
    }

    /**
     * Add a sponsorship
     *
     * @param sponsorship ~ the new sponsorship to be added
     * @return the result of the add operation on the repository
     * @throws ValidatorException if the new sponsorship is not valid
     */
    public Optional<Sponsorship> addSponsorship(final Sponsorship sponsorship) throws ValidatorException {
        return repository.save(sponsorship);
    }

    /**
     * Get all the sponsorships
     *
     * @return a set containing all sponsorships
     */
    public Set<Sponsorship> getAllSponsorships() {
        return StreamSupport
            .stream(repository.findAll().spliterator(), false)
            .collect(Collectors.toSet());
    }

    /**
     * Get a sponsorship corresponding to a given id
     *
     * @param id ~ the id for which we need the sponsorship
     * @return the sponsorship we found with that id
     */
    public Optional<Sponsorship> getSponsorshipById(final Long id) {
        return repository.findOne(id);
    }

    /**
     * Update information about a sponsorship
     *
     * @param sponsorship the updated version of the sponsorship
     * @return the result of the update operation from the repository
     */
    public Optional<Sponsorship> updateSponsorship(final Sponsorship sponsorship) {
        return repository.update(sponsorship);
    }

    /**
     * Delete a sponsorship by its id
     *
     * @param id ~ the id of the sponsorship we need to delete
     * @return the result of the delete operation from the repository
     */
    public Optional<Sponsorship> deleteSponsorship(final Long id) {
        return repository.delete(id);
    }


    /**
     * Filter all sponsorships by a filter predicate
     *
     * @param filter ~ the filtered sponsorships
     * @return the filtered sponsorships
     */
    public Set<Sponsorship> filterSponsorshipsByPredicate(final Predicate<Sponsorship> filter) {
        return StreamSupport
            .stream(repository.findAll().spliterator(), false)
            .filter(filter)
            .collect(Collectors.toSet());
    }

}