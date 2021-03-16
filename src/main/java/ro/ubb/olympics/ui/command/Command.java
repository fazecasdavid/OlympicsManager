package ro.ubb.olympics.ui.command;

/**
 * Object representing a Command available to the user.
 */
public abstract class Command {

    private final String key;
    private final String description;

    /**
     * Initializes a Command.
     *
     * @param key         the key of the command
     * @param description the command's description
     */
    public Command(final String key, final String description) {
        this.key = key;
        this.description = description;
    }

    /**
     * Executes the current command.
     */
    public abstract void execute();

    /**
     * Get the command's key.
     *
     * @return the key of the command
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the command's description.
     *
     * @return the description of the command
     */
    public String getDescription() {
        return description;
    }

}