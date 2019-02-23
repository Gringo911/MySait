import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by ВАСЯ on 18.02.2019.
 */
@WebServlet("/TestServlet")

public class TestServlet extends HttpServlet {


    private Connection connection;
    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();

        try {

            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");

            Class.forName(driverClassName);



            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }catch (SQLException e){
            throw new IllegalStateException(e);

        }catch (ClassNotFoundException e){
            throw new IllegalStateException(e);


        }
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/index.jsp").forward(request, response);

        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");



        try {
            Statement statement = connection.createStatement();
          String sqlInsert = "INSERT INTO book2(first_name, last_name)" +
                    "VALUES('" + firstName + "','" + lastName + "');";
            System.out.println(sqlInsert);



          statement.execute(sqlInsert);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }
}