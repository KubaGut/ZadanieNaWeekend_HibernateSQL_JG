import Hibernate_MySQL.MySQL_Util;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        long startTime = System.currentTimeMillis();

        MySQL_Util.saveObjectToDataBase();
        MySQL_Util sql = new MySQL_Util();
        sql.countSubordinates(20000);
        sql.showSubordinates();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println(totalTime);

    }
}