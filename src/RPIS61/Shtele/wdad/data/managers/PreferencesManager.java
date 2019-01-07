package RPIS61.Shtele.wdad.data.managers;

import RPIS61.Shtele.wdad.utils.PreferencesManagerConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PreferencesManager {
    private final static String XML_PATH = "src\\RPIS61\\Shtele\\wdad\\resources\\configuration\\appconfig.xml";
    private Document document;
    private File file;
    private static PreferencesManager instance;
    private Properties properties;

    private PreferencesManager(){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.file = new File(XML_PATH);
            this.document = builder.parse(this.file);
            this.properties = new Properties();
            createProperties();
        }
        catch ( IOException | SAXException | ParserConfigurationException e){
            e.printStackTrace();
        }
    }

    private void createProperties(){
        String[] keys = new String[]{
                PreferencesManagerConstants.createregistry, PreferencesManagerConstants.registryaddress,
                PreferencesManagerConstants.registryport, PreferencesManagerConstants.policypath,
                PreferencesManagerConstants.usecodebaseonly, PreferencesManagerConstants.classprovider,
                PreferencesManagerConstants.classname, PreferencesManagerConstants.drivertype,
                PreferencesManagerConstants.hostname, PreferencesManagerConstants.port,
                PreferencesManagerConstants.dbname, PreferencesManagerConstants.user,
                PreferencesManagerConstants.password};
        String value, key;
        String[] keyParts;
        for(int i = 0; i < keys.length; i++){
            keyParts = keys[i].split("\\.");
            key = keyParts[keyParts.length - 1];
            value = document.getElementsByTagName(key).item(0).getTextContent();
            properties.setProperty(keys[i], value);
        }
    }

    public static PreferencesManager getInstance(){
        if(instance == null){
            instance = new PreferencesManager();
        }
        return instance;
    }

    public void setProperty(String key, String value){
        document.getElementsByTagName(key).item(0).setTextContent(value);
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public void setProperties(Properties properties){
        for (String p: properties.stringPropertyNames()
             ) {
            setProperty(p, properties.getProperty(p));
        }
    }

    public Properties getProperties(){
        return this.properties;
    }

    public void addBindedObject(String name, String className){
        Element element = document.createElement("bindedobject");
        element.setAttribute("name",name);
        element.setAttribute("class", className);
        document.getElementsByTagName("server").item(0).appendChild(element);
        saveXML();
    }

    public void removeBindedObject(String name){
        NodeList bindedObjects = document.getElementsByTagName("bindedobject");
        Element element;
        for(int i = 0; i < bindedObjects.getLength(); i++){
            element = (Element) bindedObjects.item(i);
            if(element.getAttribute("name").equals(name)){
                element.getParentNode().removeChild(element);
            }
        }
        saveXML();
    }

    @Deprecated
    public String getCreateRegistry(){
        return document.getElementsByTagName("createregistry").item(0).getTextContent();
    }

    @Deprecated
    public void setCreateRegistry(String param){
        document.getElementsByTagName("createregistry").item(0).setTextContent(param);
    }

    @Deprecated
    public String getRegistryAddress(){
        return document.getElementsByTagName("registryaddress").item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryAddress(String param){
        document.getElementsByTagName("registryaddress").item(0).setTextContent(param);
    }

    @Deprecated
    public String getRegistryPort(){
        return document.getElementsByTagName("registryport").item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryPort(String param){
        document.getElementsByTagName("registryport").item(0).setTextContent(param);
    }

    @Deprecated
    public String getPolicyPath(){
        return document.getElementsByTagName("policypath").item(0).getTextContent();
    }

    @Deprecated
    public void setPolicyPath(String param){
        document.getElementsByTagName("policypath").item(0).setTextContent(param);
    }

    @Deprecated
    public String getUseCodeBaseOnly(){
        return document.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }

    @Deprecated
    public void setUseCodeBaseOnly(String param){
        document.getElementsByTagName("usecodebaseonly").item(0).setTextContent(param);
    }

    @Deprecated
    public String getClassProvider(){
        return document.getElementsByTagName("classprovider").item(0).getTextContent();
    }

    @Deprecated
    public void setClassProvider(String param){
        document.getElementsByTagName("classprovider").item(0).setTextContent(param);
    }

    private void saveXML(){
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
        }
        catch (FileNotFoundException | TransformerException e){
            e.printStackTrace();
        }
    }
}
