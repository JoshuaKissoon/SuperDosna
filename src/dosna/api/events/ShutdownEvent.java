package dosna.api.events;

import dosna.DosnaState;

/**
 * Interface for the Shutdown Event
 * - The Shutdown Event occurs when the system shuts down and after the actor has been logged out
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface ShutdownEvent extends Event
{

    /**
     * Any app that handles this event must provide an onShutdown method to do whatever it is when the system shuts down
     *
     * @param state The current state of the system
     */
    public void onShutdown(final DosnaState state);
}
