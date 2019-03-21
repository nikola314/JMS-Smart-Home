/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmscheduler;

import entities.Alarmi;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;

/**
 *
 * @author Nikola
 */
public class Alarm {
    private String ringtone=ConstsAndPaths.DEFAULT_SONG;
    private EntityManager em;
    private JMSProducer producer;
    private JMSContext context;
    
    public Alarm(){
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("AlarmSchedulerPU");
        em=emf.createEntityManager(); 
    }
    
    public Alarm(JMSProducer producer, JMSContext context){
        this.producer=producer;
        this.context=context;
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("AlarmSchedulerPU");
        em=emf.createEntityManager();        
    }
    
    public void setRingtone(String s){
        ringtone=s;
    }
    
    public void readAlarms(){   
        List<Alarmi> lis = em.createQuery("SELECT e FROM Alarmi e").getResultList(); 
       if(lis!=null && !lis.isEmpty()) System.out.println("Pending alarms: ");
       for(Alarmi a:lis){
            if(addHoursToDate(a.getVreme(),-1).getTime()>System.currentTimeMillis()){
                System.out.println("Time: "+addHoursToDate(a.getVreme(),-1)+" Repetition: "+a.getIntervalper());
                setTimer(addHoursToDate(a.getVreme(),-1),a.getIntervalper());
            }
        }        
    }
    
    private void addToDatabase(Alarmi a){
        em.getTransaction().begin();
        if(a!=null)em.persist(a);
        em.getTransaction().commit();
    }

   private Date addHoursToDate(Date date, int hours){
       Date ret=new Date();
       long time=date.getTime();
       time+=hours*(3600*1000);
       ret.setTime(time);
       return ret;     
   }
    
    private Date dateFromDatabase(Date d){
        return addHoursToDate(d,-1);
    }
    
    private Date dateToDatabase(Date d){
        return addHoursToDate(d,1);
    }
    
   
    public void setAlarm(Date date, long interval){
        Alarmi a=new Alarmi();
        Integer id = 1+(Integer)em.createQuery("select MAX(e.idalarm) from Alarmi e").getSingleResult();
        a.setIdalarm(id);
        a.setIntervalper((int)interval);
        a.setVreme(dateToDatabase(date));
        addToDatabase(a);       
        System.out.println("SETTING TIMER FOR DATE: "+date);
        setTimer(date,interval);
    }
    
    private void setTimer(Date date, long interval){
        Timer timer=new Timer();
        interval*=1000;
        TimerTask task=new TimerTask(){
            @Override
            public void run() {
                TextMessage textMessage = context.createTextMessage();
                try {
                    textMessage.setText(ringtone);
                    textMessage.setIntProperty("idR", ConstsAndPaths.ID_SONGPLAYER);
                    textMessage.setIntProperty("idS", ConstsAndPaths.ID_ALARM);      
                    textMessage.setStringProperty("action", Actions.playSong);
                    producer.send(Main.queue, textMessage);
                } catch (JMSException ex) {  }
            }      
        };
        if(interval>0){
            timer.schedule(task, date, interval);
        } 
        else{
            timer.schedule(task, date);
        }
    }
}
