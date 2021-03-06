package dosna.dhtAbstraction;

import dosna.content.DOSNAContent;
import java.io.IOException;
import java.util.NoSuchElementException;
import socialkademlia.JSocialKademliaNode;
import kademlia.dht.GetParameter;
import kademlia.dht.KadContent;
import socialkademlia.dht.JSocialKademliaStorageEntry;
import kademlia.exceptions.ContentNotFoundException;
import kademlia.node.KademliaId;

/**
 * An abstraction that handles routing data on the network and storing data.
 * This class is an abstraction of the DHT and routing protocol for our system.
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public interface DataManager
{

    /**
     * Put data onto the network.
     * This will put the data onto the network based on the routing protocol.
     * It may or may not store data locally.
     *
     * @param content The content to be stored on the network
     *
     * @return How many nodes was this content stored on
     *
     * @throws java.io.IOException
     */
    public int put(final DOSNAContent content) throws IOException;

    /**
     * Stores data and cache a copy locally
     *
     * @param content The content to be stored locally
     *
     * @return How many nodes have this content been put on excluding the local node.
     *
     * @throws java.io.IOException
     */
    public int putAndCache(final DOSNAContent content) throws IOException;

    /**
     * Get entries for the required data from the network
     *
     * @param gp
     *
     * @return A single data entry
     *
     * @throws java.io.IOException
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public JSocialKademliaStorageEntry get(final GetParameter gp) throws IOException, NoSuchElementException, ContentNotFoundException;

    /**
     * Get entries for the required data from the network
     *
     * @param key
     * @param type
     *
     * @return A single data entry
     *
     * @throws java.io.IOException
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public JSocialKademliaStorageEntry get(final KademliaId key, final String type) throws IOException, NoSuchElementException, ContentNotFoundException;

    /**
     * Get entries for the required data from the network
     *
     * @param key
     * @param type
     * @param ownerId
     *
     * @return A single data entry
     *
     * @throws java.io.IOException
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public JSocialKademliaStorageEntry get(final KademliaId key, final String type, final String ownerId) throws IOException, NoSuchElementException, ContentNotFoundException;

    /**
     * Get the content and also cache it.
     *
     * @param gp
     *
     * @return A single data entry
     *
     * @throws java.io.IOException
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public JSocialKademliaStorageEntry getAndCache(final GetParameter gp) throws IOException, ContentNotFoundException;

    /**
     * Get the content and also cache it.
     *
     * @param key
     * @param type
     *
     * @return A single data entry
     *
     * @throws java.io.IOException
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public JSocialKademliaStorageEntry getAndCache(final KademliaId key, final String type) throws IOException, ContentNotFoundException;

    /**
     * Get the content and also cache it.
     *
     * @param key
     * @param type
     * @param ownerId
     *
     * @return A single data entry
     *
     * @throws java.io.IOException
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public JSocialKademliaStorageEntry getAndCache(final KademliaId key, final String type, final String ownerId) throws IOException, ContentNotFoundException;

    /**
     * Run an update call to update the data stored locally on this computer.
     * This may involve deleting some data and adding some other data.
     */
    public void updateStorage();

    /**
     * Save the state of the DataManager and shutdown
     *
     * @param saveState Whether to save the state of the application or not
     *
     * @throws java.io.IOException
     */
    public void shutdown(final boolean saveState) throws IOException;

    /**
     * @return The JSocialKademliaNode used by this DataManager
     */
    public JSocialKademliaNode getKademliaNode();

    /**
     * Stores the specified value under the given key locally;
     * This content is permanently stored locally and will not be deleted unless the cache is cleared.
     *
     * @param content The content to put onto the local DHT
     *
     * @throws java.io.IOException
     *
     */
    public void cache(KadContent content) throws IOException;
}
