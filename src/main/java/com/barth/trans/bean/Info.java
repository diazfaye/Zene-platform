package com.barth.trans.bean;

import com.barth.trans.ejb.Xen;
import com.barth.trans.entity.Facture;
import com.barth.trans.entity.XAgence;
import com.barth.trans.entity.XGain;
import com.barth.trans.entity.XPersonne;
import com.barth.trans.entity.XTransaction;
import com.barth.trans.util.XenUtil;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author barth
 */
@Named(value = "info")
@ViewScoped
public class Info implements Serializable {

    @EJB
    private Xen xen;
    private String password;
    private long numerofacture;
    private Facture facture;
    private String code;
    private XTransaction trans;

    @Inject
    private DefaultConfig dc;

    public Info() {
    }

    public void chercherTransaction() {
        try {
            trans = xen.retraitsDisponibles().stream().filter(f -> f.getCode().equals(code)).findFirst().get();
        } catch (Exception e) {
            addMessage("Ce code n'est pas valide !", code);
        }
    }

    public void effectuerRetrait(String agence) {
        try {
            XTransaction rx = new XTransaction();
            rx.setAgence(agence);
            rx.setTdate(new Date());
            rx.setCode(code);
            rx.setDestinataire(trans.getDestinataire());
            rx.setExpediteur(trans.getExpediteur());
            rx.setEtat(XenUtil.XenTransactionState.EFFECTUEE);
            rx.setType(XenUtil.XenTransactionType.RETRAIT);
            rx.setXcondition(XenUtil.XenCondition.CODE_ET_PIECE);
            XGain gain = xen.gains();
            rx.setAgencegain(trans.getFrais() * gain.getGainDeRetrait() / 100);
            rx.setMontant(trans.getMontant());
            XAgence ag = xen.agence(agence);
            XPersonne gr = ag.getPersonne();
            if (ag.getSolde() < trans.getMontant()) {
                addMessage("Votre solde n'est pas suffisant de faire ce retrait !", agence);
            } else {
                ag.getTransactions().add(rx);
                ag.setSolde(ag.getSolde() - trans.getMontant());
                gr.setAgence(ag);
                xen.updateTransactionState(trans.getId(), XenUtil.XenTransactionState.EFFECTUEE);
                xen.updateGerant(gr);
                addMessage("Retrait effectué !", agence);
            }
        } catch (Exception e) {
            addMessage("Echec de l'opération !", e.getMessage());
        }

    }

    public void appliquer(String personne) {
        XPersonne p = xen.gerant(personne);
        p.setMot_de_passe(dc.getPasswordHash().generate(password.toCharArray()));
        xen.updateGerant(p);
        addMessage("Le mot de passe es bien modifié !", "");
    }

    public void chercher(String entreprise) {
        facture = xen.facture(numerofacture, entreprise);
    }

// nom utilisable pour le moment
    public void payerFacture() {
        xen.updateFactureState(numerofacture, XenUtil.FactureState.PAYEE);
        XGain gains = xen.gains();
        addMessage("Opération réussi !", "");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Xen getXen() {
        return xen;
    }

    public void setXen(Xen xen) {
        this.xen = xen;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getNumerofacture() {
        return numerofacture;
    }

    public void setNumerofacture(long numerofacture) {
        this.numerofacture = numerofacture;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public XTransaction getTrans() {
        return trans;
    }

    public void setTrans(XTransaction trans) {
        this.trans = trans;
    }

}
