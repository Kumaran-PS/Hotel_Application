package api;

import model.Customer;
import model.IRoom;
import model.Room;
import service.CustomerService;
import service.ReservationService;
import java.util.Collection;

public class AdminResource {
    // static reference
    private static AdminResource adminResource = null;
    private AdminResource() {}

    public static AdminResource getInstance() {
        if (adminResource != null) return adminResource;
        adminResource = new AdminResource();
        return adminResource;
    }

    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();
    // Defining all methods as per the course rubric
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(Room rooms) {
        reservationService.addRoom(rooms);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
