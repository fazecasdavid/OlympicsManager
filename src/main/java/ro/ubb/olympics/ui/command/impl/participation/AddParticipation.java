package ro.ubb.olympics.ui.command.impl.participation;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.Scanner;

/**
 * Command used to add a participation.
 */
public class AddParticipation extends Command {

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
    public AddParticipation(final String key, final String description, final Controller controller, final Scanner scanner) {
        super(key, description);
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println();
        final Long id = ConsoleUtils.readLong(scanner, "ID: ");
        final Long athleteId = ConsoleUtils.readLong(scanner, "Athlete ID: ");
        final Long competitionId = ConsoleUtils.readLong(scanner, "Competition ID: ");
        final int rank = ConsoleUtils.readInt(scanner, "Rank: ");

        controller.addParticipation(id, athleteId, competitionId, rank)
            .ifPresent(unused -> {
                    throw new RuntimeException("The Participation could not be added.");
                }
            );
    }

}