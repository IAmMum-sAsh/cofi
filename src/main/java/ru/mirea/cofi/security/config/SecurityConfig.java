package ru.mirea.cofi.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.mirea.cofi.security.jwt.JwtConfigurer;
import ru.mirea.cofi.security.jwt.JwtTokenProvider;

/**
 * The type Security config.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // AUTH ENDPOINTS
    private static final String AUTH_ENDPOINT = "/auth/**";
    private static final String SIGNUP_ENDPOINT = "/signup/**";


    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Instantiates a new Security config.
     *
     * @param jwtTokenProvider the jwt token provider
     */
    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(AUTH_ENDPOINT).permitAll()
                    .antMatchers(SIGNUP_ENDPOINT).permitAll()
                    .antMatchers("/signup/manager").hasRole("ADMIN")
                    .antMatchers("/users/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/home").permitAll()
                    .antMatchers("/menu").permitAll()
                    .antMatchers("/menu/info").permitAll()
                    .antMatchers("/menu/item").hasAnyRole("USER", "MANAGER", "ADMIN")
                    .antMatchers("/menu/add_item").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/menu/delete_item").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/basket/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                    .antMatchers("/get_orders").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/set_order_status").hasAnyRole("MANAGER", "ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
