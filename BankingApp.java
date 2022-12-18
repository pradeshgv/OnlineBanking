import java.util.ArrayList;
import java.util.Scanner;

class NoBalanceException extends Exception {
    public NoBalanceException(String str) {
        // calling the constructor of parent Exception
        super(str);
    }
}

interface Bank {
    public void openAccount();
}

class BankBrand {
    public static String bankname = "Infinity Bank";
}

class BankDetails extends BankBrand implements Bank {
    private String accno;
    private String name;
    private String acc_type;
    private long balance;
    Scanner sc = new Scanner(System.in);

    // method to open new account
    public void openAccount() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("*** Open New Account ***");
        System.out.print("Enter Account No: ");
        accno = sc.next();
        System.out.print("Enter Account type: ");
        acc_type = sc.next();
        System.out.print("Enter Name: ");
        name = sc.next();
        System.out.print("Enter Balance: ");
        try {
            balance = sc.nextLong();
        } finally {
            System.out.println("Account Successfully opened!");
            System.out.println("\nPress any key to continue...");
            sc.nextLine();
        }

    }

    // method to display account details
    public void showAccount() {
        System.out.println("*** Account Details ***");
        System.out.println("Name of account holder: " + name);
        System.out.println("Account no.: " + accno);
        System.out.println("Account type: " + acc_type);
        System.out.println("Balance: " + balance + "\n");

    }

    // method to deposit money
    public void deposit() {
        long amt;
        System.out.print("Enter the amount you want to deposit: ");
        amt = sc.nextLong();
        balance = balance + amt;
        System.out.println("Deposit Done Successfully!");
        System.out.println("\nPress any key to continue...");
        sc.nextLine();
        sc.nextLine();
    }

    // method to withdraw money
    public void withdrawal() throws NoBalanceException {
        long amt;
        System.out.print("Enter the amount you want to withdraw: ");
        amt = sc.nextLong();
        if (balance >= amt) {
            balance = balance - amt;
            System.out.println("Balance after withdrawal: " + balance);
        } else {
            throw new NoBalanceException("Your balance is less than " + amt + "\tTransaction failed...!!");

        }
        System.out.println("\nPress any key to continue...");
        sc.nextLine();
        sc.nextLine();
    }

    // method to search an account number
    public boolean search(String ac_no) {
        if (accno.equals(ac_no)) {
            showAccount();
            return (true);
        }
        return (false);
    }
}

class showAccountThread extends Thread {
    ArrayList<BankDetails> C;
    Scanner sc;

    showAccountThread(ArrayList<BankDetails> C) {
        this.C = C;
        sc = new Scanner(System.in);
    }

    public void run() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("*** Details of all accounts ***");
        if (C.size() == 0)
            System.out.println(" No accounts are available in bank!!!");
        for (int i = 0; i < C.size(); i++) {
            C.get(i).showAccount();
        }
        System.out.println("\nPress any key to continue...");
        sc.nextLine();
    }
}

class searchAccountThread extends Thread {
    ArrayList<BankDetails> C;
    Scanner sc;

    searchAccountThread(ArrayList<BankDetails> C) {
        this.C = C;
        sc = new Scanner(System.in);
    }

    public void run() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("*** Search by Account number ***");
        System.out.print("Enter account no. you want to search: ");
        String ac_no = sc.next();
        boolean found = false;
        for (int i = 0; i < C.size(); i++) {
            found = C.get(i).search(ac_no);
            if (found) {
                break;
            }
        }
        if (!found) {
            System.out.println("Search failed! Account doesn't exist..!!");
        }
        System.out.println("\nPress any key to continue...");
        sc.nextLine();
        sc.nextLine();
    }
}

class depositThread extends Thread {
    ArrayList<BankDetails> C;
    Scanner sc;

    depositThread(ArrayList<BankDetails> C) {
        this.C = C;
        sc = new Scanner(System.in);
    }

    public void run() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("*** Deposit Portal ***");
        System.out.print("Enter Account no. : ");
        String ac_no = sc.next();
        boolean found = false;
        for (int i = 0; i < C.size(); i++) {
            found = C.get(i).search(ac_no);
            if (found) {
                C.get(i).deposit();
                break;
            }
        }
        if (!found) {
            System.out.println("Search failed! Account doesn't exist..!!");
            System.out.println("\nPress any key to continue...");
            sc.nextLine();
        }
        sc.nextLine();
    }
}

class withdrawalThread extends Thread {
    ArrayList<BankDetails> C;
    Scanner sc;

    withdrawalThread(ArrayList<BankDetails> C) {
        this.C = C;
        sc = new Scanner(System.in);
    }

    public void run() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("*** Withdrawl Portal ***");
        System.out.print("Enter Account No : ");
        String ac_no = sc.next();
        boolean found = false;
        for (int i = 0; i < C.size(); i++) {
            found = C.get(i).search(ac_no);
            if (found) {
                try {
                    C.get(i).withdrawal();
                } finally {
                    break;
                }

            }
        }
        if (!found) {
            System.out.println("Search failed! Account doesn't exist..!!");
            System.out.println("\nPress any key to continue...");
            sc.nextLine();
        }
        sc.nextLine();
    }
}

class openThread extends Thread {
    ArrayList<BankDetails> C;
    Scanner sc;

    openThread(ArrayList<BankDetails> C) {
        this.C = C;
        sc = new Scanner(System.in);
    }

    public void run() {
        BankDetails b = new BankDetails();
        b.openAccount();
        C.add(b);
        sc.nextLine();
    }
}

public class BankingApp {
    public static void main(String arg[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        ArrayList<BankDetails> C = new ArrayList<>();
        BankDetails dummy = new BankDetails();
        // loop runs until number 5 is not pressed to exit
        int ch;
        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println(" ***Banking System Application***");
            System.out.println(
                    " Welcome to " + dummy.bankname
                            + "\n 1. Display all account details \n 2. Search by Account number\n 3. Deposit the amount \n 4. Withdraw the amount \n 5.Open New Account \n 0.Exit ");
            System.out.print("Enter your choice: ");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    Thread t1 = new showAccountThread(C);
                    t1.start();
                    t1.join();
                    break;
                case 2:
                    Thread t2 = new searchAccountThread(C);
                    t2.start();
                    t2.join();
                    break;
                case 3:
                    Thread t3 = new depositThread(C);
                    t3.start();
                    t3.join();
                    break;
                case 4:
                    Thread t4 = new withdrawalThread(C);
                    t4.start();
                    t4.join();
                    break;
                case 5:
                    Thread t5 = new openThread(C);
                    t5.start();
                    t5.join();
                    break;
                case 0:
                    System.out.println("See you soon...");
                    break;
            }
        } while (ch != 0);
        sc.close();
    }
}