package dosna;

import dosna.dhtAbstraction.DataManager;
import dosna.osn.actor.Actor;

/**
 * Class that contain all the DOSNA Objects that allow functionality throughout the system
 * - This class will also provide singleton methods to access common objects
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public class DosnaObjects
{

    private final DataManager dataManager;
    private final Actor actor;

    /**
     * Create a new instance of the class
     *
     * @param dataManager The DataManager used throughout the system
     * @param actor       The logged in actor
     */
    public DosnaObjects(final DataManager dataManager, final Actor actor)
    {
        this.dataManager = dataManager;
        this.actor = actor;
    }

    public DataManager getDataManager()
    {
        return this.dataManager;
    }

    public Actor getActor()
    {
        return this.actor;
    }
}
