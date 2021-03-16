package ro.ubb.olympics.ui.command.impl;

import ro.ubb.olympics.controller.Controller;
import ro.ubb.olympics.ui.command.Command;
import ro.ubb.olympics.ui.command.impl.sponsor.AddSponsor;
import ro.ubb.olympics.ui.command.impl.sponsor.DeleteSponsor;
import ro.ubb.olympics.ui.command.impl.sponsor.FindSponsorById;
import ro.ubb.olympics.ui.command.impl.sponsor.ManageSponsorFilters;
import ro.ubb.olympics.ui.command.impl.sponsor.SeeAllSponsors;
import ro.ubb.olympics.ui.command.impl.sponsor.UpdateSponsor;
import ro.ubb.olympics.utils.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Submenu that manages sponsors.
 */
public class ManageSponsors extends Command {

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
    public ManageSponsors(final String key, final String description, final Controller controller, final Scanner scanner) {
        super(key, description);
        this.controller = controller;
        this.scanner = scanner;
        this.commands = new HashMap<>();
        this.initializeCommands();
    }

    private void initializeCommands() {
        ConsoleUtils.addCommand(new AddSponsor("1", "Add a new sponsor.", controller, scanner), commands);
        ConsoleUtils.addCommand(new DeleteSponsor("2", "Delete a sponsor.", controller, scanner), commands);
        ConsoleUtils.addCommand(new UpdateSponsor("3", "Update a sponsor.", controller, scanner), commands);
        ConsoleUtils.addCommand(new FindSponsorById("4", "Find a sponsor by its ID.", controller, scanner), commands);
        ConsoleUtils.addCommand(new SeeAllSponsors("5", "See all sponsors.", controller, scanner), commands);
        ConsoleUtils.addCommand(new ManageSponsorFilters("6", "Filter sponsors.", controller, scanner), commands);
    }

    @Override
    public void execute() {
        ConsoleUtils.runMenu(scanner, commands);
    }

}