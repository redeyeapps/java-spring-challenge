package co.redeye.spring.challenge.controllers;

import co.redeye.spring.challenge.exceptions.AuthenticationException;
import co.redeye.spring.challenge.exceptions.UserException;
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
     * Retrieves the user's entire to do list
     *
     * @param authToken The user's authentication token
     * @throws AuthenticationException If the authentication token is missing or invalid.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<TodoItem> getTasks(@RequestHeader("Authorization") String authToken) throws UserException {
        return todoListService.getItems(authToken);
    }

    /**
     * Adds a new item to the authenticated user's to do list.
     *
     * @param newItem   The item being added.
     * @param authToken The user's authentication token
     * @throws AuthenticationException If the authentication token is missing or invalid.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void newTask(@RequestBody TodoItem newItem, @RequestHeader("Authorization") String authToken) throws UserException {
        newItem.validate();
        todoListService.addItem(authToken, newItem.getText(), newItem.isDone());
    }

    /**
     * Gets the user's incomplete items.
     *
     * @param authToken The user's authentication token.
     * @return All of the user's items which are not done.
     * @throws AuthenticationException If the user's token is invalid.
     */
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    @ResponseBody
    public List<TodoItem> getActiveItems(@RequestHeader("Authorization") String authToken) throws AuthenticationException {
        return todoListService.getIncompleteItems(authToken);
    }

    /**
     * Gets the user's complete items.
     *
     * @param authToken The user's authentication token.
     * @return All of the user's items which are done.
     * @throws AuthenticationException If the user's token is invalid.
     */
    @RequestMapping(value = "/inactive", method = RequestMethod.GET)
    @ResponseBody
    public List<TodoItem> getInactiveItems(@RequestHeader("Authorization") String authToken) throws AuthenticationException {
        return todoListService.getCompleteItems(authToken);
    }

    /**
     * Modifies an existing task.
     *
     * @param item      The new values for the item.
     * @param authToken The user's authentication token.
     * @param taskId    The id of the task being modified.
     * @throws UserException If there is any problem with the request.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void editTask(@RequestBody TodoItem item, @RequestHeader("Authorization") String authToken, @PathVariable("id") long taskId) throws UserException {
        item.validate();
        todoListService.editItem(authToken, taskId, item.getText(), item.isDone());
    }

    /**
     * Completely removes an item from the user's to do list.
     *
     * @param authToken The user's authentication token.
     * @param taskId    The id of the task to be removed
     * @throws UserException If there is an authentication issue or the task does not belong to the user.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@RequestHeader("Authorization") String authToken, @PathVariable("id") long taskId) throws UserException {
        todoListService.deleteItem(authToken, taskId);
    }

    /**
     * Completely removes all items user's to do list.
     *
     * @param authToken The user's authentication token.
     * @throws AuthenticationException If there is an authentication issue.
     */
    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllTasks(@RequestHeader("Authorization") String authToken) throws AuthenticationException {
        todoListService.deleteAllItems(authToken);
    }
}
