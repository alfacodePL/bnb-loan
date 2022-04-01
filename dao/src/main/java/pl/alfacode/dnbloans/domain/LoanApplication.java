package pl.alfacode.dnbloans.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal principal;

    private BigDecimal debt;

    private LocalDate dueDate;

    public LoanApplication(Builder builder) {
        this.dueDate = builder.dueDate;
        this.debt = builder.debt;
        this.principal = builder.principal;
    }

    public LoanApplication() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void extendTerm(Period period) {
        if (period == null || this.dueDate == null) {
            return;
        }
        this.dueDate = this.dueDate.plus(period);
    }


    public static final class Builder {
        private BigDecimal principal;
        private BigDecimal debt;
        private LocalDate dueDate;


        private Builder() {
        }

        public static Builder aLoanApplication() {
            return new Builder();
        }

        public Builder withPrincipal(BigDecimal principal, int interestRate) {
            this.principal = principal;
            this.debt = principal.add(principal.movePointLeft(2).multiply(new BigDecimal(interestRate)));
            return this;
        }

        public Builder withTerm(int termInDays) {
            this.dueDate = LocalDate.now().plus(Period.ofDays(termInDays));
            return this;
        }

        public LoanApplication build() {
            return new LoanApplication(this);
        }
    }
}
