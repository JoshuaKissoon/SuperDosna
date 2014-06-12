package dosna.gui;

import dosna.SystemObjectsManager;
import dosna.dhtAbstraction.DataManager;
import dosna.osn.actor.Actor;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * A form used to add a new connection
 *
 * @author Joshua Kissoon
 * @since 20140404
 */
public class ConnectionAddForm extends JFrame
{

    /* Properties */
    private final static int FRAME_WIDTH = 450;
    private final static int FRAME_HEIGHT = 250;

    private final SystemObjectsManager systemObjects;

    /**
     * Setup the add connection form
     *
     * @param systemObjects Used to put and get data
     */
    public ConnectionAddForm(final SystemObjectsManager systemObjects)
    {
        this.systemObjects = systemObjects;
    }

    /**
     * Create the frame and populate it's contents
     */
    public void create()
    {
        this.getContentPane().add(new ConnectionAddPanel(this.systemObjects));
    }

    /**
     * Display the frame onto the screen
     */
    public void display()
    {
        this.setTitle("Ananci: Add Connection - " + this.systemObjects.getActor().getName());

        this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
}
