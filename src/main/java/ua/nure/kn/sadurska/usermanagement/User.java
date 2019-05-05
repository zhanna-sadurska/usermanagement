package ua.nure.kn.sadurska.usermanagement;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {

    private static final long serialVersionUID = -1781918295756873609L;

    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public User() {

    }

    public User(final Long id, final String firstName, final String lastName, final Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return new StringJoiner(", ")
                .add(lastName)
                .add(firstName)
                .toString();
    }

    public int getAge() {
        final GregorianCalendar thisDate = new GregorianCalendar(TimeZone.getTimeZone("EET"));
        final GregorianCalendar birthDate = new GregorianCalendar();
        birthDate.setTime(dateOfBirth);

        // Birth date in this year
        final GregorianCalendar fakeDate = new GregorianCalendar(
                thisDate.get(Calendar.YEAR),
                birthDate.get(Calendar.MONTH),
                birthDate.get(Calendar.DAY_OF_MONTH),
                birthDate.get(Calendar.HOUR_OF_DAY),
                birthDate.get(Calendar.MINUTE),
                birthDate.get(Calendar.SECOND));

        final int age = thisDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        return fakeDate.compareTo(thisDate) < 0 ? age : age - 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(dateOfBirth, user.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, dateOfBirth);
    }
}
