package com.nix.lemeshuk.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.nix.lemeshuk.controller.AdminController;
import com.nix.lemeshuk.controller.RoleController;
import com.nix.lemeshuk.controller.SignInAndOutController;
import com.nix.lemeshuk.handler.GlobalExceptionMapper;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan("com.nix.lemeshuk")

public class ApacheConfig {

    public final SignInAndOutController signInAndOutController;
    public final AdminController adminController;
    public final RoleController roleController;
    public final GlobalExceptionMapper globalExceptionMapper;
    public final String SERVER_START_ENDPOINT = "/";

    public ApacheConfig(SignInAndOutController signInAndOutController, AdminController adminController, RoleController roleController, GlobalExceptionMapper globalExceptionMapper) {
        this.signInAndOutController = signInAndOutController;
        this.adminController = adminController;
        this.roleController = roleController;
        this.globalExceptionMapper = globalExceptionMapper;
    }

    @Bean
    public Bus cxf() {
        return new SpringBus();
    }

    @Bean
    public JAXRSServerFactoryBean factoryBean(CrossOriginResourceSharingFilter corsFilter,
                                              JacksonJsonProvider jacksonJsonProvider,
                                              Bus bus) {

        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();

        List<Object> serviceBeansList = List.of(signInAndOutController, adminController, roleController);

        List<Object> providerList = List.of(globalExceptionMapper, jacksonJsonProvider, corsFilter);

        factoryBean.setServiceBeans(serviceBeansList);
        factoryBean.setProviders(providerList);
        factoryBean.setBus(bus);
        factoryBean.setAddress(SERVER_START_ENDPOINT);

        return factoryBean;
    }

    @Bean
    public Server server(JAXRSServerFactoryBean factoryBean) {
        return factoryBean.create();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

    @Bean
    public CrossOriginResourceSharingFilter cors() {
        return new CrossOriginResourceSharingFilter();
    }
}
