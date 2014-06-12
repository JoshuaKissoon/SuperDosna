package dosna.messaging.gui;

import dosna.SystemObjects;
import dosna.messaging.Message;
import dosna.messaging.MessageBox;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import kademlia.exceptions.ContentNotFoundException;

/**
 * Manager for the message sending between actors
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public final class MessageSendingForm extends JPanel
{

    /* Form Elements */
    private final JTextArea messageTA;
    private final JButton submitBtn;
    private final JScrollPane scrollPane;

    /* Listeners */
    private ActionListener actionListener;

    /* General objects */
    private final SystemObjects dosnaObjects;
    private final String connectionAid;

    private final MessageFrame msgFrame;

    /**
     * Setup a new object
     *
     * @param dosnaObjects  Objects that will be needed by the form's action event handlers
     * @param connectionAid The ActorId of the connection that the logged in actor is sending the message to
     *
     * @todo Remove sending the messageframe and use the Observer pattern
     */
    public MessageSendingForm(final SystemObjects dosnaObjects, final String connectionAid, final MessageFrame msgFrame)
    {
        this.dosnaObjects = dosnaObjects;
        this.connectionAid = connectionAid;
        this.msgFrame = msgFrame;

        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        /* Status Text */
        messageTA = new JTextArea(5, 40);
        messageTA.setWrapStyleWord(true);
        messageTA.setLineWrap(true);
        scrollPane = new JScrollPane(messageTA);
        this.add(scrollPane, BorderLayout.CENTER);

        /* Submit button */
        submitBtn = new JButton("Send");
        submitBtn.setActionCommand("sendMsg");
        this.add(submitBtn, BorderLayout.EAST);

        /* Set some form properties */
        this.setPreferredSize(new Dimension(540, 100));

        /* Set the action listener */
        this.setActionListener(new SendingFormActionListener(this));
    }

    /**
     * Set the ActionListener to listen for actions from this form
     *
     * @param li
     */
    public final void setActionListener(final ActionListener li)
    {
        this.actionListener = li;
        submitBtn.addActionListener(this.actionListener);
    }

    public final String getEnteredMessage()
    {
        return this.messageTA.getText();
    }

    /**
     * Clear the text in the status box.
     */
    public final void clearMessageBox()
    {
        this.messageTA.setText("");
    }

    public final String getActorId()
    {
        return this.dosnaObjects.getActor().getId();
    }

    public final String getConnectionAid()
    {
        return this.connectionAid;
    }

    public final SystemObjects getDosnaObjects()
    {
        return this.dosnaObjects;
    }

    /**
     * An action listener class for the message sending form.
     * - This class is created to be stand alone so it can possibly be replaced later...
     */
    private final class SendingFormActionListener implements ActionListener
    {

        private final MessageSendingForm form;

        public SendingFormActionListener(final MessageSendingForm form)
        {
            this.form = form;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            final String text = form.getEnteredMessage();
            if (text.trim().isEmpty())
            {
                return;
            }

            /* We got here, means everything's all good */
            Message m = new Message(text, form.getActorId(), form.getConnectionAid());

            try
            {
                MessageBox box = this.form.getDosnaObjects().getMessagingManager().sendMessage(m, connectionAid);
                msgFrame.updateContactPanel(connectionAid);
            }
            catch (IOException | ContentNotFoundException ex)
            {
                /* @todo Update user of failed message sending */
            }
        }

    }
}
