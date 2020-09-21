package com.bank.demo01;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class BankTransaction implements Callable<Double> {

    private Semaphore semaphore;
    private BankAccount account;
    private double amount;

    public BankTransaction(BankAccount account, double amount, Semaphore semaphore) {
        this.account = account;
        this.amount = amount;
        this.semaphore = semaphore;
    }

    public Double call() throws IllegalArgumentException {
        String accountName = this.account.getAccountNumber();
        if (semaphore.availablePermits() > 0) {
            System.out.println(accountName + " started, bank window is available, go to deposit.");
        } else {
            System.out.println(accountName + " started，bank window is unavailable，wait in line.");
        }
        try {
            semaphore.acquire();
            System.out.println(accountName + " is depositing money, account balance is " + account.getAccountBalance());
            account.depositAmount(amount);
            Thread.sleep(1000);
            System.out.println(accountName + " deposited money, left bank, account balance is " + account.getAccountBalance());
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return account.getAccountBalance();
    }

    public void depositAmount() {
        this.account.depositAmount(this.amount);
    }

    public void withdrawAmount() {
        this.account.withdrawAmount(amount);
    }

    public double getBalance() {
//        System.out.println(this.account.getAccountNumber() + ", Amount: " + this.amount);
//        System.out.println("New Account Balance: " + this.account.getAccountBalance());
        return this.account.getAccountBalance();
    }
}