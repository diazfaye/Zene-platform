package com.barth.trans.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author barth
 */
@Entity
public class XFrais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float xm1; // borne 1 
    private float xm2; // borne 2
    private float taux; // taux 
    // eg : 0 - 5000 -> 425 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof XFrais)) {
            return false;
        }
        XFrais other = (XFrais) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "com.barth.trans.entity.XFrais[ id=" + id + " ]";
    }

    public XFrais() {
    }

    /**
     * 
     * @param id identifiant
     * @param xm1 le montant minimal
     * @param xm2 le montant maximal
     * @param taux le taux pour le montant appartenant dans l'intervalle xm1 - xm2
     */
    public XFrais(int id, float xm1, float xm2, float taux) {
    	super();
        this.id = id;
        this.xm1 = xm1;
        this.xm2 = xm2;
        this.taux = taux;
    }

    public float getXm1() {
        return xm1;
    }

    public void setXm1(float xm1) {
        this.xm1 = xm1;
    }

    public float getXm2() {
        return xm2;
    }

    public void setXm2(float xm2) {
        this.xm2 = xm2;
    }

    public float getTaux() {
        return taux;
    }

    public void setTaux(float taux) {
        this.taux = taux;
    }

}
