package com.barth.trans.entity;

import com.barth.trans.util.XenUtil.FactureState;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author barth
 */
@Entity
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entreprise;
    private String client;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date debut;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fin;
    @Enumerated(value = EnumType.STRING)
    private FactureState etat;
    private float amende;
    private float xengain;
    private float montant;
    private float agenceGain;
    private String agence;

    public Facture() {
    }
    

    public Facture(String entreprise, String client, Date debut, Date fin, FactureState etat, float amende,
			float xengain, float montant, float agenceGain, String agence) {
		super();
		this.entreprise = entreprise;
		this.client = client;
		this.debut = debut;
		this.fin = fin;
		this.etat = etat;
		this.amende = amende;
		this.xengain = xengain;
		this.montant = montant;
		this.agenceGain = agenceGain;
		this.agence = agence;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facture)) {
            return false;
        }
        Facture other = (Facture) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.barth.trans.entity.Facture[ id=" + id + " ]";
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public FactureState getEtat() {
        return etat;
    }

    public void setEtat(FactureState etat) {
        this.etat = etat;
    }

    public float getAmende() {
        return amende;
    }

    public void setAmende(float amende) {
        this.amende = amende;
    }

    public float getXengain() {
        return xengain;
    }

    public void setXengain(float xengain) {
        this.xengain = xengain;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public float getAgenceGain() {
        return agenceGain;
    }

    public void setAgenceGain(float agenceGain) {
        this.agenceGain = agenceGain;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }    

}
