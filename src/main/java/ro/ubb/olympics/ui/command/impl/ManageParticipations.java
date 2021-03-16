package ro.ubb.olympics.ui.command.impl;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.ui.command.impl.participation.AddParticipation;
import ro.ubb.olympics.ui.command.impl.participation.DeleteParticipation;
import ro.ubb.olympics.ui.command.impl.participation.FindParticipationById;
import ro.ubb.olympics.ui.command.impl.participation.ManageParticipationFilters;
import ro.ubb.olympics.ui.command.impl.participation.SeeAllParticipations;
import ro.ubb.olympics.ui.command.impl.participation.UpdateParticipation;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Submenu that manages participations.
 */
public class ManageParticipations extends Command {

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
    public ManageParticipations(final String key, final String description, final Controller controller, final Scanner scanner) {
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
        ConsoleUtils.addCommand(new AddParticipation("1", "Add a new participation.", controller, scanner), commands);
        ConsoleUtils.addCommand(new DeleteParticipation("2", "Delete a participation.", controller, scanner), commands);
        ConsoleUtils.addCommand(new UpdateParticipation("3", "Update a participation.", controller, scanner), commands);
        ConsoleUtils.addCommand(new FindParticipationById("4", "Find a participation by its ID.", controller, scanner), commands);
        ConsoleUtils.addCommand(new SeeAllParticipations("5", "See all participations.", controller, scanner), commands);
        ConsoleUtils.addCommand(new ManageParticipationFilters("6", "Filter participations.", controller, scanner), commands);
    }

    @Override
    public void execute() {
        ConsoleUtils.runMenu(scanner, commands);
    }

}