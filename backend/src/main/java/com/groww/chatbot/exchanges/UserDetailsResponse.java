package com.groww.chatbot.exchanges;

import lombok.Data;

/**
 * exchanges class
 * for outgoing user account details response
 */

@Data
public class UserDetailsResponse {

    private String name;
    private String email;
    private String mobileNumber;
    private String dateOfBirth;
    private String pan;
    private String gender;
    private boolean kycDone;
    private String maritalStatus;

}
