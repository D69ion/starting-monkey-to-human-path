package RPIS61.Shtele.wdad.learn.xml;

import RPIS61.Shtele.wdad.resources.objects.Item;
import RPIS61.Shtele.wdad.resources.objects.Officiant;
import RPIS61.Shtele.wdad.resources.objects.Order;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class XmlTask implements Serializable{
    private final String XML_CATALOG = "src/RPIS61/Shtele/wdad/xml/";
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
                   Integer.parseInt(element.getAttribute("month")) == calendar.get(Calendar.MONTH) &&
                   Integer.parseInt(element.getAttribute("year")) == calendar.get(Calendar.YEAR)){
               return element;
           }
       }
       return null;
   }

   public void removeDay(Calendar calendar){
       Element day = findDay(calendar);
       if(day == null){
           System.out.println("0");
           return;
       }
       document.getDocumentElement().removeChild(day);
       saveXML();
       System.out.println("1");
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

   public List<Order> getOrders(Calendar day){
       List<Order> orders = new ArrayList<>();
       List<Item> items = new ArrayList<>();
       Element orderElement, officiantElement, itemElement;
       NodeList ordersList = findDay(day).getElementsByTagName("order"), itemsList;
       Officiant officiant;

       for(int i = 0; i < ordersList.getLength(); i++){
           orderElement = (Element) ordersList.item(i);
           officiantElement = (Element) orderElement.getElementsByTagName("officiant").item(0);
           officiant = new Officiant(officiantElement.getAttribute("firstname"),
                   officiantElement.getAttribute("secondname"));
           itemsList = officiantElement.getElementsByTagName("item");
           for(int j = 0; j < itemsList.getLength(); j++){
               itemElement = (Element) itemsList.item(j);
               items.add(new Item(itemElement.getAttribute("name"), Integer.parseInt(itemElement.getAttribute("cost"))));
           }
           orders.add(new Order(officiant, items));
       }
       return orders;
   }

    public Calendar lastOfficiantWorkDate(Officiant officiant){
       NodeList officiantsList = this.document.getElementsByTagName("officiant");
       Element tmp;
       int day, month, year;
       Calendar calendar = Calendar.getInstance();
       for(int i = 0; i < officiantsList.getLength(); i++){
           tmp = (Element) officiantsList.item(i);
           if(tmp.getAttribute("firstname").equals(officiant.getFirstName()) &&
                   tmp.getAttribute("secondname").equals(officiant.getSecondName())){
               tmp = (Element) tmp.getParentNode().getParentNode();
               day = Integer.parseInt(tmp.getAttribute("day"));
               month = Integer.parseInt(tmp.getAttribute("month"));
               year = Integer.parseInt(tmp.getAttribute("year"));
               calendar.set(year, month, day);
           }
       }
       return calendar;
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
