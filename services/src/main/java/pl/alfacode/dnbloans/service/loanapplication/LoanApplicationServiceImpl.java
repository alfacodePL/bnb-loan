package pl.alfacode.dnbloans.service.loanapplication;

import java.util.Optional;

import pl.alfacode.dnbloans.domain.LoanApplication;
import pl.alfacode.dnbloans.repository.LoanApplicationRepository;
import pl.alfacode.dnbloans.service.ResourceNotFoundException;

public class LoanApplicationServiceImpl implements LoanApplicationService{
    private final LoanApplicationRepository loanApplications;

    public LoanApplicationServiceImpl(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplications = loanApplicationRepository;
    }

    @Override
    public LoanApplication getById(Long id) {
        Optional<LoanApplication> loanApplication = loanApplications.findById(id);
        if (loanApplication.isPresent()) {
            return loanApplication.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public Long save(LoanApplication loanApplication) {
        return loanApplications.save(loanApplication).getId();
    }
}
