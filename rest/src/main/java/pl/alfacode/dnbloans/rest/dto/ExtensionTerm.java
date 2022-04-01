package pl.alfacode.dnbloans.rest.dto;

import java.time.Period;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum ExtensionTerm {
    ONE_WEEK(Period.ofDays(7)),
    TWO_WEEKS(Period.ofDays(14)),
    MONTH(Period.ofMonths(1)),
    TWO_MONTHS(Period.ofMonths(2)),
    QUARTER(Period.ofMonths(3)),
    HALF_YEAR(Period.ofMonths(6)),
    YEAR(Period.ofYears(1));

    @JsonIgnore
    private Period period;

    ExtensionTerm(Period period) {
        this.period = period;
    }

    public Period getPeriod() {
        return period;
    }
}
