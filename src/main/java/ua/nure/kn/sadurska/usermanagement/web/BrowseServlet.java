package ua.nure.kn.sadurska.usermanagement.web;

import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class BrowseServlet extends HttpServlet {

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        browse(request, response);
    }

    private void browse(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        Collection<User> users;
        try {
            users = DaoFactory.getInstance().getUserDao().findAll();
            request.getSession().setAttribute("users", users);
            request.getRequestDispatcher("/browse.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
