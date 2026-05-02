package com.example.backend.utils;

import org.springframework.stereotype.Service;
import com.example.backend.model.User;

@Service
public class PasswordManager {

    public PasswordManager() {
    }

    //generate unique password based on name and email chars
    /*public String generateTemporaryPassword(User user) {
        return getEmailString(user.getEmail())
                .concat(getIntials(user.getFullName()));
    }

    // Returns an uppercase combination of first letters from surname & name
    private String getIntials(String fullName) {
        String initials = Character.toString(fullName.charAt(0));
        for (int i = 0; i < fullName.length(); i++) {
            initials += fullName.charAt(i) == ' ' ? fullName.charAt(i + 1) : "";
        }
        return initials.toUpperCase();
    }// end

    // Returns a lowercase combination of email chars found at even index, stops at
    private String getEmailString(String email) {
        String emailStr = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            emailStr += (i % 2 == 0) ? email.charAt(i) : "";
        }
        return emailStr.toLowerCase();
    }*/
}