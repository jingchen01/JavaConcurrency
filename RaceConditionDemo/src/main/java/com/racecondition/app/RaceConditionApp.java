package com.racecondition.app;

import com.racecondition.model.BankAccount;
import com.racecondition.service.BankTransaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class RaceConditionApp {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("AccountNumber");
        double depositAmount = 100;
        double withdrawAmount = 50;

        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);

        //create a list to hold the Future object associated with Callable
        List<Future<Double>> list = new ArrayList<Future<Double>>();

        //Create Callable instance for deposit and withdraw transactions
        Callable<Double> depositTransaction = new BankTransaction(account, BankTransaction.TransactionType.DEPOSIT_MONEY, depositAmount);
        Callable<Double> withdrawTransaction = new BankTransaction(account, BankTransaction.TransactionType.WITHDRAW_MONEY, withdrawAmount);

        // Total Expected Deposit: 10000 (100 x 100)
        for (int i = 0; i < 100; i++) {
            //submit Callable tasks to be executed by thread pool
            Future<Double> future = executor.submit(depositTransaction);
            //add Future to the list, we can get return value using Future
            list.add(future);
        }

        // Total Expected Withdrawal: 5000 (100 x 50)
        for (int i = 0; i < 100; i++) {
            Future<Double> future = executor.submit(withdrawTransaction);
            list.add(future);
        }

        for (Future<Double> fut : list) {
            try {
                // print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date() + "::" + fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Let's just wait for a second to make sure all thread execution completes.
        try {
            Thread.sleep(1000);
            //shut down the executor service now
            executor.shutdown();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        // Expected account balance is 5000
        System.out.println("Final Account Balance: " + account.getAccountBalance());
    }
}