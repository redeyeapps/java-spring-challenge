package co.redeye.spring.challenge.services;

import co.redeye.spring.challenge.db.Item;
import co.redeye.spring.challenge.db.ItemRepository;
import co.redeye.spring.challenge.db.User;
import co.redeye.spring.challenge.exceptions.AuthenticationException;
import co.redeye.spring.challenge.exceptions.IllegalItemException;
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
    public void editItem(String token, long taskId, String text, boolean done) throws UserException {
        User user = authenticatorService.fromToken(token);
        Item item = itemRepository.findOne(taskId);

        if (!user.equals(item.getUser())) {
            throw new IllegalItemException("This item does not belong to you.");
        }

        item.setDone(done);
        item.setDescription(text);
        itemRepository.save(item);
    }
}