package dosna.api.events;

/**
 * A class that gives unique identifiers and other data for all events within the system.
 * - Mainly to be used by apps when specifying which events they handle
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public class Events
{

    public final static String APP_ADDITION_EVENT = "APP_ADDITION_EVENT";
    public final static String APP_DELETION_EVENT = "APP_DELETION_EVENT";
    public final static String CONTENT_INSERT_EVENT = "CONTENT_INSERT_EVENT";
    public final static String CONTENT_UPDATE_EVENT = "CONTENT_UPDATE_EVENT";
    public final static String CONTENT_DELETE_EVENT = "CONTENT_DELETE_EVENT";
    public final static String LOAD_ACTIVITY_STREAM_EVENT = "LOAD_ACTIVITY_STREAM_EVENT";
    public final static String STARTUP_EVENT = "STARTUP_EVENT";
    public final static String SHUTDOWN_EVENT = "SHUTDOWN_EVENT";
}
