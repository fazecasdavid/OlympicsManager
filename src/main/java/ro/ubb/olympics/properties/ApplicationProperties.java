package ro.ubb.olympics.properties;

import lombok.SneakyThrows;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.Sponsorship;
import ro.ubb.olympics.domain.validators.AthleteValidator;
import ro.ubb.olympics.domain.validators.CompetitionValidator;
import ro.ubb.olympics.domain.validators.ParticipationValidator;
import ro.ubb.olympics.domain.validators.SponsorValidator;
import ro.ubb.olympics.domain.validators.SponsorshipValidator;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.repository.Repository;
import ro.ubb.olympics.repository.file.impl.AthleteFileRepository;
import ro.ubb.olympics.repository.file.impl.CompetitionFileRepository;
import ro.ubb.olympics.repository.file.impl.ParticipationFileRepository;
import ro.ubb.olympics.repository.file.impl.SponsorFileRepository;
import ro.ubb.olympics.repository.file.impl.SponsorshipFileRepository;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;
import ro.ubb.olympics.repository.jdbc.DatabaseProvider;
import ro.ubb.olympics.repository.jdbc.impl.AthleteJdbcRepository;
import ro.ubb.olympics.repository.jdbc.impl.CompetitionJdbcRepository;
import ro.ubb.olympics.repository.jdbc.impl.ParticipationJdbcRepository;
import ro.ubb.olympics.repository.jdbc.impl.SponsorJdbcRepository;
import ro.ubb.olympics.repository.jdbc.impl.SponsorshipJdbcRepository;
import ro.ubb.olympics.repository.xml.impl.AthleteXmlRepository;
import ro.ubb.olympics.repository.xml.impl.CompetitionXmlRepository;
import ro.ubb.olympics.repository.xml.impl.ParticipationXmlRepository;
import ro.ubb.olympics.repository.xml.impl.SponsorXmlRepository;
import ro.ubb.olympics.repository.xml.impl.SponsorshipXmlRepository;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Class holding the properties of the application.
 */
public class ApplicationProperties {

    private static final String IN_MEMORY = "inMemory";
    private static final String XML = "xml";
    private static final String CSV = "csv";
    private static final String JDBC = "jdbc";

    private static final String illegalRepositoryTypeErrorMessage =
        String.format("The repository type should be %s, %s, %s or %s.", IN_MEMORY, XML, CSV, JDBC);

    final Properties properties;

    /**
     * Loads the properties from a property file.
     *
     * @param propertiesFilePath the path to the file containing the application properties.
     */
    @SneakyThrows
    public ApplicationProperties(final String propertiesFilePath) {
        this.properties = new Properties();
        this.properties.load(new FileInputStream(propertiesFilePath));
    }

    /**
     * Determine and build an athlete repository based on the properties.
     *
     * @return the athlete repository required with the given structure.
     * @throws IllegalStateException if the properties are invalid.
     */
    public Repository<Long, Athlete> getAthleteRepository() {
        final String repositoryType = properties.getProperty("athleteRepositoryType");
        final String pathToFile = properties.getProperty("athleteRepositoryPathToFile");
        final String tableName = properties.getProperty("athleteRepositoryTableName");

        final Validator<Athlete> validator = new AthleteValidator();

        return switch (repositoryType) {
            case IN_MEMORY -> new InMemoryRepository<>(validator);
            case XML -> new AthleteXmlRepository(validator, Objects.requireNonNull(pathToFile));
            case CSV -> new AthleteFileRepository(validator, Objects.requireNonNull(pathToFile));
            case JDBC -> new AthleteJdbcRepository(validator, getDatabaseProvider(), Objects.requireNonNull(tableName));
            default -> throw new IllegalStateException(illegalRepositoryTypeErrorMessage);
        };
    }

