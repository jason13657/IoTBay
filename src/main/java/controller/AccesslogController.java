package controller;


import dao.AccessLogDAOImpl;
import dao.UserDAOImpl;
import dao.interfaces.AccessLogDAO;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.AccessLog;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AccesslogController {
    private final AccessLogDAO accessLogDAO;
    private final UserDAO userDAO;

    public AccesslogController() {
        Connection connection = DBConnection.getConnection();
        this.accessLogDAO = new AccessLogDAOImpl(connection);
        this.userDAO = new UserDAOImpl(connection);
    }

}
