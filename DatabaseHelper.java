/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




/**
 *
 * @author Malshan
 */
public class DatabaseHelper {
    
    public static Connection connectToDatabase(String url,String username, String password) throws SQLException, ClassNotFoundException{
    
    Connection connection;
    Class.forName("com.mysql.jdbc.Driver");
    
    connection = DriverManager.getConnection(url,username,password);
    return connection;
 
    } 
}
