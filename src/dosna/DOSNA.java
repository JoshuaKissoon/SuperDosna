package dosna;

import dosna.dhtAbstraction.DataManager;
import dosna.dhtAbstraction.DosnaDataManager;
import dosna.gui.AnanciUI;
import dosna.gui.LoginFrame;
import dosna.gui.SignupFrame;
import dosna.osn.actor.Actor;
import dosna.osn.actor.ActorManager;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import kademlia.dht.GetParameter;
import socialkademlia.dht.JSocialKademliaStorageEntry;
import kademlia.exceptions.ContentNotFoundException;
import kademlia.node.KademliaId;

/**
 * The main DOSNA launch file
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class DOSNA
{

    private DataManager dataManager = null;
    private PeriodicNotificationsChecker notificationsChecker;
    private SystemObjectsManager systemObjects;

    public DOSNA()
    {

    }

    /**
     * Launch the main node for this instance
     * Connect to the network
     * Launch the main Application UI
     *
     * @param actorId
     * @param nid
     *
     * @return Boolean Whether the initialization was successful or not
     */
    public boolean initRouting(String actorId, KademliaId nid)
    {
        if (dataManager == null)
        {
            boolean isConnected = false;
            while (!isConnected)
            {
                try
                {
                    dataManager = new DosnaDataManager(actorId, nid);
                    isConnected = true;
                }
                catch (IOException ex)
                {
                    /* Try again, since we may have tried to use a port already used */

                }
            }
        }
        return true;
    }

    /* Starts the DOSNA */
    public void start()
    {
        this.userLogin();
    }

    /**
     * Launch the actor login form and handle logging in the actor
     */
    public void userLogin()
    {
        /* Ask the actor to login */
        final LoginFrame login = new LoginFrame();
        login.setActionListener((final ActionEvent event) ->
        {
            switch (event.getActionCommand())
            {
                case "login":
                    final String username = login.getUsername();
                    final String password = login.getPassword();

                    final LoginResult res = this.loginUser(username, password);
                    if (res.isLoginSuccessful)
                    {
                        /* Everything's great! Launch the app */
                        login.dispose();
                        DOSNA.this.launchDosna(res.loggedInActor);
                    }
                    else
                    {
                        if (res.isActorExistent)
                        {
                            JOptionPane.showMessageDialog(null, "Invalid password! please try again.");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Sorry, no account exists for the given credentials.");
                        }
                    }
                    break;
                case "signup":
                    /* The actor wants to signup, get them the signup form */
                    login.dispose();
                    DOSNA.this.userSignup();
                    break;
            }
        });
        login.createGUI();
        login.display();
    }

    /**
     * Try to login a actor into the system, handles the logic for logging in the actor.
     *
     * @param actorId
     * @param password
     *
     * @return Whether the login was successful or not
     */
    public LoginResult loginUser(String actorId, String password)
    {
        final Actor u = new Actor(actorId);

        DOSNA.this.initRouting(actorId, u.getKey());

        try
        {
            /* Checking if a actor's account exists */
            final GetParameter gp = new GetParameter(u.getKey(), u.getType(), actorId);
            JSocialKademliaStorageEntry items = this.dataManager.get(gp);

            /* Actor exists! Now check if password matches */
            Actor actor = (Actor) new Actor().fromSerializedForm(items.getContent());
            if (actor.isPassword(password))
            {
                /* Actor is logged in, lets update the actor's node */
                new ActorManager(this.dataManager).updateActorNode(actor, this.dataManager.getKademliaNode().getNode());

                return new LoginResult(actor, true);
            }
        }
        catch (ContentNotFoundException cnfex)
        {
            /* The actor does not exist */
            return new LoginResult();
        }
        catch (IOException ex)
        {

        }

        /* Login was unsuccessful */
        return new LoginResult(false);
    }

    /**
     * A class used to return the result of a login
     */
    public class LoginResult
    {

        public Actor loggedInActor = null;
        public boolean isLoginSuccessful = false;
        public boolean isActorExistent = false;

        public LoginResult(final Actor loggedInActor, final boolean isLoginSuccessful, final boolean isUserExistent)
        {
            this.loggedInActor = loggedInActor;
            this.isLoginSuccessful = isLoginSuccessful;
            this.isActorExistent = isUserExistent;
        }

        public LoginResult(final Actor loggedInActor, final boolean isLoginSuccessful)
        {
            this(loggedInActor, isLoginSuccessful, true);
        }

        public LoginResult(final Boolean isLoginSuccessful)
        {
            this.isLoginSuccessful = isLoginSuccessful;
            this.isActorExistent = true;
        }

        public LoginResult()
        {

        }
    }

    /**
     * Launch the actor signup form and handle signing in the actor
     */
    public void userSignup()
    {
        final SignupFrame signup = new SignupFrame();

        signup.setActionListener((ActionEvent e) ->
        {
            final String username = signup.getUsername().trim();
            final String password = signup.getPassword().trim();
            final String fullName = signup.getFullName().trim();

            if (username.isEmpty() || password.isEmpty() || fullName.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "All fields are required.");
            }
            else
            {
                SignupResult res = this.signupUser(username, password, fullName);

                if (res.isSignupSuccessful)
                {
                    signup.dispose();
                    JOptionPane.showMessageDialog(null, "You have successfully joined! welcome!");
                    DOSNA.this.launchDosna(res.actor);
                }
                else
                {
                    if (res.isActorExistent)
                    {
                        JOptionPane.showMessageDialog(null, "This username is already taken! Please try another username.");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "The signup process encountered an error, please try again later.");
                    }
                }
            }
        });

        signup.createGUI();
        signup.display();
    }

    /**
     * Try to signup a actor into the system, handles the logic for signing up the actor.
     *
     * @param actor The actor object of the Actor to signup
     *
     * @return Whether the actor signup was successful or not
     */
    public SignupResult signupUser(final Actor actor)
    {
        try
        {
            /* Initialize our routing */
            DOSNA.this.initRouting(actor.getId(), actor.getKey());

            /* See if this actor object already exists on the network */
            GetParameter gp = new GetParameter(actor.getKey(), actor.getType(), actor.getId());
            dataManager.get(gp);

            /* Username is already taken */
            return new SignupResult(false);
        }
        catch (IOException | ContentNotFoundException ex)
        {
            try
            {
                /* Lets add this actor to the system */
                actor.setActorNode(this.dataManager.getKademliaNode().getNode());
                ActorManager ac = new ActorManager(dataManager);
                Actor createdActor = ac.createActor(actor);

                /* Actor added, now launch DOSNA */
                return new SignupResult(createdActor, true);
            }
            catch (IOException exc)
            {

            }
        }

        /* We got here means things were not successful */
        return new SignupResult();
    }

    public SignupResult signupUser(final String actorId, final String password, final String fullName)
    {
        final Actor u = new Actor(actorId);
        u.setPassword(password);
        u.setName(fullName);

        return this.signupUser(u);
    }

    /**
     * A class used to return the result of a login
     */
    public class SignupResult
    {

        public Actor actor = null;
        public boolean isSignupSuccessful = false;
        public boolean isActorExistent = false;

        public SignupResult(final Actor actor, final boolean isSignupSuccessful, final boolean isActorExistent)
        {
            this.actor = actor;
            this.isSignupSuccessful = isSignupSuccessful;
            this.isActorExistent = isActorExistent;
        }

        public SignupResult(final Actor actor, final boolean isSignupSuccessful)
        {
            this(actor, isSignupSuccessful, false);
        }

        public SignupResult(final Boolean isSignupSuccessful)
        {
            this.isSignupSuccessful = isSignupSuccessful;
            this.isActorExistent = true;
        }

        public SignupResult()
        {

        }
    }

    /**
     * Launch the main GUI
     *
     * @param actor Which actor are we launching it for
     */
    public void launchDosna(final Actor actor)
    {
        /* Lets set the data manager for this actor's content manager */
        actor.init(this.dataManager);
        this.systemObjects = new SystemObjectsManager(dataManager, actor);
        AnanciUI mainUi = new AnanciUI(this.systemObjects);
        mainUi.create();
        mainUi.display();

        /* Lets also launch the notifications checker */
        this.systemObjects.launchNotificationsChecker();
    }

    public DataManager getDataManager()
    {
        return this.dataManager;
    }
    
    public SystemObjectsManager getSystemObjects()
    {
        return this.systemObjects;
    }

    /**
     * Shuts down the core components of dosna
     *
     * @param saveState
     *
     * @throws java.io.IOException
     */
    public void shutdown(boolean saveState) throws IOException
    {
        this.systemObjects.shutDown(saveState);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new DOSNA().start();
    }

}
