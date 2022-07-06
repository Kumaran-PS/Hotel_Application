import model.Customer;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "test@domain.com");
        System.out.println(customer);
        // checking with invalid email
        Customer customer1 = new Customer("first", "second", "email");
        System.out.println(customer1);
    }
}
