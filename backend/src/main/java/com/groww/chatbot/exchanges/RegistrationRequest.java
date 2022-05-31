package com.groww.chatbot.exchanges;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * exchanges class
 * for incoming registration request
 */

@Data
public class RegistrationRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Digits(fraction = 0, integer = 10)
    private String mobileNumber;

    private String dateOfBirth;

    private String pan;

    private String gender;

}
