package ro.ubb.olympics.repository.xml.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.xml.AbstractXmlRepository;

import java.util.Optional;

/**
 * Athlete XML repository.
 */
public class AthleteXmlRepository extends AbstractXmlRepository<Long, Athlete> {

    /**
     * Initializes the repository with the given validator and filename.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public AthleteXmlRepository(final Validator<Athlete> validator, final String fileName) {
        super(validator, fileName);
    }

    @Override
    protected Athlete createEntityFromNode(final Element element) {
        validateEntityNode(element);

        return new Athlete(
            Long.parseLong(getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.ID.getTag())),
            getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.FIRST_NAME.getTag()),
            getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.LAST_NAME.getTag()),
            getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.COUNTRY.getTag()),
            Integer.parseInt(getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.AGE.getTag()))
        );
    }

    @Override
    protected Node createNodeFromEntity(final Athlete entity, final Document document) {
        final Element element = document.createElement(AthleteXmlRepository.EntityFieldTags.ROOT.getTag());

        addChildWithTextContent(document, element, AthleteXmlRepository.EntityFieldTags.ID.getTag(), entity.getId().toString());
        addChildWithTextContent(document, element, AthleteXmlRepository.EntityFieldTags.FIRST_NAME.getTag(), entity.getFirstName());
        addChildWithTextContent(document, element, AthleteXmlRepository.EntityFieldTags.LAST_NAME.getTag(), entity.getLastName());
        addChildWithTextContent(document, element, AthleteXmlRepository.EntityFieldTags.COUNTRY.getTag(), entity.getCountry());
        addChildWithTextContent(document, element, AthleteXmlRepository.EntityFieldTags.AGE.getTag(), Integer.toString(entity.getAge()));

        return element;
    }

    @Override
    protected void validateEntityNode(final Element element) throws ValidatorException {
        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.ID.getTag())),
            "The ID is a mandatory XML node and it should be a parsable Long value.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.FIRST_NAME.getTag()),
            "The first name is a mandatory XML node and it should be a string.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.LAST_NAME.getTag()),
            "The last name is a mandatory XML node and it should be a string.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.COUNTRY.getTag()),
            "The country is a mandatory XML node and it should be a string.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> Integer.parseInt(getTextContentOfTag(element, AthleteXmlRepository.EntityFieldTags.AGE.getTag())),
            "The age is a mandatory XML node and it should be a parsable int value.",
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while reading XML node for Sponsorship: %s", errorMessage));
            });
    }

    private enum EntityFieldTags {

        ROOT("athlete"), ID("id"), FIRST_NAME("firstName"), LAST_NAME("lastName"), COUNTRY("country"), AGE("age");

        private final String tag;

        EntityFieldTags(final String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

    }

}