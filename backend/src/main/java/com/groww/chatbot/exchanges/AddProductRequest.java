package com.groww.chatbot.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * exchanges class
 * for add product request
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {

    @NotBlank
    private String title;

    @NotNull
    @Positive
    private float price;

    @NotBlank
    private String logoUrl;

    @NotBlank
    private String chartUrl;

}
