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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nikola
 */
@Entity
@Table(name = "alarmi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alarmi.findAll", query = "SELECT a FROM Alarmi a")
    , @NamedQuery(name = "Alarmi.findByIdalarm", query = "SELECT a FROM Alarmi a WHERE a.idalarm = :idalarm")
    , @NamedQuery(name = "Alarmi.findByVreme", query = "SELECT a FROM Alarmi a WHERE a.vreme = :vreme")
    , @NamedQuery(name = "Alarmi.findByIntervalper", query = "SELECT a FROM Alarmi a WHERE a.intervalper = :intervalper")})
public class Alarmi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idalarm")
    private Integer idalarm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "intervalper")
    private int intervalper;

    public Alarmi() {
    }

    public Alarmi(Integer idalarm) {
        this.idalarm = idalarm;
    }

    public Alarmi(Integer idalarm, Date vreme, int intervalper) {
        this.idalarm = idalarm;
        this.vreme = vreme;
        this.intervalper = intervalper;
    }

    public Integer getIdalarm() {
        return idalarm;
    }

    public void setIdalarm(Integer idalarm) {
        this.idalarm = idalarm;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public int getIntervalper() {
        return intervalper;
    }

    public void setIntervalper(int intervalper) {
        this.intervalper = intervalper;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalarm != null ? idalarm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alarmi)) {
            return false;
        }
        Alarmi other = (Alarmi) object;
        if ((this.idalarm == null && other.idalarm != null) || (this.idalarm != null && !this.idalarm.equals(other.idalarm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Alarmi[ idalarm=" + idalarm + " ]";
    }
    
}
