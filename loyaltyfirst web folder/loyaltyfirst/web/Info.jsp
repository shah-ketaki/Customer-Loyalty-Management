<%-- 
    Document   : Info
    Created on : 25-Nov-2022, 1:39:11 am
    Author     : KetakiS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import= "java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Info</title>
    </head>
    <body>

        <!<!-- Service -->
        <%
            try{
                String cid = request.getParameter("cid");
                Connection conn;
                String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                conn=DriverManager.getConnection(url,"kshah30" , "utilsooh");
                String query = "select C.cname, PA.num_of_points from Customers C, Point_Accounts PA where C.cid=PA.cid AND C.cid="+cid;
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if (rs.next() == false)
                {
                
                    out.print("error");
                    conn.close();
                    
                }
                else
                {
                
                    out.print(rs.getString("cname") + ",");
                    out.print(rs.getString("num_of_points"));
                    conn.close();
                    
                }
            }
            
            catch(Exception e)
            {
                out.println(e);
            }
        %>
        
    </body>
</html>
