package ro.ubb.olympics.ui.command.impl.athlete;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;

import java.util.Scanner;

/**
 * Command used to see all athletes.
 */
public class SeeAllAthletes extends Command {

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
    public SeeAllAthletes(final String key, final String description, final Controller controller, final Scanner scanner) {
        super(key, description);
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println();
        controller
            .getAthleteService()
            .getAllAthletes()
            .forEach(System.out::println);
    }

}