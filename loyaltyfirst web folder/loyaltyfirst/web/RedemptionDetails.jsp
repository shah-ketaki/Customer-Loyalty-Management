<%-- 
    Document   : RedemptionDetails
    Created on : 25-Nov-2022, 1:41:39 am
    Author     : KetakiS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import= "java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Redemption Details</title>
    </head>
    <body>
       
        <!<!-- Service -->
        <%
            try{
                String prizeid = request.getParameter("prizeid");
                String cid = request.getParameter("cid");
                Connection conn;
                String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                conn=DriverManager.getConnection(url,"kshah30" , "utilsooh");
                String query = "select P.p_description, P.points_needed, RH.R_DATE,EC.CENTER_NAME from Redemption_History RH, EXCHGCENTERS EC, Prizes P where EC.CENTER_ID=RH.CENTER_ID AND RH.cid="+ cid +" AND P.PRIZE_ID=" + prizeid;
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
