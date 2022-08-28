package com.barth.trans.entity;

import com.barth.trans.util.XenUtil.XenCondition;
import com.barth.trans.util.XenUtil.XenTransactionState;
import com.barth.trans.util.XenUtil.XenTransactionType;
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
public class XTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String agence;
    private String expediteur;
    private String destinataire;
    private float montant;
    private float frais;
    private float Xengain;
    private float agencegain;
    private String code;
    @Enumerated(value=EnumType.STRING)
    private XenTransactionType type;
    @Enumerated(value=EnumType.STRING)
    private XenCondition xcondition; 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date tdate;
    @Enumerated(value=EnumType.STRING)
    private XenTransactionState etat;

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
        if (!(object instanceof XTransaction)) {
            return false;
        }
        XTransaction other = (XTransaction) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.barth.trans.entity.XTransaction[ id=" + id + " ]";
    }

    public XTransaction() {
    }

    public XTransaction(Long id, String agence, String expediteur, String destinataire, float montant, float frais, float Xengain, float agencegain, String code, XenTransactionType type, XenCondition xcondition, Date tdate, XenTransactionState etat) {
        this.id = id;
        this.agence = agence;
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.montant = montant;
        this.frais = frais;
        this.Xengain = Xengain;
        this.agencegain = agencegain;
        this.code = code;
        this.type = type;
        this.xcondition = xcondition;
        this.tdate = tdate;
        this.etat = etat;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public float getFrais() {
        return frais;
    }

    public void setFrais(float frais) {
        this.frais = frais;
    }

    public float getXengain() {
        return Xengain;
    }

    public void setXengain(float Xengain) {
        this.Xengain = Xengain;
    }

    public float getAgencegain() {
        return agencegain;
    }

    public void setAgencegain(float agencegain) {
        this.agencegain = agencegain;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public XenTransactionType getType() {
        return type;
    }

    public void setType(XenTransactionType type) {
        this.type = type;
    }

    public XenCondition getXcondition() {
        return xcondition;
    }

    public void setXcondition(XenCondition xcondition) {
        this.xcondition = xcondition;
    }

    public Date getTdate() {
        return tdate;
    }

    public void setTdate(Date tdate) {
        this.tdate = tdate;
    }

    public XenTransactionState getEtat() {
        return etat;
    }

    public void setEtat(XenTransactionState etat) {
        this.etat = etat;
    } 

}
