package co.redeye.spring.challenge.controllers;

import co.redeye.spring.challenge.Utils;
import co.redeye.spring.challenge.services.AuthenticationException;
import co.redeye.spring.challenge.services.AuthenticatorService;
import co.redeye.spring.challenge.views.LoginRequest;
import co.redeye.spring.challenge.views.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    @Autowired
    AuthenticatorService authenticator;

    @RequestMapping(value = "/account/register", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse register(@RequestBody LoginRequest registerRequest) throws AuthenticationException {
        validate(registerRequest);

        String token = authenticator.register(registerRequest.getUsername(), registerRequest.getPassword());
        return new LoginResponse(token);
    }

    @RequestMapping(value = "/account/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        validate(loginRequest);

        String token = authenticator.login(loginRequest.getUsername(), loginRequest.getPassword());
        return new LoginResponse(token);
    }

    private void validate(LoginRequest registerRequest) throws AuthenticationException {
        if (!Utils.stringPresent(registerRequest.getUsername())) {
            throw new AuthenticationException("Invalid username.");
        }
        if (!Utils.stringPresent(registerRequest.getPassword())) {
            throw new AuthenticationException("Invalid password.");
        }
    }
}
