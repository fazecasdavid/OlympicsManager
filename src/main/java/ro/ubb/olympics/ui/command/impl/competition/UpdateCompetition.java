package ro.ubb.olympics.ui.command.impl.competition;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.text.ParseException;
import java.util.Scanner;

/**
 * Command used to update a competition.
 */
public class UpdateCompetition extends Command {

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
    public UpdateCompetition(final String key, final String description, final Controller controller, final Scanner scanner) {
        super(key, description);
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println();
        try {
            final Competition competition = ConsoleUtils.readCompetition(scanner);
            controller
                .getCompetitionService()
                .updateCompetition(competition)
                .orElseThrow(() ->
                    new RuntimeException("The competition could not be updated")
                );
        } catch (ParseException e) {
            throw new RuntimeException(String.format("Error when reading a competition: %s", e.getMessage()));
        }
    }

}