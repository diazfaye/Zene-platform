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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author barth
 */
@Entity
public class XAgence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @NotNull(message = "Le pays ne peut pas être vide !")
    private String pays;
    @NotNull(message = "La ville ne peut pas être vide !")
    private String ville;
    private float solde;
    @Enumerated(value = EnumType.STRING)
    private XenAgenceState etat;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ouverture;

    @OneToOne(mappedBy = "agence", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private XPersonne personne;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinTable(
            joinColumns = @JoinColumn(name = "AgenceId"),
            inverseJoinColumns = @JoinColumn(name = "TransactionId"))
    private List<XTransaction> transactions = new ArrayList(); 

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
        if (!(object instanceof XAgence)) {
            return false;
        }
        XAgence other = (XAgence) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.barth.trans.entity.XAgence[ id=" + id + " ]";
    }

    public XAgence() {
    }
    

    public XAgence(@NotNull(message = "Le pays ne peut pas être vide !") String pays,
			@NotNull(message = "La ville ne peut pas être vide !") String ville, float solde, XenAgenceState etat,
			Date ouverture, XPersonne personne, List<XTransaction> transactions) {
		super();
		this.pays = pays;
		this.ville = ville;
		this.solde = solde;
		this.etat = etat;
		this.ouverture = ouverture;
		this.personne = personne;
		this.transactions = transactions;
	}

	public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public XenAgenceState getEtat() {
        return etat;
    }

    public void setEtat(XenAgenceState etat) {
        this.etat = etat;
    }

    public Date getOuverture() {
        return ouverture;
    }

    public void setOuverture(Date ouverture) {
        this.ouverture = ouverture;
    }

    public XPersonne getPersonne() {
        return personne;
    }

    public void setPersonne(XPersonne personne) {
        this.personne = personne;
    }

    public List<XTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<XTransaction> transactions) {
        this.transactions = transactions;
    } 

}
