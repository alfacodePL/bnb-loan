package pl.alfacode.dnbloans.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class LoanApplicationResponse {
    private BigDecimal amount;

    private LocalDate term;

    public LoanApplicationResponse(BigDecimal amount, LocalDate term) {
        this.amount = amount;
        this.term = term;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanApplicationResponse)) return false;
        LoanApplicationResponse that = (LoanApplicationResponse) o;
        return Objects.equals(amount, that.amount) && Objects.equals(term, that.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, term);
    }

    public LocalDate getTerm() {
        return term;
    }

    public void setTerm(LocalDate term) {
        this.term = term;
    }
}
