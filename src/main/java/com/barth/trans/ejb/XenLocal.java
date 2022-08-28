package com.barth.trans.ejb;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import com.barth.trans.entity.Entreprise;
import com.barth.trans.entity.Facture;
import com.barth.trans.entity.XAgence;
import com.barth.trans.entity.XFrais;
import com.barth.trans.entity.XGain;
import com.barth.trans.entity.XPersonne;
import com.barth.trans.entity.XTransaction;
import com.barth.trans.util.XenUtil.FactureState;
import com.barth.trans.util.XenUtil.XenTransactionState;



/**
 *
 * @author barth
 */
@Local
public interface XenLocal {

    /**
     *
     * @return une liste de tous les transfrts et les retraits
     */
    public List<XTransaction> transfertsETRetraits();

    /**
     *
     * @return une liste de toutes les recharges
     */
    public List<XTransaction> recharges();

    /**
     *
     * @return une liste des tous les transferts
     */
    public List<XTransaction> transferts();

    /**
     *
     * @return une liste de tous les retraits
     */
    public List<XTransaction> retraits();

    /**
     *
     * @param agence identifiant de l'agence
     * @return une liste de tous les retraits et transferts de l'agence
     */
    public List<XTransaction> transfertsETRetraits(String agence);

    /**
     *
     * @param agence identifiant de l'agence
     * @return une liste de tous les transferts de l'agence
     */
    public List<XTransaction> transferts(String agence);

    /**
     *
     * @param agence identifiant de l'agence
     * @return une liste de tous les retraits de l'agence
     */
    public List<XTransaction> retraits(String agence);

    /**
     *
     * @return une liste de toutes les agences
     */
    public List<XAgence> agences();

    /**
     * *
     *
     * @param agenceId identifiant de l'agence
     * @return l'agence avec l'id donné
     */
    public XAgence agence(String agenceId);

    /**
     *
     * @return une liste de tous les gerants de la platforme
     */
    public List<XPersonne> gerants();

    /**
     *
     * @param personId identifiant d'un gérant
     * @return le gerant de l'id fourni
     */
    public XPersonne gerant(String personId);

    /**
     * @see XGain
     * @return Les gains des transactions
     */
    public XGain gains();

    /**
     * @see XFrais
     * @return une liste des frais sur les transferts d'argents
     */
    public List<XFrais> frais();

    /**
     * @see XGain
     * @param gain Un objet gain à mettre à jour
     */
    public void updateGains(XGain gain);

    /**
     * @see XFrais
     * @param frais une liste des frais à mettre à jour
     */
    public void updateFrais(List<XFrais> frais);

    /**
     * @see XPersonne
     * @param gerant Mettre à jour les informations d'un gerant
     */
    public void updateGerant(XPersonne gerant);

    /**
     * @see XPersonne
     * @param p Ajouter une personne ou gerant dans la platforme
     */
    public void addPersonne(XPersonne p);

    /**
     * @see XPersonne
     * @see Entreprise
     * @param p supprimer la personne/gerant p ainsi que les entreprise associée
     * avec
     */
    public void removePersonne(XPersonne p);

    /**
     *
     * @return le nombre des transferts effectuées dans le dernier jour
     */
    public long nbTransfertsDuJour();

    /**
     *
     * @return le nombre des retrais effectués dans le dernier jour
     */
    public long nbRetraitsDuJour();

    /**
     *
     * @param agence identifiant de l'agence
     * @return le nombre de transferts du jour effectués dans cette agence
     */
    public long nbTransfertsDuJour(String agence);

    /**
     *
     * @param agence identifiant de l'agence
     * @return le nombre de retraits du jour effectués dans cette agence
     */
    public long nbRetraitsDuJour(String agence);

    /**
     *
     * @return le nombre de toutes les recharges effectuées
     */
    public long nbRechargesDuJour();

    /**
     *
     * @return un mot de passe de taille 10 générée aléatoirement
     */
    public String password();

    /**
     *
     * @return Génération d'un code de 10 chiffres aléatoirement
     */
    public String code();

    /**
     *
     * @return Un identifiant pour une personne en fonction des personnes/gerant
     * existantes
     */
    public String personneId();

    /**
     *
     * @return Un identifiant pour une agence en fonction des agences existantes
     */
    public String agenceId();

    /**
     *
     * @return le nombre d'agences actives
     */
    public long nbAgencesActives();

    /**
     *
     * @return le nombre d'agences inactives
     */
    public long nbAgencesInactives();

    /**
     *
     * @param agence identifiant de l'agence à rendre active
     */
    public void activerAgence(String agence);

