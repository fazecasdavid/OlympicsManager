package ro.ubb.olympics.repository.xml.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.xml.AbstractXmlRepository;

import java.util.Optional;

/**
 * Sponsor XML repository.
 */
public class SponsorXmlRepository extends AbstractXmlRepository<Long, Sponsor> {

    /**
     * Initializes the repository with the given validator and filename.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public SponsorXmlRepository(final Validator<Sponsor> validator, final String fileName) {
        super(validator, fileName);
    }

    @Override
    protected Sponsor createEntityFromNode(final Element element) {
        validateEntityNode(element);

        return new Sponsor(
            Long.parseLong(getTextContentOfTag(element, EntityFieldTags.ID.getTag())),
            getTextContentOfTag(element, EntityFieldTags.NAME.getTag()),
            getTextContentOfTag(element, EntityFieldTags.COUNTRY.getTag())
        );
    }

    @Override
    protected Node createNodeFromEntity(final Sponsor entity, final Document document) {
        final Element element = document.createElement(EntityFieldTags.ROOT.getTag());

        addChildWithTextContent(document, element, EntityFieldTags.ID.getTag(), entity.getId().toString());
        addChildWithTextContent(document, element, EntityFieldTags.NAME.getTag(), entity.getName());
        addChildWithTextContent(document, element, EntityFieldTags.COUNTRY.getTag(), entity.getCountry());

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
            () -> getTextContentOfTag(element, EntityFieldTags.NAME.getTag()),
            "The name is a mandatory XML node and it should be a string.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> getTextContentOfTag(element, EntityFieldTags.COUNTRY.getTag()),
            "The country is a mandatory XML node and it should be a string.",
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while reading XML node for Sponsor: %s", errorMessage));
            });
    }

    private enum EntityFieldTags {

        ROOT("sponsor"), ID("id"), NAME("name"), COUNTRY("country");

        private final String tag;

        EntityFieldTags(final String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

    }

}