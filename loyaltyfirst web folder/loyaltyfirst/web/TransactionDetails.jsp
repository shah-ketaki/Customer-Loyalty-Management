<%-- 
    Document   : TransactionDetails
    Created on : 25-Nov-2022, 1:40:50 am
    Author     : KetakiS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import= "java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaction Details</title>
    </head>
    <body>
        
        <!<!-- Service -->
        <%
            try{
                String tref = request.getParameter("tref");
                Connection conn;
                String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                conn=DriverManager.getConnection(url,"kshah30" , "utilsooh");
                String query = "select T.t_date,T.t_points,P.PROD_NAME,P.PROD_POINTS,TP.quantity from Transactions T, Products P, TRANSACTIONS_PRODUCTS TP where TP.prod_id=P.prod_id AND T.tref=TP.tref AND TP.tref="+tref;
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                {
                   
                    while(rs.next())
                    {
                        String[] list = new String[rsmd.getColumnCount()];
                        for(int i=0;i<rsmd.getColumnCount();i++)
                        {
                            
                            out.print((rs.getObject(i+1).toString()));
                            out.print(",");
                            
                        }
                        out.println("#"); 
                    }

                }
                conn.close();
            }
            catch(Exception e)
            {
                out.println("Error");
            }
        %>
        
    </body>
</html>
