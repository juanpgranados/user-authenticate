package com.challenge.userauthenticate.security;

import com.challenge.userauthenticate.model.LoginAttemptModel;
import com.challenge.userauthenticate.model.UserModel;
import com.challenge.userauthenticate.service.LoginAttemptService;
import com.challenge.userauthenticate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.challenge.userauthenticate.service.UserService.ACTIVE_USER;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserService userService;
    @Autowired
    LoginAttemptService loginAttemptService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // Store information about login successful
        UserModel user = userService.getUserByUsername(request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY));
        loginAttemptService.storeAttempt(new LoginAttemptModel(user.getUserName(), true, user.getFailedAttempts(), new Date(), ACTIVE_USER));

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
