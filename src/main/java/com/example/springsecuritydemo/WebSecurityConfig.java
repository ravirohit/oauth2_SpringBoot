package com.example.springsecuritydemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
//@EnableWebSecurity
@Order(4)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http .authorizeRequests() .antMatchers("/","/home","/loginui").permitAll()
		 * .anyRequest().authenticated() .and() .formLogin() .loginPage("/logintest")
		 * .permitAll() .and() .logout() .logoutUrl("/loginui") .permitAll();
		 */
		
		  http .authorizeRequests() .antMatchers("/oauth/authorize**", "/error**")
		  .permitAll() .anyRequest().authenticated() .and() .formLogin().permitAll()
		  .and() .logout().permitAll();
		 
		/*
		 * http .antMatcher("/**") .authorizeRequests()
		 * .antMatchers("/oauth/authorize**", "/login**", "/error**") .permitAll()
		 * .and() .authorizeRequests() .anyRequest().authenticated() .and()
		 * .formLogin().permitAll();
		 */
	}

	/*
	 * @Bean
	 * 
	 * @Override public UserDetailsService userDetailsService() { UserDetails user =
	 * User.withDefaultPasswordEncoder() .username("user") .password("password")
	 * .roles("USER") .build();
	 * 
	 * return new InMemoryUserDetailsManager(user); }
	 */
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("humptydumpty").password(passwordEncoder().encode("123456")).roles("USER");
    }
      
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ 
        return new BCryptPasswordEncoder(); 
    }
}