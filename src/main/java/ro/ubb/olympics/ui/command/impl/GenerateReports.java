package ro.ubb.olympics.ui.command.impl;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.ui.command.impl.reports.AthleteParticipationReport;
import ro.ubb.olympics.ui.command.impl.reports.CompetitionParticipationReport;
import ro.ubb.olympics.ui.command.impl.reports.CompetitionSponsorshipsReport;
import ro.ubb.olympics.ui.command.impl.reports.SponsorContributionsReport;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Submenu for report generation.
 */
public class GenerateReports extends Command {

    private final Controller controller;
    private final Scanner scanner;

    private final Map<String, Command> commands;

    /**
     * Initializes the command.
     *
     * @param key         the key of the command
     * @param description the command's description
     * @param controller  the controller used in the operations
     * @param scanner     the scanner which provides user input
     */
    public GenerateReports(final String key, final String description, final Controller controller, final Scanner scanner) {
        super(key, description);
        this.controller = controller;
        this.scanner = scanner;
        this.commands = new HashMap<>();
        this.initializeCommands();
    }

    /**
     * Initializes the commands available to the user.
     */
    private void initializeCommands() {
        ConsoleUtils.addCommand(new SponsorContributionsReport("1", "Generate sponsor contributions report.", controller), commands);
        ConsoleUtils.addCommand(new AthleteParticipationReport("2", "Generate athlete participations report.", controller), commands);
        ConsoleUtils.addCommand(new CompetitionParticipationReport("3", "Generate competition participations report.", controller), commands);
        ConsoleUtils.addCommand(new CompetitionSponsorshipsReport("4", "Generate competition sponsorships report.", controller), commands);
    }

    @Override
    public void execute() {
        ConsoleUtils.runMenu(scanner, commands);
    }

}