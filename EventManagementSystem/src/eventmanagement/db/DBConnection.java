/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/event_management?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String user = "root";
    private static final String password = "";
    
    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e){
            System.out.println("COnnection Failed!");
            e.printStackTrace();
            return null;
        }
   } 
}
