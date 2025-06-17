package com.juandavyc.loans.controller;

import com.juandavyc.loans.constants.LoanConstants;
import com.juandavyc.loans.dto.ErrorResponseDto;
import com.juandavyc.loans.dto.LoanDto;
import com.juandavyc.loans.dto.ResponseDto;
import com.juandavyc.loans.mapper.LoanMapper;
import com.juandavyc.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.function.ServerRequest;

@Tag(
        name = "CRUD REST APIs for Loans in Bank",
        description = "CRUD REST APIs in Bank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api") //,produces = {MediaType.APPLICATION_JSON_VALUE}
@RequiredArgsConstructor
public class LoanController {

    private final ILoanService iLoanService;


    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createLoan(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
            String mobileNumber
    ) {
        final var createdLoan = iLoanService.createLoan(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/api/fetch/" + createdLoan)
                .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201));

    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping(path = "/fetch")
    public ResponseEntity<LoanDto> fetchLoanByMobileNumber(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
            String mobileNumber
    ) {
        final var loan = iLoanService.fetchLoanByMobileNumber(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loan);
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping(path = "/fetch/{id}")
    public ResponseEntity<LoanDto> fetchLoanById(
            @PathVariable Long id
    ) {
        final var loan = iLoanService.fetchLoanById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loan);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(
            @Valid @RequestBody LoanDto loansDto
    ) {
        boolean isUpdated = iLoanService.updateLoan(loansDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_UPDATE));
        }
    }

//    @Operation(
//            summary = "Delete Loan Details REST API",
//            description = "REST API to delete Loan details based on a mobile number"
//    )
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "HTTP Status OK"
//            ),
//            @ApiResponse(
//                    responseCode = "417",
//                    description = "Expectation Failed"
//            ),
//            @ApiResponse(
//                    responseCode = "500",
//                    description = "HTTP Status Internal Server Error",
//                    content = @Content(
//                            schema = @Schema(implementation = ErrorResponseDto.class)
//                    )
//            )
//    }
//    )
//    @DeleteMapping("/delete")
//    public ResponseEntity<ResponseDto> deleteLoanDetails(
//            @RequestParam
//            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
//            String mobileNumber
//    ) {
//        boolean isDeleted = iLoanService.deleteLoan(mobileNumber);
//        if (isDeleted) {
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
//        } else {
//            return ResponseEntity
//                    .status(HttpStatus.EXPECTATION_FAILED)
//                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
//        }
//    }

}
