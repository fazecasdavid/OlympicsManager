package ro.ubb.olympics.ui;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.ui.command.impl.GenerateReports;
import ro.ubb.olympics.ui.command.impl.ManageAthletes;
import ro.ubb.olympics.ui.command.impl.ManageCompetitions;
import ro.ubb.olympics.ui.command.impl.ManageParticipations;
import ro.ubb.olympics.ui.command.impl.ManageSponsors;
import ro.ubb.olympics.ui.command.impl.ManageSponsorships;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Console which offers interaction to the user via a text menu.
 */
public class Console {

    private final Map<String, Command> commands;
    private final Controller controller;
    private final Scanner scanner;

    /**
     * Initializes a Console.
     *
     * @param controller the controller used by the Console
     */
    public Console(final Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
        this.commands = new HashMap<>();
        this.initializeCommands();
    }

    /**
     * Initializes the commands available to the user.
     */
    private void initializeCommands() {
        ConsoleUtils.addCommand(new ManageAthletes("1", "Manage the athletes.", controller, scanner), commands);
        ConsoleUtils.addCommand(new ManageCompetitions("2", "Manage the competitions.", controller, scanner), commands);
        ConsoleUtils.addCommand(new ManageParticipations("3", "Manage the participations.", controller, scanner), commands);
        ConsoleUtils.addCommand(new ManageSponsors("4", "Manage the sponsors.", controller, scanner), commands);
        ConsoleUtils.addCommand(new ManageSponsorships("5", "Manage the sponsorships.", controller, scanner), commands);
        ConsoleUtils.addCommand(new GenerateReports("6", "Generate reports.", controller, scanner), commands);
    }

    /**
     * Runs the text-based user interface.
     */
    public void run() {
        ConsoleUtils.runMenu(scanner, commands);
        scanner.close();
    }

}