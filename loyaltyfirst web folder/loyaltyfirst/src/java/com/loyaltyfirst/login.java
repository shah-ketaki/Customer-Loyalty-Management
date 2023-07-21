/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.loyaltyfirst;

import java.io.IOException;

//Java Servlet Classes imported
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Java JDBC drivers for the connection to database
import java.sql.*; 

/**
 *
 * @author KetakiS
 */
public class login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {       
        
        String uname = request.getParameter("user");
        String pwd = request.getParameter("pass");
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession();
        
       try{
          Connection conn;
          String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
          conn = DriverManager.getConnection(url,"kshah30" , "utilsooh");
          
          String query = "select L.cid from Login L where L.username = '" + uname + "' AND L.PASSWD='" + pwd+"'";
          Statement st = conn.createStatement();
          ResultSet rs = st.executeQuery(query);
          if (rs.next() == false)
          {
            out.print("no");
            conn.close();
            
          }
          else
          {
            out.print("yes:" + rs.getString("CID")); 
            session.setAttribute("CID", rs.getString("CID") );
            conn.close();
            
          }
       }
       catch(Exception e)
       {
           out.println(e);
       }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
