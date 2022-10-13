package Conn;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class Conn {
    Connection c;  //conn create
    public Statement s;

    public Conn() {

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Registration", "root", "1234");
            s = c.createStatement();
        } catch (Exception e) {
            System.out.println(e);

        }
    }

}


