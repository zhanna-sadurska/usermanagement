package ua.nure.kn.sadurska.usermanagement.db;

public class DatabaseException extends Exception {

    private static final long serialVersionUID = 6100632646481854551L;

    public DatabaseException() {
        super();
    }

    public DatabaseException(final String s) {
        super(s);
    }
}
