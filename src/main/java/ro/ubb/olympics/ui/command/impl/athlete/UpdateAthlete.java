package ro.ubb.olympics.ui.command.impl.athlete;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.Scanner;

/**
 * Command used to update an athlete.
 */
public class UpdateAthlete extends Command {

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
    public UpdateAthlete(final String key, final String description, final Controller controller, final Scanner scanner) {
        super(key, description);
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println();
        final Athlete athlete = ConsoleUtils.readAthlete(scanner);
        controller
            .getAthleteService()
            .updateAthlete(athlete)
            .orElseThrow(() ->
                new RuntimeException("The athlete could not be updated")
            );
    }

}