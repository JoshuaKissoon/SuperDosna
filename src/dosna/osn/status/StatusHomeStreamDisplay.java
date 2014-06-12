package dosna.osn.status;

import dosna.osn.activitystream.ActivityStreamContent;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * Class that displays a status on the home stream
 *
 * @author Joshua
 * @since
 */
public class StatusHomeStreamDisplay extends JPanel implements ActivityStreamContent
{

    /* Properties */
    private final Status status;

    /* Elements */
    private final JTextArea statusDisplayTA;

    /**
     * Setting up the status home stream display.
     *
     * @param status The status to be displayed
     */
    public StatusHomeStreamDisplay(Status status)
    {
        this.status = status;

        this.setLayout(new BorderLayout());

        StringBuilder statusText = new StringBuilder();

        statusText.append(status.getOwnerId());
        statusText.append(" on ");
        statusText.append(status.getDateTime());
        statusText.append(" \n\n");

        statusText.append(status.getStatusText());

        statusDisplayTA = new JTextArea(statusText.toString());
        statusDisplayTA.setEditable(false);
        statusDisplayTA.setWrapStyleWord(true);
        statusDisplayTA.setLineWrap(true);

        statusDisplayTA.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.add(statusDisplayTA, BorderLayout.CENTER);
    }

    @Override
    public JPanel getContentDisplay()
    {
        return this;
    }

    @Override
    public long getLastUpdatedTimestamp()
    {
        return this.status.getLastUpdatedTimestamp();
    }

}
