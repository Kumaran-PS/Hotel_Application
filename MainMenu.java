import api.HotelResource;
import model.Customer;
import model.IRoom;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    public static Scanner scanner;
    public static HotelResource hotelResource = HotelResource.getInstance();

    public static void displayMainMenu(){

        System.out.println("Main Menu:");

        for(int i=0; i<25; i++){
            System.out.print("_");
        }
        System.out.println();
        System.out.println("Welcome to our hotel Booking Menu");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account ");
        System.out.println("4. Admin ");
        System.out.println("5. Exit");
        for(int i=0; i<25; i++){
            System.out.print("_");
        }
        System.out.println();
        System.out.println("Select your choice:");
    }

    public static void menu(){
        Scanner scanner = new Scanner(System.in);
        try {
            boolean exit = true;
            while (exit) {
                displayMainMenu();
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" :
                        findAvailability();
                        break;
                    case "2" :
                        seeMyReservations();
                        break;
                    case "3" :
                        createAccount();
                        break;
                    case "4" :
                        AdminMenu.moveToAdminMenu();
                        break;
                    case "5" :
                        exit = false;
                        System.out.println("Hope You Had Great day , Thank You");
                        break;
                    default :
                        System.out.println("Please enter correct input\n");
                        break;
                }
            }
        } catch(Exception e){
            System.out.println("Please enter correct input\n");
            e.getLocalizedMessage();
        } finally{
            scanner.close();
        }
    }

    private static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        for(int i=0; i<5; i++) System.out.print("_");
        System.out.println();
        boolean notCorrectEmail = true;
        String email = "";
        while(notCorrectEmail){
            System.out.println("To view your reservations enter email in format - name@mail.com : ");
            String mail = scanner.next();
            if (isValid(mail)) {
                notCorrectEmail = false;
                email=mail;
            } else System.out.println("Please enter correct email");
        }
        System.out.println(hotelResource.getCustomerReservations(email));
        for(int i=0; i<5; i++) System.out.print("_");
        System.out.println();
    }
    private static Customer createAccount() {
        Scanner scanner = new Scanner(System.in);
        for(int i=0; i<5; i++) System.out.print("_");
        System.out.println();
        System.out.println("Set up your account by entering your details below :");
        System.out.println("Enter Customer First Name:");
        String firstName = scanner.next();
        System.out.println("Enter Customer Last Name:");
        String lastName = scanner.next();
        boolean notCorrectEmail = true;
        String mail = "";
        while(notCorrectEmail){
            System.out.println("Enter Customer Email format:name@domain.com:");
            String email = scanner.next().trim();
            if (isValid(email)) {
                notCorrectEmail = false;
                mail=email;
            } else System.out.println("Please enter correct email");
        }
        System.out.println("The user account has been added successfully");
        for(int i=0; i<5; i++) System.out.print("_");
        System.out.println();
         hotelResource.createACustomer(mail,firstName,lastName);
         return new Customer(firstName, lastName, mail);
    }

    private static void findAvailability() {

        try {

            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Enter CheckIn Date dd/mm/yyyy example (31/01/2022)");
            String dte = scanner.nextLine();
            Date checkInDate = dateFormat.parse(dte);
            System.out.println("Enter CheckOut Date dd/mm/yyyy example (31/02/2022)");
            Date checkOutDate = dateFormat.parse(scanner.nextLine());
            List<IRoom> unoccupiedRooms = hotelResource.findARoom(checkInDate, checkOutDate);
            if(!unoccupiedRooms.isEmpty()){
                Customer customer;
                boolean notCorrectEmail = true;
                String mail = "";
                while(notCorrectEmail){
                    System.out.println("Please enter email(format : name@mail.com)to verify");
                    String email = scanner.next();
                    if (isValid(email)) {
                        notCorrectEmail = false;
                        mail=email;
                    } else System.out.println("Please enter correct email");
                }
                customer = hotelResource.getCustomer(mail);
                if (customer == null) {
                    System.out.println("Customer was not found.");
                    customer = createAccount();
                }
                System.out.println("Are you willing to book a room? Press 'y' to continue and 'n' to cancel");
                char yesToBook = scanner.next().trim().charAt(0);
                if (yesToBook == 'y') {
                    boolean hotelHasRooms = true;
                    while (hotelHasRooms) {
                        System.out.println("Available rooms:");
                        System.out.println(unoccupiedRooms);
                        System.out.println("Please enter room number from the available rooms:");
                        String roomNumber = scanner.next();
                        IRoom selectedRoom = hotelResource.getRoom(roomNumber);
                        if (unoccupiedRooms.contains(selectedRoom)) {
                            hotelResource.bookARoom(customer, selectedRoom, checkInDate, checkOutDate);
                            System.out.println("The room has been booked successfully");
                            for (int i = 0; i < 5; i++) System.out.print("_");
                            System.out.println();
                            scanner.nextLine();
                            System.out.println("Reservation Summary :" + hotelResource.getCustomerReservations(mail));
                            System.out.println("Enjoy our services!");
                            hotelHasRooms = false;
                        } else {
                            System.out.println("Sorry, room number '" + roomNumber + "' is not available.");
                        }
                    }
                } else {
                    System.out.println("You have cancelled booking");
                    for (int i = 0; i < 5; i++) System.out.print("_");
                    System.out.println();
                }
            }else {
                System.out.println("No rooms available on the requested date !");
                System.out.println("Searching other recommended rooms on other days....");
                Date newCheckInDate = new Date(checkInDate.getTime() + Duration.ofDays(7).toMillis());
                Date newCheckOutDate = new Date(checkOutDate.getTime() + Duration.ofDays(7).toMillis());
                unoccupiedRooms = hotelResource.findARoom(newCheckInDate, newCheckOutDate);
                if(!unoccupiedRooms.isEmpty()){
                    System.out.println("Do you want to book on alternate days between " + newCheckInDate + " and " + newCheckOutDate + "?");
                    boolean input = true;
                    while(input){
                        System.out.println("Enter '1' to continue and '0' to cancel booking");
                        int option = scanner.nextInt();
                        if(option == 1){
                            input = false;
                            Customer customer;
                            boolean notCorrectEmail = true;
                            String mail = "";
                            while(notCorrectEmail){
                                System.out.println("Please enter email(format : name@mail.com)to verify");
                                String email = scanner.next();
                                if (isValid(email)) {
                                    notCorrectEmail = false;
                                    mail=email;
                                } else System.out.println("Please enter correct email");
                            }
                            customer = hotelResource.getCustomer(mail);
                            if (customer == null) {
                                System.out.println("Customer was not found.");
                                customer = createAccount();
                            }
                            System.out.println("Are you willing to book a room? Press 'y' to continue and 'n' to cancel");
                            char yesToBook = scanner.next().trim().charAt(0);
                            if (yesToBook == 'y') {
                                boolean hotelHasRooms = true;
                                while (hotelHasRooms) {
                                    System.out.println("Available rooms:");
                                    System.out.println(unoccupiedRooms);
                                    System.out.println("Please enter room number from the available rooms:");
                                    String roomNumber = scanner.next();
                                    IRoom selectedRoom = hotelResource.getRoom(roomNumber);
                                    if (unoccupiedRooms.contains(selectedRoom)) {
                                        hotelResource.bookARoom(customer, selectedRoom, newCheckInDate, newCheckOutDate);
                                        System.out.println("The room has been booked successfully");
                                        for (int i = 0; i < 5; i++) System.out.print("_");
                                        System.out.println();
                                        scanner.nextLine();
                                        System.out.println("Reservation Summary :" + hotelResource.getCustomerReservations(mail));
                                        System.out.println("Enjoy our services!");
                                        hotelHasRooms = false;
                                    } else {
                                        System.out.println("Sorry, room number '" + roomNumber + "' is not available.");
                                    }
                                }
                            } else {
                                System.out.println("You have cancelled booking");
                                for (int i = 0; i < 5; i++) System.out.print("_");
                                System.out.println();
                            }
                        }
                        if(option == 0){
                            input = false;
                            System.out.println("You have cancelled the reservation");
                        }
                        else System.out.println("Please enter correct input");
                    }
                }else {
                    System.out.println("Sorry there are no rooms available on the alternative date.");
                }
            }
        }catch (Exception e){
            System.out.println("Error during find And Reserve Room ");
            System.out.println("Invalid Input Please check your inputs !" + e);
        }
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^(.+)@(.+).(.com)$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
