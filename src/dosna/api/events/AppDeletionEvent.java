package dosna.api.events;

import dosna.api.RegistryAppData;

/**
 * Interface for the Event that occurs when an app is being deleted from the system
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface AppDeletionEvent extends Event
{

    /**
     * Method called just before this app is deleted from the system
     *
     * This method only allows an app to modify or delete information about itself in the registry
     *
     * @param appData The data about this app stored in the registry
     */
    public void beforeAppAddition(final RegistryAppData appData);
}
