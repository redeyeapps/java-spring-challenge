package co.redeye.spring.challenge.controllers;

import co.redeye.spring.challenge.Utils;
import co.redeye.spring.challenge.exceptions.AuthenticationException;
import co.redeye.spring.challenge.services.AuthenticatorService;
import co.redeye.spring.challenge.views.LoginRequest;
import co.redeye.spring.challenge.views.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AuthenticationController {
    @Autowired
    AuthenticatorService authenticator;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse register(@RequestBody LoginRequest registerRequest) throws AuthenticationException {
        registerRequest.validate();

        String token = authenticator.register(registerRequest.getUsername(), registerRequest.getPassword());
        return new LoginResponse(token);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        loginRequest.validate();

        String token = authenticator.login(loginRequest.getUsername(), loginRequest.getPassword());
        return new LoginResponse(token);
    }
}
