package com.juandavyc.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(
            description = "Account Number of account", example = "1000"
    )
    @NotEmpty(message = "account number cannot be a null or empty")
    private Long accountNumber;

    @Schema(
            description = "Account Type of account", example = "Savings"
    )
    @NotEmpty(message = "account type cannot be a null or empty")
    private String accountType;

    @Schema(
            description = "Account Branch of account", example = "123, New York"
    )
    @NotEmpty(message = "branch address cannot be a null or empty")
    private String branchAddress;
}