    /**
     * Determine and build a competition repository based on the properties.
     *
     * @return the competition repository required with the given structure.
     * @throws IllegalStateException if the properties are invalid.
     */
    public Repository<Long, Competition> getCompetitionRepository() {
        final String repositoryType = properties.getProperty("competitionRepositoryType");
        final String pathToFile = properties.getProperty("competitionRepositoryPathToFile");
        final String tableName = properties.getProperty("competitionRepositoryTableName");

        final Validator<Competition> validator = new CompetitionValidator();

        return switch (repositoryType) {
            case IN_MEMORY -> new InMemoryRepository<>(validator);
            case XML -> new CompetitionXmlRepository(validator, Objects.requireNonNull(pathToFile));
            case CSV -> new CompetitionFileRepository(validator, Objects.requireNonNull(pathToFile));
            case JDBC -> new CompetitionJdbcRepository(validator, getDatabaseProvider(), Objects.requireNonNull(tableName));
            default -> throw new IllegalStateException(illegalRepositoryTypeErrorMessage);
        };
    }

    /**
     * Determine and build a participation repository based on the properties.
     *
     * @return the participation repository required with the given structure.
     * @throws IllegalStateException if the properties are invalid.
     */
    public Repository<Long, Participation> getParticipationRepository() {
        final String repositoryType = properties.getProperty("participationRepositoryType");
        final String pathToFile = properties.getProperty("participationRepositoryPathToFile");
        final String tableName = properties.getProperty("participationRepositoryTableName");

        final Validator<Participation> validator = new ParticipationValidator();

        return switch (repositoryType) {
            case IN_MEMORY -> new InMemoryRepository<>(validator);
            case XML -> new ParticipationXmlRepository(validator, Objects.requireNonNull(pathToFile));
            case CSV -> new ParticipationFileRepository(validator, Objects.requireNonNull(pathToFile));
            case JDBC -> new ParticipationJdbcRepository(validator, getDatabaseProvider(), Objects.requireNonNull(tableName));
            default -> throw new IllegalStateException(illegalRepositoryTypeErrorMessage);
        };
    }

    /**
     * Determine and build a sponsor repository based on the properties.
     *
     * @return the sponsor repository required with the given structure.
     * @throws IllegalStateException if the properties are invalid.
     */
    public Repository<Long, Sponsor> getSponsorRepository() {
        final String repositoryType = properties.getProperty("sponsorRepositoryType");
        final String pathToFile = properties.getProperty("sponsorRepositoryPathToFile");
        final String tableName = properties.getProperty("sponsorRepositoryTableName");

        final Validator<Sponsor> validator = new SponsorValidator();

        return switch (repositoryType) {
            case IN_MEMORY -> new InMemoryRepository<>(validator);
            case XML -> new SponsorXmlRepository(validator, Objects.requireNonNull(pathToFile));
            case CSV -> new SponsorFileRepository(validator, Objects.requireNonNull(pathToFile));
            case JDBC -> new SponsorJdbcRepository(validator, getDatabaseProvider(), Objects.requireNonNull(tableName));
            default -> throw new IllegalStateException(illegalRepositoryTypeErrorMessage);
        };
    }

    /**
     * Determine and build a sponsorship repository based on the properties.
     *
     * @return the sponsorship repository required with the given structure.
     * @throws IllegalStateException if the properties are invalid.
     */
    public Repository<Long, Sponsorship> getSponsorshipRepository() {
        final String repositoryType = properties.getProperty("sponsorshipRepositoryType");
        final String pathToFile = properties.getProperty("sponsorshipRepositoryPathToFile");
        final String tableName = properties.getProperty("sponsorshipRepositoryTableName");

        final Validator<Sponsorship> validator = new SponsorshipValidator();

        return switch (repositoryType) {
            case IN_MEMORY -> new InMemoryRepository<>(validator);
            case XML -> new SponsorshipXmlRepository(validator, Objects.requireNonNull(pathToFile));
            case CSV -> new SponsorshipFileRepository(validator, Objects.requireNonNull(pathToFile));
            case JDBC -> new SponsorshipJdbcRepository(validator, getDatabaseProvider(), Objects.requireNonNull(tableName));
            default -> throw new IllegalStateException(illegalRepositoryTypeErrorMessage);
        };
    }

    /**
     * Build a DatabaseProvider with the properties specified in the configuration.
     *
     * @return a DatabaseProvider with the required properties.
     */
    private DatabaseProvider getDatabaseProvider() {
        return new DatabaseProvider(
            System.getProperty("url"),
            System.getProperty("user"),
            System.getProperty("password")
        );
    }

}