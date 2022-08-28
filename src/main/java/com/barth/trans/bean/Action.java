package com.barth.trans.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.barth.trans.ejb.Xen;
import com.barth.trans.entity.Entreprise;
import com.barth.trans.entity.XAgence;
import com.barth.trans.entity.XFrais;
import com.barth.trans.entity.XGain;
import com.barth.trans.entity.XPersonne;
import com.barth.trans.entity.XTransaction;
import com.barth.trans.util.XenUtil;
import com.barth.trans.util.XenUtil.XenAgenceState;
import com.barth.trans.util.XenUtil.XenPersonFunction;

/*
 * @auterur barth
 * 
 * */
@Named(value = "action")
@ViewScoped
public class Action implements Serializable {

    @EJB
    private Xen xen;

    private XAgence agence = new XAgence();
    private XPersonne respo = new XPersonne();
    private XAgence selectedAgence = new XAgence();
    private Entreprise selectedEntreprise = new Entreprise();
    private boolean active, inactive;
    private boolean eactive, einactive;
    private float rechargeMontant;
    private XTransaction transaction = new XTransaction();
    private List<XFrais> allFrais = new ArrayList();
    private XGain gain;
    private String agenceId;
    private float montant;
    private String nomExpediteur;
    private String prenomExpediteur;
    private String nomDestinataire;
    private String prenomDestinataire;
    private String rcode;
    private Entreprise entreprise = new Entreprise();
    private String personneId;
    private String entrepriseid;

    @Inject
    private DefaultConfig dconf;

    @PostConstruct
    public void init() {
        agence.setEtat(XenUtil.XenAgenceState.INACTIVE);
        agence.setId(xen.agenceId());
        agence.setOuverture(new Date());
        respo.setMot_de_passe(xen.password());
        respo.setId(xen.personneId());
        entreprise.setId(xen.entrepriseId());

        allFrais = xen.frais();
        gain = xen.gains();
        transaction.setCode(xen.code());
    }

    public void createAgence() {
        respo.setFonction(XenPersonFunction.GERANT);
        respo.setMot_de_passe(dconf.getPasswordHash().generate(respo.getMot_de_passe().toCharArray()));
        agence.setPersonne(respo);
        respo.setAgence(agence);
        xen.addPersonne(respo);
        xen.addAgence(agence);
        addMessage("L'agence " + agence.getId() + " est bien créée !", "Allez dans le menu Mes agences pour l'activer !");

    }

    public void crediterAgence(String pid1) {
        XPersonne p1 = xen.gerant(pid1);
        XPersonne p2 = xen.agence(agenceId).getPersonne();
        transaction.setTdate(new Date());
        transaction.setMontant(montant);
        p2.getAgence().setSolde(p2.getAgence().getSolde() + montant);
        transaction.setCode(xen.code());
        transaction.setExpediteur(p1.getId());
        transaction.setDestinataire(p2.getAgence().getId());
        transaction.setType(XenUtil.XenTransactionType.CREDITATION);
        transaction.setXcondition(XenUtil.XenCondition.CODE);
        transaction.setEtat(XenUtil.XenTransactionState.ATTENTE);
        p2.getAgence().getTransactions().add(transaction);
        xen.updateGerant(p1);
        xen.updateGerant(p2);
        addMessage("Opération effectuée !", "");
    }

    public void updateAgenceState() {
        XAgence xag = xen.agence(agenceId);
        if (xag.getEtat().equals(XenAgenceState.ACTIVE)) {
            xag.setEtat(XenAgenceState.INACTIVE);
            xen.suspendreAgence(xag.getId());
            addMessage("L'agence est suspendue !", "AGENCE_ACTIVE -> AGENCE_INCTIVE");
        } else {
            xag.setEtat(XenAgenceState.ACTIVE);
            xen.activerAgence(xag.getId());
            addMessage("L'agence est active !", "AGENCE_INACTIVE -> AGENCE_ACTIVE");
        }
    }

    public void updateEntrepriseState() {
        Entreprise ent = xen.entreprise(entrepriseid);
        if (ent.getEtat().equals(XenAgenceState.ACTIVE)) {
            xen.suspendreEntreprise(ent.getId());
            addMessage("L'entreprise est suspendue", "ACTIVE -> INCTIVE");
        } else {
            xen.activeEntreprise(ent.getId());
            addMessage("L'entreprise est active", "INACTIVE -> ACTIVE");
        }
    }

    public void supprimerAgence(String agenceId) {
        try {
            xen.supprimerAgence(agenceId);
            addMessage("L'agence est bien supprimée !", "");
        } catch (Exception e) {
            addMessage("Echec de la suppression !", "");
        }
    }

    public void supprimerAdmin(String id) {
        try {
            xen.removePersonne(xen.gerant(id));
            addMessage("L'admin est bien supprimée !", "");
        } catch (Exception e) {
            addMessage("Echec de la suppression !", "");

        }
    }

    public void supprimerEntreprise(String entreriseId) {
        try {
            xen.supprimerEntreprise(entreriseId);
            addMessage("L'entreprise est bien supprimée !", "");
        } catch (Exception e) {
            addMessage("Echec de la suppression !", "");
        }
    }

    public void addAdmin() {
        respo.setFonction(XenPersonFunction.ADMIN);
        respo.setMot_de_passe(dconf.getPasswordHash().generate(respo.getMot_de_passe().toCharArray()));
        try {
            xen.addPersonne(respo);
            addMessage("Opération réussi ! ", "");
        } catch (Exception e) {
            addMessage("Echec de l'opération !", "");
        }
    }

