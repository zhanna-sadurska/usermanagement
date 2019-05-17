package ua.nure.kn.sadurska.usermanagement.web;

import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DaoFactory;
import ua.nure.kn.sadurska.usermanagement.db.DatabaseException;
import ua.nure.kn.sadurska.usermanagement.db.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.logging.Logger;

public class BrowseServlet extends HttpServlet {

    @Override
    public void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("addButton") != null) {
            add(request, response);
        } else if (request.getParameter("editButton") != null) {
            edit(request, response);
        } else if (request.getParameter("deleteButton") != null) {
            delete(request, response);
        } else if (request.getParameter("detailsButton") != null) {
            details(request, response);
        } else {
            browse(request, response);
        }
    }

    private void add(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        forwardPage("/add", request, response);
    }

    private void edit(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String idString = (String) request.getAttribute("id");
        if (idString == null || idString.isEmpty()) {
            request.setAttribute("error", "Select user");
            forwardPage("/browse.jsp", request, response);
            return;
        }
        try {
            final User user = DaoFactory.getInstance().getUserDao().find(Long.valueOf(idString));
            request.getSession().setAttribute("user", user);
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            forwardPage("/browse.jsp", request, response);
            return;
        }
        forwardPage("/edit", request, response);
    }

    private void delete(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String idString = (String) request.getAttribute("id");
        System.out.println("ID of User to Delete: " + idString);
        if (idString == null || idString.isEmpty()) {
            System.out.println("No user id");
            request.setAttribute("error", "Select user");
            forwardPage("/browse.jsp", request, response);
            return;
        }
        try {
            final UserDao userDao = DaoFactory.getInstance().getUserDao();
            final Long id = Long.valueOf(idString);
            System.out.println("Correct ID of user: " + id);
            userDao.delete(userDao.find(id));
            forwardPage("/browse.jsp", request, response);
        } catch (final Exception e) {
            System.out.println("Exception: " + e.getMessage());
            request.setAttribute("error", "Error: " + e.getMessage());
            forwardPage("/browse.jsp", request, response);
        }
    }

    private void details(final HttpServletRequest request, final HttpServletResponse response) {

    }

    private void browse(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            final Collection<User> users = DaoFactory.getInstance().getUserDao().findAll();
            System.out.println("Settings users attribute to session: " + users);
            request.getSession().setAttribute("users", users);
            forwardPage("/browse.jsp", request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException(e);
        }
    }

    private void forwardPage(final String page, final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Forwarding to " + page);
        request.getRequestDispatcher(page).forward(request, response);
    }
}
