<%-- 
    Document   : Transactions
    Created on : 25-Nov-2022, 1:39:40 am
    Author     : KetakiS
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import= "java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transactions</title>
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
                String query = "select T.tref, T.t_date, T.t_points, T.amount from Transactions T where T.cid="+cid;
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
                        out.print("#"); 
                    } 
                }
                conn.close();
            }
            catch(Exception e)
            {
                out.println(e);
            }
        %>
    </body>
</html>
