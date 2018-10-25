package RPIS61.Shtele.wdad.data.managers;

import org.w3c.dom.Document;
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

public class PreferencesManager {
    private final static String XML_PATH = "src\\RPIS61\\Shtele\\wdad\\resources\\configuration\\appconfig.xml";
    private Document document;
    private File file;
    private static PreferencesManager instance;

    private PreferencesManager(){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.file = new File(XML_PATH);
            this.document = builder.parse(this.file);
        }
        catch ( IOException | SAXException | ParserConfigurationException e){
            e.printStackTrace();
        }
    }

    public static PreferencesManager getInstance(){
        if(instance == null){
            instance = new PreferencesManager();
        }
        return instance;
    }

    public String getCreateRegistry(){
        return document.getElementsByTagName("createregistry").item(0).getTextContent();
    }

    public void setCreateRegistry(String param){
        document.getElementsByTagName("createregistry").item(0).setTextContent(param);
    }

    public String getRegistryAddress(){
        return document.getElementsByTagName("registryaddress").item(0).getTextContent();
    }

    public void setRegistryAddress(String param){
        document.getElementsByTagName("registryaddress").item(0).setTextContent(param);
    }

    public String getRegistryPort(){
        return document.getElementsByTagName("registryport").item(0).getTextContent();
    }

    public void setRegistryPort(String param){
        document.getElementsByTagName("registryport").item(0).setTextContent(param);
    }

    public String getPolicyPath(){
        return document.getElementsByTagName("policypath").item(0).getTextContent();
    }

    public void setPolicyPath(String param){
        document.getElementsByTagName("policypath").item(0).setTextContent(param);
    }

    public String getUseCodeBaseOnly(){
        return document.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }

    public void setUseCodeBaseOnly(String param){
        document.getElementsByTagName("usecodebaseonly").item(0).setTextContent(param);
    }

    public String getClassProvider(){
        return document.getElementsByTagName("classprovider").item(0).getTextContent();
    }

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
