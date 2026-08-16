package com.example.backend.utils;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class PasswordManager {

    public PasswordManager() {
    }

    // generate unique password based on name and email chars
    public String generateTemporaryPassword(String email, String fullName) {
        return getEmailString(email).concat(getIntials(fullName)) + "" +
                randomSpecialsChars().concat(randomNum());
    }

    // Returns an uppercase combination of first letters from surname & name
    private String getIntials(String fullName) {
        String initials = Character.toString(fullName.charAt(0));
        for (int i = 0; i < fullName.length(); i++) {
            initials += fullName.charAt(i) == ' ' ? fullName.charAt(i + 1) : "";
        }
        return initials.toUpperCase();
    }

    // Returns a lowercase combination of email chars found at even index, stops at
    private String getEmailString(String email) {
        String emailStr = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            emailStr += (i % 2 == 0) ? email.charAt(i) : "";
        }
        return emailStr.toLowerCase();
    }

    private String randomNum() {
        String randomNums = null;
        for (int i = 1; i < 2; i++) {
            int randomNum = (58 + new Random().nextInt(7));
            randomNums += String.valueOf(randomNum);
        }

        return randomNums;
    }

    private String randomSpecialsChars() {
        String randomChars = null;
        for (int i = 1; i < 2; i++) {
            char randomChar = (char) (58 + new Random().nextInt(7));
            randomChars += randomChar;
        }
        return randomChars;
    }
}