package co.redeye.spring.challenge.controllers;

import co.redeye.spring.challenge.db.User;
import co.redeye.spring.challenge.exceptions.AuthenticationException;
import co.redeye.spring.challenge.exceptions.UserException;
import co.redeye.spring.challenge.services.AuthenticatorService;
import co.redeye.spring.challenge.services.TodoListService;
import co.redeye.spring.challenge.views.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling all access of the user's to do list.
 */
@Controller
@RequestMapping("/todo")
public class TodoListController {
    @Autowired
    private TodoListService todoListService;

    /**
     * Adds a new item to the authenticated user's to do list.
     *
     * @param newItem The item being added.
     * @param authToken The user's authentication token
     * @throws AuthenticationException If the authentication token is missing or invalid.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    void newTask(@RequestBody TodoItem newItem, @RequestHeader("Authorization") String authToken) throws UserException {
        newItem.validate();
        todoListService.addItem(authToken, newItem.getText(), newItem.isDone());
    }

    /**
     * Retrieves the user's entire to do list
     *
     * @param authToken The user's authentication token
     * @throws AuthenticationException If the authentication token is missing or invalid.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    List<TodoItem> newTask(@RequestHeader("Authorization") String authToken) throws UserException {
        return todoListService.getItems(authToken);
    }
}
