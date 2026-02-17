import java.util.*;

class EventManagementSystem {

    
    static String loggedInUser = null;
    static String role = null;

    
    static Map<String, String> users = new HashMap<>();
    static Map<String, String> roles = new HashMap<>();
    static Map<Integer, Membership> memberships = new HashMap<>();

    static Scanner sc = new Scanner(System.in);
    static int membershipIdCounter = 1;

    
    static class Membership {
        int id;
        String name;
        String duration;
        boolean active;

        Membership(int id, String name, String duration) {
            this.id = id;
            this.name = name;
            this.duration = duration;
            this.active = true;
        }
    }

    
    public static void main(String[] args) {

        // default users
        users.put("admin", "admin123");
        roles.put("admin", "ADMIN");

        users.put("user", "user123");
        roles.put("user", "USER");

        while (true) {
            login();

            if (role.equals("ADMIN"))
                adminMenu();
            else
                userMenu();
        }
    }

    
    static void login() {
        System.out.println("\n===== LOGIN =====");

        while (true) {
            System.out.print("Username: ");
            String username = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine(); // console hides visually by design requirement simulation

            if (users.containsKey(username) &&
                users.get(username).equals(password)) {

                loggedInUser = username;
                role = roles.get(username);
                System.out.println("Login Successful! Role: " + role);
                return;
            }
            System.out.println("Invalid credentials. Try again.");
        }
    }

    
    static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Maintenance");
            System.out.println("2. Reports");
            System.out.println("3. Transactions");
            System.out.println("4. Logout");

            int choice = getIntInput();

            switch (choice) {
                case 1: maintenanceMenu(); break;
                case 2: reports(); break;
                case 3: transactions(); break;
                case 4: logout(); return;
                default: System.out.println("Invalid option");
            }
        }
    }

    
    static void userMenu() {
        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Reports");
            System.out.println("2. Transactions");
            System.out.println("3. Logout");

            int choice = getIntInput();

            switch (choice) {
                case 1: reports(); break;
                case 2: transactions(); break;
                case 3: logout(); return;
                default: System.out.println("Invalid option");
            }
        }
    }

    
    static void maintenanceMenu() {
        while (true) {
            System.out.println("\n--- MAINTENANCE ---");
            System.out.println("1. Add Membership");
            System.out.println("2. Update Membership");
            System.out.println("3. Back");

            int choice = getIntInput();

            switch (choice) {
                case 1: addMembership(); break;
                case 2: updateMembership(); break;
                case 3: return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    
    static void addMembership() {
        System.out.println("\nAdd Membership");

        System.out.print("Enter Member Name: ");
        String name = sc.nextLine();

        if (name.isEmpty()) {
            System.out.println("Name is mandatory!");
            return;
        }

        // Radio button logic (only one selection)
        System.out.println("Select Duration:");
        System.out.println("1. 6 Months (Default)");
        System.out.println("2. 1 Year");
        System.out.println("3. 2 Years");

        int option = getIntInput();

        String duration = "6 Months"; // default
        if (option == 2) duration = "1 Year";
        else if (option == 3) duration = "2 Years";

        Membership m = new Membership(membershipIdCounter++, name, duration);
        memberships.put(m.id, m);

        System.out.println("Membership Added Successfully!");
    }

    
    static void updateMembership() {
        System.out.print("Enter Membership ID: ");
        int id = getIntInput();

        if (!memberships.containsKey(id)) {
            System.out.println("Membership not found!");
            return;
        }

        Membership m = memberships.get(id);

        System.out.println("1. Extend Membership (Default 6 months)");
        System.out.println("2. Cancel Membership");

        int option = getIntInput();

        if (option == 1) {
            m.duration += " + 6 Months";
            System.out.println("Membership Extended!");
        } else if (option == 2) {
            m.active = false;
            System.out.println("Membership Cancelled!");
        }
    }

    
    static void reports() {
        System.out.println("\n--- REPORTS ---");

        if (memberships.isEmpty()) {
            System.out.println("No records available.");
            return;
        }

        for (Membership m : memberships.values()) {
            System.out.println(
                "ID: " + m.id +
                " | Name: " + m.name +
                " | Duration: " + m.duration +
                " | Active: " + (m.active ? "Yes" : "No")
            );
        }
    }

   
    static void transactions() {
        System.out.println("\nTransaction module executed.");
        System.out.println("(Sample implementation)");
    }

    
    static void logout() {
        loggedInUser = null;
        role = null;
        System.out.println("Logged out successfully.");
    }

   
    static int getIntInput() {
        while (true) {
            try {
                System.out.print("Enter choice: ");
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid number!");
            }
        }
    }
}
