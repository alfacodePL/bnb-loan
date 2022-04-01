package pl.alfacode.dnbloans.rest.dto.validation;

import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

import pl.alfacode.dnbloans.rest.dto.LoanApplicationRequest;

public class LoanTermRangeValidator implements ConstraintValidator<TermRangeConstraint, LoanApplicationRequest> {


    @Value("${dnbloans.loan.term.min}")
    private int minTerm;

    @Value("${dnbloans.loan.term.max}")
    private int maxTerm;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean isValid(LoanApplicationRequest loanApplicationRequest, ConstraintValidatorContext context) {
        if (!isBetween(loanApplicationRequest.getTerm(), minTerm, maxTerm)) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(
                            messageSource.getMessage("validation.loanapplication.request.term.range",
                                    new Object[]{minTerm, maxTerm}, Locale.getDefault()))
                    .addConstraintViolation();
            return false;

        }
        return true;
    }

    private boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
