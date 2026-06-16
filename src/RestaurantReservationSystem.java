package RestaurantReservationSystem;

import java.util.Scanner;

public class RestaurantReservationSystem {
    public static void main(String[] args) {
        ReservationManager  = new ReservationManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== WELCOME TO RESTAURANT RESERVATION SYSTEM ===");


        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add New Reservation");
            System.out.println("2. View Recent Reservations");
            System.out.println("3. Search Reservation by Phone Number");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Process Waitlist Queue");
            System.out.println("6. Exit System");
            System.out.print("Choose an option (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> manager.addReservation(scanner);
                case 2 -> manager.viewRecentReservations();
                case 3 -> manager.searchReservationByPhone(scanner);
                case 4 -> manager.cancelReservation(scanner);
                case 5 -> manager.processWaitlistQueue();
                case 6 -> {
                    System.out.println("\nThank you for using Restaurant Reservation System!");
                    System.out.println("Exiting now... Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1-6.");
            }
        }
    }
}