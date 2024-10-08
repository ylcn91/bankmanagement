package org.doksanbir.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.doksanbir.interfaces.Printable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Slf4j
public abstract class Account implements Printable {

    /*
       deposit
       getBalance
       getTransactions?
       withdraw
       getTransactionHistory
       printAccountSummary
     */

    private static final AtomicLong idCounter = new AtomicLong(1000000000);

    private String accountNumber;
    protected BigDecimal balance;
    protected final List<Transaction> transactinHistory;


    public Account() {
        this.accountNumber = String.valueOf(idCounter.getAndIncrement());
        this.balance = BigDecimal.ZERO;
        this.transactinHistory = new ArrayList<>();
        log.info("Account created: {}", accountNumber);
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Deposit amount must be greater than zero.");
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        balance = balance.add(amount);

        transactinHistory.add(Transaction.builder()
                .type(TransactionType.DEPOSIT)
                .amount(amount)
                .description("Deposit to account " + accountNumber)
                .build());

        log.info("Deposit successful: {}", amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Withdrawal amount must be greater than zero.");
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (amount.compareTo(balance) > 0) {
            log.warn("Insufficient funds for withdrawal.");
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }
        balance = balance.subtract(amount);

        transactinHistory.add(Transaction.builder()
                .type(TransactionType.WITHDRAWAL)
                .amount(amount)
                .description("Withdrawal from account " + accountNumber)
                .build());

        log.info("Withdrawal successful: {}", amount);
    }

    public BigDecimal getBalance() {
        log.info("Retrieving balance for account: {}: {}", accountNumber, balance);
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        log.info("Retrieving transaction history for account: {}", accountNumber);
        return Collections.unmodifiableList(transactinHistory);
    }

    public void transfer(Account targetAccount, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Transfer amount must be greater than zero.");
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        if (amount.compareTo(balance) > 0) {
            log.warn("Insufficient funds for transfer.");
            throw new IllegalArgumentException("Insufficient funds for transfer.");
        }
        balance = balance.subtract(amount);
        targetAccount.deposit(amount);

        transactinHistory.add(Transaction.builder()
                .type(TransactionType.TRANSFER_OUT)
                .amount(amount)
                .description("Transfer to account " + targetAccount.getAccountNumber())
                .build());

        targetAccount.getTransactinHistory().add(Transaction.builder()
                .type(TransactionType.TRANSFER_IN)
                .amount(amount)
                .description("Transfer from account " + accountNumber)
                .build());

        log.info("Transfer successful: {}", amount);
    }

    /*
      prints account number, balance, and transaction history
      must be implemented by subclasses
     */

    @Override
    public abstract void printAccountSummary();

}
