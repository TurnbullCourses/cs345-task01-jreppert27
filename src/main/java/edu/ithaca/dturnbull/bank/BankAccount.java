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


    public static boolean isEmailValid(String email){
        if (email.isEmpty()){
            return false;
        }

        int atCount = 0;
        for (int i = 0; i < email.length() -1; i++){
            if (email.charAt(i) == '@'){
                atCount = atCount + 1;
            }
        }
        if (atCount > 1){
            return false;
        }

        int atIndex = email.indexOf("@");
        if (atIndex == -1 || atIndex == 0 || atIndex == email.length() - 1){
            return false;
        }

        for (int i = 0; i < email.indexOf("@"); i++) {
            StringBuilder local = new StringBuilder();
            local.append(email.charAt(i));
        }

        for (int i = 0; i > email.indexOf("@") && i < email.length() -1; i++) {
            StringBuilder domain = new StringBuilder();
            domain.append(email.charAt(i));
        }

        public static boolean isLocalValid(String local){

        }

        public static boolean isDomainValid(String domain){

        }

    }
}