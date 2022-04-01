package pl.alfacode.dnbloans.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import pl.alfacode.dnbloans.domain.LoanApplication;
import pl.alfacode.dnbloans.rest.dto.LoanApplicationRequest;
import pl.alfacode.dnbloans.rest.dto.LoanApplicationResponse;
import pl.alfacode.dnbloans.rest.dto.LoanExtendRequest;
import pl.alfacode.dnbloans.service.loanapplication.LoanApplicationService;

@RestController
@RequestMapping(path = "/api/loanapplication")
@Tag(name = "LoanController", description = "Loan application controller")
public class LoanController {

    @Value("${dnbloans.loan.interestrate}")
    private int interestRate;

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Operation(description = "Apply for loan. Return the unique identifier of new loan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan applied successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping
    public ResponseEntity<Long> applyForLoan(@RequestBody @Valid LoanApplicationRequest loanApplicationRequest) {
        return ResponseEntity.ok(loanApplicationService.save(LoanApplication.builder()
                .withTerm(loanApplicationRequest.getTerm())
                .withPrincipal(loanApplicationRequest.getAmount(), interestRate)
                .build()));
    }

    @Operation(description = "Extend loan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan extended successfully"),
            @ApiResponse(responseCode = "400", description = "Loan application with given id not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Long> extendLoan(@PathVariable("id") Long id,
                                           @RequestBody @Validated LoanExtendRequest loanExtendRequest) {
        LoanApplication loanApplication = loanApplicationService.getById(id);
        loanApplication.extendTerm(loanExtendRequest.getExtensionTerm().getPeriod());
        return ResponseEntity.ok(loanApplicationService.save(loanApplication));
    }

    @Operation(description = "Fetch loan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Loan application with given id not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> fetchLoanApplication(@PathVariable("id") Long id) {
        LoanApplication loanApplication = loanApplicationService.getById(id);
        return ResponseEntity.ok(new LoanApplicationResponse(loanApplication.getDebt(), loanApplication.getDueDate()));
    }
}
