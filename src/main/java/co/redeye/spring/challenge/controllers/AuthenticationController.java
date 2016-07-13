package co.redeye.spring.challenge.controllers;

import co.redeye.spring.challenge.Utils;
import co.redeye.spring.challenge.services.AuthenticationException;
import co.redeye.spring.challenge.services.AuthenticatorService;
import co.redeye.spring.challenge.views.LoginRequest;
import co.redeye.spring.challenge.views.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    @Autowired
    AuthenticatorService authenticator;

    @RequestMapping(value = "/account/register", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse home(@RequestBody LoginRequest registerRequest) throws AuthenticationException {
        validate(registerRequest);

        String token = authenticator.register(registerRequest.getUsername(), registerRequest.getPassword());
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return response;
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
