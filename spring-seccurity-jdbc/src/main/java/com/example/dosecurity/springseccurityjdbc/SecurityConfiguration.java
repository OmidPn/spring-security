package com.example.dosecurity.springseccurityjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* using userDetailService

         */

        auth.userDetailsService(this.userDetailsService);

        /*
        using h2 db with script in resource folder.

       auth.jdbcAuthentication()
               .dataSource(this.dataSource);

         */

/* if we do not have schema it uses default schema
  auth.jdbcAuthentication()
               .dataSource(this.dataSource)
               .withDefaultSchema().withUser(
                       User.withUsername("user").password("pass").roles("USER"))
        .withUser( User.withUsername("admin").password("pass").roles("ADMIN"))
               ;
 */
       /*
       //set your configuration on the outh object
        auth.inMemoryAuthentication()
                .withUser("blah").password("blah").roles("USER").and()
                .withUser("foo")
                .password("foo").roles("ADMIN");
        */

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/admin")
                .hasRole("ADMIN")
                .antMatchers("/user")
                .hasAnyRole("USER","ADMIN")
                .antMatchers("/").permitAll()//we permit to have access to root role and static forms.
                .and().formLogin();

    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        //this is not good for prod
        return NoOpPasswordEncoder.getInstance();
    }
}
