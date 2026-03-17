import java.util.*;

// Reservation Model
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

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Queue<String>> availableRooms = new HashMap<>();

    public InventoryService() {
        addRooms("Single", Arrays.asList("Single-1", "Single-2", "Single-3", "Single-4", "Single-5", "Single-6"));
    }

    private void addRooms(String type, List<String> rooms) {
        inventory.put(type, rooms.size());
        availableRooms.put(type, new LinkedList<>(rooms));
    }

    public void restoreRoom(String roomType, String roomId) {
        availableRooms.get(roomType).offer(roomId);
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.roomId, r);
    }

    public Reservation getReservation(String roomId) {
        return reservations.get(roomId);
    }

    public void removeReservation(String roomId) {
        reservations.remove(roomId);
    }
}

// Cancellation Service
class CancellationService {
    private BookingHistory history;
    private InventoryService inventory;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(BookingHistory history, InventoryService inventory) {
        this.history = history;
        this.inventory = inventory;
    }

    public void cancelBooking(String roomId) {
        System.out.println("Booking Cancellation");

        Reservation r = history.getReservation(roomId);

        if (r == null) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        // Push to rollback stack
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.restoreRoom(r.roomType, r.roomId);

        // Remove from history
        history.removeReservation(roomId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + r.roomType);

        // Display rollback history
        System.out.println("\nRollback History (Most Recent First):");
        for (int i = rollbackStack.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + rollbackStack.get(i));
        }

        System.out.println("\nUpdated " + r.roomType + " Room Availability: " +
                inventory.getAvailability(r.roomType));
    }
}

// Main Demo
public class BookMyStay {
    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed booking
        Reservation r1 = new Reservation("Abhi", "Single", "Single-1");
        history.addReservation(r1);

        // Reduce inventory manually (simulate allocation)
        inventory.restoreRoom("Single", "TEMP"); // adjust count baseline
        inventory.getAvailability("Single"); // just to align

        // Cancel booking
        CancellationService service = new CancellationService(history, inventory);
        service.cancelBooking("Single-1");
    }
}