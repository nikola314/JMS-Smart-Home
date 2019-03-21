/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nikola
 */
@Entity
@Table(name = "dogadjaji")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dogadjaji.findAll", query = "SELECT d FROM Dogadjaji d")
    , @NamedQuery(name = "Dogadjaji.findByIddogadjaji", query = "SELECT d FROM Dogadjaji d WHERE d.iddogadjaji = :iddogadjaji")
    , @NamedQuery(name = "Dogadjaji.findByDatumvreme", query = "SELECT d FROM Dogadjaji d WHERE d.datumvreme = :datumvreme")
    , @NamedQuery(name = "Dogadjaji.findByPodsetnik", query = "SELECT d FROM Dogadjaji d WHERE d.podsetnik = :podsetnik")
    , @NamedQuery(name = "Dogadjaji.findByNazivdogadjaja", query = "SELECT d FROM Dogadjaji d WHERE d.nazivdogadjaja = :nazivdogadjaja")
    , @NamedQuery(name = "Dogadjaji.findByDestinacija", query = "SELECT d FROM Dogadjaji d WHERE d.destinacija = :destinacija")})
public class Dogadjaji implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddogadjaji")
    private Integer iddogadjaji;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumvreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumvreme;
    @Column(name = "podsetnik")
    @Temporal(TemporalType.TIMESTAMP)
    private Date podsetnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nazivdogadjaja")
    private String nazivdogadjaja;
    @Size(max = 45)
    @Column(name = "destinacija")
    private String destinacija;

    public Dogadjaji() {
    }

    public Dogadjaji(Integer iddogadjaji) {
        this.iddogadjaji = iddogadjaji;
    }

    public Dogadjaji(Integer iddogadjaji, Date datumvreme, String nazivdogadjaja) {
        this.iddogadjaji = iddogadjaji;
        this.datumvreme = datumvreme;
        this.nazivdogadjaja = nazivdogadjaja;
    }

    public Integer getIddogadjaji() {
        return iddogadjaji;
    }

    public void setIddogadjaji(Integer iddogadjaji) {
        this.iddogadjaji = iddogadjaji;
    }

    public Date getDatumvreme() {
        return datumvreme;
    }

    public void setDatumvreme(Date datumvreme) {
        this.datumvreme = datumvreme;
    }

    public Date getPodsetnik() {
        return podsetnik;
    }

    public void setPodsetnik(Date podsetnik) {
        this.podsetnik = podsetnik;
    }

    public String getNazivdogadjaja() {
        return nazivdogadjaja;
    }

    public void setNazivdogadjaja(String nazivdogadjaja) {
        this.nazivdogadjaja = nazivdogadjaja;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(String destinacija) {
        this.destinacija = destinacija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddogadjaji != null ? iddogadjaji.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dogadjaji)) {
            return false;
        }
        Dogadjaji other = (Dogadjaji) object;
        if ((this.iddogadjaji == null && other.iddogadjaji != null) || (this.iddogadjaji != null && !this.iddogadjaji.equals(other.iddogadjaji))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Dogadjaji[ iddogadjaji=" + iddogadjaji + " ]";
    }
    
}
