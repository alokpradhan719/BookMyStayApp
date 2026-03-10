import java.util.HashMap;

abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void display(int available) {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: ₹" + price);
        System.out.println("Available: " + available);
        System.out.println();
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}



class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchRooms() {

        System.out.println("Room Search\n");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvail = inventory.getAvailability("Single");
        int doubleAvail = inventory.getAvailability("Double");
        int suiteAvail = inventory.getAvailability("Suite");

        if (singleAvail > 0) {
            System.out.println("Single Room:");
            single.display(singleAvail);
        }

        if (doubleAvail > 0) {
            System.out.println("Double Room:");
            doubleRoom.display(doubleAvail);
        }

        if (suiteAvail > 0) {
            System.out.println("Suite Room:");
            suite.display(suiteAvail);
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        SearchService search = new SearchService(inventory);

        search.searchRooms();
    }
}