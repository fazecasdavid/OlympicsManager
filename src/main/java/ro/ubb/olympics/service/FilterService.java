package ro.ubb.olympics.service;

import lombok.AllArgsConstructor;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.Sponsorship;

import java.util.Set;

/**
 * Service used for reports.
 */
@AllArgsConstructor
public class FilterService {

    private final AthleteService athleteService;
    private final CompetitionService competitionService;
    private final ParticipationService participationService;
    private final SponsorService sponsorService;
    private final SponsorshipService sponsorshipService;

    /**
     * Filter the athletes by their first name.
     *
     * @param firstName the name used as a filter
     * @return a set containing the required athletes.
     */
    public Set<Athlete> filterAthletesByFirstName(final String firstName) {
        return athleteService.filterAthletesByPredicate(
            athlete -> athlete.getFirstName().equals(firstName)
        );
    }

    /**
     * Filter the athletes by their provenience country.
     *
     * @param country the country used as a filter
     * @return a set containing the required athletes.
     */
    public Set<Athlete> filterAthletesByCountry(final String country) {
        return athleteService.filterAthletesByPredicate(
            athlete -> athlete.getCountry().equals(country)
        );
    }

    /**
     * Filter the competitions by their location.
     *
     * @param location the location used as a filter
     * @return a set containing the required competitions.
     */
    public Set<Competition> filterCompetitionsByLocation(final String location) {
        return competitionService.filterCompetitionsByPredicate(
            competition -> competition.getLocation().equals(location)
        );
    }

    /**
     * Filter the participations by the rank of the athlete.
     *
     * @param rank the rank used as a filter
     * @return a set containing the required participations.
     */
    public Set<Participation> filterParticipationsByRank(final int rank) {
        return participationService.filterParticipationsByPredicate(
            participation -> participation.getRank() == rank
        );
    }

    /**
     * Filter the participations by their competition ID.
     *
     * @param competitionId the competition ID used as a filter
     * @return a set containing the required participations.
     */
    public Set<Participation> filterParticipationsByCompetitionId(final Long competitionId) {
        return participationService.filterParticipationsByPredicate(
            participation -> participation.getCompetitionId().equals(competitionId)
        );
    }

    /**
     * Filter the sponsors by their provenience country.
     *
     * @param country the country used as a filter
     * @return a set containing the required sponsors.
     */
    public Set<Sponsor> filterSponsorsByCountry(final String country) {
        return sponsorService.filterSponsorsByPredicate(
            sponsor -> sponsor.getCountry().equals(country)
        );
    }

    /**
     * Filter the sponsorships by the amount of money contribution greater than or equal to a given value.
     *
     * @param moneyContribution the money contributed used for the filter
     * @return a set containing the required sponsorships.
     */
    public Set<Sponsorship> filterSponsorshipsByMoneyContributionGreaterOrEqual(final int moneyContribution) {
        return sponsorshipService.filterSponsorshipsByPredicate(
            sponsorship -> sponsorship.getMoneyContribution() >= moneyContribution
        );
    }

    /**
     * Filter the sponsorships by the ID of a sponsor.
     *
     * @param sponsorId the ID of the sponsor.
     * @return a set containing the required sponsorships.
     */
    public Set<Sponsorship> filterSponsorshipsBySponsorId(final Long sponsorId) {
        return sponsorshipService.filterSponsorshipsByPredicate(
            sponsorship -> sponsorship.getSponsorId().equals(sponsorId)
        );
    }

}