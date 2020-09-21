
package com.bank.demo01;

/**
 *
 */

public class BankAccount {
    private String accountNumber;
    private double accountBalance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Make a note of this line -- synchronized keyword added
    public synchronized boolean depositAmount(double amount) {
        if (amount < 0) {
            return false;
        } else {
            accountBalance += amount;
            return true;
        }
    }

    // Make a note of this line -- synchronized keyword added
    public synchronized boolean withdrawAmount(double amount) {
        if (amount > accountBalance) {
            return false;
        } else {
            accountBalance -= amount;
            return true;
        }
    }
}
