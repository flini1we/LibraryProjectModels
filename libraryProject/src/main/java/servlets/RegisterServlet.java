package servlets;

import models.User;
import repositories.UserRepositoryJdbcImpl;
import utils.DatabaseConnection;
import utils.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserRepositoryJdbcImpl userRepository;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            userRepository = new UserRepositoryJdbcImpl(connection);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        user.setRole("READER");

        System.out.println("logged");
        response.sendRedirect("login.jsp");
//        try {
////            userRepository.createUser(user);
//            System.out.println("logged");
//            response.sendRedirect("login.jsp");
//        } catch (SQLException e) {
//            throw new ServletException(e);
//        }
    }
}