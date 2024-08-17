import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
class EWasteItem implements Serializable {
    private static int idCounter = 0;
    private int id;
    private String name;
    private String type;
    private double weight;
    private String condition;
    private boolean isRecycled;
    private Date dateAdded;

    public EWasteItem(String name, String type, double weight, String condition) {
        this.id = ++idCounter;
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.condition = condition;
        this.isRecycled = false;
        this.dateAdded = new Date();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public String getCondition() {
        return condition;
    }

    public boolean isRecycled() {
        return isRecycled;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void recycle() {
        isRecycled = true;
    }

    public void updateDetails(String name, String type, double weight, String condition) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Type: " + type + ", Weight: " + weight + "kg, Condition: " + condition + ", Recycled: " + (isRecycled ? "Yes" : "No") + ", Date Added: " + dateAdded;
    }
}

public class Main {
    private static List<EWasteItem> eWasteItems = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final double WEIGHT_THRESHOLD = 100.0; // Example threshold for alert

    public static void main(String[] args) {
        loadFromFile();
        while (true) {
            System.out.println("\nE-waste Monitoring System");
            System.out.println("1. Add E-waste");
            System.out.println("2. View All E-waste");
            System.out.println("3. Categorize E-waste");
            System.out.println("4. Simulate Recycling");
            System.out.println("5. Generate Reports");
            System.out.println("6. Recycling Alerts");
            System.out.println("7. Advanced Search");
            System.out.println("8. Update E-waste");
            System.out.println("9. Delete E-waste");
            System.out.println("10. Save & Exit");
            System.out.print("\nChoose an option: \n");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEWaste();
                    break;
                case 2:
                    viewAllEWaste();
                    break;
                case 3:
                    categorizeEWaste();
                    break;
                case 4:
                    simulateRecycling();
                    break;
                case 5:
                    generateReports();
                    break;
                case 6:
                    recyclingAlerts();
                    break;
                case 7:
                    advancedSearch();
                    break;
                case 8:
                    updateEWaste();
                    break;
                case 9:
                    deleteEWaste();
                    break;
                case 10:
                    saveToFile();
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void addEWaste() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item type (e.g., Computer, Phone): ");
        String type = scanner.nextLine();
        System.out.print("Enter item weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter item condition (Working/Non-working): ");
        String condition = scanner.nextLine();

        EWasteItem newItem = new EWasteItem(name, type, weight, condition);
        eWasteItems.add(newItem);
        System.out.println("E-waste item added successfully!");
    }

    private static void viewAllEWaste() {
        if (eWasteItems.isEmpty()) {
            System.out.println("No e-waste items found.");
        } else {
            System.out.println("All E-waste Items:");
            for (EWasteItem item : eWasteItems) {
                System.out.println(item);
            }
        }
    }

    private static void categorizeEWaste() {
        System.out.println("Categorizing E-waste by type:");
        Map<String, List<EWasteItem>> categorizedItems = new HashMap<>();
        for (EWasteItem item : eWasteItems) {
            categorizedItems.computeIfAbsent(item.getType(), k -> new ArrayList<>()).add(item);
        }
        for (String type : categorizedItems.keySet()) {
            System.out.println("\nType: " + type);
            for (EWasteItem item : categorizedItems.get(type)) {
                System.out.println(item);
            }
        }
    }

    private static void simulateRecycling() {
        System.out.print("Enter the ID of the item to recycle: ");
        int id = scanner.nextInt();

        for (EWasteItem item : eWasteItems) {
            if (item.getId() == id && !item.isRecycled()) {
                item.recycle();
                System.out.println("Item recycled successfully!");
                return;
            }
        }
        System.out.println("Item not found or already recycled.");
    }

    private static void generateReports() {
        System.out.println("\nGenerate Reports");
        System.out.println("1. Type-Based Report");
        System.out.println("2. Condition-Based Report");
        System.out.println("3. Recycling Status Report");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                generateTypeBasedReport();
                break;
            case 2:
                generateConditionBasedReport();
                break;
            case 3:
                generateRecyclingStatusReport();
                break;
            default:
                System.out.println("Invalid choice! Returning to main menu.");
        }
    }

