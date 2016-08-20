package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    //wei: should it be private?
    public List<Transaction> transactions;
    private double totalBalance;


    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        totalBalance = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            //wei:
            totalBalance = totalBalance + amount;
            transactions.add(new Transaction(amount, totalBalance));
        }
    }


public void withdraw(double amount) throws OverdraftException {
 
    //wei: should check the overall balance to prevent overdraftting

    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        totalBalance = totalBalance - amount;
        if(totalBalance < 0) {
           System.out.println("Overdrating. Withdraw abort.");
           throw (new OverdraftException("Overdraft"));
        }else 
           transactions.add(new Transaction(-amount, totalBalance));
    }

}

/*  not accurate calculation of interest 

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }
*/

  //wei: updated method to use daily interest rate
    public double interestEarned() {

        double interest = 0;
        Date now = DateProvider.getInstance().now();

        switch(accountType){
            case SAVINGS:
               for( Transaction t: transactions ){
                  long elapsedDays = elapsedDays(t.transactionDate, now); 
                  if(t.totalBalance <= 1000){
                     interest = interest + t.amount * elapsedDays * 0.001/365; 
                  }else{
                     interest = interest + t.amount * elapsedDays * 0.002/365; 
                  } 
               }
               return interest;
            case MAXI_SAVINGS:
               double dailyRate = 0.05/365;
               for( Transaction t: transactions){
                  long elapsedDays = elapsedDays(t.transactionDate, now); 
                  if((t.amount<0) && (elapsedDays <=10)) {
                     dailyRate = 0.001/365;
                     break;
                  }
               }
               for( Transaction t: transactions){
                  long elapsedDays = elapsedDays(t.transactionDate, now); 
                  interest = interest + t.amount * elapsedDays * dailyRate;
               }
               return interest;
            default:
               for( Transaction t: transactions){
                  long elapsedDays = elapsedDays(t.transactionDate, now); 
                 interest = interest + t.amount * elapsedDays * 0.001/365;
               }
               return interest;
        }       
    }


    //wei: utility method to return days elapsed between two dates
    public long elapsedDays(Date beginDate, Date endDate){

        long difference = endDate.getTime() - beginDate.getTime();
        System.out.println(difference);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = difference / daysInMilli;
        return elapsedDays;
    }


    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
