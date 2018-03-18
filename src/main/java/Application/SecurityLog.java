package Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/**
 * Created by Ionel Carmen on 5/2/2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityLog extends WebSecurityConfigurerAdapter
{

    @Autowired
    private UserService userData;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().antMatchers("/", "/resources/**","/templates/**", "/static/**",
                "/css/**", "/js/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/adminMeniu", "/create","/delete","update", "view").hasRole("ADMIN")
                .antMatchers("/doctorMeniu", "/addComments","/viewComments").hasRole("DOCTOR")
                .antMatchers("/secretaryMeniu","/addConsultation", "/addPatient", "/deleteConsultation",
                        "/updateConsultation", "/updatePatient", "/viewConsultation", "/viewPatient", "/patientArrived").hasRole("SECRETARY")
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().defaultSuccessUrl("/pass")
                .loginPage("/login").usernameParameter("user").passwordParameter("pass").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll()
                .and().exceptionHandling().accessDeniedPage("/denied");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception
    {
        auth.userDetailsService(this.userData);
    }

}