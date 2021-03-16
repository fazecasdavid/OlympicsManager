package ro.ubb.olympics.utils;

import org.w3c.dom.Document;
import ro.ubb.olympics.exception.FileException;
import ro.ubb.olympics.exception.XmlException;

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
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class containing utility methods operating with files.
 */
public class FileUtils {

    /**
     * Create a file if it does not already exist.
     *
     * @param filename the name of the file to be created.
     */
    public static void createFileIfNotExists(final String filename) {
        try {
            Files.createFile(Paths.get(filename));
        } catch (final FileAlreadyExistsException ignored) {
            // this is made to ensure that the file already exists
            // todo: find a more elegant solution
        } catch (final IOException ioException) {
            throw new FileException(ioException);
        }
    }

    /**
     * Create an XML file if it does not already exist.
     *
     * @param filename the name of the XML file to be created.
     */
    public static void createXmlFileIfNotExists(final String filename) {
        try {
            Files.createFile(Paths.get(filename));

            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final Document document = documentBuilder.newDocument();
            document.appendChild(document.createElement("entities"));

            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(
                new DOMSource(document),
                new StreamResult(new File(filename))
            );
        } catch (final FileAlreadyExistsException ignored) {
            // this is made to ensure that the file already exists
            // todo: find a more elegant solution
        } catch (final ParserConfigurationException | TransformerException | IOException exception) {
            throw new XmlException(exception);
        }
    }

}