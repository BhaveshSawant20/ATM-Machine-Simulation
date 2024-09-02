import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class ATMSimulation {
    private String pinHash;
    private double balance;
    private ArrayList<String> transactionHistory;

    public ATMSimulation(String pin, double initialBalance) {
        this.pinHash = hashPin(pin);
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    private String hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(pin.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found.");
        }
    }

    private boolean authenticate(String inputPin) {
        return hashPin(inputPin).equals(this.pinHash);
    }

    public void changePin(String oldPin, String newPin) {
        if (authenticate(oldPin)) {
            this.pinHash = hashPin(newPin);
            System.out.println("PIN changed successfully.");
            transactionHistory.add("PIN changed.");
        } else {
            System.out.println("Incorrect old PIN.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Deposited: $" + amount);
            transactionHistory.add("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount, String inputPin) {
        if (authenticate(inputPin)) {
            if (amount > 0 && amount <= this.balance) {
                this.balance -= amount;
                System.out.println("Withdrew: $" + amount);
                transactionHistory.add("Withdrew: $" + amount);
            } else {
                System.out.println("Invalid withdrawal amount or insufficient balance.");
            }
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    public void checkBalance(String inputPin) {
        if (authenticate(inputPin)) {
            System.out.println("Current Balance: $" + this.balance);
            transactionHistory.add("Balance checked.");
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    public void printTransactionHistory(String inputPin) {
        if (authenticate(inputPin)) {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATMSimulation atm = new ATMSimulation("1234", 10000.0);

        System.out.println("Welcome to the ATM Simulation!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Change PIN");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter PIN: ");
                    String pin1 = scanner.nextLine();
                    atm.checkBalance(pin1);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter PIN: ");
                    String pin2 = scanner.nextLine();
                    atm.withdraw(amount, pin2);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    atm.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter old PIN: ");
                    String oldPin = scanner.nextLine();
                    System.out.print("Enter new PIN: ");
                    String newPin = scanner.nextLine();
                    atm.changePin(oldPin, newPin);
                    break;
                case 5:
                    System.out.print("Enter PIN: ");
                    String pin3 = scanner.nextLine();
                    atm.printTransactionHistory(pin3);
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM Simulation. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}