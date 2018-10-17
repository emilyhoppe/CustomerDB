/** ***********
 *
 *      Class:         SearchServlet.java
 *      Package:       javaeetutorial.CustomerDBServlets
 *      Date:          July 2018
 *
 *      Course: UMUC CMIS 440 Advanced Programming in Java
 *
 *      Class Description: Searches for a customer in the database using servlets.
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
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    //Fields
    private static String dbURL = "jdbc:derby://localhost:1527/contact;create=true;user=nbuser;password=nbuser";
    private static String tableName = "Customers";
    private static Connection conn = null;
    private static Statement stmt = null;
    private PrintWriter out;
    ResultSet rset = null;

    //Methods
    
    //Sets html form to gather search criteria to find a Customer in the database
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
            out = response.getWriter();
            
            //Set header
            out.println("<html lang=\"en\">"
                    + "<head><title>Search Customers</title></head>");
            
            //Set image
            out.println("<body  bgcolor=\"#ffffff\">"
                    + "<img src=\"resources/images/smiley.png\" "
                    + "alt=\"Smiley\">");
            
            //Create form
            out.println("<form method=\"get\">"
                    + "<h2>Search Customers:</h2>"
                    + "<p>Check and Enter 2 or More Items"
                    //ID
                    + "<div>\n"
                    + "      <input type=\"checkbox\" id=\"id\" name=\"search\" value=\"id\">\n"
                    + "      <label for=\"id\">ID:</label>\n"
                    + "<input title=\"ID: \" type=\"text\" "
                    + "name=\"id\" size=\"25\"/>"
                    + "    </div>\n"
                    //Last name
                    + "    <div>\n"
                    + "      <input type=\"checkbox\" id=\"lastName\" name=\"search\" value=\"lastName\">\n"
                    + "      <label for=\"lastName\">Last Name:</label>\n"
                    + "<input title=\"Last name: \" type=\"text\" "
                    + "name=\"lastName\" size=\"25\"/>"
                    + "    </div>\n"
                    //First name
                    + "    <div>\n"
                    + "      <input type=\"checkbox\" id=\"firstName\" name=\"search\" value=\"firstName\">\n"
                    + "      <label for=\"firstName\">First Name:</label>\n"
                    + "<input title=\"First Name: \" type=\"text\" "
                    + "name=\"firstName\" size=\"25\"/>"
                    + "    </div>\n"
                    //Street
                    + "    <div>\n"
                    + "      <input type=\"checkbox\" id=\"street\" name=\"search\" value=\"street\">\n"
                    + "      <label for=\"street\">Street:</label>\n"
                    + "<input title=\"Street: \" type=\"text\" "
                    + "name=\"street\" size=\"25\"/>"
                    + "    </div>\n"
                    //City
                    + "    <div>\n"
                    + "      <input type=\"checkbox\" id=\"city\" name=\"search\" value=\"city\">\n"
                    + "      <label for=\"city\">City:</label>\n"
                    + "<input title=\"City: \" type=\"text\" "
                    + "name=\"city\" size=\"25\"/>"
                    + "    </div>\n"
                    //State
                    + "    <div>\n"
                    + "      <input type=\"checkbox\" id=\"state\" name=\"search\" value=\"state\">\n"
                    + "      <label for=\"state\">State:</label>\n"
                    + "<input title=\"State: \" type=\"text\" "
                    + "name=\"state\" size=\"25\"/>"
                    + "    </div>"
                    //Zip
                    + "<div>\n"
                    + "      <input type=\"checkbox\" id=\"zip\" name=\"search\" value=\"zip\">\n"
                    + "      <label for=\"zip\">Zip:</label>\n"
                    + "<input title=\"Zip: \" type=\"text\" "
                    + "name=\"zip\" size=\"25\"/>"
                    + "    </div>\n"
                    //Submit and reset
                    + "<p><input type=\"submit\" value=\"Submit\"/>"
                    + "<input type=\"reset\" value=\"Reset\"/></p>"
                    + "</form>");
            
            //Provide links to perform other operations on the database
            out.println("<h3><a href=\"http://localhost:8080/CustomerDB/insert\">Insert Customers</a></h3>");
            out.println("<h3><a href=\"http://localhost:8080/CustomerDB/view\">View Customers</a></h3>");
            
            //Retrieve data from user for search criteria
            String id = request.getParameter("id");
            String lastName = request.getParameter("lastName");
            String firstName = request.getParameter("firstName");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");
            
            //Array of search criteria names gathered from checkbox names in html form
            String search[] = request.getParameterValues("search");
            
            //Verify that there was data entered
            if ((id != null && id.length() > 0) || (lastName != null && lastName.length() > 0) || (firstName != null && firstName.length() > 0) || (street != null && street.length() > 0) || (city != null && city.length() > 0) || (state != null && state.length() > 0) || (zip != null && zip.length() > 0)) {

                //Ensure that at least 2 search criterias were entered
                //Then call doGet method from FoundServlet.java
                if (search.length >= 2) {
                    for (int i = 0; i < search.length; i++) {
                    }
                    RequestDispatcher dispatcher
                            = getServletContext().getRequestDispatcher("/found");

                    if (dispatcher != null) {
                        dispatcher.include(request, response);
                    }
                }
            }
        } catch (Exception ex) {
            out.println(ex);
        }
        out.println("</body></html>");
    }

    @Override
    public String getServletInfo() {
        return "The Response servlet says hello.";

    }
}
