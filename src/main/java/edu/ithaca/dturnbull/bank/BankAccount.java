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
        if (email.isEmpty() || email.length() > 320) { // is email empty? if so, not valid
            return false;
        }

        int atCount = 0;   // counts the occurrences of the @ symbol in email
        for (char c : email.toCharArray()) {
            if (c == '@') {
                atCount++;
            }
        }
        if (atCount != 1) {  // if the count is anything other than 1, not valid
            return false;
        }

        int atIndex = email.indexOf('@'); // gets the index of @ symbol to split email into prefix and domain
        String prefix = email.substring(0, atIndex);  // all chr before @ is prefix
        String domain = email.substring(atIndex + 1); // all chr after @ is domain

        return isPrefixValid(prefix) && isDomainValid(domain);  // return boolean of subfunctions, if either of them is false, email not valid
    }

    private static boolean isPrefixValid(String prefix) {
        if (prefix.isEmpty() || prefix.length() > 64 || prefix.startsWith(".") || prefix.endsWith(".")) {
            return false;
        } // if email is empty or longer than 64 characters or begins with a . or ends with a ., email not valid

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
        if (domain.isEmpty() || domain.length() > 255 || domain.startsWith("-") || domain.endsWith("-")) {
            return false;
        } // if email is empty or longer than 255 characters or starts with - or ends with -, email not validd

        String[] parts = domain.split("\\."); // Splits by dot into domain parts using regex
        if (parts.length < 2) {
            return false; // Needs at least one subdomain and a top-level domain
        }

        for (String part : parts) {
            if (part.isEmpty() || part.length() > 63) {
                return false; // Empty or overly long subdomain
            }
            for (char c : part.toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '-') {
                    return false; // Invalid character in subdomain
                }
            }
        }

        String tld = parts[parts.length - 1]; // Top-level domain
        return tld.length() >= 2; // TLD must be at least 2 characters
    }

}