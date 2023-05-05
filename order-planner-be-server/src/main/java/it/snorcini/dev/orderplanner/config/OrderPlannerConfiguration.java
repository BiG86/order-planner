package it.snorcini.dev.orderplanner.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;


/**
 * Main spring configuration class.
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"it.snorcini.dev.orderplanner.repository",
        "it.snorcini.dev.orderplanner.entity"})
@EntityScan(basePackages = "it.snorcini.dev.orderplanner.*")
public class OrderPlannerConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper()));
    }

    /**
     * Customization for mapping of date/timestamp
     */
    private static ObjectMapper objectMapper() {
        return new ObjectMapper()
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .registerModule(new JavaTimeModule());
    }
}
