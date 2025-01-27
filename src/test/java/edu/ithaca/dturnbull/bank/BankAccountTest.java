package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest() {
        assertTrue(BankAccount.isEmailValid("a@b.com")); // valid email address
        assertFalse(BankAccount.isEmailValid("")); // empty string

        // Prefix Testing
        assertFalse(BankAccount.isEmailValid(".a@b.com")); // special character at beginning
        assertFalse(BankAccount.isEmailValid("lola@.a@b.com")); // multiple @ symbols
        assertFalse(BankAccount.isEmailValid("a.@b.com")); // special character at end
        assertTrue(BankAccount.isEmailValid("a.b@c.com")); // special character in middle
        assertFalse(BankAccount.isEmailValid("a..b@c.com")); // two special characters in a row
        assertFalse(BankAccount.isEmailValid("a#b@c.com")); // invalid special character

        // Domain Testing
        assertFalse(BankAccount.isEmailValid("a@b")); // no top level domain
        assertFalse(BankAccount.isEmailValid("a@b#c.com")); // invalid special character in domain name
        assertTrue(BankAccount.isEmailValid("a@b-c.com")); // valid special character in domain
        assertFalse(BankAccount.isEmailValid("a@.com")); // empty domain
        assertFalse(BankAccount.isEmailValid("a@b..com")); // two dots in a row
        assertFalse(BankAccount.isEmailValid("a@b.c")); // invalid top level domain
        assertTrue(BankAccount.isEmailValid("a@b.cc")); // boundry case top level domain
        assertTrue(BankAccount.isEmailValid("a@b.org")); // valid top level domain  */

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        // check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));
    }

}