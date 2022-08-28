package com.barth.trans.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.barth.trans.ejb.Xen;
import com.barth.trans.entity.XFrais;

/**
 *
 * @author silah
 */
@Named(value = "fr")
@RequestScoped
public class Frais {

    private int id;
    private float montant1, montant2, cout;
    @Inject
    private Xen xen;

    public void ajouter() {
        try {
            XFrais xf = new XFrais();
            xf.setId(xen.frais().size() + 1);
            xf.setXm1(montant1);
            xf.setXm2(montant2);
            xf.setTaux(cout);
            xen.addFrais(xf);
            addMessage("Opération effectiuée !", "");
        } catch (Exception e) {
            addMessage("Echec de l'opération !", "");
        }
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Frais() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMontant1() {
        return montant1;
    }

    public void setMontant1(float montant1) {
        this.montant1 = montant1;
    }

    public float getMontant2() {
        return montant2;
    }

    public void setMontant2(float montant2) {
        this.montant2 = montant2;
    }

    public float getCout() {
        return cout;
    }

    public void setCout(float cout) {
        this.cout = cout;
    }

}
