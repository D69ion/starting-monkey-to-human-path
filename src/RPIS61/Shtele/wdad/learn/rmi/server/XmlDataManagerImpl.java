package RPIS61.Shtele.wdad.learn.rmi.server;

import RPIS61.Shtele.wdad.learn.rmi.XmlDataManager;
import RPIS61.Shtele.wdad.learn.xml.XmlTask;
import RPIS61.Shtele.wdad.resources.objects.Officiant;
import RPIS61.Shtele.wdad.resources.objects.Order;

import java.util.Calendar;
import java.util.List;

public class XmlDataManagerImpl implements XmlDataManager {
    private XmlTask restaurant;

    public XmlDataManagerImpl(String fileName){
        this.restaurant = new XmlTask(fileName);
    }

    @Override
    public int earningTotal(Officiant officiant, Calendar date) {
        return this.restaurant.earningTotal(officiant.getSecondName(), date);
    }

    @Override
    public void removeDay(Calendar date) {
        this.restaurant.removeDay(date);
    }

    @Override
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) {
        this.restaurant.changeOfficiantName(oldOfficiant.getFirstName(), oldOfficiant.getSecondName(),
                newOfficiant.getFirstName(), newOfficiant.getSecondName());
    }

    @Override
    public List<Order> getOrders(Calendar date) {
        return this.restaurant.getOrders(date);
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) {
        return this.restaurant.lastOfficiantWorkDate(officiant);
    }
}
