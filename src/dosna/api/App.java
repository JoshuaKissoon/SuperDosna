package dosna.api;

/**
 * Specification of an app. A class for this interface must be provided by all apps
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface App
{

    /**
     * @return A unique identifier for this app
     */
    public String getIdentifier();

    /**
     * @return The name of this app to be displayed to the end user
     */
    public String getName();

    /**
     * @return A short description of the app to be shown to the end user
     */
    public String getDescription();

    /**
     * @return A list of other apps that this app depends on
     */
    public default String[] getDependenciesList()
    {
        return new String[0];
    }

    /**
     * @return A list of events this app handles
     */
    public default String[] getEventsList()
    {
        return new String[0];
    }

    /**
     * @return A list of content types this app creates
     */
    public default String[] getContentTypesList()
    {
        return new String[0];
    }

    /**
     * @return A list of menus this app provides
     */
    public default String[] getMenusList()
    {
        return new String[0];
    }

    /**
     * @return A list of blocks provided by this app
     */
    public default String[] getBlocksList()
    {
        return new String[0];
    }

}