    public void transferer(String agenceId) {
        transaction.setTdate(new Date());
        transaction.setEtat(XenUtil.XenTransactionState.ATTENTE);
        transaction.setType(XenUtil.XenTransactionType.TRANSFERT);
        transaction.setXcondition(XenUtil.XenCondition.CODE_ET_PIECE);
        transaction.setExpediteur(nomExpediteur +"  "+ prenomExpediteur);
        transaction.setDestinataire(nomDestinataire +"  "+ prenomDestinataire);
        transaction.setAgence(agenceId);
        XFrais xfrais = xen.frais(montant);
        XGain conf = xen.gains();
        float g = xfrais.getTaux();
        transaction.setMontant(montant - g);
        transaction.setAgencegain((g * conf.getGainDeTransfert()) / 100);
        transaction.setXengain((g * conf.getXenGain()) / 100);
        transaction.setFrais(g);
        XPersonne p = xen.agence(agenceId).getPersonne();
        XAgence ag = p.getAgence();
        if (ag.getSolde() < montant) {
            addMessage("Le solde de l'agence n'est pas suffisant pour effectuer cette transaction !", "");
        } else {
            p.getAgence().getTransactions().add(transaction);
            ag.setSolde(ag.getSolde() - transaction.getMontant());
            p.setAgence(ag);
            xen.updateGerant(p);
            addMessage("Transaction effectuée !", "");
        }

    }

    public void updateGain() {
        xen.updateGains(gain);
        addMessage("Modifications effectuées", "");
    }

    public void updateFrais() {
        xen.updateFrais(allFrais);
        addMessage("Modifications effectuées", "");
    }

    public void processAgence() {
        respo = xen.gerant(personneId);
        agence = respo.getAgence();
    }

    public void creerEntreprise() {
        entreprise.setEtat(XenAgenceState.ACTIVE);
        entreprise.setEdate(new Date());
        xen.addEntreprise(entreprise);
        addMessage("Entreprise ajoutée", entreprise.getNom());
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Action() {
    }

    public XAgence getAgence() {
        return agence;
    }

    public void setAgence(XAgence agence) {
        this.agence = agence;
    }

    public XPersonne getRespo() {
        return respo;
    }

    public void setRespo(XPersonne respo) {
        this.respo = respo;
    }

    public XAgence getSelectedAgence() {
        return selectedAgence;
    }

    public void setSelectedAgence(XAgence selectedAgence) {
        this.selectedAgence = selectedAgence;
    }

    public boolean isActive() {
        if (selectedAgence.getEtat() != null) {
            active = (selectedAgence.getEtat().equals(XenAgenceState.ACTIVE));
        }

        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isInactive() {
        if (selectedAgence.getEtat() != null) {
            inactive = (selectedAgence.getEtat().equals(XenAgenceState.INACTIVE));
        }
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public boolean isEactive() {
        if (selectedEntreprise.getEtat() != null) {
            eactive = (selectedEntreprise.getEtat().equals(XenAgenceState.ACTIVE));
        }
        return eactive;
    }

    public void setEactive(boolean eactive) {
        this.eactive = eactive;
    }

    public boolean isEinactive() {
        if (selectedEntreprise.getEtat() != null) {
            einactive = (selectedEntreprise.getEtat().equals(XenAgenceState.INACTIVE));
        }
        return einactive;
    }

    public void setEinactive(boolean einactive) {
        this.einactive = einactive;
    }

    public XTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(XTransaction transaction) {
        this.transaction = transaction;
    }

    public float getRechargeMontant() {
        return rechargeMontant;
    }

    public void setRechargeMontant(float rechargeMontant) {
        this.rechargeMontant = rechargeMontant;
    }

    public Xen getXen() {
        return xen;
    }

    public void setXen(Xen xen) {
        this.xen = xen;
    }

    public List<XFrais> getAllFrais() {
        return allFrais;
    }

    public void setAllFrais(List<XFrais> allFrais) {
        this.allFrais = allFrais;
    }

    public XGain getGain() {
        return gain;
    }

    public void setGain(XGain gain) {
        this.gain = gain;
    }

    public String getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(String agenceId) {
        this.agenceId = agenceId;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getNomExpediteur() {
        return nomExpediteur;
    }

    public void setNomExpediteur(String nomExpediteur) {
        this.nomExpediteur = nomExpediteur;
    }

    public String getPrenomExpediteur() {
        return prenomExpediteur;
    }

    public void setPrenomExpediteur(String prenomExpediteur) {
        this.prenomExpediteur = prenomExpediteur;
    }

    public String getNomDestinataire() {
        return nomDestinataire;
    }

    public void setNomDestinataire(String nomDestinataire) {
        this.nomDestinataire = nomDestinataire;
    }

    public String getPrenomDestinataire() {
        return prenomDestinataire;
    }

    public void setPrenomDestinataire(String prenomDestinataire) {
        this.prenomDestinataire = prenomDestinataire;
    }

    public String getRcode() {
        return rcode;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Entreprise getSelectedEntreprise() {
        return selectedEntreprise;
    }

    public void setSelectedEntreprise(Entreprise selectedEntreprise) {
        this.selectedEntreprise = selectedEntreprise;
    }

    public String getPersonneId() {
        return personneId;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }

    public String getEntrepriseid() {
        return entrepriseid;
    }

    public void setEntrepriseid(String entrepriseid) {
        this.entrepriseid = entrepriseid;
    }

}
