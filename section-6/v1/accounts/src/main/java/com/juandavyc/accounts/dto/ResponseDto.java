package com.juandavyc.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold Successful response information"
)
public class ResponseDto {

    @Schema(
            description = "StatusCode in the Response"
    )
    private String statusCode;

    @Schema(
            description = "StatusMessage in the Response"
    )
    private String statusMessage;


}
