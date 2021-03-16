package ro.ubb.olympics.ui.command.impl.reports;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;

/**
 * Command used to generate a report containing the number of athletes that participated in a given competition.
 */
public class CompetitionParticipationReport extends Command {

    private final Controller controller;

    /**
     * Initializes the command.
     *
     * @param key         the key of the command
     * @param description the command's description
     * @param controller  the controller used in the operations
     */
    public CompetitionParticipationReport(final String key, final String description, final Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        System.out.println();
        System.out.println(
            controller
                .getReportService()
                .generateCompetitionParticipationReport()
        );
    }

}
