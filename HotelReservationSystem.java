import java.io.*;
import java.util.*;

// ---------- ROOM CLASS ----------
class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    @Override
    public String toString() {
        return roomNumber + "," + category + "," + price + "," + available;
    }
}

// ---------- RESERVATION CLASS ----------
class Reservation {
    int roomNumber;
    String customerName;

    Reservation(int roomNumber, String customerName) {
        this.roomNumber = roomNumber;
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return roomNumber + "," + customerName;
    }
}

// ---------- MAIN CLASS ----------
public class HotelReservationSystem {

    static List<Room> rooms = new ArrayList<>();
    static List<Reservation> reservations = new ArrayList<>();

    static final String ROOM_FILE = "rooms.txt";
    static final String RES_FILE = "reservations.txt";

    public static void main(String[] args) {
        loadRooms();
        loadReservations();

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n🏨 HOTEL RESERVATION SYSTEM");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Reservations");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showRooms();
                case 2 -> bookRoom(sc);
                case 3 -> cancelReservation(sc);
                case 4 -> showReservations();
                case 5 -> System.out.println("Thank you!");
                default -> System.out.println("Invalid option!");
            }

        } while (choice != 5);

        sc.close();
    }

    // ---------- ROOM METHODS ----------
    static void loadRooms() {
        File file = new File(ROOM_FILE);
        if (!file.exists()) {
            rooms.add(new Room(101, "Standard", 2000));
            rooms.add(new Room(102, "Deluxe", 3500));
            rooms.add(new Room(103, "Suite", 5000));
            saveRooms();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                Room r = new Room(
                        Integer.parseInt(d[0]),
                        d[1],
                        Double.parseDouble(d[2])
                );
                r.available = Boolean.parseBoolean(d[3]);
                rooms.add(r);
            }
        } catch (Exception e) {
            System.out.println("Error loading rooms");
        }
    }

    static void saveRooms() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ROOM_FILE))) {
            for (Room r : rooms) pw.println(r);
        } catch (Exception e) {
            System.out.println("Error saving rooms");
        }
    }

    static void showRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (r.available) {
                System.out.println("Room " + r.roomNumber +
                        " | " + r.category +
                        " | ₹" + r.price);
            }
        }
    }

    // ---------- RESERVATION METHODS ----------
    static void loadReservations() {
        File file = new File(RES_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                reservations.add(new Reservation(
                        Integer.parseInt(d[0]), d[1]
                ));
            }
        } catch (Exception e) {
            System.out.println("Error loading reservations");
        }
    }

    static void saveReservations() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RES_FILE))) {
            for (Reservation r : reservations) pw.println(r);
        } catch (Exception e) {
            System.out.println("Error saving reservations");
        }
    }

    static void bookRoom(Scanner sc) {
        showRooms();
        System.out.print("Enter room number to book: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo && r.available) {
                System.out.print("Enter your name: ");
                String name = sc.nextLine();

                System.out.println("Processing payment of ₹" + r.price + "...");
                System.out.println("Payment successful ✅");

                r.available = false;
                reservations.add(new Reservation(roomNo, name));
                saveRooms();
                saveReservations();

                System.out.println("Room booked successfully!");
                return;
            }
        }
        System.out.println("Room not available!");
    }

    static void cancelReservation(Scanner sc) {
        System.out.print("Enter room number to cancel: ");
        int roomNo = sc.nextInt();

        Iterator<Reservation> it = reservations.iterator();
        while (it.hasNext()) {
            Reservation res = it.next();
            if (res.roomNumber == roomNo) {
                it.remove();
                for (Room r : rooms) {
                    if (r.roomNumber == roomNo) r.available = true;
                }
                saveRooms();
                saveReservations();
                System.out.println("Reservation cancelled!");
                return;
            }
        }
        System.out.println("No reservation found!");
    }

    static void showReservations() {
        System.out.println("\nBooking Details:");
        for (Reservation r : reservations) {
            System.out.println("Room " + r.roomNumber +
                    " booked by " + r.customerName);
        }
    }
}



