package ro.ubb.olympics.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.Sponsorship;
import ro.ubb.olympics.dto.AthleteParticipationReportDTO;
import ro.ubb.olympics.dto.CompetitionParticipationReportDTO;
import ro.ubb.olympics.dto.CompetitionSponsorshipsReportDTO;
import ro.ubb.olympics.dto.SponsorContributionReportDTO;
import ro.ubb.olympics.repository.Repository;
import ro.ubb.olympics.utils.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service used for reports.
 */
@Getter
@AllArgsConstructor
public class ReportService {

    private final Repository<Long, Sponsorship> sponsorshipRepository;
    private final Repository<Long, Sponsor> sponsorRepository;
    private final Repository<Long, Competition> competitionRepository;
    private final Repository<Long, Participation> participationRepository;
    private final Repository<Long, Athlete> athleteRepository;

    /**
     * Generate a report containing the amount of money contributed by each sponsor,
     * sorted decreasingly by the amount of money contributed.
     *
     * @return the required report
     */
    public SponsorContributionReportDTO generateSponsorContributionsReport() {
        final Map<Long, Integer> sponsorContributions = new HashMap<>();

        sponsorRepository
            .findAll()
            .forEach(sponsor -> sponsorContributions.putIfAbsent(sponsor.getId(), 0));

        sponsorshipRepository
            .findAll()
            .forEach(sponsorship -> sponsorContributions.put(
                sponsorship.getSponsorId(),
                sponsorContributions.get(sponsorship.getSponsorId()) + sponsorship.getMoneyContribution()
            ));

        final List<Pair<Sponsor, Integer>> sponsorContributionsPairs =
            sponsorContributions
                .entrySet()
                .stream()
                .map(entry -> new Pair<>(sponsorRepository.findOne(entry.getKey()).orElseThrow(), entry.getValue()))
                .sorted((a, b) -> b.getSecond().compareTo(a.getSecond()))
                .collect(Collectors.toList());

        return new SponsorContributionReportDTO(sponsorContributionsPairs);
    }

    /**
     * Generate a report containing the amount of sponsorships for each competition,
     * sorted decreasingly by the amount of sponsorships.
     *
     * @return the required report
     */
    public CompetitionSponsorshipsReportDTO generateCompetitionSponsorshipsReport() {
        final Map<Long, Integer> competitionSponsorships = new HashMap<>();

        competitionRepository
            .findAll()
            .forEach(competition -> competitionSponsorships.putIfAbsent(competition.getId(), 0));

        sponsorshipRepository
            .findAll()
            .forEach(sponsorship -> competitionSponsorships.put(
                sponsorship.getCompetitionId(),
                competitionSponsorships.get(sponsorship.getCompetitionId()) + 1
            ));

        final List<Pair<Competition, Integer>> competitionSponsorshipsPairs =
            competitionSponsorships
                .entrySet()
                .stream()
                .map(entry -> new Pair<>(competitionRepository.findOne(entry.getKey()).orElseThrow(), entry.getValue()))
                .sorted((a, b) -> b.getSecond().compareTo(a.getSecond()))
                .collect(Collectors.toList());

        return new CompetitionSponsorshipsReportDTO(competitionSponsorshipsPairs);
    }

    /**
     * Generate a report containing the number of participations for each athlete,
     * sorted decreasingly by number of participations.
     *
     * @return the required report
     */
    public AthleteParticipationReportDTO generateAthleteParticipationReport() {
        final Map<Long, Integer> athleteParticipation = new HashMap<>();

        athleteRepository
            .findAll()
            .forEach(athlete -> athleteParticipation.putIfAbsent(athlete.getId(), 0));

        participationRepository
            .findAll()
            .forEach(participation -> athleteParticipation.put(
                participation.getAthleteId(),
                athleteParticipation.get(participation.getAthleteId()) + 1
            ));

        final List<Pair<Athlete, Integer>> athleteParticipationPairs =
            athleteParticipation
                .entrySet()
                .stream()
                .map(entry -> new Pair<>(athleteRepository.findOne(entry.getKey()).orElseThrow(), entry.getValue()))
                .sorted((a, b) -> b.getSecond().compareTo(a.getSecond()))
                .collect(Collectors.toList());

        return new AthleteParticipationReportDTO(athleteParticipationPairs);
    }

    /**
     * Generate a report containing the number of athletes for each competition,
     * sorted decreasingly by the number of athletes.
     *
     * @return the required report
     */
    public CompetitionParticipationReportDTO generateCompetitionParticipationReport() {
        final Map<Long, Integer> competitionParticipation = new HashMap<>();

        competitionRepository
            .findAll()
            .forEach(competition -> competitionParticipation.putIfAbsent(competition.getId(), 0));

        participationRepository
            .findAll()
            .forEach(participation -> competitionParticipation.put(
                participation.getCompetitionId(),
                competitionParticipation.get(participation.getCompetitionId()) + 1
            ));

        final List<Pair<Competition, Integer>> competitionParticipationPairs =
            competitionParticipation
                .entrySet()
                .stream()
                .map(entry -> new Pair<>(competitionRepository.findOne(entry.getKey()).orElseThrow(), entry.getValue()))
                .sorted((a, b) -> b.getSecond().compareTo(a.getSecond()))
                .collect(Collectors.toList());

        return new CompetitionParticipationReportDTO(competitionParticipationPairs);
    }

}