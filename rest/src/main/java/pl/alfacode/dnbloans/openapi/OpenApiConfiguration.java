package pl.alfacode.dnbloans.openapi;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI(MessageSource messageSource, String applicationVersion) {
        return new OpenAPI().info(
                new Info().title(messageSource.getMessage("openapi.ui.title", null, LocaleContextHolder.getLocale()))
                .version(applicationVersion));
    }
}
