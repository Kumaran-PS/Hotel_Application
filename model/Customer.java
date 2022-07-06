package model;
import java.util.*;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName,String lastName, String email) {
        String emailForm = "^(.+)@(.+).(.com)$";
        Pattern checkPattern = Pattern.compile(emailForm);
        if (!checkPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address, please enter correct email ID");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer : " +
                "firstName='" + firstName +
                ", lastName='" + lastName +
                ", email='" + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
