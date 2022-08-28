package com.barth.trans.entity;

import com.barth.trans.util.XenUtil.XenAgenceState;
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author barth
 */
@Entity
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @NotNull(message = "Le nom ne peut pas être vide !")
    private String nom;
    @Enumerated(value = EnumType.STRING)
    private XenAgenceState etat;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date edate; 
    private float solde;   

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinTable(
            joinColumns = @JoinColumn(name = "EntrepriseId"),
            inverseJoinColumns = @JoinColumn(name = "FactureId")
    )
    private List<Facture> factures = new ArrayList();

    public Entreprise() {
    }
    

    public Entreprise(@NotNull(message = "Le nom ne peut pas être vide !") String nom, XenAgenceState etat, Date edate,
			float solde, List<Facture> factures) {
		super();
		this.nom = nom;
		this.etat = etat;
		this.edate = edate;
		this.solde = solde;
		this.factures = factures;
	}


	public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (!(object instanceof Entreprise)) {
            return false;
        }
        Entreprise other = (Entreprise) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.barth.trans.entity.Entreprise[ id=" + id + " ]";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public XenAgenceState getEtat() {
        return etat;
    }

    public void setEtat(XenAgenceState etat) {
        this.etat = etat;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    } 

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    } 

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }  

}
