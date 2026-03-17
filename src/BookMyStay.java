import java.util.*;


class Reservation {
    String guestName;
    String roomType;
    String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();


    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }


    public List<Reservation> getAllReservations() {
        return history;
    }
}


class BookingReportService {

    public void generateReport(List<Reservation> reservations) {
        System.out.println("Booking History and Reporting\n");
        System.out.println("Booking History Report");

        for (Reservation r : reservations) {
            System.out.println("Guest: " + r.guestName + ", Room Type: " + r.roomType);
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();


        history.addReservation(new Reservation("Abhi", "Single", "Single-1"));
        history.addReservation(new Reservation("Subha", "Double", "Double-1"));
        history.addReservation(new Reservation("Vanmathi", "Suite", "Suite-1"));


        reportService.generateReport(history.getAllReservations());
    }
}