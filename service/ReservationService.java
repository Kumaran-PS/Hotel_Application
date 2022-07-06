package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
// Standard naming conventions followed for objects of classes
public class ReservationService {
    // static reference
    private static ReservationService reservationService = null;
    private ReservationService() {}

    public static ReservationService getInstance() {
        if (reservationService != null) return reservationService;
        reservationService = new ReservationService();
        return reservationService;
    }

    //    Datastructures used Hashmap and Arraylist
    // Defining all methods as per the course rubric

    private final Map<String, IRoom> rooms = new HashMap<>();
    ArrayList<Reservation> reservations = new ArrayList<>();

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public void printAllReservations() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Reservation reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public LinkedList<Reservation> getCustomerReservations(String customerEmail) {
        LinkedList<Reservation> reservationsCustomerEmail = new LinkedList<>();
        for (Reservation reservation : reservations) {
            if (!customerEmail.equals(reservation.getCustomer().getEmail())) continue;
            reservationsCustomerEmail.add(reservation);
        }
        return reservationsCustomerEmail;
    }
// Searching for available rooms and recommend rooms based on input date range
    public List<IRoom> findRooms(Date InDate, Date OutDate) {
        List<IRoom> getAvailableRooms = new ArrayList<>();
        try {
            List<IRoom> sameDayRooms = isRoomReserved(InDate, OutDate);
            for (IRoom room : rooms.values()) {
                if (!sameDayRooms.contains(room)) {
                    getAvailableRooms.add(room);
                }
            }
        }catch (Exception e){
            return !getAvailableRooms.isEmpty() ? getAvailableRooms : null;
        }
        return getAvailableRooms;
    }

    public LinkedList<IRoom> isRoomReserved(Date InDate, Date OutDate) {
        LinkedList<IRoom> bookedRooms = new LinkedList<>();
        try {
            for (Reservation reservation : reservations) {
                // if date range requested falls in already booked date range for a room , add into bookedRooms
                if (reservation.getCheckInDate().getTime() <= InDate.getTime() && reservation.getCheckOutDate().getTime() >= OutDate.getTime()) {
                    bookedRooms.add(reservation.getRoom());
                }
            }
        } catch (Exception e){
            return (!bookedRooms.isEmpty()) ? bookedRooms : null;
        }
        return bookedRooms;
    }

//     The method getCustomerReservation is an additional method

//    public LinkedList<Reservation> getCustomerReservation(Customer customer) {
//        LinkedList<Reservation> reservationCustomerClass = new LinkedList<>();
//        for (Reservation reservation : reservations) {
//            if (!customer.equals(reservation.getCustomer())) continue;
//            reservationCustomerClass.add(reservation);
//        }
//        return reservationCustomerClass;
//    }




}
