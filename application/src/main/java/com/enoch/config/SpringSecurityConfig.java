/*
 * package com.enoch.config;
 * 
 * import javax.sql.DataSource;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter;
 * 
 * @Configuration public class SpringSecurityConfig extends
 * WebSecurityConfigurerAdapter {
 * 
 * @Autowired DataSource dataSource;
 * 
 * @Autowired public void configAuthentication(AuthenticationManagerBuilder
 * auth) throws Exception {
 * 
 * auth.jdbcAuthentication().dataSource(dataSource)
 * .usersByUsernameQuery("Select EMAIL,PASSWORD,ISACTIVE from USER where EMAIL=?"
 * ) .authoritiesByUsernameQuery("Select EMAIL,EMAIL from USER where EMAIL=?");
 * }
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception {
 * 
 * http.authorizeRequests().antMatchers("/countries/**").
 * access("hasRole('ROLE_ADMIN')").and()
 * .formLogin().loginPage("/login").failureUrl("/login?error").usernameParameter
 * ("username").passwordParameter("password").and().
 * logout().logoutSuccessUrl("/login?logout").and()
 * .exceptionHandling().accessDeniedPage("/403").and().csrf(); } }
 */