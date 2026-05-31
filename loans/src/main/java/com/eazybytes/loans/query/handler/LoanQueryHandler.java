package com.eazybytes.loans.query.handler;

import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.query.FindLoanQuery;
import com.eazybytes.loans.service.ILoansService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanQueryHandler {

    private final ILoansService iLoansService;

    @QueryHandler
    public LoansDto findLoan(FindLoanQuery findLoanQuery) {
        return iLoansService.fetchLoan(findLoanQuery.getMobileNumber());
    }
}
