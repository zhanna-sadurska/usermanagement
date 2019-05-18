package ua.nure.kn.sadurska.usermanagement.agent;

import ua.nure.kn.sadurska.usermanagement.db.DatabaseException;

public class SearchException extends Exception {
    private static final long serialVersionUID = -7130708675838819841L;

    public SearchException(Exception e) {
        super(e);
    }
}
