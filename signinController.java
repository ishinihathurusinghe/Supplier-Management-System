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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Malshan
 */
@WebServlet(name = "signinController", urlPatterns = {"/signinController"})
public class signinController extends HttpServlet {

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            PrintWriter out = response.getWriter();
            
            PreparedStatement pst;
            Connection con;
            ResultSet rs;
            con = DatabaseHelper.connectToDatabase("jdbc:mysql://localhost:3306/jspcurd","root","");
            
            if (con != null) {
                try {
                    // Define SQL query to check if username and password match
                    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, username);
                    pst.setString(2, password);
                    
                    // Execute the query
                    rs = pst.executeQuery();
                    
                    // Check if a matching user is found
                    if (rs.next()) {  
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        request.setAttribute("Message","Hello " + username );                    
                        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
                    } else {        
                       
                        request.setAttribute("Message","Invalid username or password. Please try again.");  
                        request.getRequestDispatcher("signin.jsp").forward(request, response);
                    }
                    
                    // Close resources
                    rs.close();
                    pst.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(signinController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // Failed to establish database connection
                out.println("Failed to connect to the database.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(signinController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(signinController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }

  

}
