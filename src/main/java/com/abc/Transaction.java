package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    //wei: need to know the total balance to calculate the interest later on
    public final double totalBalance;

    public final Date transactionDate;

    public Transaction(double amount, double totalBalance) {
        this.amount = amount;
        this.totalBalance = totalBalance;
        this.transactionDate = DateProvider.getInstance().now();
    }

}

