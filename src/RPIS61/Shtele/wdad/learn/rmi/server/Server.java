package RPIS61.Shtele.wdad.learn.rmi.server;

import RPIS61.Shtele.wdad.data.managers.PreferencesManager;
import RPIS61.Shtele.wdad.data.managers.DataManager;
import RPIS61.Shtele.wdad.utils.PreferencesManagerConstants;

import java.io.File;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server {
    private static Registry registry;
    private static PreferencesManager manager;

    public static void main(String[] args) {
        System.out.println("Server started");

        manager = PreferencesManager.getInstance();
        System.setProperty("java.rmi.server.hostname", manager.getProperty(PreferencesManagerConstants.registryaddress));
        System.setProperty("java.security.policy", manager.getProperty(PreferencesManagerConstants.policypath));
        System.setProperty("java.rmi.server.usecodebaseonly", manager.getProperty(PreferencesManagerConstants.usecodebaseonly));

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Security manager created");
        }

        try {
            int port = Integer.parseInt(manager.getProperty(PreferencesManagerConstants.registryport));
            if (manager.getProperty(PreferencesManagerConstants.createregistry).equals("yes")) {
                registry = LocateRegistry.createRegistry(port);
                System.out.println("Registry created");
            } else {
                registry = LocateRegistry.getRegistry(port);
                System.out.println("Registry initialized");
            }

            XmlDataManagerImpl xmlDataManager = new XmlDataManagerImpl(new File("src/RPIS61/Shtele/wdad/learn/xml/Correct XML"));

            DataManager stub = (DataManager) UnicastRemoteObject.exportObject(xmlDataManager, 0);
            System.out.println("Binding object");
            registry.bind("DataManager", xmlDataManager);
            manager.addBindedObject("DataManager", xmlDataManager.getClass().toString());
        } catch (RemoteException | AlreadyBoundException e) {
            System.out.println("Server crashed");
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Server is ready to work");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.nextLine().equals("exit")) {
                System.exit(0);
            }
        }
    }
}
