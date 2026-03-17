import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BookMyStay {

    // Serializable Inventory Class
    static class Inventory implements Serializable {
        private static final long serialVersionUID = 1L;

        private Map<String, Integer> rooms = new HashMap<>();

        public Inventory() {
            rooms.put("Single", 5);
            rooms.put("Double", 3);
            rooms.put("Suite", 2);
        }

        public void display() {
            System.out.println("\nCurrent Inventory:");
            for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    // Persistence Service
    static class PersistenceService {
        private static final String FILE_NAME = "inventory.dat";

        public static void save(Inventory inventory) {
            try (ObjectOutputStream oos =
                         new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

                oos.writeObject(inventory);
                System.out.println("\nInventory saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving inventory.");
            }
        }

        public static Inventory load() {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return new Inventory();
            }

            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(FILE_NAME))) {

                return (Inventory) ois.readObject();

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return new Inventory();
            }
        }
    }

    // Main Method
    public static void main(String[] args) {

        System.out.println("System Recovery");

        Inventory inventory = PersistenceService.load();

        inventory.display();

        PersistenceService.save(inventory);
    }
}