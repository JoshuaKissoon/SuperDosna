package dosna.api.events;

import dosna.DosnaState;

/**
 * The main event interface for all events
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface Event
{

    /**
     * A method that can run some operations when the event starts
     * - It's not mandatory to execute this method
     *
     * @param state The current state of the system
     */
    public default void onEventStart(final DosnaState state)
    {

    }

    /**
     * A method that can run some operations after the event ends
     * - It's not mandatory to execute this method
     *
     * @param state The current state of the system
     */
    public default void onEventEnd(final DosnaState state)
    {

    }
}
