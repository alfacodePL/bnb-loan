package pl.alfacode.dnbloans.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@DnbLoansBootApplication
public class DnbLoansApplication {
    @Bean
    public String applicationVersion() {
        return "Local-Version";
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplicationBuilder(DnbLoansApplication.class)
                .build();
        app.run(args);
    }
}
