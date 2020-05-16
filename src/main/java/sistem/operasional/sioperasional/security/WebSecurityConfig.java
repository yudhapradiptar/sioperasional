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
                .antMatchers("/delivery-order/").hasAnyAuthority("Operation Manager")
                .antMatchers("/delivery-order/**").hasAnyAuthority("Product Operation Specialist")
                .antMatchers("/jenis-outlet/**").hasAnyAuthority("Operation Manager")
                .antMatchers("/dashboard/**").hasAnyAuthority("Operation Manager")
                .antMatchers("/account/**").hasAnyAuthority("Operation Manager")
                .antMatchers("/delivery-order/**").hasAnyAuthority("Operation Manager")
                .antMatchers("/hardware-fullfillment/item/**").hasAnyAuthority("Operation Manager", "Product Operation Specialist")
                .antMatchers("/hardware-fulfillment/jenis/**").hasAnyAuthority("Operation Manager", "Product Operation Specialist")
//                .antMatchers("/outlet/**").hasAnyAuthority("Operation Manager", "Product Operation Specialist", "Operation Lead","Product Operation Specialist")
                .antMatchers("/jenis-outlet/**").hasAnyAuthority("Operation Manager", "Product Operation Specialist", "Operation Lead","Product Operation Specialist")
                .antMatchers("/training/create/**").hasAnyAuthority("Operation Manager", "Operation Lead")
                .antMatchers("/training/edit/**").hasAnyAuthority("Operation Manager", "Operation Lead")
                .antMatchers("/training/delete/**").hasAnyAuthority("Operation Manager", "Operation Lead")
                .antMatchers("/training/view/**").hasAnyAuthority("Operation Manager", "Operation Lead", "Operation Staff")
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
         auth.inMemoryAuthentication()
                 .passwordEncoder(encoder())
                 .withUser("opslead").password(encoder().encode("opslead123"))
                 .roles("Operation Lead");
         auth.inMemoryAuthentication()
                 .passwordEncoder(encoder())
                 .withUser("opsstaff").password(encoder().encode("opsstaff123"))
                 .roles("Operation Staff");
         auth.inMemoryAuthentication()
                 .passwordEncoder(encoder())
                 .withUser("product").password(encoder().encode("product123"))
                 .roles("Product Operation Specialist");
     }


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}