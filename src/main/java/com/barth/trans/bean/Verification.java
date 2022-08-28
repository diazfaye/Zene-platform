package com.barth.trans.bean;

import com.barth.trans.ejb.Xen;
import com.barth.trans.entity.XFrais;
import com.barth.trans.entity.XGain;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author barth
 */
@Named(value = "verif")
@RequestScoped
public class Verification {

    private float montant;
    private XGain gain;
    private XFrais frais;
    @Inject
    private Xen xen;
    private float cout;
    private float mongain;
    private float mtfinal;

    public void verify() {
        try {
            gain = xen.gains();
            frais = xen.frais(montant);

            cout = frais.getTaux();
            mongain = (cout * gain.getGainDeTransfert()) / 100;
            mtfinal = montant - cout;
            addMessage("Opération effectuée", "");
        } catch (Exception ex) {
            addMessage("Echec de l'opération", "");
            Logger.getLogger(Verification.class.getName()).log(Level.INFO, "Probl\u00e8me lors de la verification d''un montant{0}", ex.getMessage());
        }
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Verification() {
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public XFrais getFrais() {
        return frais;
    }

    public void setFrais(XFrais frais) {
        this.frais = frais;
    }

    public float getCout() {
        return cout;
    }

    public void setCout(float cout) {
        this.cout = cout;
    }

    public float getMongain() {
        return mongain;
    }

    public void setMongain(float mongain) {
        this.mongain = mongain;
    }

    public float getMtfinal() {
        return mtfinal;
    }

    public void setMtfinal(float mtfinal) {
        this.mtfinal = mtfinal;
    }

    public XGain getGain() {
        return gain;
    }

    public void setGain(XGain gain) {
        this.gain = gain;
    }

}
