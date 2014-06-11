package dosna;

/**
 * A class that contains information about the state of the system,
 * This class can also contain system objects to be passed around.
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public class DosnaState
{

    private boolean isLoggedIn;     // Whether the actor is logged in
    private boolean isConnectedToNetwork;   // Whether the system is connected to the DHT network

    
    {
        this.isLoggedIn = false;
        this.isConnectedToNetwork = false;
    }

    public DosnaState()
    {

    }

    /**
     * Set whether the actor is logged in
     *
     * @param isLoggedIn
     */
    public final void setLoggedIn(final boolean isLoggedIn)
    {
        this.isLoggedIn = isLoggedIn;
    }

    /**
     * @return boolean whether the actor is logged in or not
     */
    public final boolean isLoggedIn()
    {
        return this.isLoggedIn;
    }

    /**
     * Use this method to set whether the system is connected to the network or not.
     *
     * @param isConnectedToNetwork
     */
    public final void setConnectionState(final boolean isConnectedToNetwork)
    {
        this.isConnectedToNetwork = isConnectedToNetwork;
    }

    /**
     * @return boolean whether the system is connected to the network
     */
    public final boolean isConnectedToNetwork()
    {
        return this.isConnectedToNetwork;
    }
}
