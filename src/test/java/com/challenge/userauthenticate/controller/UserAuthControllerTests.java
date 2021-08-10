package com.challenge.userauthenticate.controller;

import com.challenge.userauthenticate.security.CustomAuthenticationFailureHandler;
import com.challenge.userauthenticate.service.UserService;
import com.challenge.userauthenticate.security.CustomAuthenticationSuccessHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AuthenticationController.class)
class UserAuthControllerTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserService userService;
	@MockBean
	CustomAuthenticationFailureHandler authFailureHandler;
	@MockBean
	CustomAuthenticationSuccessHandler authSuccessHandler;

	@MockBean
	private AuthenticationManager authenticationManager;

	@Test
	@WithMockUser(roles = {"USER"})
	void testAuthRequestSuccessful() throws Exception{
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/main").accept(MediaType.ALL))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(roles = {"USER123"})
	void testAuthRequestNotSuccessful() throws Exception{
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/main").accept(MediaType.ALL))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
