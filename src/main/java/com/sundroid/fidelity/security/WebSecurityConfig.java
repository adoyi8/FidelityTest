package com.sundroid.fidelity.security;


import com.sundroid.fidelity.user.AppUser;
import com.sundroid.fidelity.user.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()

                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests() // manage access
                .antMatchers(HttpMethod.POST, "/withdrawal").authenticated().antMatchers(HttpMethod.POST, "/deposit").authenticated().antMatchers(HttpMethod.POST, "/transfer").authenticated().antMatchers(HttpMethod.GET, "/account_info").authenticated().antMatchers(HttpMethod.POST, "/disable_user").hasAuthority(AppUserRole.ADMIN.name()).antMatchers(HttpMethod.POST, "/enable_user").hasAuthority(AppUserRole.ADMIN.name()).antMatchers(HttpMethod.POST, "/delete_user").hasAuthority(AppUserRole.ADMIN.name()).antMatchers(HttpMethod.POST, "/make_admin").hasAuthority(AppUserRole.ADMIN.name()).antMatchers(HttpMethod.POST, "/remove_admin").hasAuthority(AppUserRole.ADMIN.name()).antMatchers(HttpMethod.POST, "/create_account").permitAll()
                // other matchers
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}
