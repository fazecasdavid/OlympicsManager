package ro.ubb.olympics.ui.command.impl.sponsorship;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.ui.command.impl.sponsorship.filters.FilterSponsorshipsByMoneyContributionGreaterOrEqual;
import ro.ubb.olympics.ui.command.impl.sponsorship.filters.FilterSponsorshipsBySponsorId;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Submenu for the sponsorship filters.
 */
public class ManageSponsorshipsFilters extends Command {

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
    public ManageSponsorshipsFilters(final String key, final String description, final Controller controller, final Scanner scanner) {
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
        ConsoleUtils.addCommand(new FilterSponsorshipsByMoneyContributionGreaterOrEqual("1", "Filter by moneyContribution ( greater than or equal to ).", controller, scanner), commands);
        ConsoleUtils.addCommand(new FilterSponsorshipsBySponsorId("2", "Filter by sponsorId.", controller, scanner), commands);
    }

    @Override
    public void execute() {
        ConsoleUtils.runMenu(scanner, commands);
    }

}