package RPIS61.Shtele.wdad.data.storage;

import RPIS61.Shtele.wdad.data.managers.PreferencesManager;
import RPIS61.Shtele.wdad.utils.PreferencesManagerConstants;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    public static DataSource createDataSource(){
        PreferencesManager manager = PreferencesManager.getInstance();
        return createDataSource(manager.getProperty(PreferencesManagerConstants.classname),
                manager.getProperty(PreferencesManagerConstants.drivertype),
                manager.getProperty(PreferencesManagerConstants.hostname),
                Integer.parseInt(manager.getProperty(PreferencesManagerConstants.port)),
                manager.getProperty(PreferencesManagerConstants.dbname),
                manager.getProperty(PreferencesManagerConstants.user),
                manager.getProperty(PreferencesManagerConstants.password));
    }

    public static DataSource createDataSource(String className, String driverType, String host,
                                              int port, String dbName, String user, String password){
        try{
            MysqlDataSource dataSource = new MysqlDataSource();
            Class.forName(className);
            String URL = "jdbc:" + driverType + "://" + host + ":" + port + "/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            dataSource.setURL(URL);
            dataSource.setUser(user);
            dataSource.setPassword(password);
            return dataSource;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
