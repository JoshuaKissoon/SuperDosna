package dosna.api.events;

import dosna.DosnaState;

/**
 * Interface for the Startup Event
 * - Startup Event occurs when the system starts up before the user logs in
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface StartupEvent extends Event
{

    /**
     * Any app that handles this event must provide an onStartup method to do whatever it is when the system starts
     *
     * @param state The current state of the system
     */
    public void onStartup(final DosnaState state);
}
