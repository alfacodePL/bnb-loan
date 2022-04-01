package pl.alfacode.dnbloans.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.alfacode.dnbloans.domain.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
}
