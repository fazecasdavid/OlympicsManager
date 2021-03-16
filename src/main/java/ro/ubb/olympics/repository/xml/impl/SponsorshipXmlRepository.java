package ro.ubb.olympics.repository.xml.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.olympics.domain.Sponsorship;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.xml.AbstractXmlRepository;

import java.util.Optional;

/**
 * Sponsorship XML repository.
 */
public class SponsorshipXmlRepository extends AbstractXmlRepository<Long, Sponsorship> {

    /**
     * Initializes the repository with the given validator and filename.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public SponsorshipXmlRepository(final Validator<Sponsorship> validator, final String fileName) {
        super(validator, fileName);
    }

    @Override
    protected Sponsorship createEntityFromNode(final Element element) {
        validateEntityNode(element);

        return new Sponsorship(
            Long.parseLong(getTextContentOfTag(element, EntityFieldTags.ID.getTag())),
            Long.parseLong(getTextContentOfTag(element, EntityFieldTags.COMPETITION_ID.getTag())),
            Long.parseLong(getTextContentOfTag(element, EntityFieldTags.SPONSOR_ID.getTag())),
            Integer.parseInt(getTextContentOfTag(element, EntityFieldTags.MONEY_CONTRIBUTION.getTag()))
        );
    }

    @Override
    protected Node createNodeFromEntity(final Sponsorship entity, final Document document) {
        final Element element = document.createElement(EntityFieldTags.ROOT.getTag());

        addChildWithTextContent(document, element, EntityFieldTags.ID.getTag(), entity.getId().toString());
        addChildWithTextContent(document, element, EntityFieldTags.COMPETITION_ID.getTag(), entity.getCompetitionId().toString());
        addChildWithTextContent(document, element, EntityFieldTags.SPONSOR_ID.getTag(), entity.getSponsorId().toString());
        addChildWithTextContent(document, element, EntityFieldTags.MONEY_CONTRIBUTION.getTag(), Integer.toString(entity.getMoneyContribution()));

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
            () -> Long.parseLong(getTextContentOfTag(element, EntityFieldTags.COMPETITION_ID.getTag())),
            "The competitionId is a mandatory XML node and it should be a parsable Long value.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(getTextContentOfTag(element, EntityFieldTags.SPONSOR_ID.getTag())),
            "The sponsorId is a mandatory XML node and it should be a parsable Long value.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> Integer.parseInt(getTextContentOfTag(element, EntityFieldTags.MONEY_CONTRIBUTION.getTag())),
            "The money contribution is a mandatory XML node and it should be a parsable int value.",
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

        ROOT("sponsorship"), ID("id"), COMPETITION_ID("competitionId"), SPONSOR_ID("sponsorId"), MONEY_CONTRIBUTION("moneyContribution");

        private final String tag;

        EntityFieldTags(final String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

    }

}