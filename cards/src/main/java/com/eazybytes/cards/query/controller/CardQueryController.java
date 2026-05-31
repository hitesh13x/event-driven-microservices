package com.eazybytes.cards.query.controller;

import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.query.FindCardQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class CardQueryController {

    private final QueryGateway queryGateway;

    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam("mobileNumber") String mobileNumber) {
        FindCardQuery findCardQuery = new FindCardQuery(mobileNumber);
        CardsDto card = queryGateway.query(findCardQuery, ResponseTypes.instanceOf(CardsDto.class)).join();
        return ResponseEntity.status(HttpStatus.OK).body(card);
    }
}
