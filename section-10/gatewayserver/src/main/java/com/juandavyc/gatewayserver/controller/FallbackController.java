package com.juandavyc.gatewayserver.controller;

import com.juandavyc.gatewayserver.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
public class FallbackController {

    @RequestMapping(path = "/contactSupport")
    public Mono<ErrorResponseDto> contactSupport() {
        return Mono.just(
                new ErrorResponseDto(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "An error occurred, please try after some time or contact support team",
                        LocalDateTime.now()
                )
        );
    }

}
