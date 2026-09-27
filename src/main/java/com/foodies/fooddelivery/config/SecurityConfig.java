package com.foodies.fooddelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // =========================
                // PUBLIC FRONTEND PAGES
                // =========================
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/login.html",
                    "/signup.html",

                    "/customer-dashboard.html",
                    "/orders.html",
                    "/track-order.html",
                    "/profile.html",

                    "/admin-dashboard.html",
                    "/admin-orders.html",
                    "/admin-restaurants.html",
                    "/admin-menu.html",
                    "/admin-users.html",
                    "/admin-delivery.html",
                    "/admin-coupons.html",

                    "/delivery-dashboard.html",

                    "/restaurants.html",
                    "/menu.html",
                    "/cart.html",
                    "/payment.html",
                    "/coupons.html",

                    "/css/**",
                    "/js/**",
                    "/error",

                    "/api/auth/**"
                )
                .permitAll()


                // =========================
                // USER APIs
                // =========================

                // Used by customer frontend
                // to find current user
                .requestMatchers("/api/users/by-email")
                .permitAll()

                // Logged-in user can update own profile
                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/users/{id}"
                )
                .authenticated()

                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                // Other user-management APIs = ADMIN only
                .requestMatchers("/api/users/**")
                .hasRole("ADMIN")


                // =========================
                // RESTAURANT APIs
                // =========================

                // Restaurant list is public
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/restaurants"
                )
                .permitAll()

                // Single restaurant details are public
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/restaurants/{id}"
                )
                .permitAll()

                // Restaurant create/update/delete = ADMIN
                .requestMatchers("/api/restaurants/**")
                .hasRole("ADMIN")


                // =========================
                // MENU APIs
                // =========================

                // Customers can view restaurant menu
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/menu-items/restaurant/**"
                )
                .permitAll()

                // Menu management = ADMIN
                .requestMatchers("/api/menu-items")
                .hasRole("ADMIN")

                .requestMatchers("/api/menu-items/**")
                .hasRole("ADMIN")


                // =========================
                // COUPON APIs
                // =========================

                .requestMatchers("/api/coupons")
                .authenticated()

                .requestMatchers("/api/coupons/apply")
                .authenticated()

                .requestMatchers("/api/coupons/**")
                .authenticated()


                // =========================
                // REVIEW APIs
                // =========================

                // Anyone can view restaurant reviews
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/reviews/restaurant/*"
                )
                .permitAll()

                // Anyone can view restaurant average rating
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/reviews/restaurant/*/average"
                )
                .permitAll()

                // Creating/checking reviews requires login
                .requestMatchers("/api/reviews/**")
                .authenticated()


                // =========================
                // DELIVERY PARTNER APIs
                // =========================

                // Customer can see delivery partner location
                .requestMatchers(
                    "/api/delivery-partners/*/location"
                )
                .permitAll()

                // Delivery partner management
                .requestMatchers("/api/delivery-partners/**")
                .hasAnyRole(
                    "ADMIN",
                    "DELIVERY_PARTNER"
                )


                // =========================
                // ORDER APIs
                // =========================

                // Order status can be updated by
                // Admin or Delivery Partner
                .requestMatchers(
                    "/api/orders/*/status"
                )
                .hasAnyRole(
                    "ADMIN",
                    "DELIVERY_PARTNER"
                )


                // =========================
                // EVERYTHING ELSE
                // =========================

                .anyRequest()
                .authenticated()
            )

            // JWT Authentication Filter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }


    // =========================
    // PASSWORD ENCODER
    // =========================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}