/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
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
    @Resource(lookup = "myTopic")
    static Topic topic;
    @Resource(lookup = "alarmQueue")
    static javax.jms.Queue alarmQueue;
    @Resource(lookup = "jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
    @Resource(lookup = "plannerQueue")
    static javax.jms.Queue plannerQueue;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CommunicationManager.initialize();
        int choice = 1;
        String choices = System.lineSeparator() + System.lineSeparator() + "/**************************************/" + System.lineSeparator()
                + "1.\t Play The Song" + System.lineSeparator()
                + "2.\t Get All Songs That User Played" + System.lineSeparator()
                + "3.\t Set Alarm" + System.lineSeparator()
                + "4.\t Set Alarm At Suggested Time" + System.lineSeparator()
                + "5.\t Change Alarm Ringtone" + System.lineSeparator()
                + "6.\t List All Events" + System.lineSeparator()
                + "7.\t Add An Event" + System.lineSeparator()
                + "8.\t Remove Event" + System.lineSeparator()
                + "9.\t Modify Event" + System.lineSeparator()
                + "10.\t Time From A to B" + System.lineSeparator()
                + "11.\t Time From Here To B" + System.lineSeparator()               
                + "0.\t Exit" + System.lineSeparator() + "/***************************************/" + System.lineSeparator()
                + "Choice: ";

        while (choice != 0) {
            System.out.println(choices);
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    CommunicationManager.playTheSong();
                    break;
                case 2:
                    CommunicationManager.getAllSongs();
                    break;
                case 3:
                    CommunicationManager.setAlarm();
                    break;
                case 4:
                    CommunicationManager.setSuggestedAlarm();
                    break;
                case 5:
                    CommunicationManager.changeAlarmRingtone();
                    break;
                case 6:
                    CommunicationManager.listAllEvents();
                    break;
                case 7:
                    CommunicationManager.addAnEvent();
                    break;
                case 8:
                    CommunicationManager.removeEvent();
                    break;
                case 9:
                    CommunicationManager.modifyEvent();
                    break;
                case 10:
                    CommunicationManager.timeFromAToB();
                    break;
                case 11:
                    CommunicationManager.timeFromHereToB();
                    break;
                
                case 0:
                    System.exit(0);
            }
        }
    }

}