    private static void generateTypeBasedReport() {
        System.out.println("Type-Based Report:");
        Map<String, Double> typeTotals = new HashMap<>();
        for (EWasteItem item : eWasteItems) {
            typeTotals.put(item.getType(), typeTotals.getOrDefault(item.getType(), 0.0) + item.getWeight());
        }
        for (String type : typeTotals.keySet()) {
            System.out.println(type + ": " + typeTotals.get(type) + "kg");
        }
    }

    private static void generateConditionBasedReport() {
        System.out.println("Condition-Based Report:");
        Map<String, Integer> conditionCounts = new HashMap<>();
        for (EWasteItem item : eWasteItems) {
            conditionCounts.put(item.getCondition(), conditionCounts.getOrDefault(item.getCondition(), 0) + 1);
        }
        for (String condition : conditionCounts.keySet()) {
            System.out.println(condition + ": " + conditionCounts.get(condition) + " items");
        }
    }

    private static void generateRecyclingStatusReport() {
        System.out.println("Recycling Status Report:");
        int recycledCount = 0;
        int notRecycledCount = 0;
        for (EWasteItem item : eWasteItems) {
            if (item.isRecycled()) {
                recycledCount++;
            } else {
                notRecycledCount++;
            }
        }
        System.out.println("Recycled: " + recycledCount + " items");
        System.out.println("Not Recycled: " + notRecycledCount + " items");
    }

    private static void recyclingAlerts() {
        System.out.println("Recycling Alerts:");
        double totalWeight = 0;
        for (EWasteItem item : eWasteItems) {
            if (!item.isRecycled()) {
                totalWeight += item.getWeight();
            }
        }
        if (totalWeight > WEIGHT_THRESHOLD) {
            System.out.println("Alert: Total unrecycled e-waste weight exceeds threshold of " + WEIGHT_THRESHOLD + "kg!");
        } else {
            System.out.println("Total unrecycled e-waste weight is within safe limits.");
        }
    }

    private static void advancedSearch() {
        System.out.println("\nAdvanced Search");
        System.out.println("1. Search by Type");
        System.out.println("2. Search by Condition");
        System.out.println("3. Search by Date");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                searchByType();
                break;
            case 2:
                searchByCondition();
                break;
            case 3:
                searchByDate();
                break;
            default:
                System.out.println("Invalid choice! Returning to main menu.");
        }
    }

    private static void searchByType() {
        System.out.print("Enter type to search (e.g., Computer, Phone): ");
        String type = scanner.nextLine();
        for (EWasteItem item : eWasteItems) {
            if (item.getType().equalsIgnoreCase(type)) {
                System.out.println(item);
            }
        }
    }

    private static void searchByCondition() {
        System.out.print("Enter condition to search (e.g., Working, Non-working): ");
        String condition = scanner.nextLine();
        for (EWasteItem item : eWasteItems) {
            if (item.getCondition().equalsIgnoreCase(condition)) {
                System.out.println(item);
            }
        }
    }

    private static void searchByDate() {
        System.out.print("Enter date to search (in format YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        try {
            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            for (EWasteItem item : eWasteItems) {
                if (item.getDateAdded().compareTo(searchDate) == 0) {
                    System.out.println(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please try again.");
        }
    }

    private static void updateEWaste() {
        System.out.print("Enter the ID of the item to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (EWasteItem item : eWasteItems) {
            if (item.getId() == id) {
                System.out.print("Enter new item name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new item type (e.g., Computer, Phone): ");
                String type = scanner.nextLine();
                System.out.print("Enter new item weight (kg): ");
                double weight = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new item condition (Working/Non-working): ");
                String condition = scanner.nextLine();

                item.updateDetails(name, type, weight, condition);
                System.out.println("E-waste item updated successfully!");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    private static void deleteEWaste() {
        System.out.print("Enter the ID of the item to delete: ");
        int id = scanner.nextInt();

        Iterator<EWasteItem> iterator = eWasteItems.iterator();
        while (iterator.hasNext()) {
            EWasteItem item = iterator.next();
            if (item.getId() == id) {
                iterator.remove();
                System.out.println("E-waste item deleted successfully!");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    private static void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("eWasteData.dat"))) {
            out.writeObject(eWasteItems);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("eWasteData.dat"))) {
            eWasteItems = (List<EWasteItem>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data or no previous data found. Starting fresh.");
        }
    }
}
