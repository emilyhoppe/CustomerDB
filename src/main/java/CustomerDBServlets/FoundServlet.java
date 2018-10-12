/** ***********
 *
 *      Class:         FoundServlet.java
 *      Package:       javaeetutorial.CustomerDBServlets
 *      Date:          July 2018
 *
 *      Course: UMUC CMIS 440 Advanced Programming in Java
 *
 *      Class Description: Finds and displays a customer from the database using servlets.
 *              Uses GlassFish Server and JavaDB.
 *
 *********** */

package CustomerDBServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/found")
public class FoundServlet extends HttpServlet {

    private static String dbURL = "jdbc:derby://localhost:1527/contact;create=true;user=nbuser;password=nbuser";
    private static String tableName = "Customers";
    private static Connection conn = null;
    private static Statement stmt = null;
    private PrintWriter out;
    ResultSet rset = null;

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
            out = response.getWriter();
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(dbURL);
            int id = 0;
            String custID = request.getParameter("id");
            if (custID != null && custID.length() > 0) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");
            String search[] = request.getParameterValues("search");
            String where = "";
            String values[] = new String[search.length];
            boolean idPresent = false;
            if (search[0].equals("id")) {
                idPresent = true;
                for (int i = 1; i < search.length; i++) {
                    values[i] = request.getParameter(search[i]);
                    where += " AND (" + search[i] + " = '" + values[i] + "') ";
                }
            }else{
                for (int i = 0; i < search.length; i++) {
                    values[i] = request.getParameter(search[i]);
                    if (i == 0) {
                        where += " (" + search[i] + " = '" + values[i] + "') ";
                    }else{
                        where += " AND (" + search[i] + " = '" + values[i] + "') ";
                    }
                }
            }
            stmt = conn.createStatement();
            if (idPresent) {
                rset = stmt.executeQuery("select * from " + tableName + " where " + "(id = " + id + ") " + where);
            }else{
                rset = stmt.executeQuery("select * from " + tableName + " where " + where);
            }
            out.println("<h2>Customers Found:</h2>");
            out.println("<table><tr><th>ID</th><th>Last Name</th>");
            out.println("<th>First Name</th><th>Street</th><th>City</th><th>State</th><th>Zip</th></tr>");
            while (rset.next()) {
                out.println("<tr><td>" + rset.getInt("id") + "</td>");
                out.println("<td>" + rset.getString("lastName") + "</td>");
                out.println("<td>" + rset.getString("firstName") + "</td>");
                out.println("<td>" + rset.getString("street") + "</td>");
                out.println("<td>" + rset.getString("city") + "</td>");
                out.println("<td>" + rset.getString("state") + "</td>");
                out.println("<td>" + rset.getString("zip") + "</td></tr>");
            }
            out.println("</table>");
        } catch (Exception ex) {
            out.println(ex);
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                out.println("SQLException: " + e.getMessage() + "<br>");
                while ((e = e.getNextException()) != null) {
                    out.println(e.getMessage() + "<br>");
                }
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "The Response servlet says hello.";

    }
}
