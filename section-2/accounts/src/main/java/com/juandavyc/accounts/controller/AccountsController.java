package com.juandavyc.accounts.controller;

import com.juandavyc.accounts.constants.AccountConstants;
import com.juandavyc.accounts.dto.CustomerDto;
import com.juandavyc.accounts.dto.ErrorResponseDto;
import com.juandavyc.accounts.dto.ResponseDto;
import com.juandavyc.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated

@Tag(
        name = "CRUD REST APIs for Accounts",
        description = "to CREATE, READ, UPDATE and DELETE account details"
)

public class AccountsController {

    private final IAccountsService accountsService;


    @Operation(
            summary = "Create account REST API",
            description = "REST API to create a new Customer and Account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createAccount(
            @Valid @RequestBody CustomerDto customerDto
    ) {
        final var createdAccount = accountsService.createAccount(customerDto);
        return ResponseEntity.created(URI.create("/api/accounts/" + createdAccount))
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Read account REST API",
            description = "REST API to read a Customer and Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping(path = "/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(
            @Pattern(regexp = "^\\d{10}$", message = "mobile number must be exactly 10 digits")
            @RequestParam String mobileNumber
    ) {
        CustomerDto customerDto = accountsService.fetchAccountByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update customer and account REST API",
            description = "REST API to update Customer and Account details based in a account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Http Status EXPECTATION FAILED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(
            @Valid @RequestBody CustomerDto customerDto
    ) {
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }

    }

    @Operation(
            summary = "Delete customer and account REST API",
            description = "REST API to delete customer and account based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Http Status EXPECTATION FAILED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(
            @Pattern(regexp = "^\\d{10}$", message = "mobile number must be exactly 10 digits")
            @RequestParam String mobileNumber
    ) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }


}
