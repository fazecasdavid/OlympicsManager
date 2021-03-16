package ro.ubb.olympics.ui.command.impl.sponsorship.filters;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.Scanner;

/**
 * Command used to filter sponsorships by sponsor id.
 */
public class FilterSponsorshipsBySponsorId extends Command {

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
    public FilterSponsorshipsBySponsorId(final String key, final String description, final Controller controller, final Scanner scanner) {
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
        final Long sponsorId = ConsoleUtils.readLong(scanner, "SponsorId: ");
        controller
            .getFilterService()
            .filterSponsorshipsBySponsorId(sponsorId)
            .forEach(System.out::println);
    }

}