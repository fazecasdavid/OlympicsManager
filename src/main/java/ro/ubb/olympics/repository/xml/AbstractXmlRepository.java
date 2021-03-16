package ro.ubb.olympics.repository.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.ubb.olympics.domain.BaseEntity;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.exception.XmlException;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;
import ro.ubb.olympics.utils.FileUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Abstract XML repository.
 *
 * @param <ID> the type of the ID of the stored entities
 * @param <T>  the type of the entity to be stored
 */
public abstract class AbstractXmlRepository<ID, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> {

    private final String fileName;

    /**
     * Initializes the repository with the given validator and filename and loads the data from the file.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public AbstractXmlRepository(final Validator<T> validator, final String fileName) {
        super(validator);
        this.fileName = fileName;
        FileUtils.createXmlFileIfNotExists(fileName);
        loadData();
    }

    /**
     * Add a child to a node and set its text content.
     *
     * @param document    the document on which the node is added.
     * @param parent      the parent node to which the child node should be added.
     * @param tagName     the tag of the child node.
     * @param textContent the text content of the child node.
     */
    protected static void addChildWithTextContent(final Document document, final Element parent, final String tagName, final String textContent) {
        final Element childElement = document.createElement(tagName);
        childElement.setTextContent(textContent);
        parent.appendChild(childElement);
    }

    /**
     * Get the text content of a given node determined by its tag.
     *
     * @param element the parent node where the tag is to be searched
     * @param tag     the tag that is to be searched
     * @return the text content of the tag
     */
    protected static String getTextContentOfTag(final Element element, final String tag) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

    /**
     * Validate if a node represents a valid entity.
     *
     * @param element the element to be validated.
     * @throws ValidatorException if the element does not represent a proper entity.
     */
    protected abstract void validateEntityNode(final Element element) throws ValidatorException;

    /**
     * Create a node from an entity.
     *
     * @param entity   the entity to be turned into a node.
     * @param document the document to which the node will be added.
     * @return the Node representing the entity.
     */
    protected abstract Node createNodeFromEntity(final T entity, final Document document);

    /**
     * Create an entity from a node.
     *
     * @param element the node from which the entity is to be created.
     * @return the entity built from the given node.
     * @throws ValidatorException if the element does not represent a proper entity.
     */
    protected abstract T createEntityFromNode(final Element element);

    /**
     * Add an entity to a document.
     *
     * @param entity   the entity to be added.
     * @param document the document to which the entity shall be added.
     */
    private void addEntityToDom(final T entity, final Document document) {
        final Element root = document.getDocumentElement();
        final Node entityNode = createNodeFromEntity(entity, document);
        root.appendChild(entityNode);
    }

    /**
     * Load the data from a file and save it in the parent InMemoryRepository class.
     */
    private void loadData() {
        try {
            final DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document root = db.parse(fileName);
            final Element xmlEntitiesContainer = root.getDocumentElement();
            final NodeList xmlEntitiesNodeList = xmlEntitiesContainer.getChildNodes();

            IntStream
                .range(0, xmlEntitiesNodeList.getLength())
                .mapToObj(xmlEntitiesNodeList::item)
                .filter(node -> node instanceof Element)
                .forEach(node -> super.save(createEntityFromNode((Element) node)));

        } catch (ParserConfigurationException | SAXException | IOException exception) {
            throw new XmlException(exception);
        }
    }

    /**
     * Save the data stored in memory to the file.
     *
     * @throws XmlException if an I/O error occurs during the process of saving the data.
     */
    private void saveData() throws XmlException {
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final Document document = documentBuilder.newDocument();
            document.appendChild(document.createElement("entities"));

            StreamSupport
                .stream(super.findAll().spliterator(), false)
                .forEach(entity -> addEntityToDom(entity, document));

            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(
                new DOMSource(document),
                new StreamResult(new File(fileName))
            );
        } catch (final ParserConfigurationException | TransformerException exception) {
            throw new XmlException(exception);
        }
    }

    @Override
    public Optional<T> save(final T entity) throws ValidatorException {
        final Optional<T> optional = super.save(entity);

        Optional
            .of(optional)
            .filter(Optional::isEmpty)
            .ifPresent(unused -> saveData());

        return optional;
    }

    @Override
    public Optional<T> delete(final ID id) {
        final Optional<T> optional = super.delete(id);

        Optional
            .of(optional)
            .filter(Optional::isPresent)
            .ifPresent(unused -> saveData());

        return optional;
    }

    @Override
    public Optional<T> update(final T entity) throws ValidatorException {
        final Optional<T> optional = super.update(entity);

        Optional
            .of(optional)
            .filter(Optional::isPresent)
            .ifPresent(unused -> saveData());

        return optional;
    }

}