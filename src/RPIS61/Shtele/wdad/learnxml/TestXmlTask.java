package RPIS61.Shtele.wdad.learnxml;

import java.io.File;
import java.util.Calendar;

public class TestXmlTask {
    public static void main(String[] args){
        XmlTask xmlTask = new XmlTask("Wrongtotalcost");
        xmlTask.changeOfficiantName("Arthur", "Williams", "qwerty", "asdfg");

        xmlTask = new XmlTask(new File("src/RPIS61/Shtele/wdad/learnxml/Xmlfordate"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018,0,1);
        xmlTask.removeDay(calendar);

        xmlTask = new XmlTask("Correct XML");
        System.out.println(xmlTask.earningTotal("Snow", calendar));
    }
}
