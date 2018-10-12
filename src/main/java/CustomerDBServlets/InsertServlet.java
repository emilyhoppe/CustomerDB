/** ***********
 *
 *      Class:         InsertServlet.java
 *      Package:       javaeetutorial.hello2
 *      Date:          July 2018
 *
 *      Course: UMUC CMIS 440 Advanced Programming in Java
 *
 *      Class Description: Inserts a customer into the database using servlets.
 *              Uses GlassFish Server and JavaDB.
 *
 *********** */

package CustomerDBServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/insert")
public class InsertServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setBufferSize(8192);
        try (PrintWriter out = response.getWriter()) {
            out.println("<html lang=\"en\">"
                    + "<head><title>Insert Customers</title></head>");

            // Write the data of the response
            out.println("<body  bgcolor=\"#ffffff\">"
                    + "<img src=\"resources/images/smiley.png\" "
                    + "alt=\"Duke waving his hand\">"
                    + "<form method=\"get\">"
                    + "<h2>Insert Customer: </h2>"
                    + "<div>Customer ID:"
                    + "<input title=\"ID: \" type=\"text\" "
                    + "name=\"id\" size=\"25\"/>"
                    + "</div>"
                    + "<div>Last Name: "
                    + "<input title=\"Last Name: \" type=\"text\" "
                    + "name=\"lastName\" size=\"25\"/>"
                    + "</div>"
                    + "<div>First Name: "
                    + "<input title=\"First Name: \" type=\"text\" "
                    + "name=\"firstName\" size=\"25\"/>"
                    + "</div>"
                    + "<div>Street: "
                    + "<input title=\"Street: \" type=\"text\" "
                    + "name=\"street\" size=\"25\"/>"
                    + "</div>"
                    + "<div>City: "
                    + "<input title=\"City: \" type=\"text\" "
                    + "name=\"city\" size=\"25\"/>"
                    + "</div>"
                    + "<div>State: "
                    + "<input title=\"State: \" type=\"text\" "
                    + "name=\"state\" size=\"25\"/>"
                    + "</div>"
                    + "<div>Zip: "
                    + "<input title=\"Zip: \" type=\"text\" "
                    + "name=\"zip\" size=\"25\"/>"
                    + "</div>"
                    + "<br><input type=\"submit\" value=\"Submit\"/>"
                    + "<input type=\"reset\" value=\"Reset\"/>"
                    + "</form>");
            out.println("<h3><a href=\"http://localhost:8080/CustomerDB/search\">Search Customers</a></h3>");
            out.println("<h3><a href=\"http://localhost:8080/CustomerDB/view\">View Customers</a></h3>");
            String id = request.getParameter("id");
            String lastName = request.getParameter("lastName");
            String firstName = request.getParameter("firstName");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");
            if ((id != null && id.length() > 0) && (lastName != null && lastName.length() > 0) && (firstName != null && firstName.length() > 0) && (street != null && street.length() > 0) && (city != null && city.length() > 0) && (state != null && state.length() > 0) && (zip != null && zip.length() > 0)) {
                RequestDispatcher dispatcher
                        = getServletContext().getRequestDispatcher("/added");

                if (dispatcher != null) {
                    dispatcher.include(request, response);
                }
            }
            out.println("</body></html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "The Hello servlet says hello.";

    }
}
