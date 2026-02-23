package br.com.exam.assembly;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2Servlet() {
        var reg = new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2/*");
        reg.setLoadOnStartup(1);
        return reg;
    }
}
