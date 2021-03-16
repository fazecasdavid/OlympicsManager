package ro.ubb.olympics.ui.command.impl;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.ui.command.impl.competition.AddCompetition;
import ro.ubb.olympics.ui.command.impl.competition.DeleteCompetition;
import ro.ubb.olympics.ui.command.impl.competition.FindCompetitionById;
import ro.ubb.olympics.ui.command.impl.competition.ManageCompetitionFilters;
import ro.ubb.olympics.ui.command.impl.competition.SeeAllCompetitions;
import ro.ubb.olympics.ui.command.impl.competition.UpdateCompetition;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Submenu that manages competitions.
 */
public class ManageCompetitions extends Command {

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
    public ManageCompetitions(final String key, final String description, final Controller controller, final Scanner scanner) {
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
        ConsoleUtils.addCommand(new AddCompetition("1", "Add a new competition.", controller, scanner), commands);
        ConsoleUtils.addCommand(new DeleteCompetition("2", "Delete a competition.", controller, scanner), commands);
        ConsoleUtils.addCommand(new UpdateCompetition("3", "Update a competition.", controller, scanner), commands);
        ConsoleUtils.addCommand(new FindCompetitionById("4", "Find a competition by its ID.", controller, scanner), commands);
        ConsoleUtils.addCommand(new SeeAllCompetitions("5", "See all competitions.", controller, scanner), commands);
        ConsoleUtils.addCommand(new ManageCompetitionFilters("6", "Filter competitions.", controller, scanner), commands);
    }

    @Override
    public void execute() {
        ConsoleUtils.runMenu(scanner, commands);
    }

}