package com.eazybytes.loans.query.projection;

import com.eazybytes.common.event.LoanMobileNumUpdatedEvent;
import com.eazybytes.loans.command.event.LoanCreatedEvent;
import com.eazybytes.loans.command.event.LoanDeletedEvent;
import com.eazybytes.loans.command.event.LoanUpdatedEvent;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.service.ILoansService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("loan-group")
public class LoanProjection {

    private final ILoansService iLoansService;

    @EventHandler
    public void on(LoanCreatedEvent customerCreatedEvent) {
        Loans loan = new Loans();
        BeanUtils.copyProperties(customerCreatedEvent, loan);
        iLoansService.createLoan(loan);
    }

    @EventHandler
    public void on(LoanUpdatedEvent loanUpdatedEvent) {
        iLoansService.updateLoan(loanUpdatedEvent);
    }

    @EventHandler
    public void on(LoanDeletedEvent loanDeletedEvent) {
        iLoansService.deleteLoan(loanDeletedEvent.getLoanNumber());
    }

    @EventHandler
    public void on(LoanMobileNumUpdatedEvent loanMobileNumUpdatedEvent) {
        iLoansService.updateMobileNumberOrchestor(loanMobileNumUpdatedEvent.getMobileNumber(), loanMobileNumUpdatedEvent.getNewMobileNumber());
    }
}
