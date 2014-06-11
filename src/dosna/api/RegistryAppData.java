package dosna.api;

import java.util.Map;

/**
 * Data about an app stored in the registry.
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface RegistryAppData
{

    /**
     * Each app is allowed to store data in it's registry app data object, this data is stored in the form of a Map
     *
     * @return The data storage of this app
     */
    public Map getAppDataStorage();
}
