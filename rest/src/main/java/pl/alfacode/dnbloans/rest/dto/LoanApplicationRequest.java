package pl.alfacode.dnbloans.rest.dto;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import pl.alfacode.dnbloans.rest.dto.validation.AmountRangeConstraint;
import pl.alfacode.dnbloans.rest.dto.validation.TermRangeConstraint;
import pl.alfacode.dnbloans.rest.dto.validation.TimeRangeConstraint;

@AmountRangeConstraint
@TermRangeConstraint
@TimeRangeConstraint
public class LoanApplicationRequest {

    public LoanApplicationRequest(BigDecimal amount, int term) {
        this.amount = amount;
        this.term = term;
    }

    public LoanApplicationRequest() {
    }

    @NotNull(message = "validation.loanapplication.request.amount.null")
    private BigDecimal amount;

    @NotNull(message = "validation.loanapplication.request.term.null")
    private int term;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanApplicationRequest)) return false;
        LoanApplicationRequest that = (LoanApplicationRequest) o;
        return Objects.equals(amount, that.amount) && Objects.equals(term, that.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, term);
    }
}
