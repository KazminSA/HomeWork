package iTProger;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args)  {

        DB db = new DB();
        try {

//            db.getUserId("users", "login", "john");
//            db.getItemsId("items", "category", "hats");
//            db.setOrders(db.getUser_id(), db.getListItem());
            db.printOrder();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
