package core.xml;

import core.Constants;
import org.w3c.dom.*;

import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlFile {

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;
    private Element rootElement, screenElement, login, username, token, userId;
    private TransformerFactory transformerFactory;
    private Transformer transformer;
    private DOMSource source;
    private File directory, file, existingFile;
    private StreamResult result;
    private String mapName = "myGame";
    private String fileName = "game.xml";
    private String filePath;

    public void createXmlFile() {
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            document = documentBuilder.newDocument();

            rootElement = document.createElement("Game");
            document.appendChild(rootElement);

            screenElement = document.createElement("LoginScreen");
            rootElement.appendChild(screenElement);

            login = document.createElement("Login");
            screenElement.appendChild(login);

            login.setAttribute("id", "1");

            token = document.createElement("token");
            token.appendChild(document.createTextNode(Constants.GAMETOKEN));
            login.appendChild(token);

            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(document);

            directory = new File(FileSystemView.getFileSystemView().getDefaultDirectory(), mapName);
            if (!directory.exists()) directory.mkdirs();

            file = new File(directory.getAbsolutePath(), fileName);

            result = new StreamResult(file);
            transformer.transform(source, result);
            /*if (!file.exists()) {
                result = new StreamResult(file);
                transformer.transform(source, result);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserData() {
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            filePath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/" + mapName + "/" + fileName;
            existingFile = new File(filePath);

            if (!existingFile.exists()) createXmlFile();

            document = documentBuilder.parse(existingFile);

            Node loginState = document.getElementsByTagName("Login").item(0);

            NodeList state = loginState.getChildNodes();
            Node token = state.item(0);

            Element tokenElement = (Element) token;

            Constants.GAMETOKEN = tokenElement.getTextContent();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteGameToken() {
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            filePath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/" + mapName + "/" + fileName;
            existingFile = new File(filePath);

            if (!existingFile.exists()) createXmlFile();

            document = documentBuilder.parse(existingFile);

            Node loginState = document.getElementsByTagName("Login").item(0);

            NodeList state = loginState.getChildNodes();
            Node node = state.item(0);
            Element element = (Element) node;
            if ("token".equals(element.getNodeName())) element.setTextContent("");

            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(document);

            result = new StreamResult(existingFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}