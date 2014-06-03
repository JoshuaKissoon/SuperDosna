package dosna.dhtAbstraction;

import dosna.content.DOSNAContent;
import dosna.DOSNAConfig;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import kademlia.dht.GetParameter;
import socialkademlia.JSocialKademliaNode;
import kademlia.dht.KadContent;
import socialkademlia.dht.JSocialKademliaStorageEntry;
import kademlia.exceptions.ContentNotFoundException;
import kademlia.node.KademliaId;

/**
 * An implementation of the DataManager for this Application.
 * We use the Kademlia routing protocol here.
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public final class DosnaDataManager implements DataManager
{

    /**
     * The Kademlia instance to be used.
     * We use composition rather than inheritance.
     */
    private final JSocialKademliaNode kad;

    /**
     * Initialize Kademlia
     *
     * @param ownerId
     * @param nodeId  The NodeId to be used for this Kademlia instance
     * @param port
     *
     * @throws java.io.IOException
     * @throws java.net.UnknownHostException
     * @todo Try loading data from file, if no file exist, then create a new Kademlia instance
     */
    public DosnaDataManager(final String ownerId, final KademliaId nodeId, final int port) throws IOException, UnknownHostException
    {
        kad = new JSocialKademliaNode(ownerId, nodeId, port);
        kad.bootstrap(new DOSNAConfig().getBootstrapNode());
    }

    /**
     * Initialize Kademlia with a random port number
     *
     * @param ownerId
     * @param nodeId
     *
     * @throws java.io.IOException
     */
    public DosnaDataManager(final String ownerId, final KademliaId nodeId) throws IOException
    {
        this(ownerId, nodeId, (int) ((Math.random() * 20000) + 5000));
    }

    /**
     * Put data onto the network.
     * This will put the data onto the network based on the routing protocol.
     * It may or may not store data locally.
     */
    @Override
    public synchronized int put(final DOSNAContent content) throws IOException
    {
        this.kad.putLocally(content);   // Keep it locally so it'll be distributed on next refresh.
        return kad.put(content);
    }

    @Override
    public synchronized int putAndCache(final DOSNAContent content) throws IOException
    {
        return kad.putAndCache(content);
    }

    @Override
    public JSocialKademliaStorageEntry get(final GetParameter gp) throws IOException, ContentNotFoundException
    {
        return kad.get(gp);
    }

    @Override
    public JSocialKademliaStorageEntry get(KademliaId key, String type) throws IOException, NoSuchElementException, ContentNotFoundException
    {
        return this.get(new GetParameter(key, type));
    }

    @Override
    public JSocialKademliaStorageEntry get(KademliaId key, String type, String ownerId) throws IOException, NoSuchElementException, ContentNotFoundException
    {
        return this.get(new GetParameter(key, type, ownerId));
    }

    @Override
    public JSocialKademliaStorageEntry getAndCache(GetParameter gp) throws IOException, ContentNotFoundException
    {
        return this.kad.getAndCache(gp);
    }

    @Override
    public JSocialKademliaStorageEntry getAndCache(KademliaId key, String type) throws IOException, ContentNotFoundException
    {
        return this.getAndCache(new GetParameter(key, type));
    }

    @Override
    public JSocialKademliaStorageEntry getAndCache(KademliaId key, String type, String ownerId) throws IOException, ContentNotFoundException
    {
        return this.getAndCache(new GetParameter(key, type, ownerId));
    }

    @Override
    public void cache(KadContent content) throws IOException
    {
        this.kad.cache(content);
    }

    /**
     * Run an update call to update the data stored locally on this computer.
     * This may involve deleting some data and adding some other data.
     */
    @Override
    public void updateStorage()
    {

    }

    @Override
    public void shutdown(final boolean saveState) throws IOException
    {
        this.kad.shutdown(saveState);
    }

    @Override
    public JSocialKademliaNode getKademliaNode()
    {
        return this.kad;
    }
}
