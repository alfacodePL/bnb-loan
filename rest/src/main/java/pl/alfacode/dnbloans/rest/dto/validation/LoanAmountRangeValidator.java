package pl.alfacode.dnbloans.rest.dto.validation;

import java.math.BigDecimal;
import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

import pl.alfacode.dnbloans.rest.dto.LoanApplicationRequest;

public class LoanAmountRangeValidator implements ConstraintValidator<AmountRangeConstraint, LoanApplicationRequest> {

    @Value("${dnbloans.loan.amount.min}")
    private BigDecimal minAmount;

    @Value("${dnbloans.loan.amount.max}")
    private BigDecimal maxAmount;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean isValid(LoanApplicationRequest loanApplicationRequest, ConstraintValidatorContext context) {
        if (!isBetween(loanApplicationRequest.getAmount(), minAmount, maxAmount)) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(messageSource.getMessage("validation.loanapplication.request.amount.range", new Object[]{minAmount, maxAmount}, Locale.getDefault()))
                    .addConstraintViolation();
            return false;

        }
        return true;
    }

    private boolean isBetween(BigDecimal value, BigDecimal start, BigDecimal end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }
}
