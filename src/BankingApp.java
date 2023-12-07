import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class BankingApp {
    //Used for reading input (Keyboard)
    private static Scanner scanner = new Scanner(System.in);

    //A list of customers
    private static List<Customer> customers = new ArrayList<>();

    //Customer class to store customer data
    public static class Customer {
        //Customer Properties
        private String name;
        private String accountNumber;
        private double balance;

        public Customer(String name, String accountNumber, double balance) {
            this.name = name;
            this.accountNumber = accountNumber;
            this.balance = balance;
        }

        public String getName() {
            return name;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String displayAccountNumber() {
            return accountNumber;
        }
    }

    //Adding new customer to the list
    private static void addCustomer(String name, double initialBalance) {
        //Auto generate a new account number
        String accountNumber = generateAccountNumber();

        //Creating a new instance of the customer
        Customer newCustomer = new Customer(name, accountNumber, initialBalance);
        customers.add(newCustomer);
    }

    //To handle customer deposits
    private static void deposit(String accountNumber, double amount) {
        Customer customer = findCustomer(accountNumber);
        if (customer != null) {
            customer.setBalance(customer.getBalance() + amount);
            System.out.println("Deposit successful. New balance: $" + customer.getBalance());
        } else {
            System.out.println("Customer not found.");
        }
    }

    //To handle customer withdrawals
    private static void withdraw(String accountNumber, double amount) {
        Customer customer = findCustomer(accountNumber);
        if (customer != null) {
            if (customer.getBalance() >= amount) {
                customer.setBalance(customer.getBalance() - amount);
                System.out.println("Withdrawal successful. New balance: $" + customer.getBalance());
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

    //Check customer balance
    private static void checkBalance(String accountNumber) {
        Customer customer = findCustomer(accountNumber);
        if (customer != null) {
            System.out.println("Current balance: $" + customer.getBalance());
        } else {
            System.out.println("Customer not found.");
        }
    }

    //Finding the customer by unique IC
    private static Customer findCustomer(String accountNumber) {
        return customers.stream()
                .filter(customer -> customer.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    //Generating unique account number for the customer
    private static String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    //Customer Menu
    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("0. Get Account Number");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Simple Banking App!");

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial balance: $");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine();
        addCustomer(name, initialBalance);

        boolean exit = false;

        while (!exit) {

            printMenu();

            int choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.print("Account Number: " + customers.get(0).displayAccountNumber());
                    break;

                case 1:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();
                    deposit(customers.get(0).getAccountNumber(), depositAmount);
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawalAmount = scanner.nextDouble();
                    scanner.nextLine();
                    withdraw(customers.get(0).getAccountNumber(), withdrawalAmount);
                    break;

                case 3:
                    checkBalance(customers.get(0).getAccountNumber());
                    break;

                case 4:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using the Simple Banking App!");
    }
}
