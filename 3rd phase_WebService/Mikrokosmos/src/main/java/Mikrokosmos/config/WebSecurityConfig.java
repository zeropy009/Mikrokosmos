package Mikrokosmos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Yukinon
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    CustomAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/author/**","/category/**","/book/**").hasAnyAuthority("Depart-001","Depart-003","Depart-004")
                .antMatchers("/supplier/**","/receipt/**").hasAnyAuthority("Depart-001","Depart-004")
                .antMatchers("/mikrokosmos/staff/**","/mikrokosmos/depart/**").hasAnyAuthority("Depart-002","Depart-004")
                .antMatchers("/mikrokosmos/customer/**","/mikrokosmos/invoice/**","/mikrokosmos/level/**","/mikrokosmos/method/**").hasAnyAuthority("Depart-003","Depart-004")
                .antMatchers("/log/**").hasAnyAuthority("Depart-004")
                .antMatchers("/js/**", "/css/**", "/Decoration/**", "/img/**", "/fonts/**", "/vendor/**","/mik/image/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/mikrokosmos")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
//                .permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(authProvider());
//         auth.inMemoryAuthentication()
//            .withUser("user").password("password")
//            .roles("ADMIN");
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
