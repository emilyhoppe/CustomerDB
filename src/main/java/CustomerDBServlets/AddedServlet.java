/** ***********
 *
 *      Class:         AddedServlet.java
 *      Package:       javaeetutorial.hello2
 *      Date:          July 2018
 *
 *      Course: UMUC CMIS 440 Advanced Programming in Java
 *
 *      Class Description: Adds a customer to the database and displays if successful using servlets. 
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
import javax.swing.JOptionPane;

@WebServlet("/added")
public class AddedServlet extends HttpServlet {

    private static String dbURL = "jdbc:derby://localhost:1527/contact;create=true;user=nbuser;password=nbuser";
    private static String tableName = "Customers";
    private static Connection conn = null;
    private static Statement stmt = null;
    private PrintWriter out;
    private int id = 0;
    ResultSet rset = null;

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
            out = response.getWriter();
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(dbURL);

            id = Integer.parseInt(request.getParameter("id"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");

            stmt = conn.createStatement();
            stmt.execute("insert into " + tableName + " values ("
                    + id + ",'" + lastName + "','" + firstName + "','" + street + "','" + city + "','" + state + "','" + zip + "')");
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Cannot Connect to Database", "Error Message", JOptionPane.OK_OPTION);
            ex.printStackTrace();
        }
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT * FROM " + tableName + " where (id = " + id + ")");
            out.println("<h2>Customer Added:</h2>");
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
            JOptionPane.showMessageDialog(null, "Cannot Connect to Database", "Error Message", JOptionPane.OK_OPTION);
            ex.printStackTrace();
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
