package com.bank.demo01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class BankSemaphoreApp {

    /**
     * process deposit
     */
    public void processDeposit() {

        // create 5 permits for semaphore
        Semaphore semaphore = new Semaphore(5);
        // create cached thread pool
        ExecutorService es = Executors.newCachedThreadPool();

        int depositAmount = 100;

        // create 20 bank accounts for depositing
        for (int i = 0; i < 20; i++) {
            BankAccount bankAccount = new BankAccount("BankAccount-" + i);
            Callable<Double> depositTransaction = new BankTransaction(bankAccount, depositAmount, semaphore);

            // submit one thread
            es.submit(depositTransaction);
        }
        // shutdown thread pool
        es.shutdown();

        // get 2 permits from semaphore
        semaphore.acquireUninterruptibly(2);
        System.out.println("It is time for lunch.");
        // release 2 permits to semaphore
        semaphore.release(2);
    }

    public static void main(String[] args) {
        BankSemaphoreApp test = new BankSemaphoreApp();
        test.processDeposit();
    }
}
