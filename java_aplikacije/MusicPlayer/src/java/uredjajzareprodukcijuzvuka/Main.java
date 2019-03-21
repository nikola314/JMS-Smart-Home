/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uredjajzareprodukcijuzvuka;

/**
 *
 * @author Nikola
 */
import javax.persistence.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSProducer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class Main {

    @Resource(lookup = "myQueue")
    static javax.jms.Queue queue;
    @Resource(lookup = "responseQueue")
    static javax.jms.Queue responseQueue;
    @Resource(lookup = "myTopic")
    static Topic topic;
    @Resource(lookup = "jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;

    private static final int MY_RID = ConstsAndPaths.ID_SONGPLAYER;

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        UredjajZaReprodukciju ur = new UredjajZaReprodukciju();
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();
        System.out.println(ur.getAllSongsForUser("Nikola"));

        while (true) {
            Message message = consumer.receive();

            if (message instanceof TextMessage) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    int idR = textMessage.getIntProperty("idR");
                    int idS = textMessage.getIntProperty("idS");
                    System.out.println("Message recieved, idR=" + Integer.toString(textMessage.getIntProperty("idR")) + ", idS=" + Integer.toString(textMessage.getIntProperty("idS")) + ", text: " + textMessage.getText());

                    if (idR != MY_RID) {
                        continue;
                    }
                    String action = textMessage.getStringProperty("action");
                    String msgTxt = textMessage.getText();
                    String ret;
                    switch (action) {
                        case Actions.listSongs:
                            ret = ur.getAllSongsForUser(msgTxt);
                            reply(context, producer, ret);
                            break;
                        case Actions.playSong:
                            ur.playTheSong(msgTxt);
                            break;
                        case Actions.stop:
                            System.exit(0);
                    }
                } catch (JMSException ex) {
                }
            }
        }
    }

    private static void reply(JMSContext context, JMSProducer producer, String s) {

        TextMessage textMessage = context.createTextMessage();
        try {
            textMessage.setText(s);
            textMessage.setIntProperty("idR", ConstsAndPaths.ID_USER);
            textMessage.setIntProperty("idS", ConstsAndPaths.ID_SONGPLAYER);
            textMessage.setStringProperty("action", Actions.listSongs);
            producer.send(Main.responseQueue, textMessage);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
