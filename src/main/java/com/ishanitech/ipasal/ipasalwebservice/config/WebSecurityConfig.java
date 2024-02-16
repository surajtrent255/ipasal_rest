package com.ishanitech.ipasal.ipasalwebservice.config;

import com.ishanitech.ipasal.ipasalwebservice.security.CustomAccessDeniedHandler;
import com.ishanitech.ipasal.ipasalwebservice.security.CustomAuthenticationEntryPoint;
import com.ishanitech.ipasal.ipasalwebservice.security.CustomUserAuthenticationProvider;
import com.ishanitech.ipasal.ipasalwebservice.security.JWTAuthenticationFilter;
import com.ishanitech.ipasal.ipasalwebservice.security.JWTAuthorizationFilter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserAuthenticationProvider customUserAuthenticationProvider;
    //private final CustomCustomerAuthenticationProvider customCustomerAuthenticationProvider;
    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;
    
    public WebSecurityConfig(CustomUserAuthenticationProvider customUserAuthenticationProvider){
        this.customUserAuthenticationProvider = customUserAuthenticationProvider;
        //this.customCustomerAuthenticationProvider = customCustomerAuthenticationPrlearovider;
    }
 


	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customUserAuthenticationProvider);
        //auth.authenticationProvider(customCustomerAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
		http.cors().and().csrf().disable()
		.httpBasic().disable()
		.exceptionHandling()
		.accessDeniedHandler(customAccessDeniedHandler)
		.authenticationEntryPoint(customAuthenticationEntryPoint)
		.and()
		.authorizeRequests()
                .antMatchers("/Pictures/**").permitAll()
                .antMatchers("/api/v1/payment/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/users/register").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/users/checkUserName").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/users/checkDuplicateEmail").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/activateUser").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/products").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/products/search/all***").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/products/{productId}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/category/parent/{parentId}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/category/featured").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/category/offered").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/products/featured").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/category").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/products/category/{categoryId}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/products/parentCategory/{categoryId}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/order/confirm").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/slider").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/hot-deals").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/offer-product").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/order/most-bought-product").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/category/image/{categoryId}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/category/{categoryId}/product/rate***").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/about***").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/about").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/about/upload/{aboutId}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/shipping-rate/{location}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/shipping-rate").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/reset-user***").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/pw-reset-token-verify***").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/get-user***").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/update-password/{userId}***").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/category/parent/all").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/review/reviewProduct/{productId}***").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/payment-methods/active").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/toolbarMessage").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/social").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/social/active").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/products/allSales").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/products/sales").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/promotionalSales/{promotionTag}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/promotionalSales").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/promotionalSales/tag/{promotionTag}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/promotionalSales/prListing/{promotionalSalesId}").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
