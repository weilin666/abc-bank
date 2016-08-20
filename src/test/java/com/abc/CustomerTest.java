package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
   public static void main(String[] argv){
        Customer oscar = new Customer("Oscar");

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingAccount = new Account(Account.SAVINGS);

        checkingAccount.deposit(300);
        savingAccount.deposit(200);
        oscar.openAccount(checkingAccount);
        oscar.openAccount(savingAccount);
        try{
           checkingAccount.withdraw(200);
           System.out.println(checkingAccount.sumTransactions());
           oscar.transferAccount(checkingAccount, savingAccount, 50);
           System.out.println(checkingAccount.sumTransactions());
           System.out.println(savingAccount.sumTransactions());
        }catch(OverdraftException e) {
           e.printStackTrace();
        }
        System.out.println(oscar.totalInterestEarned());

    }

@Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        try{
           savingsAccount.withdraw(200.0);
        }catch(OverdraftException e) {
           e.printStackTrace();
        }
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }


    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
