package ro.ubb.olympics.ui.command.impl.athlete;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.ui.command.impl.athlete.filters.FilterAthletesByCountry;
import ro.ubb.olympics.ui.command.impl.athlete.filters.FilterAthletesByFirstName;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Submenu that manages athlete filters.
 */
public class ManageAthletesFilters extends Command {
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
    public ManageAthletesFilters(final String key, final String description, final Controller controller, final Scanner scanner) {
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
        ConsoleUtils.addCommand(new FilterAthletesByFirstName("1", "Filter by firstName.", controller, scanner), commands);
        ConsoleUtils.addCommand(new FilterAthletesByCountry("2", "Filter by country.", controller, scanner), commands);
    }

    @Override
    public void execute() {
        ConsoleUtils.runMenu(scanner, commands);
    }

}