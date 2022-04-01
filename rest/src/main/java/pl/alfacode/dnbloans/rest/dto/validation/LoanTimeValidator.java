package pl.alfacode.dnbloans.rest.dto.validation;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

import pl.alfacode.dnbloans.rest.dto.LoanApplicationRequest;

public class LoanTimeValidator implements ConstraintValidator<TimeRangeConstraint, LoanApplicationRequest> {

    private LocalTime minTime;
    private LocalTime maxTime;

    @Value("${dnbloans.loan.amount.max}")
    private BigDecimal maxAmount;

    @Autowired
    private MessageSource messageSource;

    public LoanTimeValidator(
            @Value("${dnbloans.loan.time.hour.min}") int minHour,
            @Value("${dnbloans.loan.time.hour.max}") int maxHour,
            @Value("${dnbloans.loan.amount.max}") BigDecimal maxAmount,
            MessageSource messageSource) {
        this.minTime = LocalTime.of(minHour, 0);
        this.maxTime = LocalTime.of(maxHour, 0);
        this.maxAmount = maxAmount;
        this.messageSource = messageSource;
    }

    @Override
    public boolean isValid(LoanApplicationRequest loanApplicationRequest, ConstraintValidatorContext context) {
        if (fitTimeRange() && loanApplicationRequest.getAmount().compareTo(maxAmount) == 0) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(
                            messageSource.getMessage("validation.loanapplication.request.time",
                                    new Object[]{minTime, maxTime, maxAmount}, Locale.getDefault()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean fitTimeRange() {
        return LocalTime.now().isAfter(minTime) && LocalTime.now().isBefore(maxTime);
    }
}
