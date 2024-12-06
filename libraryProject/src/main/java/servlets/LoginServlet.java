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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserRepositoryJdbcImpl userRepository;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            userRepository = new UserRepositoryJdbcImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Отображаем страницу входа
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseConnection.getConnection()) {
            UserRepositoryJdbcImpl userRepository = new UserRepositoryJdbcImpl(connection);
            User user = userRepository.findByUsername(username);

            if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("welcome.jsp");
            } else {
                response.sendRedirect("login.jsp?error=Invalid%20credentials");
            }
        } catch (SQLException e) {
            throw new ServletException("Error when checking user credentials", e);
        }
    }
}
