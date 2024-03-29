package sistem.operasional.sioperasional.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//     @Override
//     public void configure(WebSecurity web) throws Exception {
//             super.configure(web);
//             web.ignoring().antMatchers("/api/**");
//     }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/customer-feedback/**").permitAll()
                .antMatchers("/delivery-order/set-tanggal-subscribe/**").hasAnyAuthority("Operation Manager")
                .antMatchers("/delivery-order/daftar-delivery-order-set-tanggal").hasAnyAuthority("Operation Manager")
                .antMatchers("/delivery-order/add/**").hasAnyAuthority("Product Operation Specialist", "Operation Manager")
                .antMatchers("/delivery-order/addwithtxt/**").hasAnyAuthority("Product Operation Specialist", "Operation Manager")
                .antMatchers("/delivery-order/detail/**").hasAnyAuthority("Product Operation Specialist", "Operation Manager")
                .antMatchers("/delivery-order/update/**").hasAnyAuthority("Product Operation Specialist", "Operation Manager")
                .antMatchers("/purchase-order/**").hasAnyAuthority("Product Operation Specialist", "Operation Manager")
                .antMatchers("/hardware-fulfillment/**").hasAnyAuthority("Operation Manager", "Product Operation Specialist")
                .antMatchers("/dashboard/**").hasAnyAuthority("Operation Manager", "Operation Lead")
                .antMatchers("/training/create/**").hasAnyAuthority("Operation Manager", "Operation Lead")
                .antMatchers("/training/edit/**").hasAnyAuthority("Operation Manager", "Operation Lead")
                .antMatchers("/training/delete/**").hasAnyAuthority("Operation Manager", "Operation Lead")
                .antMatchers("/training/view/**").hasAnyAuthority("Operation Manager", "Operation Lead", "Operation Staff")
                .antMatchers("/training/schedule/**").hasAnyAuthority("Operation Manager", "Operation Lead", "Operation Staff")
                .antMatchers("/jenis-outlet/**").hasAnyAuthority("Operation Manager", "Product Operation Specialist", "Operation Lead")
                .antMatchers("/vendor/").hasAnyAuthority("Operation Manager", "Product Operation Specialist")
                .antMatchers("/vendor/add/**").hasAnyAuthority("Operation Manager", "Product Operation Specialist")
                .antMatchers("/vendor/delete/**").hasAnyAuthority("Operation Manager", "Product Operation Specialist")
                .antMatchers("/account/**").hasAnyAuthority("Operation Manager", "ROLE_Operation Manager")
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

     @Autowired
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
         auth.inMemoryAuthentication()
                 .passwordEncoder(encoder())
                 .withUser("manager").password(encoder().encode("manager123"))
                 .roles("Operation Manager");
     }


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}