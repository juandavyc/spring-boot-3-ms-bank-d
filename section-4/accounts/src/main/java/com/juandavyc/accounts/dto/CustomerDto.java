package com.juandavyc.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"

)
public class CustomerDto {

    @Schema(
            description = "Name of the Customer", example = "Juan Davyc"
    )
    @NotEmpty(message = "name cannot be a null or empty ") // required
    @Size(min = 5, max = 30, message = "the length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email of the Customer", example = "juandavyc@juandavyc.com"
    )
    @NotEmpty(message = "email cannot be a null or empty")
    @Email(message = "email should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the Customer", example = "3000000000"
    )
    @Pattern(regexp = "^\\d{10}$", message = "mobile number must be exactly 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}
