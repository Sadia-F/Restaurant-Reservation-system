package RestaurantReservationSystem;

public class Reservation implements Comparable<Reservation> {
    private static int idCounter = 1001;  // Starting from 1001 for professional look
    private final int reservationID;
    private final String customerName;
    private final String phoneNumber;
    private final String reservationTime;
    private final String location;
    private final String date;  // Added date field for completeness

    // Constructor
    public Reservation(String customerName, String phoneNumber, String date, String reservationTime, String location) {
        this.reservationID = idCounter++;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.reservationTime = reservationTime;
        this.location = location;
    }

    // Getters
    public int getReservationID() {
        return reservationID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int compareTo(Reservation other) {
        // First compare by date, then by time
        int dateCompare = this.date.compareTo(other.date);
        if (dateCompare != 0) {
            return dateCompare;
        }
        return this.reservationTime.compareTo(other.reservationTime);
    }

    @Override
    public String toString() {
        return String.format("[ID: %d] %s | %s | %s @ %s | Table: %s",
                reservationID, customerName, phoneNumber, date, reservationTime, location);
    }
}