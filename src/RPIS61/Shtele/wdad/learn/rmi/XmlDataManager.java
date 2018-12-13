package RPIS61.Shtele.wdad.learn.rmi;

import RPIS61.Shtele.wdad.resources.objects.*;

import java.rmi.Remote;
import java.util.Calendar;
import java.util.List;

public interface XmlDataManager extends Remote{
    public int earningTotal(Officiant officiant, Calendar date);
    public void removeDay(Calendar date);
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant);
    public List<Order> getOrders(Calendar date);
    public Calendar lastOfficiantWorkDate(Officiant officiant);
}
