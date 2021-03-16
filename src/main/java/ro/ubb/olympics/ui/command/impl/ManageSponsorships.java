package ro.ubb.olympics.ui.command.impl;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.ui.command.impl.sponsorship.AddSponsorship;
import ro.ubb.olympics.ui.command.impl.sponsorship.DeleteSponsorship;
import ro.ubb.olympics.ui.command.impl.sponsorship.FindSponsorshipById;
import ro.ubb.olympics.ui.command.impl.sponsorship.ManageSponsorshipsFilters;
import ro.ubb.olympics.ui.command.impl.sponsorship.SeeAllSponsorships;
import ro.ubb.olympics.ui.command.impl.sponsorship.UpdateSponsorship;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Submenu that manages sponsorships.
 */
public class ManageSponsorships extends Command {

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
    public ManageSponsorships(final String key, final String description, final Controller controller, final Scanner scanner) {
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
        ConsoleUtils.addCommand(new AddSponsorship("1", "Add a new sponsorship.", controller, scanner), commands);
        ConsoleUtils.addCommand(new DeleteSponsorship("2", "Delete a sponsorship.", controller, scanner), commands);
        ConsoleUtils.addCommand(new UpdateSponsorship("3", "Update a sponsorship.", controller, scanner), commands);
        ConsoleUtils.addCommand(new FindSponsorshipById("4", "Find a sponsorship by its ID.", controller, scanner), commands);
        ConsoleUtils.addCommand(new SeeAllSponsorships("5", "See all sponsorships.", controller, scanner), commands);
        ConsoleUtils.addCommand(new ManageSponsorshipsFilters("6", "Filter sponsorships.", controller, scanner), commands);
    }

    @Override
    public void execute() {
        ConsoleUtils.runMenu(scanner, commands);
    }

}