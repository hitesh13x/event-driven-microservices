package com.eazybytes.accounts.command.controller;

import com.eazybytes.accounts.command.CreateAccountCommand;
import com.eazybytes.accounts.command.DeleteAccountCommand;
import com.eazybytes.accounts.command.UpdateAccountCommand;
import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class AccountsCommandController {

    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(
            @RequestParam("mobileNumber") @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        CreateAccountCommand createAccountCommand = CreateAccountCommand.builder()
                .accountNumber(randomAccNumber)
                .mobileNumber(mobileNumber)
                .accountType(AccountsConstants.SAVINGS)
                .branchAddress(AccountsConstants.ADDRESS)
                .activeSw(AccountsConstants.ACTIVE_SW)
                .build();

        commandGateway.send(createAccountCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetaild(@Valid @RequestBody AccountsDto accountsDto) {

        UpdateAccountCommand updateAccountCommand = UpdateAccountCommand.builder()
                .accountNumber(accountsDto.getAccountNumber())
                .mobileNumber(accountsDto.getMobileNumber())
                .accountType(accountsDto.getAccountType())
                .branchAddress(accountsDto.getBranchAddress())
                .activeSw(AccountsConstants.ACTIVE_SW)
                .build();

        commandGateway.send(updateAccountCommand);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetaild(@RequestParam("accountNumber") Long accountNumber) {

        DeleteAccountCommand deleteAccountCommand = DeleteAccountCommand.builder()
                .accountNumber(accountNumber)
                .build();

        commandGateway.send(deleteAccountCommand);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }


}
