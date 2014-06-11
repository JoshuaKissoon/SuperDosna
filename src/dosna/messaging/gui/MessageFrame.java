package dosna.messaging.gui;

import dosna.DosnaObjects;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This frame shows all messages provides the messaging facility
 *
 * @author Joshua
 * @since
 */
public final class MessageFrame extends JFrame implements Runnable
{

    /* Panel to show a list of contacts */
    private JPanel contactsPanel;

    /* Panel to show the messages from a single contact */
    private JPanel messagesPanel;

    private final DosnaObjects dosnaObjects;

    /**
     * Setup a new messenger frame
     *
     * @param dosnaObjects The set of DOSNA objects
     */
    public MessageFrame(final DosnaObjects dosnaObjects)
    {
        this.dosnaObjects = dosnaObjects;
    }

    /**
     * Create the GUI and it's contents
     */
    public void create()
    {

    }

    /**
     * Displays the GUI onto the screen
     */
    public void display()
    {
        this.setTitle("Messenger");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(1000, 500));
        this.setMinimumSize(new Dimension(1000, 500));
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    /**
     * When the run method is called, we'll launch the frame
     */
    @Override
    public void run()
    {
        this.create();
        this.display();
    }

}
