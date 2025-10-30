public class BankAccount {

    private String accountNumber;
    private String accountHolder;
    private double balance;


    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }


    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid or insufficient funds for withdrawal.");
        }
    }


    public double getBalance() {
        return balance;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getAccountHolder() {
        return accountHolder;
    }
}

class BankDemo {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount("ACC123", "Jane Doe", 500.0);

        System.out.println("Account Holder: " + account1.getAccountHolder());
        System.out.println("Initial Balance: $" + account1.getBalance());

        account1.deposit(150.0);
        account1.withdraw(200.0);

        System.out.println("Final Balance: $" + account1.getBalance());

    }
}

