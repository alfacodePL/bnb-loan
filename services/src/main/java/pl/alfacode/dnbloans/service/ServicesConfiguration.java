package pl.alfacode.dnbloans.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.alfacode.dnbloans.repository.LoanApplicationRepository;
import pl.alfacode.dnbloans.service.loanapplication.LoanApplicationService;
import pl.alfacode.dnbloans.service.loanapplication.LoanApplicationServiceImpl;

@Configuration
public class ServicesConfiguration {

    @Bean
    public LoanApplicationService loanApplicationService(LoanApplicationRepository loanApplicationRepository) {
        return new LoanApplicationServiceImpl(loanApplicationRepository);
    }
}
