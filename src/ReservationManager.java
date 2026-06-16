package RestaurantReservationSystem;

import java.util.*;

public class ReservationManager {
    // DATA STRUCTURE 1: LinkedList - Stores recent reservations for quick access
    private final LinkedList<Reservation> recentReservations = new LinkedList<>();

    // DATA STRUCTURE 2: TreeMap - Auto-sorts reservations by date & time
    private final TreeMap<String, Reservation> reservationsByDateTime = new TreeMap<>();

    // DATA STRUCTURE 3: Queue (LinkedList) - Handles waitlist in FIFO order
    private final Queue<Reservation> waitlistQueue = new LinkedList<>();

    // DATA STRUCTURE 4: HashSet - Tracks unique phone numbers for rewards program
    private final HashSet<String> rewardsMembers = new HashSet<>();

    // DATA STRUCTURE 5: Binary Search Tree - Fast O(log n) phone number operations
    private final BST phoneNumberBST = new BST();

    // Add a new reservation
    public void addReservation(Scanner scanner) {
        System.out.println("\n=== NEW RESERVATION ===");

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        String phone;
        while (true) {
            System.out.print("Enter phone number (10 digits only): ");
            phone = scanner.nextLine();
            if (phone.matches("\\d{10}")) break;
            System.out.println("Invalid! Please enter exactly 10 digits.");
        }

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        String time;
        while (true) {
            System.out.print("Enter time (HH:00, 10:00 to 22:00): ");
            time = scanner.nextLine();
            if (time.matches("([01]?[0-9]|2[0-2]):00")) break;
            System.out.println("Invalid! Use HH:00 format (10:00 to 22:00)");
        }

        System.out.print("Enter table/location (e.g., 'Table 5' or 'Window Seat'): ");
        String location = scanner.nextLine();

        // Check for rewards program using HashSet
        boolean isRewardsMember = rewardsMembers.contains(phone);
        if (!isRewardsMember) {
            System.out.print("Join our FREE rewards program? (yes/no): ");
            String joinResponse = scanner.nextLine().toLowerCase();
            if (joinResponse.equals("yes")) {
                rewardsMembers.add(phone);      // HashSet add - prevents duplicates automatically
                phoneNumberBST.insert(phone);   // BST insert
                System.out.println("✓ Welcome to Rewards Program!");
            }
        } else {
            System.out.println("✓ Existing Rewards Member - Points will be added!");
        }

        // Create and store reservation
        Reservation reservation = new Reservation(name, phone, date, time, location);

        recentReservations.addFirst(reservation);                    // LinkedList: add to front
        String key = date + "|" + time;
        reservationsByDateTime.put(key, reservation);                 // TreeMap: auto-sorted
        waitlistQueue.add(reservation);                               // Queue: add to waitlist

        System.out.println("\n✓ RESERVATION CONFIRMED!");
        System.out.println("  Reservation ID: " + reservation.getReservationID());
        System.out.println("  " + reservation);
    }

    // View recent reservations (LinkedList)
    public void viewRecentReservations() {
        System.out.println("\n=== RECENT RESERVATIONS ===");
        if (recentReservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("(Most recent first)");
        for (Reservation res : recentReservations) {
            System.out.println(res);
        }
    }

    // Search by phone number using BST
    public void searchReservationByPhone(Scanner scanner) {
        System.out.print("\nEnter phone number to search: ");
        String phone = scanner.nextLine();

        // BST search - O(log n) efficiency
        if (phoneNumberBST.search(phone)) {
            System.out.println("✓ Phone number found in Rewards Database!");
            for (Reservation res : recentReservations) {
                if (res.getPhoneNumber().equals(phone)) {
                    System.out.println("\nActive Reservation Found:");
                    System.out.println(res);
                    return;
                }
            }
            System.out.println("Note: Phone number is in rewards program but no active reservation.");
        } else {
            System.out.println("✗ Phone number not found in system.");
            System.out.println("  (Not a rewards member or no active reservation)");
        }
    }

    // Cancel reservation
    public void cancelReservation(Scanner scanner) {
        System.out.println("\n=== CANCEL RESERVATION ===");
        System.out.println("1. Cancel by Reservation ID");
        System.out.println("2. Cancel by Phone Number");
        System.out.print("Choose: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter Reservation ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Iterator<Reservation> iterator = recentReservations.iterator();
            while (iterator.hasNext()) {
                Reservation res = iterator.next();
                if (res.getReservationID() == id) {
                    removeReservationFromAllStructures(res);
                    System.out.println("✓ Reservation #" + id + " cancelled successfully.");
                    return;
                }
            }
            System.out.println("✗ Reservation ID not found.");

        } else if (choice == 2) {
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine();

            if (phoneNumberBST.search(phone)) {
                Iterator<Reservation> iterator = recentReservations.iterator();
                while (iterator.hasNext()) {
                    Reservation res = iterator.next();
                    if (res.getPhoneNumber().equals(phone)) {
                        removeReservationFromAllStructures(res);
                        System.out.println("✓ Reservation cancelled successfully.");
                        return;
                    }
                }
            }
            System.out.println("✗ No active reservation found for this phone number.");
        } else {
            System.out.println("Invalid option.");
        }
    }

    // Helper method to remove reservation from all data structures
    private void removeReservationFromAllStructures(Reservation res) {
        recentReservations.remove(res);                           // Remove from LinkedList
        String key = res.getDate() + "|" + res.getReservationTime();
        reservationsByDateTime.remove(key);                        // Remove from TreeMap
        waitlistQueue.remove(res);                                 // Remove from Queue
        // Note: Keep phone number in rewards program (customer retains membership)
        System.out.println("  - Removed from recent reservations list");
        System.out.println("  - Removed from time-sorted schedule");
        System.out.println("  - Removed from waitlist queue");
        System.out.println("  (Rewards membership retained for future visits)");
    }

    // Process waitlist queue (FIFO order)
    public void processWaitlistQueue() {
        System.out.println("\n=== PROCESSING WAITLIST (FIFO ORDER) ===");
        if (waitlistQueue.isEmpty()) {
            System.out.println("No customers currently on waitlist.");
            return;
        }

        System.out.println("Serving customers in order of arrival:\n");
        int position = 1;
        while (!waitlistQueue.isEmpty()) {
            Reservation res = waitlistQueue.poll();   // Queue: remove from front
            System.out.println(position++ + ". " + res.getCustomerName() +
                    " (ID: " + res.getReservationID() + ") - Table ready!");
        }
        System.out.println("\n✓ All waitlist customers have been seated!");
    }

    // Display all data structures stats (for debugging/presentation)
    public void displaySystemStats() {
        System.out.println("\n=== SYSTEM STATISTICS ===");
        System.out.println("Total active reservations: " + recentReservations.size());
        System.out.println("Waitlist queue size: " + waitlistQueue.size());
        System.out.println("Rewards program members: " + rewardsMembers.size());
        phoneNumberBST.displayAllPhoneNumbers();
    }
}