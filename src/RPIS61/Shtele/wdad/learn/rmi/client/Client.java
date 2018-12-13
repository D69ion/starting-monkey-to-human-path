package RPIS61.Shtele.wdad.learn.rmi.client;

import RPIS61.Shtele.wdad.data.managers.PreferencesManager;
import RPIS61.Shtele.wdad.learn.rmi.XmlDataManager;
import RPIS61.Shtele.wdad.resources.objects.Order;
import RPIS61.Shtele.wdad.utils.PreferencesManagerConstants;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.List;

public class Client {
    private static PreferencesManager manager;
    private static Registry registry;

    public static void main(String[] args){
        System.out.println("Client started");
        manager = PreferencesManager.getInstance();
        System.setProperty("java.rmi.server.hostname", manager.getProperty(PreferencesManagerConstants.registryaddress));
        System.setProperty("java.security.policy", manager.getProperty(PreferencesManagerConstants.policypath));
        System.setProperty("java.rmi.server.usecodebaseonly", manager.getProperty(PreferencesManagerConstants.usecodebaseonly));

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Security manager created");
        }

        System.out.println("Authorization");
        try{
            int port = Integer.parseInt(manager.getProperty(PreferencesManagerConstants.registryport));
            String address = manager.getProperty(PreferencesManagerConstants.registryaddress);
            registry = LocateRegistry.getRegistry(address, port);
            XmlDataManager xmlDataManager = (XmlDataManager) registry.lookup("XmlDataManager");
            System.out.println("object lookup");
            testRMI(xmlDataManager);
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Client crashed");
            e.printStackTrace();
        }
    }

    private static void testRMI(XmlDataManager xmlDataManager)throws RemoteException{
        Calendar date = Calendar.getInstance();
        date.set(2018, Calendar.DECEMBER, 10);
        List<Order> orderList = xmlDataManager.getOrders(date);
        for(int i = 0; i < orderList.size(); i++){
            System.out.println(orderList.get(i).toString());
        }
    }
}
