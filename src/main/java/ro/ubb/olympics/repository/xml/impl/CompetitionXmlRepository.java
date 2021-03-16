package ro.ubb.olympics.repository.xml.impl;

import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.xml.AbstractXmlRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;

/**
 * Competition XML repository.
 */
public class CompetitionXmlRepository extends AbstractXmlRepository<Long, Competition> {

    /**
     * Initializes the repository with the given validator and filename.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public CompetitionXmlRepository(final Validator<Competition> validator, final String fileName) {
        super(validator, fileName);
    }

    @SneakyThrows
    @Override
    protected Competition createEntityFromNode(final Element element) {
        validateEntityNode(element);

        return new Competition(
            Long.parseLong(getTextContentOfTag(element, EntityFieldTags.ID.getTag())),
            new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(getTextContentOfTag(element, EntityFieldTags.DATE.getTag())),
            getTextContentOfTag(element, EntityFieldTags.LOCATION.getTag()),
            getTextContentOfTag(element, EntityFieldTags.NAME.getTag()),
            getTextContentOfTag(element, EntityFieldTags.DESCRIPTION.getTag())
        );
    }

    @Override
    protected Node createNodeFromEntity(final Competition entity, final Document document) {
        final Element element = document.createElement(EntityFieldTags.ROOT.getTag());

        addChildWithTextContent(document, element, EntityFieldTags.ID.getTag(), entity.getId().toString());
        addChildWithTextContent(document, element, EntityFieldTags.DATE.getTag(), new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(entity.getDate()));
        addChildWithTextContent(document, element, EntityFieldTags.LOCATION.getTag(), entity.getLocation());
        addChildWithTextContent(document, element, EntityFieldTags.NAME.getTag(), entity.getName());
        addChildWithTextContent(document, element, EntityFieldTags.DESCRIPTION.getTag(), entity.getDescription());

        return element;
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
            () -> {
                try {
                    new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(
                        getTextContentOfTag(element, EntityFieldTags.DATE.getTag())
                    );
                } catch (ParseException e) {
                    throw new RuntimeException();
                }
            },
            "The Date is a mandatory XML node and it should be in the format dd-MM-yyyy.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> getTextContentOfTag(element, EntityFieldTags.LOCATION.getTag()),
            "The location is a mandatory XML node and it should be a string.",
            errorMessageBuilder
        );


        Validator.validateDoesNotThrowException(
            () -> getTextContentOfTag(element, EntityFieldTags.NAME.getTag()),
            "The name is a mandatory XML node and it should be a string.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> getTextContentOfTag(element, EntityFieldTags.DESCRIPTION.getTag()),
            "The description is a mandatory XML node and it should be a string.",
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while reading XML node for Competition: %s", errorMessage));
            });
    }

    private enum EntityFieldTags {

        ROOT("competition"), ID("id"), DATE("date"), LOCATION("location"), NAME("name"), DESCRIPTION("description");

        private final String tag;

        EntityFieldTags(final String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

    }

}