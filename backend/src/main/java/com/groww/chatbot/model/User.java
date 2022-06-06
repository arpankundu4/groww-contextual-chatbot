package com.groww.chatbot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * model class for User data
 * gets persisted in database
 */

@Data
@Document("users")
public class User {

    @Id
    private String id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    private String pan;

    @Digits(fraction = 0, integer = 10)
    private String mobileNumber;

    private boolean kycDone = false;

    private String dateOfBirth;

    private String gender;

    private String maritalStatus;

}
