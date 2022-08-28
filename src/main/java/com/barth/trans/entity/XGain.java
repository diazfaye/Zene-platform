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
public class XGain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private float xenGain = 55f;
    private float gainDeTransfert = 20f;
    private float gainDeRetrait = 25f;
    private float eauGain = 20f;
    private float electgain = 20f;
    private float xengaineau = 1f;
    private float xengainelec = 1f;
    

    public XGain() {
		super();
		// TODO Auto-generated constructor stub
	}
    

	public XGain(float xenGain, float gainDeTransfert, float gainDeRetrait, float eauGain, float electgain,
			float xengaineau, float xengainelec) {
		super();
		this.xenGain = xenGain;
		this.gainDeTransfert = gainDeTransfert;
		this.gainDeRetrait = gainDeRetrait;
		this.eauGain = eauGain;
		this.electgain = electgain;
		this.xengaineau = xengaineau;
		this.xengainelec = xengainelec;
	}


	public Integer getId() {
        return id;
    }

    public void setId(int id) {
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
        if (!(object instanceof XGain)) {
            return false;
        }
        XGain other = (XGain) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.barth.trans.entity.XGain[ id=" + id + " ]";
    }

    public float getXenGain() {
        return xenGain;
    }

    public void setXenGain(float xenGain) {
        this.xenGain = xenGain;
    }

    public float getGainDeTransfert() {
        return gainDeTransfert;
    }

    public void setGainDeTransfert(float gainDeTransfert) {
        this.gainDeTransfert = gainDeTransfert;
    }

    public float getGainDeRetrait() {
        return gainDeRetrait;
    }

    public void setGainDeRetrait(float gainDeRetrait) {
        this.gainDeRetrait = gainDeRetrait;
    }

    public float getEauGain() {
        return eauGain;
    }

    public void setEauGain(float eauGain) {
        this.eauGain = eauGain;
    }

    public float getElectgain() {
        return electgain;
    }

    public void setElectgain(float electgain) {
        this.electgain = electgain;
    }

    public float getXengaineau() {
        return xengaineau;
    }

    public void setXengaineau(float xengaineau) {
        this.xengaineau = xengaineau;
    }

    public float getXengainelec() {
        return xengainelec;
    }

    public void setXengainelec(float xengainelec) {
        this.xengainelec = xengainelec;
    }

}
