package dosna.api.events;

import dosna.api.RegistryAppData;

/**
 * Interface for the Event that occurs when a new app is being added to the system
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface AppAdditionEvent extends Event
{

    /**
     * Method called just after this app is added to the system
     *
     * This method only allows an app to store more information about itself in the registry to be used later
     *
     * @param appData The data about this app stored in the registry
     */
    public void afterAppAddition(final RegistryAppData appData);
}
