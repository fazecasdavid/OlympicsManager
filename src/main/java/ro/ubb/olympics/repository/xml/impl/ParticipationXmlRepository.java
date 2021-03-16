package ro.ubb.olympics.repository.xml.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.xml.AbstractXmlRepository;

import java.util.Optional;

/**
 * Participation XML repository.
 */
public class ParticipationXmlRepository extends AbstractXmlRepository<Long, Participation> {

    /**
     * Initializes the repository with the given validator and filename.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public ParticipationXmlRepository(final Validator<Participation> validator, final String fileName) {
        super(validator, fileName);
    }

    @Override
    protected Node createNodeFromEntity(final Participation entity, final Document document) {
        final Element element = document.createElement(EntityFieldTags.ROOT.getTag());

        addChildWithTextContent(document, element, EntityFieldTags.ID.getTag(), entity.getId().toString());
        addChildWithTextContent(document, element, EntityFieldTags.ATHLETE_ID.getTag(), entity.getAthleteId().toString());
        addChildWithTextContent(document, element, EntityFieldTags.COMPETITION_ID.getTag(), entity.getCompetitionId().toString());
        addChildWithTextContent(document, element, EntityFieldTags.RANK.getTag(), Integer.toString(entity.getRank()));

        return element;
    }

    @Override
    protected Participation createEntityFromNode(final Element element) {
        validateEntityNode(element);

        return new Participation(
            Long.parseLong(getTextContentOfTag(element, EntityFieldTags.ID.getTag())),
            Long.parseLong(getTextContentOfTag(element, EntityFieldTags.ATHLETE_ID.getTag())),
            Long.parseLong(getTextContentOfTag(element, EntityFieldTags.COMPETITION_ID.getTag())),
            Integer.parseInt(getTextContentOfTag(element, EntityFieldTags.RANK.getTag()))
        );
    }

    @Override
    protected void validateEntityNode(final Element element) throws ValidatorException {
        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(getTextContentOfTag(element, EntityFieldTags.ID.getTag())),
            "The ID is a mandatory XML node and it should be a parsable Long value.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(getTextContentOfTag(element, EntityFieldTags.ATHLETE_ID.getTag())),
            "The athleteId is a mandatory XML node and it should be a parsable Long value",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(getTextContentOfTag(element, EntityFieldTags.COMPETITION_ID.getTag())),
            "The competitionId is a mandatory XML node and it should be a parsable Long value",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> Integer.parseInt(getTextContentOfTag(element, EntityFieldTags.RANK.getTag())),
            "The rank is a mandatory XML node and it should be a parsable int value.",
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while reading XML node for Participation: %s", errorMessage));
            });
    }

    private enum EntityFieldTags {
        ROOT("participation"), ID("id"), ATHLETE_ID("athleteId"), COMPETITION_ID("competitionId"), RANK("rank");

        private final String tag;

        EntityFieldTags(final String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }

}