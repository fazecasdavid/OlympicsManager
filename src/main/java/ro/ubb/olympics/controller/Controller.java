package ro.ubb.olympics.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.Sponsorship;
import ro.ubb.olympics.exception.UnknownIdException;
import ro.ubb.olympics.service.AthleteService;
import ro.ubb.olympics.service.CompetitionService;
import ro.ubb.olympics.service.FilterService;
import ro.ubb.olympics.service.ParticipationService;
import ro.ubb.olympics.service.ReportService;
import ro.ubb.olympics.service.SponsorService;
import ro.ubb.olympics.service.SponsorshipService;

import java.util.Optional;

/**
 * The controller handling all of the business logic in the application.
 */
@Getter
@AllArgsConstructor
public class Controller {

    private final AthleteService athleteService;
    private final CompetitionService competitionService;
    private final ParticipationService participationService;
    private final SponsorService sponsorService;
    private final SponsorshipService sponsorshipService;
    private final ReportService reportService;
    private final FilterService filterService;

    /**
     * Add a new participation using the corresponding service.
     *
     * @param id            the ID of the participation
     * @param athleteId     the ID of an athlete
     * @param competitionId the ID of a competition
     * @param rank          the rank of the athlete in the competition
     * @return the result of the add operation from the service.
     * @throws UnknownIdException if there is no athlete or competition with the specified IDs.
     */
    public Optional<Participation> addParticipation(final Long id, final Long athleteId, final Long competitionId, final int rank) {
        athleteService
            .getAthleteById(athleteId)
            .orElseThrow(() -> new UnknownIdException("There is no athlete with the given ID."));

        competitionService
            .getCompetitionById(competitionId)
            .orElseThrow(() -> new UnknownIdException("There is no competition with the given ID."));

        return participationService
            .addParticipation(
                new Participation(
                    id,
                    athleteId,
                    competitionId,
                    rank
                )
            );
    }

    /**
     * Updates a participation using the corresponding service.
     *
     * @param id            the ID of the participation
     * @param athleteId     the ID of an athlete
     * @param competitionId the ID of a competition
     * @param rank          the rank of the athlete in the competition
     * @return the result of the update operation from the service.
     * @throws UnknownIdException if there is no athlete or competition with the specified IDs.
     */
    public Optional<Participation> updateParticipation(final Long id, final Long athleteId, final Long competitionId, final int rank) {
        athleteService
            .getAthleteById(athleteId)
            .orElseThrow(() -> new UnknownIdException("There is no athlete with the given ID."));

        competitionService
            .getCompetitionById(competitionId)
            .orElseThrow(() -> new UnknownIdException("There is no competition with the given ID."));

        return participationService
            .updateParticipation(
                new Participation(
                    id,
                    athleteId,
                    competitionId,
                    rank
                )
            );
    }

    /**
     * Adds a sponsorship using the corresponding service.
     *
     * @param id                the ID of the sponsorship
     * @param competitionId     the ID of the competition
     * @param sponsorId         the ID of the sponsor
     * @param moneyContribution the amount of money contributed by the sponsor
     * @return the result of the add operation from the service.
     * @throws UnknownIdException if there is no sponsor or competition with the specified IDs.
     */
    public Optional<Sponsorship> addSponsorship(final Long id, final Long competitionId, final Long sponsorId, final int moneyContribution) {
        competitionService
            .getCompetitionById(competitionId)
            .orElseThrow(() -> new UnknownIdException("There is no competition with the given ID."));

        sponsorService
            .getSponsorById(sponsorId)
            .orElseThrow(() -> new UnknownIdException("There is no sponsor with the given ID"));

        return sponsorshipService
            .addSponsorship(
                new Sponsorship(
                    id,
                    competitionId,
                    sponsorId,
                    moneyContribution
                )
            );
    }

    /**
     * Updates a sponsorship using the corresponding service.
     *
     * @param id                the ID of the sponsorship
     * @param competitionId     the ID of the competition
     * @param sponsorId         the ID of the sponsor
     * @param moneyContribution the amount of money contributed by the sponsor
     * @return the result of the update operation from the service.
     * @throws UnknownIdException if there is no sponsor or competition with the specified IDs.
     */
    public Optional<Sponsorship> updateSponsorship(final Long id, final Long competitionId, final Long sponsorId, final int moneyContribution) {
        competitionService
            .getCompetitionById(competitionId)
            .orElseThrow(() -> new RuntimeException("There is no competition with the given ID."));

        sponsorService
            .getSponsorById(sponsorId)
            .orElseThrow(() -> new RuntimeException("There is no sponsor with the given ID"));

        return sponsorshipService
            .updateSponsorship(
                new Sponsorship(
                    id,
                    competitionId,
                    sponsorId,
                    moneyContribution
                )
            );
    }

    /**
     * Delete an athlete while cascading the delete on the dependent entities (participation).
     *
     * @param id the ID of the athlete to be deleted
     * @return the result of the delete operation from the corresponding service
     * @throws UnknownIdException if there is no athlete with the specified ID.
     */
    public Optional<Athlete> deleteAthlete(final Long id) {
        athleteService
            .getAthleteById(id)
            .orElseThrow(() -> new UnknownIdException("There is no athlete with the given ID."));

        participationService
            .getAllParticipations()
            .forEach(participation -> {
                Optional
                    .of(participation)
                    .filter(currentParticipation -> currentParticipation.getAthleteId().equals(id))
                    .ifPresent(currentParticipation -> participationService.deleteParticipation(currentParticipation.getId()));
            });

        return athleteService.deleteAthlete(id);
    }

    /**
     * Delete a competition while cascading the delete on the dependent entities (participation, sponsorship).
     *
     * @param id the ID of the competition to be deleted
     * @return the result of the delete operation from the corresponding service
     * @throws UnknownIdException if there is no competition with the specified ID.
     */
    public Optional<Competition> deleteCompetition(final Long id) {
        competitionService
            .getCompetitionById(id)
            .orElseThrow(() -> new UnknownIdException("There is no competition with the given ID."));

        participationService
            .getAllParticipations()
            .forEach(participation -> {
                Optional
                    .of(participation)
                    .filter(currentParticipation -> currentParticipation.getCompetitionId().equals(id))
                    .ifPresent(currentParticipation -> participationService.deleteParticipation(currentParticipation.getId()));
            });

        sponsorshipService
            .getAllSponsorships()
            .forEach(sponsorship -> {
                Optional
                    .of(sponsorship)
                    .filter(currentSponsorship -> currentSponsorship.getCompetitionId().equals(id))
                    .ifPresent(currentSponsorship -> sponsorshipService.deleteSponsorship(currentSponsorship.getId()));
            });

        return competitionService.deleteCompetition(id);
    }

    /**
     * Delete a sponsor while cascading the delete on the dependent entities (sponsorship).
     *
     * @param id the ID of the sponsor to be deleted
     * @return the result of the delete operation from the corresponding service
     * @throws UnknownIdException if there is no sponsor with the specified ID.
     */
    public Optional<Sponsor> deleteSponsor(final Long id) {
        sponsorService
            .getSponsorById(id)
            .orElseThrow(() -> new UnknownIdException("There is no sponsor with the given ID."));

        sponsorshipService
            .getAllSponsorships()
            .forEach(sponsorship -> {
                Optional
                    .of(sponsorship)
                    .filter(currentSponsorship -> currentSponsorship.getSponsorId().equals(id))
                    .ifPresent(currentSponsorship -> sponsorshipService.deleteSponsorship(currentSponsorship.getId()));
            });

        return sponsorService.deleteSponsor(id);
    }

}