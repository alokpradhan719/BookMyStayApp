import java.util.*;

// Booking Request Model
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}
// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Queue<String>> availableRooms = new HashMap<>();

    public InventoryService() {
        // Initialize rooms
        addRooms("Single", Arrays.asList("Single-1", "Single-2"));
        addRooms("Suite", Arrays.asList("Suite-1"));
    }

    private void addRooms(String type, List<String> rooms) {
        inventory.put(type, rooms.size());
        availableRooms.put(type, new LinkedList<>(rooms));
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public String allocateRoom(String roomType) {
        if (!isAvailable(roomType)) return null;

        String roomId = availableRooms.get(roomType).poll();
        inventory.put(roomType, inventory.get(roomType) - 1);
        return roomId;
    }
}

// Booking Service
class BookingService {
    private Queue<BookingRequest> requestQueue = new LinkedList<>();
    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocationMap = new HashMap<>();
    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addRequest(BookingRequest request) {
        requestQueue.offer(request);
    }

    public void processBookings() {
        System.out.println("Room Allocation Processing");

        while (!requestQueue.isEmpty()) {
            BookingRequest request = requestQueue.poll();
            String roomType = request.roomType;

            if (!inventoryService.isAvailable(roomType)) {
                System.out.println("Booking failed for " + request.guestName + ": No rooms available");
                continue;
            }

            String roomId = inventoryService.allocateRoom(roomType);

            // Double-booking protection
            if (roomId == null || allocatedRoomIds.contains(roomId)) {
                System.out.println("Booking failed for " + request.guestName + ": Duplicate allocation detected");
                continue;
            }

            // Record allocation
            allocatedRoomIds.add(roomId);
            roomAllocationMap
                    .computeIfAbsent(roomType, k -> new HashSet<>())
                    .add(roomId);

            System.out.println("Booking confirmed for Guest: " + request.guestName + ", Room ID: " + roomId);
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);


        bookingService.addRequest(new BookingRequest("Abhi", "Single"));
        bookingService.addRequest(new BookingRequest("Subha", "Single"));
        bookingService.addRequest(new BookingRequest("Vanmathi", "Suite"));


        bookingService.processBookings();
    }
}