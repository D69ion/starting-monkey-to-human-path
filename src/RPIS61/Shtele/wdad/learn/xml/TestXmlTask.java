package RPIS61.Shtele.wdad.learn.xml;

import java.io.File;
import java.util.Calendar;

public class TestXmlTask {
    public static void main(String[] args){
       XmlTask xmlTask = new XmlTask(new File("src/RPIS61/Shtele/wdad/learn/xml/Correct XML"));
        xmlTask.changeOfficiantName("Arthur", "Williams", "qwerty", "asdfg");

        xmlTask = new XmlTask(new File("src/RPIS61/Shtele/wdad/learn/xml/Correct XML"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018,11,10);
        xmlTask.removeDay(calendar);

        xmlTask = new XmlTask("Correct XML");
        System.out.println(xmlTask.earningTotal("Snow", calendar));
    }
}
