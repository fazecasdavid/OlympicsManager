package ro.ubb.olympics;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.Sponsorship;
import ro.ubb.olympics.properties.ApplicationProperties;
import ro.ubb.olympics.repository.Repository;
import ro.ubb.olympics.service.AthleteService;
import ro.ubb.olympics.service.CompetitionService;
import ro.ubb.olympics.service.FilterService;
import ro.ubb.olympics.service.ParticipationService;
import ro.ubb.olympics.service.ReportService;
import ro.ubb.olympics.service.SponsorService;
import ro.ubb.olympics.service.SponsorshipService;
import ro.ubb.olympics.ui.Console;

/**
 * Main class representing the entry point in the application.
 */

public class Main {

    /**
     * Entry point in the application.
     * <p>
     * Validators, repositories, services are instantiated.
     * A new controller is created using all of the above,
     * and a new Console is instantiated, which then starts waiting for user input.
     *
     * @param args command-line arguments for the program, currently unused.
     */
    public static void main(final String[] args) {
        final ApplicationProperties applicationProperties = new ApplicationProperties("C:\\Users\\sdumi\\OneDrive\\Desktop\\InfoYear2\\Lab2_4Git\\lab2x-team-gamestonk\\data\\app.properties");

        final Repository<Long, Athlete> athleteRepository = applicationProperties.getAthleteRepository();
        final AthleteService athleteService = new AthleteService(athleteRepository);

        final Repository<Long, Competition> competitionRepository = applicationProperties.getCompetitionRepository();
        final CompetitionService competitionService = new CompetitionService(competitionRepository);

        final Repository<Long, Participation> participationRepository = applicationProperties.getParticipationRepository();
        final ParticipationService participationService = new ParticipationService(participationRepository);

        final Repository<Long, Sponsor> sponsorRepository = applicationProperties.getSponsorRepository();
        final SponsorService sponsorService = new SponsorService(sponsorRepository);

        final Repository<Long, Sponsorship> sponsorshipRepository = applicationProperties.getSponsorshipRepository();
        final SponsorshipService sponsorshipService = new SponsorshipService(sponsorshipRepository);

        final FilterService filterService = new FilterService(athleteService, competitionService, participationService, sponsorService, sponsorshipService);
        final ReportService reportService = new ReportService(sponsorshipRepository, sponsorRepository, competitionRepository, participationRepository, athleteRepository);

        final Controller controller = new Controller(athleteService, competitionService, participationService, sponsorService, sponsorshipService, reportService, filterService);

        final Console console = new Console(controller);
        console.run();
    }

    // TODO: prepare for Lab 5 (Alexandru)
    // TODO: refactor code for Lab 5 (Filip Rares)
    //TODO: understand the requirements and do my part for lab 5 ~ Sabina

}