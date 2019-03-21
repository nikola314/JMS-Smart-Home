/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.SECONDS;
import java.util.Date;
import java.util.Scanner;
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
public class CommunicationManager {

    static JMSContext context;
    static JMSConsumer consumer;
    static JMSProducer producer;

    public static void initialize() {
        context = Main.connectionFactory.createContext();
        consumer = context.createConsumer(Main.responseQueue);
        producer = context.createProducer();
    }

    public static void playTheSong() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name of the song: ");
        String name = sc.nextLine();
        TextMessage textMessage = context.createTextMessage();
        try {
            textMessage.setText(name);
            textMessage.setIntProperty("idR", ConstsAndPaths.ID_SONGPLAYER);
            textMessage.setIntProperty("idS", ConstsAndPaths.ID_USER);
            textMessage.setStringProperty("action", Actions.playSong);
            producer.send(Main.queue, textMessage);
        } catch (JMSException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getAllSongs() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name of the user: ");
        String name = sc.nextLine();
        TextMessage textMessage = context.createTextMessage();
        try {
            textMessage.setText(name);
            textMessage.setIntProperty("idR", ConstsAndPaths.ID_SONGPLAYER);
            textMessage.setIntProperty("idS", ConstsAndPaths.ID_USER);
            textMessage.setStringProperty("action", Actions.listSongs);
            producer.send(Main.queue, textMessage);
            while (true) {
                Message message = consumer.receive(2000);
                if (message instanceof TextMessage) {
                    TextMessage response;
                    response = (TextMessage) message;
                    int idR = response.getIntProperty("idR");
                    int idS = response.getIntProperty("idS");
                    System.out.println("Message recieved, idR=" + Integer.toString(response.getIntProperty("idR")) + ", idS=" + Integer.toString(response.getIntProperty("idS")) + ", text: " + response.getText());
                    if (idR != ConstsAndPaths.ID_USER || idS != ConstsAndPaths.ID_SONGPLAYER) {
                        continue;
                    }
                    String msgTxt = response.getText();
                    System.out.println(msgTxt);
                    break;
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setAlarm() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Date And Time (dd-MMM-yyyy HH:mm:ss): ");
            String timeString = sc.nextLine();
            System.out.println("Interval: ");
            int interval = sc.nextInt();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date date = formatter.parse(timeString);
            sendMessageToAlarm(date, interval);
        } catch (ParseException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void sendMessageToAlarm(Date date, int interval) {
        try {
            TextMessage obm = context.createTextMessage();
            obm.setLongProperty("date", date.getTime());
            obm.setIntProperty("interval", interval);
            obm.setIntProperty("idR", ConstsAndPaths.ID_ALARM);
            obm.setIntProperty("idS", ConstsAndPaths.ID_USER);
            obm.setStringProperty("action", Actions.setAlarm);
            producer.send(Main.alarmQueue, obm);
        } catch (JMSException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Date dateFromDatabase(Date d) {
        return addHoursToDate(d, -1);
    }

    private static Date dateToDatabase(Date d) {
        return addHoursToDate(d, 1);
    }

    private static Date addHoursToDate(Date date, int hours) {
        Date ret = new Date();
        long time = date.getTime();
        time += hours * (3600 * 1000);
        ret.setTime(time);
        return ret;
    }

    static void setSuggestedAlarm() {
        Scanner sc = new Scanner(System.in);
        Date currDate = new Date(System.currentTimeMillis());
        Date suggestedDates[] = new Date[5];
        suggestedDates[0] = addHoursToDate(currDate, 1);
        suggestedDates[1] = addHoursToDate(currDate, 2);
        suggestedDates[2] = addHoursToDate(currDate, 6);
        suggestedDates[3] = addHoursToDate(currDate, 24);
        suggestedDates[4] = addHoursToDate(currDate, 7 * 24);
        System.out.println("Choose: ");
        for (int i = 0; i < 5; i++) {
            System.out.println((i + 1) + ". " + suggestedDates[i]);
        }
        int choice = sc.nextInt();
        if (choice > 5) {
            return;
        }
        Date chosen = suggestedDates[choice - 1];
        sendMessageToAlarm(chosen, 0);
    }

    static void changeAlarmRingtone() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Change Ringtone To: ");
            String newRingtone = sc.nextLine();
            TextMessage msg = context.createTextMessage();
            msg.setIntProperty("idR", ConstsAndPaths.ID_ALARM);
            msg.setIntProperty("idS", ConstsAndPaths.ID_USER);
            msg.setStringProperty("action", Actions.changeAlarmRingtone);
            msg.setText(newRingtone);
            producer.send(Main.alarmQueue, msg);
        } catch (JMSException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void listAllEvents() {
        TextMessage textMessage = context.createTextMessage();
        try {
            textMessage.setText("");
            textMessage.setIntProperty("idR", ConstsAndPaths.ID_PLANNER);
            textMessage.setIntProperty("idS", ConstsAndPaths.ID_USER);
            textMessage.setStringProperty("action", Actions.listEvents);
            producer.send(Main.plannerQueue, textMessage);
            while (true) {
                Message message = consumer.receive(2000);
                if (message instanceof TextMessage) {
                    TextMessage response = (TextMessage) message;
                    int idR = response.getIntProperty("idR");
                    int idS = response.getIntProperty("idS");
                    System.out.println("Message recieved, idR=" + Integer.toString(response.getIntProperty("idR")) + ", idS=" + Integer.toString(response.getIntProperty("idS")) + ", text: " + response.getText());
                    if (idR != ConstsAndPaths.ID_USER || idS != ConstsAndPaths.ID_PLANNER) {
                        continue;
                    }
                    String msgTxt = response.getText();
                    System.out.println(System.lineSeparator());
                    System.out.println("ID\tName\t\tDestination\t\t\tScheduled\t\t\tReminder");
                    System.out.println(msgTxt);
                    Scanner sc = new Scanner(System.in);
                    sc.nextLine();
                    break;
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void addAnEvent() {
        try {
            long lEDate = 0, lRDate = 0;
            Scanner sc = new Scanner(System.in);
            System.out.println("Date And Time Of The Event (dd-MMM-yyyy HH:mm:ss): ");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String eventDateString = sc.nextLine();
            Date eventDate = formatter.parse(eventDateString);
            lEDate = eventDate.getTime();
            System.out.println("Do You Want To Set A Reminder For This Event?");
            String choice = sc.nextLine();
            Date reminderDate = null;
            if (choice.compareToIgnoreCase("yes") == 0) {
                System.out.println("Date And Time Of The Reminder (dd-MMM-yyyy HH:mm:ss): ");
                String reminderDateString = sc.nextLine();
                reminderDate = formatter.parse(reminderDateString);
                lRDate = reminderDate.getTime();
            }
            System.out.println("Name Of This Event: ");
            String name = sc.nextLine();
            TextMessage msg = context.createTextMessage();
            msg.setStringProperty("action", Actions.addEvent);
            msg.setStringProperty("name", name);
            msg.setLongProperty("date", lEDate);
            msg.setLongProperty("reminder", lRDate);
            msg.setIntProperty("idR", ConstsAndPaths.ID_PLANNER);
            msg.setIntProperty("idS", ConstsAndPaths.ID_USER);
            System.out.println("Do You Want To Enter A Destination: ");
            choice = sc.nextLine();
            String destination = "";
            if (choice.compareToIgnoreCase("yes") == 0) {
                System.out.println("Destination: ");
                destination = sc.nextLine();
            }
            msg.setStringProperty("destination", destination);
            producer.send(Main.plannerQueue, msg);
        } catch (ParseException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void removeEvent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("ID Of The Event You Want To Remove: ");
        int id = sc.nextInt();
        TextMessage textMessage = context.createTextMessage();
        try {
            textMessage.setText("");
            textMessage.setIntProperty("idR", ConstsAndPaths.ID_PLANNER);
            textMessage.setIntProperty("idS", ConstsAndPaths.ID_USER);
            textMessage.setStringProperty("action", Actions.removeEvent);
            textMessage.setIntProperty("id", id);
            producer.send(Main.plannerQueue, textMessage);
        } catch (JMSException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void modifyEvent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("ID Of The Event You Want To Modify: ");
        int id = sc.nextInt();
        sc.nextLine();
        
        try {
            long lEDate = 0, lRDate = 0;
            System.out.println("Date And Time Of The Event (dd-MMM-yyyy HH:mm:ss): ");                        
            String eventDateString = sc.nextLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date eventDate = formatter.parse(eventDateString);
            lEDate = eventDate.getTime();
            System.out.println("Do You Want To Set A Reminder For This Event?");
            String choice = sc.nextLine();
            Date reminderDate = null;
            if (choice.compareToIgnoreCase("yes") == 0) {
                System.out.println("Date And Time Of The Reminder (dd-MMM-yyyy HH:mm:ss): ");
                String reminderDateString = sc.nextLine();
                reminderDate = formatter.parse(reminderDateString);
                lRDate = reminderDate.getTime();
            }
            System.out.println("Name Of This Event: ");
            String name = sc.nextLine();
            TextMessage msg = context.createTextMessage();
            msg.setIntProperty("id", id);
            msg.setStringProperty("action", Actions.modifyEvent);
            msg.setStringProperty("name", name);
            msg.setLongProperty("date", lEDate);
            msg.setLongProperty("reminder", lRDate);
            msg.setIntProperty("idR", ConstsAndPaths.ID_PLANNER);
            msg.setIntProperty("idS", ConstsAndPaths.ID_USER);
            System.out.println("Do You Want To Enter A Destination: ");
            choice = sc.nextLine();
            String destination = "";
            if (choice.compareToIgnoreCase("yes") == 0) {
                System.out.println("Destination: ");
                destination = sc.nextLine();
            }
            msg.setStringProperty("destination", destination);
            producer.send(Main.plannerQueue, msg);
        } catch (JMSException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void timeFromAToB() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Location A: ");
        String from=sc.nextLine();
        calculateTime(Actions.timeFromAToB,from);
    }

    static void timeFromHereToB() {
        calculateTime(Actions.timeFromMyPosition,"");
    }
    
    static void calculateTime(String action,String from){
        Scanner sc=new Scanner(System.in);
                System.out.println("Location B: ");
        String to=sc.nextLine();
        TextMessage textMessage = context.createTextMessage();
        try {
            textMessage.setText("");
            textMessage.setIntProperty("idR", ConstsAndPaths.ID_PLANNER);
            textMessage.setIntProperty("idS", ConstsAndPaths.ID_USER);
            textMessage.setStringProperty("action", action);
            textMessage.setStringProperty("from",from);
            textMessage.setStringProperty("to", to);
            producer.send(Main.plannerQueue, textMessage);
            while (true) {
                Message message = consumer.receive(2000);
                if (message instanceof TextMessage) {
                    TextMessage response = (TextMessage) message;
                    int idR = response.getIntProperty("idR");
                    int idS = response.getIntProperty("idS");
                    System.out.println("Message recieved, idR=" + Integer.toString(response.getIntProperty("idR")) + ", idS=" + Integer.toString(response.getIntProperty("idS")) + ", text: " + response.getText());
                    if (idR != ConstsAndPaths.ID_USER || idS != ConstsAndPaths.ID_PLANNER) {
                        continue;
                    }
                    String msgTxt = response.getText();
                    System.out.println(System.lineSeparator());
                    System.out.println(msgTxt);
                    sc.nextLine();
                    break;
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
