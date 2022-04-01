package pl.alfacode.dnbloans.service.loanapplication;

import pl.alfacode.dnbloans.domain.LoanApplication;

public interface LoanApplicationService {

    LoanApplication getById(Long id);

    Long save(LoanApplication loanApplication);

}
