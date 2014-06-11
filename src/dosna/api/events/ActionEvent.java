package dosna.api.events;

import dosna.action.Action;

/**
 * Generalized event that occurs whenever an Actor performs any action in the system
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface ActionEvent
{

    /**
     * Called after an action has been performed by an actor
     *
     * @param action The action that has been performed
     */
    public void onActionPerformed(final Action action);
}
