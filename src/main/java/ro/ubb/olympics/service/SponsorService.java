package ro.ubb.olympics.service;


import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service that handles repository operations with Sponsor objects.
 */
public class SponsorService {

    private final Repository<Long, Sponsor> repository;


    /**
     * Constructor for the Sponsor service
     *
     * @param repository ~ the repository used by the service
     */
    public SponsorService(Repository<Long, Sponsor> repository) {
        this.repository = repository;
    }

    /**
     * Get the current repository
     *
     * @return the repository
     */
    public Repository<Long, Sponsor> getRepository() {
        return repository;
    }

    /**
     * Add an sponsor
     *
     * @param sponsor ~ the new sponsor to be added
     * @return the result of the add operation on the repository
     * @throws ValidatorException if the new sponsor is not valid
     */
    public Optional<Sponsor> addSponsor(final Sponsor sponsor) throws ValidatorException {
        return repository.save(sponsor);
    }

    /**
     * Get all the sponsors
     *
     * @return a set containing all the the sponsors
     */
    public Set<Sponsor> getAllSponsors() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .collect(Collectors.toSet());
    }

    /**
     * Get an sponsor corresponding to a given id
     *
     * @param id ~ the id for which we need the sponsor
     * @return the sponsor we found with that id
     */
    public Optional<Sponsor> getSponsorById(final Long id) {
        return repository.findOne(id);
    }

    /**
     * Update information about an sponsor
     *
     * @param sponsor the updated version of the sponosr
     * @return the result of the update operation from the repository
     */
    public Optional<Sponsor> updateSponsor(final Sponsor sponsor) {
        return repository.update(sponsor);
    }

    /**
     * Delete an sponsor by its id
     *
     * @param id ~ the id of the sponsor we need to delete
     * @return the result of the delete operation from the repository
     */
    public Optional<Sponsor> deleteSponsor(final Long id) {
        return repository.delete(id);
    }


    /**
     * Filter all sponsors by filter predicate
     *
     * @param filter ~ the sponsors filtered by the predicate
     * @return the filtered sponsors
     */
    public Set<Sponsor> filterSponsorsByPredicate(final Predicate<Sponsor> filter) {
        return StreamSupport
            .stream(repository.findAll().spliterator(), false)
            .filter(filter)
            .collect(Collectors.toSet());
    }

}
