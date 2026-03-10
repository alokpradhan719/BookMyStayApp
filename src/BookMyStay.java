abstract class Room {
    int beds;
    int size;
    double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    abstract void displayRoom(int available);
}

class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 250, 1500.0);
    }

    void displayRoom(int available) {
        System.out.println("Single Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: $" + price);
        System.out.println("Available: " + available);
        System.out.println();
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 400, 2500.0);
    }

    void displayRoom(int available) {
        System.out.println("Double Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: $" + price);
        System.out.println("Available: " + available);
        System.out.println();
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 750, 5000.0);
    }

    void displayRoom(int available) {
        System.out.println("Suite Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: $" + price);
        System.out.println("Available: " + available);
        System.out.println();
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Hotel Room Initialization\n");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        single.displayRoom(singleAvailable);
        doubleRoom.displayRoom(doubleAvailable);
        suite.displayRoom(suiteAvailable);
    }
}
