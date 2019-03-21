/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planner;

import entities.Dogadjaji;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Nikola
 */
public class Main {

    @Resource(lookup = "myQueue")
    static javax.jms.Queue queue;
    @Resource(lookup = "responseQueue")
    static javax.jms.Queue responseQueue;
    @Resource(lookup = "alarmQueue")
    static javax.jms.Queue alarmQueue;
    @Resource(lookup = "myTopic")
    static Topic topic;
    @Resource(lookup = "jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
    @Resource(lookup = "plannerQueue")
    static javax.jms.Queue plannerQueue;

    private static final int MY_RID = ConstsAndPaths.ID_PLANNER;

    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(plannerQueue);
        JMSProducer producer = context.createProducer();
        Planner planner = new Planner(context, consumer, producer);
        planner.readEvents();
        while (true) {
            Message message = consumer.receive();
            try {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    int idR = textMessage.getIntProperty("idR");
                    int idS = textMessage.getIntProperty("idS");
                    System.out.println("Message recieved, idR=" + Integer.toString(textMessage.getIntProperty("idR")) + ", idS=" + Integer.toString(textMessage.getIntProperty("idS")) + ", text: " + textMessage.getText());
                    if (idR != MY_RID) {
                        continue;
                    }
                    String action = textMessage.getStringProperty("action");
                    switch (action) {
                        case Actions.listEvents:
                            planner.listEvents();
                            break;
                        case Actions.addEvent:
                            addEventToPlanner(planner, textMessage);
                            break;
                        case Actions.removeEvent:
                            int id = textMessage.getIntProperty("id");
                            planner.removeEvent(id);
                            break;
                        case Actions.modifyEvent:
                            modifyEventInPlanner(planner, textMessage);
                            break;
                        case Actions.timeFromAToB:
                            String a = textMessage.getStringProperty("from");
                            String b = textMessage.getStringProperty("to");
                            planner.timeFromAToB(a, b);
                            break;
                        case Actions.timeFromMyPosition:
                            planner.timeFromMyPosition(textMessage.getStringProperty("to"));
                    }
                }
            } catch (JMSException e) {}
        }
    }

    private static void modifyEventInPlanner(Planner p, TextMessage textMessage) {
        try {
            int id = textMessage.getIntProperty("id");
            Date dateAndTime = new Date();
            dateAndTime.setTime(textMessage.getLongProperty("date"));
            long reminder = textMessage.getLongProperty("reminder");
            Date reminderDate = null;
            if (reminder > 0) {
                reminderDate = new Date();
                reminderDate.setTime(reminder);
            }
            String name = textMessage.getStringProperty("name");
            String destination = textMessage.getStringProperty("destination");
            p.modifyEvent(id, dateAndTime, reminderDate, name, destination);

        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void addEventToPlanner(Planner p, TextMessage textMessage) {
        try {
            Date dateAndTime = new Date();
            dateAndTime.setTime(textMessage.getLongProperty("date"));
            long reminder = textMessage.getLongProperty("reminder");
            Date reminderDate = null;
            if (reminder > 0) {
                reminderDate = new Date();
                reminderDate.setTime(reminder);
            }
            String name = textMessage.getStringProperty("name");
            String destination = textMessage.getStringProperty("destination");
            p.addEvent(dateAndTime, reminderDate, name, destination);

        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
