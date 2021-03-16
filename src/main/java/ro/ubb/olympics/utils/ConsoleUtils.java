package ro.ubb.olympics.utils;

import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.ui.command.Command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * Utility methods used in the console UI.
 */
public class ConsoleUtils {

    private static final String EXIT_KEY = "exit";

    /**
     * Adds a command to a map of commands.
     *
     * @param command  the command to be added
     * @param commands the map to which the command should be added
     */
    public static void addCommand(final Command command, final Map<String, Command> commands) {
        commands.put(command.getKey(), command);
    }

    /**
     * Prints a menu, given a map of commands.
     *
     * @param commands the map of commands that makes up the menu
     */
    public static void printMenu(final Map<String, Command> commands) {
        System.out.println();

        commands
            .values()
            .stream()
            .sorted(Comparator.comparingInt(o -> Integer.parseInt(o.getKey())))
            .forEach(command -> System.out.printf("%s. %s\n", command.getKey(), command.getDescription()));

        System.out.println();
    }

    /**
     * Runs a menu given the available commands for the user.
     *
     * @param scanner  the scanner which provides user input
     * @param commands the map of available commands
     */
//    todo: replace infinite stream with WHILE maybe
    public static void runMenu(final Scanner scanner, final Map<String, Command> commands) {
        final AtomicBoolean running = new AtomicBoolean(true);

        IntStream.generate(() -> 0)
            .takeWhile(unused -> running.get())
            .forEach(e -> {
                    ConsoleUtils.printMenu(commands);

                    System.out.print("Input the option: ");

                    final String key = scanner.nextLine();

                    Optional.of(key)
                        .filter(k -> k.equals(EXIT_KEY))
                        .ifPresent(unused -> running.set(false));

                    Optional.of(key)
                        .filter(k -> !k.equals(EXIT_KEY))
                        .ifPresent(unused -> {
                            final Command command = commands.get(key);

                            try {
                                Validator.validateNonNull(command, "Please, input a valid option.");
                                try {
                                    command.execute();
                                } catch (final Exception exception) {
                                    System.out.printf("\n%s\n", exception.getMessage());
                                }
                            } catch (final IllegalArgumentException validatorException) {
                                System.out.printf("\n%s\n", validatorException.getMessage());
                            }
                        });
                }
            );

    }

    /**
     * Reads an athlete from the keyboard.
     *
     * @param scanner the scanner which provides user input
     * @return the athlete that has been read
     */
    public static Athlete readAthlete(final Scanner scanner) {
        return new Athlete(
            readLong(scanner, "ID: "),
            readString(scanner, "First name: "),
            readString(scanner, "Last name: "),
            readString(scanner, "Country: "),
            readInt(scanner, "Age: ")
        );
    }

    /**
     * Reads a competition from the keyboard.
     *
     * @param scanner the scanner which provides user input
     * @return the competition that has been read
     * @throws ParseException if the date is in an invalid format
     */
    public static Competition readCompetition(final Scanner scanner) throws ParseException {
        return new Competition(
            readLong(scanner, "ID: "),
            new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(readString(scanner, "Date dd-MM-yyyy: ")),
            readString(scanner, "Location: "),
            readString(scanner, "Name: "),
            readString(scanner, "Description: ")
        );
    }

    /**
     * Reads a sponsor from the keyboard.
     *
     * @param scanner the scanner which provides user input
     * @return the Sponsor that has been read
     */
    public static Sponsor readSponsor(final Scanner scanner) {
        return new Sponsor(
            readLong(scanner, "ID: "),
            readString(scanner, "Name: "),
            readString(scanner, "Country: ")
        );
    }

    /**
     * Reads a Long value from the keyboard.
     *
     * @param scanner the scanner which provides user input
     * @param message the message to be displayed to the user
     * @return the Long value that has been read
     */
    public static Long readLong(final Scanner scanner, final String message) {
        System.out.print(message);
        Long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    /**
     * Reads a int value from the keyboard.
     *
     * @param scanner the scanner which provides user input
     * @param message the message to be displayed to the user
     * @return the int value that has been read
     */
    public static int readInt(final Scanner scanner, final String message) {
        System.out.print(message);
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    /**
     * Reads a String value from the keyboard.
     *
     * @param scanner the scanner which provides user input
     * @param message the message to be displayed to the user
     * @return the String value that has been read
     */
    public static String readString(final Scanner scanner, final String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

}