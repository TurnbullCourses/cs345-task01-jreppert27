package edu.ithaca.dturnbull.bank;

import java.util.List;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email) {
        if (email.isEmpty() || email.length() > 320) {
            return false;
        }

        int atCount = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atCount++;
            }
        }
        if (atCount != 1) {
            return false;
        }

        int atIndex = email.indexOf("@");
        if (atIndex == 0 || atIndex == email.length() - 1) { // '@' can't be first or last
            return false;
        }

        String local = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return isLocalValid(local) && isDomainValid(domain);
    }

    private static boolean isLocalValid(String local) {
        if (local.isEmpty() || local.length() > 64 || local.startsWith(".") || local.endsWith(".")) {
            return false;
        }

        for (char c : local.toCharArray()) {
            if (!(Character.isLetterOrDigit(c) || "!#$%&'*+/=?^_`{|}~.-".indexOf(c) != -1)) {
                return false;
            }
        }

        // Check for consecutive dots
        for (int i = 1; i < local.length(); i++) {
            if (local.charAt(i) == '.' && local.charAt(i - 1) == '.') {
                return false;
            }
        }

        return true;
    }


    private static boolean isDomainValid(String domain) {
        if (domain.isEmpty() || domain.length() > 255 || domain.startsWith("-") || domain.endsWith("-")) {
            return false;
        }

        String[] parts = domain.split("\\.");
        if (parts.length < 2) { // Must have at least one dot
            return false;
        }

        for (String part : parts) {
            if (part.isEmpty() || part.length() > 63) {
                return false;
            }

            for (char c : part.toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '-') {
                    return false;
                }
            }
        }

        return true;
    }


}