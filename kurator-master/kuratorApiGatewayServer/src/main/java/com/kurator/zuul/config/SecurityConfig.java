package com.kurator.zuul.config;

import com.kurator.zuul.filter.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// For implementing security on api endpoints
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/search/**").permitAll().anyRequest().authenticated()
				.and().addFilterAfter(new JwtFilter(), BasicAuthenticationFilter.class).authorizeRequests();

	}

	// For ignoring security of some api endpoints
	@Override
	public void configure(WebSecurity webSecurity) {
		webSecurity.ignoring().antMatchers("/", "/assets/**", "/*.js","**/kurator.mp4", "/favicon.png", "/*.css", "/*.eot", "/*.svg",
				"/*.woff2", "/*.ttf", "/*.woff", "/*.jpg", "/*.html", "/users/auth/**","/search/guest/**","/editprofile/**","/users/status/**","/index/indexingStatus/**");
	}
}