    /**
     *
     * @param agence identifiant de l'agence à rendre inactive
     */
    public void suspendreAgence(String agence);

    /**
     *
     * @param agence identifiant de l'agence à supprimer
     */
    public void supprimerAgence(String agence);

    /**
     *
     * @param agence identifiant de l'agence
     * @return la somme de toues les commissions de l'agence sur les transferts
     * et les retraits
     */
    public float commissions(String agence);

    /**
     *
     * @param agence identifiant de l'agence
     * @return la somme de toutes les commissions de l'agence sur les retraits
     */
    public float commissionsRetraits(String agence);

    /**
     *
     * @param agence identifiant de l'agence
     * @return la somme de toutes les commissions de l'agence sur les transferts
     */
    public float commissionsTransferts(String agence);

    /**
     *
     * @return une liste de tous les retraits disponibles
     */
    public List<XTransaction> retraitsDisponibles();

    /**
     *
     * @param montant un montant
     * @return les frais concernant ce montant
     */
    public XFrais frais(float montant);

    /**
     *
     * @param password un mot de passe
     * @return le hasher du mot de passe avec l'algorithme SHA-256
     */
    public String hash(String password);

    /**
     *
     * @return une liste de toutes les entreprises enregistrées
     */
    public List<Entreprise> entreprises();

    /**
     *
     * @param entreprise identifiant de l'entreprise
     * @return l'entreprise concernée avec l'identifiant donné
     */
    public Entreprise entreprise(String entreprise);

    /**
     *
     * @return le nombre des entreprises actives
     */
    public long nbEntrepriseActives();

    /**
     *
     * @return le nobre des entreprises inactives
     */
    public long nbEntrepriseInactives();

    /**
     *
     * @param entreprise identifiant de lentreprise
     * @return une liste des factures de l'entreprise
     */
    public List<Facture> factures(String entreprise);

    /**
     *
     * @param numero le numero de la facture
     * @param entreprise identifiant de l'entreprise
     * @return la facture concernée
     */
    public Facture facture(long numero, String entreprise);

    /**
     * @see Facture
     * @param fact ajouter une facture dans la platforme
     */
    public void addFacture(Facture fact);

    /**
     *
     * @param num numero de la facture
     * @param state etat de la facture
     * @see FactureState
     */
    public void updateFactureState(long num, FactureState state);

    /**
     *
     * @param id numero/idntifiant de la transaction
     * @param state etat de la transaction
     * @see XenTransactionState
     */
    public void updateTransactionState(long id, XenTransactionState state);

    /**
     *
     * @param entreprise identifiant de l'entreprise
     * @return la somme des gains de l'entreprise sur les factures payées
     */
    public float commissionFacture(String entreprise);

    /**
     *
     * @return un identifiant pour une entreprise en fonction des entreprises
     * existantes
     */
    public String entrepriseId();

    /**
     *
     * @param id identifiant de l'entreprise à suspendre
     */
    public void suspendreEntreprise(String id);

    /**
     *
     * @param id identifiant de l'entreprise à rendre active
     */
    public void activeEntreprise(String id);

    /**
     *
     * @param entreprise identifiant de l'entreprise à supprimer
     */
    public void supprimerEntreprise(String entreprise);

    /**
     *
     * @param d1 une prémiere date
     * @param d2 une seconde date
     * @return true si les deux dates sont d'aujourd'hui
     */
    public boolean areToDay(Date d1, Date d2);

    /**
     *
     * @return la somme des gains de l'agence principale sur les transactions
     */
    public float zeneGain();

    /**
     *
     * @param id identifiant de la personne
     * @return la personne correspondante
     */
    public Optional<XPersonne> personne(String id);

    /**
     *
     * @return tous les administrateurs
     */
    public List<XPersonne> admins();

    /**
     *
     * @return tous les gerants des agences
     */
    public List<XPersonne> agenciers();

    /**
     *
     * @param ent Une entreprise
     */
    public void addEntreprise(Entreprise ent);
    
    public void addAgence(XAgence agence);

    /**
     *
     * @param entreprise identifiant de l'entreprise
     * @param agence identifiant de l'agence
     * @return la somme des gains pour cette agence
     */
    public float commissionAgenceFacture(String entreprise, String agence);

    /**
     *
     * @param agence identifiant de l'agence
     * @return toutes les factures payées par cette agence
     */
    public List<Facture> agencefacture(String agence);
    /**
     * 
     * @param frais le frais à ajouter
     */
    public void addFrais(XFrais frais);    
}
