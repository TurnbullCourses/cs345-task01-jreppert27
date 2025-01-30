package edu.ithaca.dturnbull.bank;

import java.util.List;
import org.apache.commons.validator.routines.DomainValidator;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (isEmailValid(email) && isAmountValid(startingBalance)) {
            this.email = email;
            this.balance = startingBalance;
        } else {
            if (!isAmountValid(startingBalance)) {
                throw new IllegalArgumentException("Starting balance is invalid, cannot create account");
            } else {
                throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");

            }
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller
     *       than balance
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid dollar amount");
        }

        if (amount <= balance) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    public void deposit(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid dollar amount");
        }

        balance += amount;
    }

    public void transfer(double amount, BankAccount otherAccount) throws InsufficientFundsException {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid dollar amount");
        }

        withdraw(amount);
        otherAccount.deposit(amount);
    }

    public static boolean isAmountValid(double amount) {
        if (amount < 0) { // if amount is negative, not valid
            return false;
        }
        return (amount * 100) == Math.floor(amount * 100); // Ensures only two decimal places
    }

    public static boolean isEmailValid(String email) {
        if (email.isEmpty() || email.length() > 320) { // is email empty? if so, not valid
            return false;
        }

        int atCount = 0; // counts the occurrences of the @ symbol in email
        for (char c : email.toCharArray()) {
            if (c == '@') {
                atCount++;
            }
        }
        if (atCount != 1) { // if the count is anything other than 1, not valid
            return false;
        }

        int atIndex = email.indexOf('@'); // gets the index of @ symbol to split email into prefix and domain
        String prefix = email.substring(0, atIndex); // all chr before @ is prefix
        String domain = email.substring(atIndex + 1); // all chr after @ is domain

        return isPrefixValid(prefix) && isDomainValid(domain); // return boolean of subfunctions, if either of them is
                                                               // false, email not valid
    }

    private static boolean isPrefixValid(String prefix) {
        if (prefix.isEmpty() || prefix.length() > 64 || prefix.startsWith(".") || prefix.endsWith(".")) {
            return false;
        } // if email is empty or longer than 64 characters or begins with a . or ends
          // with a ., email not valid

        char prevChar = 0; // previous character
        for (char c : prefix.toCharArray()) {
            // Allow only valid characters
            if (!(Character.isLetterOrDigit(c) || "!$%&'*+/=?^_`{|}~.-".indexOf(c) != -1)) {
                return false; // Invalid character (e.g., '#')
            }
            if (c == '.' && prevChar == '.') {
                return false; // Consecutive dots
            }
            prevChar = c;
        }

        return true;
    }

    private static boolean isDomainValid(String domain) {
        // uses DomainValidator from Apache Commons Validator to validate domain
        // not only checks for valid characters but also checks if domain is a valid
        return DomainValidator.getInstance().isValid(domain);
        // in code review, listener suggested I test that the domain not only has valid
        // charcters but also is a valid domain
    }

}