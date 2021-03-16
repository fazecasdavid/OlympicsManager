package ro.ubb.olympics.ui.command.impl;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.ui.command.impl.athlete.AddAthlete;
import ro.ubb.olympics.ui.command.impl.athlete.DeleteAthlete;
import ro.ubb.olympics.ui.command.impl.athlete.FindAthleteById;
import ro.ubb.olympics.ui.command.impl.athlete.ManageAthletesFilters;
import ro.ubb.olympics.ui.command.impl.athlete.SeeAllAthletes;
import ro.ubb.olympics.ui.command.impl.athlete.UpdateAthlete;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Submenu that manages athletes.
 */
public class ManageAthletes extends Command {

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
    public ManageAthletes(final String key, final String description, final Controller controller, final Scanner scanner) {
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
        ConsoleUtils.addCommand(new AddAthlete("1", "Add a new athlete.", controller, scanner), commands);
        ConsoleUtils.addCommand(new DeleteAthlete("2", "Delete an athlete.", controller, scanner), commands);
        ConsoleUtils.addCommand(new UpdateAthlete("3", "Update an athlete.", controller, scanner), commands);
        ConsoleUtils.addCommand(new FindAthleteById("4", "Find an athlete by its ID.", controller, scanner), commands);
        ConsoleUtils.addCommand(new SeeAllAthletes("5", "See all athletes.", controller, scanner), commands);
        ConsoleUtils.addCommand(new ManageAthletesFilters("6", "Filter athletes.", controller, scanner), commands);
    }

    @Override
    public void execute() {
        ConsoleUtils.runMenu(scanner, commands);
    }

}