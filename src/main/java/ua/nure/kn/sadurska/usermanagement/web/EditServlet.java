package ua.nure.kn.sadurska.usermanagement.web;

import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DaoFactory;
import ua.nure.kn.sadurska.usermanagement.db.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

public class EditServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        showPage(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("okButton") != null) {
            try {
                doOk(req, resp);
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        } else if (req.getParameter("cancelButton") != null) {
            doCancel(req, resp);
        } else {
            showPage(req, resp);
        }
    }

    protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, DatabaseException {
        User user = null;
        try {
            user = getUser(req);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            showPage(req, resp);
            return;
        }
        try {
            processUser(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    protected User getUser(HttpServletRequest req) throws ValidationException {
        final User user = new User();
        final String idStr = req.getParameter("id");
        final String firstName = req.getParameter("firstName");
        final String lastName = req.getParameter("lastName");
        final String dateStr = req.getParameter("date");

        if (firstName == null) {
            throw new ValidationException("First Name is absent");
        }
        if (lastName == null) {
            throw new ValidationException("Last Name is absent");
        }
        if (dateStr == null) {
            throw new ValidationException("Date is absent");
        }

        if (idStr != null) {
            user.setId(Long.valueOf(idStr));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDateOfBirth(DateFormat.getDateInstance().parse(dateStr));
        } catch (ParseException e) {
            throw new ValidationException("Date format is incorrect");
        }
        return user;
    }

    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().update(user);
    }

    protected void doCancel(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/browse").forward(request, response);
    }

    protected void showPage(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }
}
