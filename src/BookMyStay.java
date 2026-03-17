import java.util.*;

class AddOnService {
    String serviceName;
    double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }
}

class AddOnServiceManager {
    // reservationId -> list of services
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null) return 0.0;

        double total = 0;
        for (AddOnService service : services) {
            total += service.cost;
        }
        return total;
    }
    public void displayServices(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);

        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService service : services) {
            System.out.println("- " + service.serviceName + " : " + service.cost);
        }

        System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationId));
    }
}
public class BookMyStay {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation (from Use Case 6)
        String reservationId = "Single-1";

        // Add services
        manager.addService(reservationId, new AddOnService("Breakfast", 500));
        manager.addService(reservationId, new AddOnService("Airport Pickup", 1000));

        // Display result
        manager.displayServices(reservationId);
    }
}