package com.challenge.userauthenticate.security;

import com.challenge.userauthenticate.model.LoginAttemptModel;
import com.challenge.userauthenticate.model.UserModel;
import com.challenge.userauthenticate.service.LoginAttemptService;
import com.challenge.userauthenticate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.challenge.userauthenticate.service.UserService.*;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    UserService userService;
    @Autowired
    LoginAttemptService loginAttemptService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        UserModel user = userService.getUserByUsername(request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY));

        // If the user does not exist or is locked, Spring Security's predefined exceptions are used
        if (user != null && user.getStatus().equals(ACTIVE_USER)) {
            // Increase attempt if it have not reached the limit of allowed attempts
            if (user.getFailedAttempts() < MAX_FAILED_ATTEMPTS - 1) {
                userService.increaseFailedAttempts(user.getUserName());
                // Store information about failed login
                loginAttemptService.storeAttempt(new LoginAttemptModel(user.getUserName(), false, user.getFailedAttempts() + 1, new Date(), ACTIVE_USER));
            // Block account by third unsuccessful attempt
            } else {
                userService.updateStatus(user.getUserName(), LOCKED_USER);
                exception = new LockedException("Account is locked since you have exceeded the number of attempts");
                // Store information about failed login
                loginAttemptService.storeAttempt(new LoginAttemptModel(user.getUserName(), false, user.getFailedAttempts() + 1, new Date(), LOCKED_USER));
            }
        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
