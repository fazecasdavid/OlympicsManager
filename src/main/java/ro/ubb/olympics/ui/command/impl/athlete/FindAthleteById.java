package ro.ubb.olympics.ui.command.impl.athlete;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.Scanner;

/**
 * Command used to find an athlete by ID.
 */
public class FindAthleteById extends Command {

    private final Controller controller;
    private final Scanner scanner;

    /**
     * Initializes the command.
     *
     * @param key         the key of the command
     * @param description the command's description
     * @param controller  the controller used in the operations
     * @param scanner     the scanner which provides user input
     */
    public FindAthleteById(final String key, final String description, final Controller controller, final Scanner scanner) {
        super(key, description);
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println();
        final Long id = ConsoleUtils.readLong(scanner, "ID: ");
        System.out.println(
            controller
                .getAthleteService()
                .getAthleteById(id)
                .orElseThrow(() -> new RuntimeException("There is no athlete with the given ID."))
        );
    }

}