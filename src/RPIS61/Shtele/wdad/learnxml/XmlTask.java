package RPIS61.Shtele.wdad.learnxml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class XmlTask {
    private final String XML_CATALOG = "src/RPIS61/Shtele/wdad/learnxml/";
    private Document document;
    private File file;

   public XmlTask(String filename){
       try{
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           factory.setIgnoringElementContentWhitespace(true);
           DocumentBuilder builder = factory.newDocumentBuilder();
           this.file = new File(XML_CATALOG + filename);
           this.document = builder.parse(this.file);
           totalCostVerification();
       } catch (ParserConfigurationException | SAXException | IOException e) {
           e.printStackTrace();
       }
   }

   public XmlTask(File file){
       try{
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           DocumentBuilder builder = factory.newDocumentBuilder();
           this.file = file;
           this.document = builder.parse(file);
           totalCostVerification();
       } catch (ParserConfigurationException | SAXException | IOException e) {
           e.printStackTrace();
       }
   }

   private void totalCostVerification(){
       NodeList orders = document.getElementsByTagName("order");
       Element order, totalCost;
       NodeList totalCosts;
       int cost;
       for(int i = 0; i < orders.getLength(); i++){
           order = (Element)orders.item(i);
           totalCosts = order.getElementsByTagName("totalcost");
           //todo order.getElementsByTagName("totalcost") - в переменную сохраняй результат
           //todo - разные действия - создаешь тэг если его нет, если есть но с не правильным контентом - изменяешь контент
           if(totalCosts.getLength() == 0){
               totalCost = document.createElement("totalcost");
               totalCost.setTextContent(String.valueOf(calculatingTotalCost(order)));
               order.appendChild(totalCost);
               saveXML();
           }
           else if(totalCosts.item(0).getTextContent() == null){
               totalCost = (Element) order.getElementsByTagName("totalcost").item(0);
               totalCost.setTextContent(String.valueOf(calculatingTotalCost(order)));
               saveXML();
           }
           else{
               cost = calculatingTotalCost(order);
               if(Integer.parseInt(totalCosts.item(0).getTextContent()) != cost){
                   totalCost = (Element) order.getElementsByTagName("totalcost").item(0);
                   totalCost.setTextContent(String.valueOf(cost));
                   saveXML();
               }
           }
       }
   }

   private int calculatingTotalCost(Element order){
       int sum = 0, cost = 0;
       NodeList items = order.getElementsByTagName("item");
       Element item;
       for(int j = 0; j < items.getLength(); j++){
           item = (Element)items.item(j);
           cost = Integer.parseInt(item.getAttribute("cost"));
           sum += cost;
       }
       return sum;
   }

   public int earningTotal(String officiantSecondName, Calendar calendar){
       int sum = 0;
       Element day = findDay(calendar), officiant, order;
       if(day == null)
           return -1;
       NodeList officiants = day.getElementsByTagName("officiant");
       for(int i = 0; i < officiants.getLength(); i++){
           officiant = (Element) officiants.item(i);
           if(officiant.getAttribute("secondname").equals(officiantSecondName)){
               order = (Element)officiant.getParentNode();
               sum += Integer.parseInt(order.getElementsByTagName("totalcost").item(0).getTextContent());
           }
       }
       return sum;
   }

   private Element findDay(Calendar calendar){
       NodeList days = document.getElementsByTagName("date");
       Element element;
       for(int i = 0; i < days.getLength(); i++){
           element = (Element) days.item(i);
           if(Integer.parseInt(element.getAttribute("day")) == calendar.get(Calendar.DATE) &&
                   Integer.parseInt(element.getAttribute("month")) == calendar.get(Calendar.MONTH) + 1&&
                   Integer.parseInt(element.getAttribute("year")) == calendar.get(Calendar.YEAR)){
               return element;
           }
       }
       return null;
   }

   public void removeDay(Calendar calendar){
       Element day = findDay(calendar);
       if(day == null)
           return;
       document.getDocumentElement().removeChild(day);
       saveXML();
   }

   public void changeOfficiantName(String oldFirstName, String oldSecondName,
                                  String newFirstName, String newSecondName){
       NodeList officiantList = document.getElementsByTagName("officiant");
       Element officiant;
       for(int i = 0; i < officiantList.getLength(); i++){
           officiant = (Element) officiantList.item(i);
           if(officiant.getAttribute("firstname").equals(oldFirstName) && officiant.getAttribute("secondname").equals(oldSecondName)){
               officiant.setAttribute("firstname", newFirstName);
               officiant.setAttribute("secondname", newSecondName);
           }
       }
       saveXML();
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
