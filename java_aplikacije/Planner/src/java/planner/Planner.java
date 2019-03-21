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
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Nikola
 */
public class Planner {

    private JMSContext context;
    private JMSConsumer consumer;
    private JMSProducer producer;
    private EntityManager em;
    private DistanceCalculator distCalc;

    Planner(JMSContext context, JMSConsumer consumer, JMSProducer producer) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlannerPU");
        em = emf.createEntityManager();
        this.context = context;
        this.consumer = consumer;
        this.producer = producer;
        distCalc = new DistanceCalculator();
    }

    public void readEvents() {
        // No need
    }

    public List<Dogadjaji> getAllEvents() {
        return em.createQuery("select e from Dogadjaji e").getResultList();
    }

    private static Date dateFromDatabase(Date d) {
        return addHoursToDate(d, -1);
    }

    private static Date dateToDatabase(Date d) {
        return addHoursToDate(d, 1);
    }

    private static Date addHoursToDate(Date date, int hours) {
        if(date==null) return null;
        Date ret = new Date();
        long time = date.getTime();
        time += hours * (3600 * 1000);
        ret.setTime(time);
        return ret;
    }
    
    public String getAllEventsAsString() {
        StringBuilder sb = new StringBuilder();
        List<Dogadjaji> list = getAllEvents();
        for (Dogadjaji d : list) {
            sb.append(d.getIddogadjaji() + "\t")
                    .append(d.getNazivdogadjaja() + "\t\t")
                    .append(d.getDestinacija() + "\t\t\t\t")
                    .append(dateFromDatabase(d.getDatumvreme()) + "\t")
                    .append(dateFromDatabase(d.getPodsetnik()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void print() {
        System.out.println(getAllEventsAsString());
    }

    void listEvents() {
        try {
            String s = getAllEventsAsString();
            TextMessage msg = context.createTextMessage();
            msg.setText(s);
            msg.setIntProperty("idR", ConstsAndPaths.ID_USER);
            msg.setIntProperty("idS", ConstsAndPaths.ID_PLANNER);
            producer.send(Main.responseQueue, msg);
        } catch (JMSException ex) {
            Logger.getLogger(Planner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addEvent(Date dateAndTime, Date reminderDate, String name, String destination) {
        Dogadjaji dog = new Dogadjaji();
        dog.setIddogadjaji(1 + (int) em.createQuery("select MAX(d.iddogadjaji) from Dogadjaji d").getSingleResult());
        dog.setDatumvreme(dateToDatabase(dateAndTime));
        dog.setDestinacija(destination);
        dog.setNazivdogadjaja(name);
        dog.setPodsetnik(dateToDatabase(reminderDate));
        em.getTransaction().begin();
        if(dog!=null) em.persist(dog);
        em.getTransaction().commit();
        addAlarm(reminderDate);
       
    }
    
    void addAlarm(Date date){
        try {
            if(date==null) return;
            TextMessage msg=context.createTextMessage();
            msg.setIntProperty("idR", ConstsAndPaths.ID_ALARM);
            msg.setIntProperty("idS", ConstsAndPaths.ID_PLANNER);
            msg.setLongProperty("date", date.getTime());
            msg.setIntProperty("interval", 0);
            msg.setStringProperty("action", Actions.setAlarm);
            System.out.println(Actions.setAlarm);
            producer.send(Main.alarmQueue, msg);
        } catch (JMSException ex) {
            Logger.getLogger(Planner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void removeEvent(int id) {
        Dogadjaji dog = em.find(Dogadjaji.class, id);
        em.getTransaction().begin();
        if(dog!=null) em.remove(dog);
        em.getTransaction().commit();
    }

    void modifyEvent(int id, Date dateAndTime, Date reminderDate, String name, String destination) {       
        Dogadjaji dog = em.find(Dogadjaji.class, id);
        if(dog==null) return;
        em.getTransaction().begin();
        if(dog.getDatumvreme().getTime()!=dateAndTime.getTime())
            dog.setDatumvreme(dateAndTime);
        if(dog.getPodsetnik().getTime()!=reminderDate.getTime())
            dog.setPodsetnik(reminderDate);
        if(!dog.getNazivdogadjaja().equals(name))
            dog.setNazivdogadjaja(name);
        dog.setDestinacija(destination);
        em.getTransaction().commit();
    }

    void timeFromAToB(String a, String b) {
        try {
            TextMessage msg = context.createTextMessage();            
            String timeDist=distCalc.timeToGetFromAToB(a,b);
            msg.setText(timeDist);
            msg.setStringProperty("time", timeDist);
            msg.setIntProperty("idR", ConstsAndPaths.ID_USER);
            msg.setIntProperty("idS", ConstsAndPaths.ID_PLANNER);
            producer.send(Main.responseQueue, msg);
        } catch (JMSException ex) {
            Logger.getLogger(Planner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void timeFromMyPosition(String to) {
        try {
            TextMessage msg = context.createTextMessage();
            String timeDist=distCalc.timeToGetFromHereTo(to);
            msg.setText(timeDist);
            msg.setStringProperty("time", timeDist);
            msg.setIntProperty("idR", ConstsAndPaths.ID_USER);
            msg.setIntProperty("idS", ConstsAndPaths.ID_PLANNER);
            producer.send(Main.responseQueue, msg);
        } catch (JMSException ex) {
            Logger.getLogger(Planner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
