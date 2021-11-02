package iTProger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3307";
    private final String DB_NAME = "java_db";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private int user_id;
    private List<Integer> listItem = new ArrayList();

    private Connection dbConn = null;

    private Connection getDBConnection () throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnection () throws SQLException, ClassNotFoundException {
        dbConn = getDBConnection();
        System.out.println(dbConn.isValid(1000));
    }

//    public void createTable (String tableName) throws SQLException, ClassNotFoundException {
//        String sql = "CREATE TABLE IF NOT EXISTS " + tableName
//                + " (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), password VARCHAR(100))"
//                + " ENGINE=MYISAM;";
//
//        Statement statement = getDBConnection().createStatement();
//        statement.executeUpdate(sql);
//    }

    public void getUserId(String nameTable, String columnName, String logName) throws SQLException, ClassNotFoundException  {
        String sql = "SELECT * FROM " + nameTable + " WHERE " + "`" +  columnName + "`" + "=" + "'" + logName + "'";
//        System.out.println(sql);

        Statement statement = getDBConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        if (res.next()) {
            user_id = res.getInt("id");
        }
    }

    public List<Integer> getListItem() {
        return listItem;
    }

    public int getUser_id() {
        return user_id;
    }

    public void getItemsId (String nameTable, String columnName, String logName) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM " + nameTable + " WHERE " + "`" +  columnName + "`" + "=" + "'" + logName + "'";
//        System.out.println(sql);
        Statement statement = getDBConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        while (res.next()) {
            listItem.add(res.getInt("id"));
        }
//        System.out.println(listItem);
    }

    public void setOrders (int user_id, List<Integer> items) throws SQLException, ClassNotFoundException {
        for (int el: listItem) {
            String sql = "INSERT INTO `orders` (user_id, item_id) VALUES (? , ?)";
//            System.out.println(sql);
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(sql);
        preparedStatement.setInt(1, user_id);
        preparedStatement.setInt(2, el);
        preparedStatement.executeUpdate();
        }
    }

    public void printOrder () throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `orders` JOIN `users` ON orders.user_id = users.id JOIN `items` ON orders.item_id = items.id";

        Statement statement = getDBConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        while (res.next()) {
            System.out.println((res.getString("login")) + " - " + (res.getString("title")));
        }
    }
}
