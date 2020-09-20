package com.racecondition.service;

import com.racecondition.model.BankAccount;

import java.util.concurrent.Callable;

public class BankTransaction implements Callable<Double> {

    public static enum TransactionType {
        DEPOSIT_MONEY(1), WITHDRAW_MONEY(2);

        private TransactionType(int value) {
        }
    }

    private TransactionType transactionType;
    private BankAccount account;
    private double amount;

    /*
     * If transactionType == 1, depositAmount() else if transactionType == 2 withdrawAmount()
     */
    public BankTransaction(BankAccount account, TransactionType transactionType, double amount) {
        this.transactionType = transactionType;
        this.account = account;
        this.amount = amount;
    }

    public Double call() throws IllegalArgumentException {
        double balance = 0;

        switch (this.transactionType) {
            case DEPOSIT_MONEY:
                depositAmount();
                balance = printBalance();
                break;
            case WITHDRAW_MONEY:
                withdrawAmount();
                balance = printBalance();
                break;
            default:
                System.out.println("NOT A VALID TRANSACTION");
        }
        return balance;
    }

    public void depositAmount() {
        this.account.depositAmount(this.amount);
    }

    public void withdrawAmount() {
        this.account.withdrawAmount(amount);
    }

    public double printBalance() {
        System.out.println(Thread.currentThread().getName() + " : TransactionType: " + this.transactionType + ", Amount: " + this.amount);
        double balance = this.account.getAccountBalance();
        System.out.println("New Account Balance: " + balance);
        return balance;
    }
}