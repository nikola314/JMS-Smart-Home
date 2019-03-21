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
import entities.Pustanepesme;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;

public class UredjajZaReprodukciju {

    EntityManager em;

    public UredjajZaReprodukciju() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UredjajZaReprodukcijuZvukaPU");
        em = emf.createEntityManager();
    }

    String getAllSongsForUser(String name) {
        StringBuilder sb = new StringBuilder();
        Query query = em.createQuery("Select e from Pustanepesme e");
        List<Pustanepesme> lis = (List<Pustanepesme>) query.getResultList();
        for (Pustanepesme p : lis) {
            if (name.equals(p.getIdkorisnik().getIme())) {
                sb.append(p.getNazivpesme()).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    private List<Process> list = new LinkedList<Process>();

    void playTheSong(String name) {
        System.out.println("PLAYING: " + name);
        ProcessBuilder pb = new ProcessBuilder(ConstsAndPaths.JAVA_PATH, "-jar", ConstsAndPaths.YT_OPEN_PATH, name);
        try {
            Process p = pb.start();
            list.add(p);
            p.waitFor(2, TimeUnit.SECONDS);
        } catch (IOException ex) {
            Logger.getLogger(UredjajZaReprodukciju.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(UredjajZaReprodukciju.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
