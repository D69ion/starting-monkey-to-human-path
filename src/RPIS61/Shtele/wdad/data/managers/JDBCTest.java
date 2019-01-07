package RPIS61.Shtele.wdad.data.managers;

import RPIS61.Shtele.wdad.resources.objects.Order;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public class JDBCTest {
    public static void main(String[] args){
        JDBCDataManager dataManager = new JDBCDataManager();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.OCTOBER, 5);
        try {
            List<Order> orders = dataManager.getOrders(calendar);
            System.out.println(orders.size());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
