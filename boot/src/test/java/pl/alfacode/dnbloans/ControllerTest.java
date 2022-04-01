package pl.alfacode.dnbloans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.alfacode.dnbloans.boot.DnbLoansApplication;
import pl.alfacode.dnbloans.domain.LoanApplication;
import pl.alfacode.dnbloans.repository.LoanApplicationRepository;
import pl.alfacode.dnbloans.rest.dto.ExtensionTerm;
import pl.alfacode.dnbloans.rest.dto.LoanApplicationRequest;
import pl.alfacode.dnbloans.rest.dto.LoanExtendRequest;

@SpringBootTest(
        classes = DnbLoansApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = {"classpath:dnbloans.properties",
                "classpath:messages.properties",
                "classpath:validation.properties"})
public class ControllerTest {

    public static final String API_URL = "/api/loanapplication";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private LoanApplicationRepository loanApplications;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenValidInput_thenCreateLoanApplication() throws Exception {
        BigDecimal principal = new BigDecimal("10000.00");
        int termInDays = 80;
        LoanApplicationRequest request = new LoanApplicationRequest(principal, termInDays);

        mvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        List<LoanApplication> found = loanApplications.findAll();

        assertThat(found).extracting(LoanApplication::getDebt).containsOnly(new BigDecimal("11000.00"));
        assertThat(found).extracting(LoanApplication::getDueDate).containsOnly(LocalDate.now().plus(Period.ofDays(termInDays)));

    }

    @Test
    public void givenLoanApplication_whenGetLoanApplication_thenStatus200()
            throws Exception {

        BigDecimal principal = new BigDecimal("10000.00");
        int interestRate = 20;
        int termInDays = 80;
        Long id =
                loanApplications.save(LoanApplication.builder().withPrincipal(principal, interestRate).withTerm(termInDays).build()).getId();

        mvc.perform(get(API_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.amount", is(12000.00)))
                .andExpect(jsonPath("$.term", is(LocalDate.now().plus(Period.ofDays(termInDays)).toString())));
    }

    @Test
    public void givenLoanApplication_whenExtendLoan_thenDueDateIsUpdated() throws Exception {
        BigDecimal principal = new BigDecimal("10000.00");
        int interestRate = 20;
        int termInDays = 80;
        Long id =
                loanApplications.save(LoanApplication.builder().withPrincipal(principal, interestRate).withTerm(termInDays).build()).getId();


        LoanExtendRequest request = new LoanExtendRequest(ExtensionTerm.TWO_MONTHS);

        mvc.perform(put(API_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk());

        List<LoanApplication> found = loanApplications.findAll();

        assertThat(found).extracting(LoanApplication::getDueDate).containsOnly(LocalDate.now().plus(Period.ofMonths(2).plus(Period.ofDays(termInDays))));
    }

    @BeforeEach
    public void cleanLoans() {
        loanApplications.deleteAll();
    }
}
