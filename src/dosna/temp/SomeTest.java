package dosna.temp;

import dosna.osn.actor.Actor;
import dosna.osn.status.Status;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import socialkademlia.dht.JSocialKademliaStorageEntry;
import socialkademlia.dht.util.StorageEntryCompressor;
import kademlia.util.serializer.JsonSerializer;
import kademlia.util.serializer.KadSerializer;

/**
 *
 * @author Joshua
 * @since
 */
public class SomeTest
{

    public SomeTest() throws IOException, ClassNotFoundException
    {
        Actor actor = new Actor("Some User");
        Actor a2 = new Actor("joshy");
        Actor a3 = new Actor("safi");
        
        actor.addConnection(a2.getId());
        actor.addConnection(a3.getId());
        
        Status s1 = Status.createNew(actor, "Some status 1");
        Status s2 = Status.createNew(actor, "Some status 12");
        Status s3 = Status.createNew(actor, "Some status 123");
        Status s4 = Status.createNew(actor, "Some status 1234");
        
        actor.getContentManager().addContentToActorContentSet(s1);
        actor.getContentManager().addContentToActorContentSet(s2);
        actor.getContentManager().addContentToActorContentSet(s3);
        actor.getContentManager().addContentToActorContentSet(s4);
        
        JSocialKademliaStorageEntry entry = new JSocialKademliaStorageEntry(actor);
        
        System.out.println(entry);
        System.out.println("\n\n\n");
        
        
        entry = StorageEntryCompressor.compress(entry);
        System.out.println("Entry: " + entry);
        System.out.println("\n\n\n");
        
        KadSerializer<JSocialKademliaStorageEntry> serializer = new JsonSerializer<>();
        String fileName = "some file.kct";
        try (FileOutputStream fout = new FileOutputStream(fileName);
                DataOutputStream dout = new DataOutputStream(fout))
        {
            serializer.write(entry, dout);
        }
        
        DataInputStream din = new DataInputStream(new FileInputStream(fileName));
        JSocialKademliaStorageEntry retrievedEntry = serializer.read(din);
        
        retrievedEntry = StorageEntryCompressor.decompress(retrievedEntry);
        System.out.println(retrievedEntry);
    }

    public static void main(String[] args)
    {
        try
        {
            new SomeTest();
        }
        catch(IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }

}
