/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nikola
 */
@Entity
@Table(name = "pustanepesme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pustanepesme.findAll", query = "SELECT p FROM Pustanepesme p")
    , @NamedQuery(name = "Pustanepesme.findByIdpustanepesme", query = "SELECT p FROM Pustanepesme p WHERE p.idpustanepesme = :idpustanepesme")
    , @NamedQuery(name = "Pustanepesme.findByNazivpesme", query = "SELECT p FROM Pustanepesme p WHERE p.nazivpesme = :nazivpesme")})
public class Pustanepesme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpustanepesme")
    private Integer idpustanepesme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nazivpesme")
    private String nazivpesme;
    @JoinColumn(name = "idkorisnik", referencedColumnName = "idkorisnik")
    @ManyToOne(optional = false)
    private Korisnik idkorisnik;

    public Pustanepesme() {
    }

    public Pustanepesme(Integer idpustanepesme) {
        this.idpustanepesme = idpustanepesme;
    }

    public Pustanepesme(Integer idpustanepesme, String nazivpesme) {
        this.idpustanepesme = idpustanepesme;
        this.nazivpesme = nazivpesme;
    }

    public Integer getIdpustanepesme() {
        return idpustanepesme;
    }

    public void setIdpustanepesme(Integer idpustanepesme) {
        this.idpustanepesme = idpustanepesme;
    }

    public String getNazivpesme() {
        return nazivpesme;
    }

    public void setNazivpesme(String nazivpesme) {
        this.nazivpesme = nazivpesme;
    }

    public Korisnik getIdkorisnik() {
        return idkorisnik;
    }

    public void setIdkorisnik(Korisnik idkorisnik) {
        this.idkorisnik = idkorisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpustanepesme != null ? idpustanepesme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pustanepesme)) {
            return false;
        }
        Pustanepesme other = (Pustanepesme) object;
        if ((this.idpustanepesme == null && other.idpustanepesme != null) || (this.idpustanepesme != null && !this.idpustanepesme.equals(other.idpustanepesme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pustanepesme[ idpustanepesme=" + idpustanepesme + " ]";
    }
    
}
