<%-- 
    Document   : FamilyIncrease
    Created on : 25-Nov-2022, 1:42:47 am
    Author     : KetakiS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import= "java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Family Increase</title>
    </head>
    <body>
        
        <!<!-- Service -->
        <%
            try{
                String cid = request.getParameter("cid");
                String FAMILY_ID = request.getParameter("fid");
                int npoints = Integer.parseInt(request.getParameter("npoints"));
                Connection conn;
                String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                conn=DriverManager.getConnection(url,"kshah30" , "utilsooh");
                String query = "select C.cid from Customers C where C.FAMILY_ID =" + FAMILY_ID +" AND C.cid!=" + cid;
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                boolean flag = true;
                while(rs.next())
                {
                    Statement st1 = conn.createStatement();
                    ResultSet rs1 = st1.executeQuery("select P.NUM_OF_POINTS from POINT_ACCOUNTS P where P.CID=" + rs.getString("cid"));
                    rs1.next();
                    int np = (rs1.getInt("NUM_OF_POINTS"))+ npoints;
                    //out.println(rs.getString("cid")+ " :" + rs1.getInt("NUM_OF_POINTS") +  " : " + np + "<br>");
                    Statement st2 = conn.createStatement();
                    if(st2.executeUpdate("update POINT_ACCOUNTS PA SET PA.NUM_OF_POINTS=" + np + " where PA.CID=" + rs.getString("cid")) == 0)
                    {
                        flag = false;
                    }
                    st2.executeQuery("COMMIT");                   
                }
                if(flag == false){
                    out.println("Error");
                }
                else{
                    out.println(npoints + " Points added to the members of Family ID: "+ FAMILY_ID);
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
