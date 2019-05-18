package ua.nure.kn.sadurska.usermanagement.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DaoFactory;
import ua.nure.kn.sadurska.usermanagement.db.DatabaseException;

import java.util.Collection;


public class SearchAgent extends Agent {

    private AID[] aids;
    private SearchGui searchGui;

    @Override
    protected void setup() {
        super.setup();
        System.out.println(getAID().getName() + " started");
        searchGui = new SearchGui(this);
        searchGui.setVisible(true);
        final DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        final ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName("JADE-searching");
        serviceDescription.setType("searching");
        description.addServices(serviceDescription);
        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        addBehaviour(new RequestServer());
        addBehaviour(new TickerBehaviour(this, 60000) {
            @Override
            protected void onTick() {
                final DFAgentDescription agentDescription = new DFAgentDescription();
                final ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("searching");
                agentDescription.addServices(serviceDescription);
                try {
                    final DFAgentDescription[] descriptions = DFService.search(myAgent, agentDescription);
                    aids = new AID[descriptions.length];
                    for (int i = 0; i < aids.length; i++) {
                        final DFAgentDescription d = descriptions[i];
                        aids[i] = d.getName();
                    }
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void takeDown() {
        System.out.println(getAID().getName() + " terminated");
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        searchGui.setVisible(false);
        searchGui.dispose();
        super.takeDown();
    }

    public void search(final String firstName, final String lastName) throws SearchException {
        try {
            Collection<User> users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            if (!users.isEmpty()) {
                showUsers(users);
            } else {
                addBehaviour(new SearchRequestBehaviour(aids, firstName, lastName));
            }
        } catch (final DatabaseException e) {
            throw new SearchException(e);
        }
    }

    public void showUsers(final Collection<User> users) {
        searchGui.addUsers(users);
    }
}
