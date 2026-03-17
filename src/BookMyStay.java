import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service (Thread-Safe)
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Queue<String>> availableRooms = new HashMap<>();

    public InventoryService() {
        addRooms("Single", Arrays.asList("Single-1", "Single-2", "Single-3", "Single-4", "Single-5"));
        addRooms("Double", Arrays.asList("Double-1", "Double-2", "Double-3"));
        addRooms("Suite", Arrays.asList("Suite-1", "Suite-2"));
    }

    private void addRooms(String type, List<String> rooms) {
        inventory.put(type, rooms.size());
        availableRooms.put(type, new LinkedList<>(rooms));
    }

    // 🔒 Critical Section (Thread-Safe)
    public synchronized String allocateRoom(String roomType) {
        if (inventory.getOrDefault(roomType, 0) == 0) {
            return null;
        }

        String roomId = availableRooms.get(roomType).poll();
        inventory.put(roomType, inventory.get(roomType) - 1);

        return roomId;
    }

    public void printInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Booking Service (Shared Queue)
class BookingService {
    private Queue<BookingRequest> queue = new LinkedList<>();
    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    // 🔒 Thread-safe enqueue
    public synchronized void addRequest(BookingRequest request) {
        queue.offer(request);
    }

    // 🔒 Thread-safe dequeue
    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }

    public InventoryService getInventory() {
        return inventory;
    }
}

// Worker Thread
class BookingProcessor extends Thread {
    private BookingService service;

    public BookingProcessor(BookingService service) {
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // 🔒 Get request safely
            synchronized (service) {
                if (!service.hasRequests()) break;
                request = service.getRequest();
            }

            if (request == null) continue;

            // 🔒 Allocate room safely
            String roomId = service.getInventory().allocateRoom(request.roomType);

            if (roomId != null) {
                System.out.println("Booking confirmed for Guest : "
                        + request.guestName + ", Room ID :  " + roomId);
            } else {
                System.out.println("Booking failed for Guest : "
                        + request.guestName + " (No rooms available)");
            }
        }
    }
}

// Main Class
public class BookMyStay {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Concurrent Booking Simulation");

        InventoryService inventory = new InventoryService();
        BookingService service = new BookingService(inventory);

        // Add requests
        service.addRequest(new BookingRequest("Abhi", "Single"));
        service.addRequest(new BookingRequest("Subha", "Single"));
        service.addRequest(new BookingRequest("Vanmathi", "Double"));
        service.addRequest(new BookingRequest("Kural", "Suite"));

        // Create multiple threads
        Thread t1 = new BookingProcessor(service);
        Thread t2 = new BookingProcessor(service);
        Thread t3 = new BookingProcessor(service);

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        t1.join();
        t2.join();
        t3.join();

        // Print remaining inventory
        inventory.printInventory();
    }
}