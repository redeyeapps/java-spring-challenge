package co.redeye.spring.challenge.services;

import co.redeye.spring.challenge.db.Item;
import co.redeye.spring.challenge.db.ItemRepository;
import co.redeye.spring.challenge.db.User;
import co.redeye.spring.challenge.exceptions.AuthenticationException;
import co.redeye.spring.challenge.exceptions.IllegalItemException;
import co.redeye.spring.challenge.exceptions.InvalidItemException;
import co.redeye.spring.challenge.exceptions.UserException;
import co.redeye.spring.challenge.views.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * All functionality related to the manipulation of to do lists.
 */
@Service
public class TodoListService {
    @Autowired
    private AuthenticatorService authenticatorService;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Gets all the items a user has in their to do list, complete and incomplete.
     *
     * @param token The user's authentication token
     * @return A list of this user's to do tasks.
     * @throws AuthenticationException
     */
    @Transactional(readOnly = true)
    public List<TodoItem> getItems(String token) throws AuthenticationException {
        User user = authenticatorService.fromToken(token);

        return user.getItems().stream()
                .map(TodoItem::new)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new list item for the current user
     *
     * @param token    The user's authentication token
     * @param desc     The new task's description
     * @param complete Whether the user has completed this task.
     * @
     */
    @Transactional
    public void addItem(String token, String desc, boolean complete) throws AuthenticationException {
        User user = authenticatorService.fromToken(token);

        Item newItem = new Item();
        newItem.setUser(user);
        newItem.setDescription(desc);
        newItem.setDone(complete);

        itemRepository.save(newItem);
    }

    /**
     * Edits an existing item on a user's to do list.
     *
     * @param token  The user's authentication token.
     * @param taskId The id of the task.
     * @param text   The new text for the list item.
     * @param done   The new status for the item.
     * @throws AuthenticationException If the user's token is invalid.
     * @throws IllegalItemException    If the specified task does not belong to the user.
     */
    @Transactional
    public void editItem(String token, long taskId, String text, boolean done) throws UserException {
        User user = authenticatorService.fromToken(token);
        Item item = itemRepository.findOne(taskId);

        if (item == null) {
            throw new InvalidItemException("The specified to do list item does not exist.");
        }

        if (!user.equals(item.getUser())) {
            throw new IllegalItemException("This item does not belong to you.");
        }

        item.setDone(done);
        item.setDescription(text);
        itemRepository.save(item);
    }

    /**
     * Deletes a specified item from the current user's to do list.
     *
     * @param token  The user's authentication token.
     * @param taskId The id of the task to be removed.
     * @throws UserException If there is an issue with authentication or the item.
     */
    @Transactional
    public void deleteItem(String token, long taskId) throws UserException {
        User user = authenticatorService.fromToken(token);
        Item item = itemRepository.findOne(taskId);

        if (item == null) {
            throw new InvalidItemException("The specified to do list item does not exist.");
        }

        if (!user.equals(item.getUser())) {
            throw new IllegalItemException("This item does not belong to you.");
        }

        itemRepository.delete(item);
    }

    /**
     * Gets all of the user's incomplete to do list items.
     *
     * @param token The user's authentication token.
     * @return The user's incomplete items.
     * @throws AuthenticationException If the user's token is invalid.
     */
    public List<TodoItem> getIncompleteItems(String token) throws AuthenticationException {
        return getItemsWithDoneStatus(token, false);
    }

    /**
     * Gets all of the user's complete to do list items.
     *
     * @param token The user's authentication token.
     * @return The user's complete items.
     * @throws AuthenticationException If the user's token is invalid.
     */
    public List<TodoItem> getCompleteItems(String token) throws AuthenticationException {
        return getItemsWithDoneStatus(token, true);
    }

    /**
     * Method for handling requests for a user's complete/incomplete items.
     *
     * @param token      The user's authentication token.
     * @param doneStatus The desired status of the TodoItems to return.
     * @return The user's items with the desired status.
     * @throws AuthenticationException If the user's token is invalid.
     */
    @Transactional
    private List<TodoItem> getItemsWithDoneStatus(String token, boolean doneStatus) throws AuthenticationException {
        User user = authenticatorService.fromToken(token);

        return itemRepository.findByUserAndDone(user, doneStatus).stream()
                .filter(item -> item.isDone() == doneStatus)
                .map(TodoItem::new)
                .collect(Collectors.toList());
    }

    /**
     * Deletes all of the user's to do list items.
     *
     * @param token The user's authentication token.
     * @throws AuthenticationException If the user's token is invalid.
     */
    @Transactional
    public void deleteAllItems(String token) throws AuthenticationException {
        User user = authenticatorService.fromToken(token);
        itemRepository.delete(user.getItems());
    }
}
