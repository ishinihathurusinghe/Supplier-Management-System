/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.DatabaseHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Malshan
 */
@WebServlet(name = "signupController", urlPatterns = {"/signupController","/signup"})
public class signupController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
       try {
           
           String firstName = request.getParameter("firstname");
           String lastName = request.getParameter("lastname");
           String address = request.getParameter("address");
           String username = request.getParameter("username");
           String password = request.getParameter("password");
           
          PrintWriter out = response.getWriter();
           
           PreparedStatement pst;
           
           Connection con;
           
           con = DatabaseHelper.connectToDatabase("jdbc:mysql://localhost:3306/jspcurd","root","");
           
           try {
               if (con != null) {
                   // Define SQL query
                   String sql = "INSERT INTO users (first_name, last_name, username,address, password) VALUES (?, ?, ?, ?, ?)";
                   
                   // Create prepared statement
                   pst = con.prepareStatement(sql);
                   
                   // Set parameters
                   pst.setString(1, firstName);
                   pst.setString(2, lastName);
                   pst.setString(3, username);
                   pst.setString(4, address);
                   pst.setString(5, password);
                   
                   // Execute the query
                   int rowsAffected = pst.executeUpdate();
                   
                   // Check if the query was successful
                   if (rowsAffected > 0) {
                        request.setAttribute("Message","Hello " + firstName + " User registered successfully. You can now log in.");
                        request.getRequestDispatcher("signin.jsp").forward(request, response);
                   } else {
                       out.println("Failed to insert user.");
                   }
               } else {
                   out.println("Failed to establish database connection.");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
              
           
       } catch (SQLException ex) {
            Logger.getLogger(signupController.class.getName()).log(Level.SEVERE, null, ex);
}       catch (ClassNotFoundException ex) {
            Logger.getLogger(signupController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        
        
        
    }

    

}
