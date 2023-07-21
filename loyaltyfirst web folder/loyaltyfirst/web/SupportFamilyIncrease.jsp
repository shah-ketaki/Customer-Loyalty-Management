<%-- 
    Document   : SupportFamilyIncrease
    Created on : 25-Nov-2022, 1:42:14 am
    Author     : KetakiS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import= "java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Support Family Increase</title>
    </head>
    <body>
        <!<!-- Service -->
        <%
            try{
                String tref = request.getParameter("tref");
                String cid = request.getParameter("cid");
                Connection conn;
                String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                conn=DriverManager.getConnection(url,"kshah30" , "utilsooh");
                String query = "select C.FAMILY_ID, T.T_POINTS from CUSTOMERS C, Transactions T where C.cid=T.cid AND T.TREF=" + tref;
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                
                if (rs.next() == false)
                {
                
                    out.println("error");
                    conn.close();
                    
                }
                out.print(rs.getString("FAMILY_ID") +",");
                out.print((int)((rs.getInt("T_POINTS"))*0.3));
                out.print("," + rs.getString("T_POINTS"));
                
                conn.close();
            }
            catch(Exception e)
            {
                out.println(e);
                
            }
        %>
    </body>
</html>
