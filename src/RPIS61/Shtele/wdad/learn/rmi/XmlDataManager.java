package RPIS61.Shtele.wdad.learn.rmi;

import RPIS61.Shtele.wdad.resources.objects.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public interface XmlDataManager extends Remote{
    public int earningTotal(Officiant officiant, Calendar date)throws RemoteException;
    public void removeDay(Calendar date)throws RemoteException;
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant)throws RemoteException;
    public List<Order> getOrders(Calendar date)throws RemoteException;
    public Calendar lastOfficiantWorkDate(Officiant officiant)throws RemoteException;
}
