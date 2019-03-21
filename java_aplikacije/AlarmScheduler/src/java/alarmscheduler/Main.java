/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmscheduler;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;

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
    
    private static final int MY_RID = ConstsAndPaths.ID_ALARM;
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(alarmQueue);
        JMSProducer producer = context.createProducer();
        Alarm alarm = new Alarm(producer, context);
        
        alarm.readAlarms();
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
                    System.out.println("action: "+action);
                    switch (action) {
                        case Actions.setAlarm:
                            long dateCode = textMessage.getLongProperty("date");
                            Date d = new Date();
                            d.setTime(dateCode);
                            int interval = textMessage.getIntProperty("interval");
                            alarm.setAlarm(d, interval);
                            break;
                        case Actions.changeAlarmRingtone:
                            alarm.setRingtone(textMessage.getText());
                            break;
                    }   
                }             
            } catch (JMSException e) { }
        }
    }
    
}
