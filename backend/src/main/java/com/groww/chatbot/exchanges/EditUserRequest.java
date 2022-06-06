package com.groww.chatbot.exchanges;

import lombok.Data;

/**
 * exchanges class
 * for edit user account details request
 */

@Data
public class EditUserRequest {

    private String name;
    private String password;
    private String pan;
    private String mobileNumber;
    private String dateOfBirth;
    private String gender;
    private String maritalStatus;

}
