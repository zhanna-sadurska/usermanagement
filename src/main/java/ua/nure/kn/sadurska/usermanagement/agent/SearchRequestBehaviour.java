package ua.nure.kn.sadurska.usermanagement.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehaviour extends Behaviour {

    private static final long serialVersionUID = 2352204424311743910L;

    private final AID[] aids;
    private final String firstName;
    private final String lastName;

    public SearchRequestBehaviour(AID[] aids, String firstName, String lastName) {
        this.aids = aids;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void action() {
        if (aids != null) {
            final ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
            message.setContent(firstName + ", " + lastName);
            for (final AID aid : aids) {
                message.addReceiver(aid);
            }
            myAgent.send(message);
        }
    }

    public boolean done() {
        return true;
    }
}
