package RPIS61.Shtele.wdad.data.managers;

import RPIS61.Shtele.wdad.data.storage.DataSourceFactory;
import RPIS61.Shtele.wdad.resources.objects.Item;
import RPIS61.Shtele.wdad.resources.objects.Officiant;
import RPIS61.Shtele.wdad.resources.objects.Order;

import javax.sql.DataSource;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JDBCDataManager implements DataManager{
    private DataSource dataSource;

    public JDBCDataManager(){
        dataSource = DataSourceFactory.createDataSource();
    }

    @Override
    public int earningTotal(Officiant officiant, Calendar date) throws RemoteException {
        int totalCost = -1;
        String sql = "select sum(`items`.`cost` * `items_orders`.`quantity`) as `totalcost` from `orders` join `items_orders` on (`orders`.`id` = `items_orders`.`orders_id`) join `items` on (`items`.`id` = `items_orders`.`items_dictionary_id`) join `officiants` on (`officiants`.`id` = `orders`.`officiant_id`) where (`officiants.`first_name` = ? and `officiants`.`second_name` =?) and (date(`orders`.`date`) = ?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, officiant.getFirstName());
            statement.setString(2, officiant.getSecondName());
            statement.setDate(3, new Date(date.getTime().getTime()));
            ResultSet result = statement.executeQuery();
            if(result.next()){
                totalCost = result.getInt("totalcost");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCost;
    }

    @Override
    public void removeDay(Calendar date) throws RemoteException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement("select `id` from `orders` where `date(`date`)` = ?");
            PreparedStatement deleteOrderStatement = connection.prepareStatement("delete from `orders` where date(`date`) = ?");
            PreparedStatement deleteItemsStatement = connection.prepareStatement("delete from `items_orders` where `orders_id` = ?")
        ){
            selectStatement.setDate(1, new Date(date.getTime().getTime()));
            ResultSet result = selectStatement.executeQuery();
            if(!result.next())
                return;
            int id = result.getInt("id");

            deleteOrderStatement.setString(1, String.valueOf(id));
            deleteOrderStatement.executeUpdate();
            deleteItemsStatement.setString(1, String.valueOf(id));
            deleteItemsStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws RemoteException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("update `officiants` set `first_name` = ?, `second_name` = ? where `first_name` = ? and `second_name` = ?")){
            statement.setString(1, newOfficiant.getFirstName());
            statement.setString(2, newOfficiant.getSecondName());
            statement.setString(3, oldOfficiant.getFirstName());
            statement.setString(4, oldOfficiant.getSecondName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrders(Calendar date) throws RemoteException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("select `orders`.`id`, `officiants`.`first_name`, `officiants`.`second_name`, `items`.`name`, `items`.`cost`, `items_orders`.`quantity` from `orders` join `officiants` on (`orders`.`officiant_id` = `officiants`.`id`) join `items_orders` on (`orders`.`id` = `items_orders`.`orders_id`) join `items` on (`items_orders`.`items_dictionary_id` = `items`.`id`) where date(`orders`.`date`) = ?")){
            statement.setDate(1, new Date(date.getTime().getTime()));
            ResultSet result = statement.executeQuery();
            if(!result.next())
                return null;
            result.previous();
            List<Order> orderList = new ArrayList<>();
            List<Item> itemList = new ArrayList<>();
            Order order;
            Item item;
            Officiant officiant;

            int id = 0;
            while (result.next()){
                id = result.getInt("id");
                officiant = new Officiant(result.getString("first_name"), result.getString("second_name"));
                while(id == result.getInt("id")){
                    itemList.add(new Item(result.getString("name"), result.getInt("cost")));
                    if(!result.next()){
                        break;
                    }
                }
                orderList.add(new Order(officiant, itemList));
                itemList = new ArrayList<>();
                if(!result.next()){
                    break;
                }
                result.previous();
            }
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) throws RemoteException {
        Calendar calendar = Calendar.getInstance();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("select max(`date`) as `max_date` from `orders` join `officiants` on (`orders`.`officiant_id` = `officiants`.`id` where `officiants`.`first_name` = ? and `officiants`.`second_name` = ?)")){
            statement.setString(1, officiant.getFirstName());
            statement.setString(2, officiant.getSecondName());
            ResultSet result = statement.executeQuery();
            if(result.next()){
                calendar.setTime(result.getDate("max_date"));
                return calendar;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
