import api.AdminResource;
import model.Room;
import model.RoomType;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AdminMenu {
    public static AdminResource adminResource = AdminResource.getInstance();

    public static void displayAdminMenu() {

        System.out.println("\nAdmin Menu:\n");

        for (int i = 0; i < 25; i++) System.out.print("_");
        System.out.println();
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations ");
        System.out.println("4. Add a Room ");
        System.out.println("5. Back to Main Menu ");
        for (int i = 0; i < 25; i++) System.out.print("_");
        System.out.println();
        System.out.println("Select your choice:");
    }

    public static void moveToAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        try {
            boolean exit = true;
            while (exit) {
                displayAdminMenu();
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println(adminResource.getAllCustomers());
                        break;
                    case "2":
                        System.out.println(adminResource.getAllRooms());
                        break;
                    case "3":
                        adminResource.displayAllReservations();
                        break;
                    case "4":
                        adminAddRoom();
                        break;
                    case "5":
                        exit = false;
                        System.out.println("Exiting Admin Menu");
                        break;
                    default:
                        System.out.println("Please enter correct input\n");
                        displayAdminMenu();
                        break;
                }
            }
            MainMenu.menu();
        } catch (Exception e) {
            System.out.println("Please enter correct input\n");
            e.getLocalizedMessage();
        } finally {
            scanner.close();
        }
    }

    private static void adminAddRoom() {

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter room number or ID");
            final String roomNumber = scanner.nextLine().trim();

            System.out.println("Enter room price per night");
            final double price = scanner.nextDouble();
            boolean notCorrectInput = true;
            while (notCorrectInput){
                try {
                    System.out.println("Enter room type: 1 for single,  2 for double bed");
                    int type = scanner.nextInt();
                    if (type == 1) {
                        final RoomType roomType = RoomType.SINGLE;
                        Room room = new Room(roomNumber, price, roomType);
                        System.out.println("Room added successfully");
                        notCorrectInput = false;
                        for (int i = 0; i < 5; i++) System.out.print("_");
                        System.out.println();
                        adminResource.addRoom(room);
                    } else if(type == 2) {
                        final RoomType roomType = RoomType.DOUBLE;
                        Room room = new Room(roomNumber, price, roomType);
                        System.out.println("Room added successfully");
                        notCorrectInput = false;
                        for (int i = 0; i < 5; i++) System.out.print("_");
                        System.out.println();
                        adminResource.addRoom(room);
                    }else System.out.println("Invalid input");
                }catch (Exception e){
                    e.getLocalizedMessage();
                }
            }
        }catch (Exception e){
            System.out.println("Please enter correct input\n");
            e.getLocalizedMessage();
        }
    }
}
