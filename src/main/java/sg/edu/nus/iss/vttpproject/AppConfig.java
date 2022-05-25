package sg.edu.nus.iss.vttpproject;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sg.edu.nus.iss.vttpproject.filter.AuthFilter;

@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<AuthFilter> registerFilter() {
        FilterRegistrationBean<AuthFilter> regBean = new FilterRegistrationBean<>();
        regBean.setFilter(new AuthFilter());
        regBean.addUrlPatterns("/user/*");
        return regBean;
    }
}
