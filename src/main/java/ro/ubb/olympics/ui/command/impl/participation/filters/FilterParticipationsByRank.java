package ro.ubb.olympics.ui.command.impl.participation.filters;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.Scanner;

/**
 * Command used to filter participations by the rank of the athlete.
 */
public class FilterParticipationsByRank extends Command {

    private final Controller controller;
    private final Scanner scanner;

    /**
     * Initializes a Command.
     *
     * @param key         the key of the command
     * @param description the command's description
     * @param controller  the controller used in the operations
     * @param scanner     the scanner which provides user input
     */
    public FilterParticipationsByRank(String key, String description, Controller controller, Scanner scanner) {
        super(key, description);
        this.controller = controller;
        this.scanner = scanner;
    }

    /**
     * Executes the current command.
     */
    @Override
    public void execute() {
        System.out.println();
        final int rank = ConsoleUtils.readInt(scanner, "Rank: ");
        controller
            .getFilterService()
            .filterParticipationsByRank(rank)
            .forEach(System.out::println);
    }
}
