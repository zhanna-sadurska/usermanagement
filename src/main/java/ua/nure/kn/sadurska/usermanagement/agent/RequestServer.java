package ua.nure.kn.sadurska.usermanagement.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DaoFactory;
import ua.nure.kn.sadurska.usermanagement.db.DatabaseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class RequestServer extends CyclicBehaviour {


    @Override
    public void action() {
        final ACLMessage message = myAgent.receive();
        if (message != null) {
            if (message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            } else {
                final Collection<User> users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        } else {
            block();
        }
    }

    private Collection<User> parseMessage(final ACLMessage message) {
        final Collection<User> users = new LinkedList<>();
        final String content = message.getContent();
        if (content != null) {
            final StringTokenizer tokenizer = new StringTokenizer(content, ";");
            while (tokenizer.hasMoreTokens()) {
                final String userInfo = tokenizer.nextToken();
                final StringTokenizer userTokenizer = new StringTokenizer(userInfo, ",");
                final String id = userTokenizer.nextToken();
                final String firstName = userTokenizer.nextToken();
                final String lastName = userTokenizer.nextToken();
                users.add(new User(Long.valueOf(id), firstName, lastName, null));
            }
        }
        return users;
    }

    private ACLMessage createReply(final ACLMessage message) {
        final ACLMessage reply = message.createReply();
        final String content = message.getContent();
        final StringTokenizer tokenizer = new StringTokenizer(content, ",");
        if (tokenizer.countTokens() == 2) {
            final String firstName = tokenizer.nextToken();
            final String lastName = tokenizer.nextToken();
            Collection<User> users = null;
            try {
                users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            } catch (final DatabaseException e) {
                e.printStackTrace();
                users = new ArrayList<>();
            }
            final StringBuffer buffer = new StringBuffer();
            for (final User user : users) {
                buffer.append(user.getId()).append(",")
                        .append(user.getFirstName()).append(",")
                        .append(user.getLastName()).append(";");
            }
            reply.setContent(buffer.toString());
        }
        return reply;
    }
}
