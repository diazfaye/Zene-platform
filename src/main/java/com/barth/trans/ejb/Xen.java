package com.barth.trans.ejb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import com.barth.trans.entity.Entreprise;
import com.barth.trans.entity.Facture;
import com.barth.trans.entity.XAgence;
import com.barth.trans.entity.XFrais;
import com.barth.trans.entity.XGain;
import com.barth.trans.entity.XPersonne;
import com.barth.trans.entity.XTransaction;
import com.barth.trans.util.XenUtil;
import com.barth.trans.util.XenUtil.FactureState;
import com.barth.trans.util.XenUtil.XenAgenceState;
import com.barth.trans.util.XenUtil.XenPersonFunction;
import com.barth.trans.util.XenUtil.XenTransactionType;



/**
 *
 * @author barth
 */
@Stateless
@LocalBean
@DependsOn({"FraisFacade", "PersonFacade", "AgenceFacade", "GainFacade", "TransactionFacade", "EntrepriseFacade", "FactureFacade"})
public class Xen implements XenLocal {

    @EJB
    private PersonFacade pf;
    @EJB
    private TransactionFacade tf;
    @EJB
    private AgenceFacade af;
    @EJB
    private FraisFacade ff;
    @EJB
    private GainFacade gf;
    @EJB
    private EntrepriseFacade ef;
    @EJB
    private FactureFacade fafa;

    private float montant = 0f;

    @Override
    public List<XTransaction> transfertsETRetraits() {
        return tf.findAll().stream()
                .filter(t -> (t.getType()
                .equals(XenUtil.XenTransactionType.RETRAIT)) || (t.getType()
                .equals(XenUtil.XenTransactionType.TRANSFERT)))
                .collect(Collectors.toList());
    }

    @Override
    public List<XTransaction> recharges() {
        return tf.findAll().stream()
                .filter(t -> t.getType().equals(XenTransactionType.CREDITATION))
                .collect(Collectors.toList());
    }

    @Override
    public List<XTransaction> transferts() {
        return tf.findAll().stream()
                .filter(t -> t.getType().equals(XenUtil.XenTransactionType.TRANSFERT))
                .collect(Collectors.toList());
    }

    @Override
    public List<XTransaction> retraits() {
        return tf.findAll().stream()
                .filter(t -> t.getType().equals(XenUtil.XenTransactionType.RETRAIT))
                .collect(Collectors.toList());
    }

    @Override
    public List<XTransaction> transfertsETRetraits(String agence) {
        return transfertsETRetraits().stream()
                .filter(t -> t.getAgence().equals(agence))
                .collect(Collectors.toList());
    }

    @Override
    public List<XTransaction> transferts(String agence) {
        return transferts().stream()
                .filter(t -> t.getAgence().equals(agence))
                .collect(Collectors.toList());
    }

    @Override
    public List<XTransaction> retraits(String agence) {
        return retraits().stream()
                .filter(t -> t.getAgence().equals(agence))
                .collect(Collectors.toList());

    }

    @Override
    public List<XAgence> agences() {
        return af.findAll();
    }

    @Override
    public XAgence agence(String agenceId) {
        return af.find(agenceId);
    }

    @Override
    public List<XPersonne> gerants() {
        return pf.findAll();
    }

    @Override
    public XPersonne gerant(String personId) {
        return pf.find(personId);
    }

    @Override
    public XGain gains() {
        return gf.find(1);
    }

    @Override
    public List<XFrais> frais() {
        return ff.findAll();
    }

    @Override
    public void updateGains(XGain gain) {
        if (gf.findAll().isEmpty()) {
            gf.create(gain);
        } else {
            gf.edit(gain);
        }
    }

    @Override
    public void updateFrais(List<XFrais> frais) {
        frais.stream().forEach(f -> {
            ff.edit(f);
        });
    }

    @Override
    public void updateGerant(XPersonne gerant) {
        pf.edit(gerant);
    }

    @Override
    public void addPersonne(XPersonne p) {
        pf.create(p);
    }

    @Override
    public void removePersonne(XPersonne p) {
        pf.remove(p);
    }

    @Override
    public long nbTransfertsDuJour() {
        return transferts().stream()
                .filter(t -> areToDay(t.getTdate(), new Date()))
                .count();
    }

    @Override
    public long nbRetraitsDuJour() {
        return retraits().stream()
                .filter(t -> areToDay(t.getTdate(), new Date()))
                .count();
    }

    @Override
    public long nbRechargesDuJour() {
        return recharges().stream()
                .filter(t -> areToDay(t.getTdate(), new Date()))
                .count();
    }

    @Override
    public long nbTransfertsDuJour(String agence) {
        return transferts().stream()
                .filter(t -> t.getAgence().equals(agence))
                .filter(t -> areToDay(t.getTdate(), new Date()))
                .count();

    }

    @Override
    public long nbRetraitsDuJour(String agence) {
        return retraits().stream()
                .filter(t -> t.getAgence().equals(agence))
                .filter(t -> areToDay(t.getTdate(), new Date()))
                .count();
    }

    @Override
    public String password() {
        PasswordGenerator gen = new PasswordGenerator();
        // Nombre minimum de charactères miniscules
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);
        // Nombre minimum de caractère majuscules
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);
        // Nombre minimum de chiffres
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(3);
        return gen.generatePassword(10, lowerCaseRule, upperCaseRule, digitRule);
    }

    @Override
    public String code() {
        int size = 10;
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(10);
        return gen.generatePassword(size, digitRule);
    }

    @Override
    public String personneId() {
        long size = pf.count();
        return (size > 10) ? "ZENE-PE-" + size : "ZENE-PE-0" + size;
    }

    @Override
    public String agenceId() {
        long size = af.count();
        return (size > 10) ? "ZENE-AG-" + size : "ZENE-AG-0" + size;
    }

    @Override
    public long nbAgencesActives() {
        return agences().stream()
                .filter(ag -> ag.getEtat().equals(XenUtil.XenAgenceState.ACTIVE))
                .count();
    }

    @Override
    public long nbAgencesInactives() {
        return agences().stream()
                .filter(ag -> ag.getEtat().equals(XenUtil.XenAgenceState.INACTIVE))
                .count();
    }

    @Override
    public void activerAgence(String agence) {
        XAgence ag = agence(agence);
        ag.setEtat(XenUtil.XenAgenceState.ACTIVE);
        af.edit(ag);
    }

    @Override
    public void suspendreAgence(String agence) {
        XAgence ag = agence(agence);
        ag.setEtat(XenUtil.XenAgenceState.INACTIVE);
        af.edit(ag);
    }

    @Override
    public void supprimerAgence(String agence) {
        XAgence ag = agence(agence);
        pf.remove(ag.getPersonne());
    }

    @Override
    public float commissions(String agence) {
        montant = 0f;
        transfertsETRetraits(agence).stream()
                .forEach(t -> {
                    montant += t.getAgencegain();
                });
        return montant;
    }

    @Override
    public float commissionsRetraits(String agence) {
        montant = 0f;
        retraits(agence).stream()
                .forEach(t -> {
                    montant += t.getAgencegain();
                });
        return montant;
    }

    @Override
    public float commissionsTransferts(String agence) {
        montant = 0f;
        transferts(agence).stream()
                .forEach(t -> {
                    montant += t.getAgencegain();
                });
        return montant;
    }

    @Override
    public List<XTransaction> retraitsDisponibles() {
        return tf.findAll().stream()
                .filter(t -> t.getType().equals(XenUtil.XenTransactionType.TRANSFERT))
                .filter(t -> t.getEtat().equals(XenUtil.XenTransactionState.ATTENTE))
                .collect(Collectors.toList());
    }

    @Override
    public XFrais frais(float montant) {
        return ff.findAll().stream()
                .filter(f -> f.getXm1() <= montant)
                .filter(f -> f.getXm2() > montant)
                .findAny().get();
    }

    @Override
    public String hash(String password) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] data = md.digest();

            for (int i = 0; i < data.length; i++) {
                sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Xen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }

    @Override
    public List<Entreprise> entreprises() {
        return ef.findAll();
    }

    @Override
    public Entreprise entreprise(String entreprise) {
        return ef.find(entreprise);
    }

    @Override
    public long nbEntrepriseActives() {
        return ef.findAll().stream()
                .filter(f -> f.getEtat().equals(XenAgenceState.ACTIVE))
                .count();
    }

    @Override
    public long nbEntrepriseInactives() {
        return ef.findAll().stream()
                .filter(f -> f.getEtat().equals(XenAgenceState.INACTIVE))
                .count();
    }

    @Override
    public List<Facture> factures(String entreprise) {
        return fafa.findAll().stream()
                .filter(f -> f.getEntreprise().equals(entreprise))
                .collect(Collectors.toList());
    }

    @Override
    public Facture facture(long numero, String entreprise) {
        return factures(entreprise).stream()
                .filter(f -> f.getId().equals(numero))
                .findAny().get();
    }

    @Override
    public void addFacture(Facture fact) {
        Entreprise ent = entreprise(fact.getEntreprise());
        ent.getFactures().add(fact);
        ef.edit(ent);
    }

    @Override
    public void updateFactureState(long num, FactureState state) {
        Facture fac = fafa.find(num);
        fac.setEtat(state);
        Entreprise ent = entreprise(fac.getEntreprise());
        ent.getFactures().stream().filter(f -> f.getId().equals(num))
                .findFirst().get().setEtat(state);
        ef.edit(ent);
    }

    @Override
    public void updateTransactionState(long id, XenUtil.XenTransactionState state) {
        XTransaction tr = tf.find(id);
        XAgence ag = agence(tr.getAgence());
        ag.getTransactions().stream().filter(t -> t.getId().equals(id))
                .findFirst().get().setEtat(state);
        tr.setEtat(state);
        updateGerant(ag.getPersonne());
    }

    @Override
    public float commissionFacture(String entreprise) {
        montant = 0;
        factures(entreprise).stream()
                .filter(f -> f.getEtat().equals(FactureState.PAYEE))
                .forEach(f -> {
                    montant += f.getXengain();
                });
        return montant;
    }

    @Override
    public String entrepriseId() {
        long size = ef.count();
        return (size > 10) ? "ZENE-EP-" + size : "ZENE-EP-0" + size;
    }

    @Override
    public void suspendreEntreprise(String id) {
        Entreprise ent = entreprise(id);
        ent.setEtat(XenAgenceState.INACTIVE);
        ef.edit(ent);
    }

    @Override
    public void activeEntreprise(String id) {
        Entreprise ent = entreprise(id);
        ent.setEtat(XenAgenceState.ACTIVE);
        ef.edit(ent);
    }

    @Override
    public void supprimerEntreprise(String entreprise) {
        Entreprise ent = entreprise(entreprise);
        ef.remove(ent);
    }

    @Override
    public boolean areToDay(Date d1, Date d2) {
        String sd1 = DateFormat.getDateInstance(DateFormat.SHORT).format(d1);
        String sd2 = DateFormat.getDateInstance(DateFormat.SHORT).format(d2);
        return sd1.equals(sd2);
    }

    @Override
    public float zeneGain() {
        montant = 0;
        transferts().stream()
                .forEach(t -> {
                    montant += t.getXengain();
                });
        return montant;
    }

    @Override
    public Optional<XPersonne> personne(String id) {
        return pf.findAll().stream().filter(f -> f.getId().equals(id)).findFirst();
    }

    @Override
    public List<XPersonne> admins() {
        return pf.findAll().stream().filter(p -> p.getFonction().equals(XenPersonFunction.ADMIN)).collect(Collectors.toList());
    }

    @Override
    public List<XPersonne> agenciers() {
        return pf.findAll().stream().filter(p -> p.getFonction().equals(XenPersonFunction.GERANT)).collect(Collectors.toList());
    }

    @Override
    public void addEntreprise(Entreprise ent) {
        ef.create(ent);
    }

    @Override
    public float commissionAgenceFacture(String entreprise, String agence) {
        montant = 0;
        factures(entreprise).stream()
                .filter(f -> f.getAgence().equals(agence))
                .forEach(f -> {
                    montant += f.getAgenceGain();
                });
        return montant;
    }

    @Override
    public List<Facture> agencefacture(String agence) {
        return fafa.findAll().stream().filter(f -> f.getAgence().equals(agence))
                .collect(Collectors.toList());
    }

    @Override
    public void addFrais(XFrais frais) {
        ff.create(frais);
    }

	@Override
	public void addAgence(XAgence agence) {
		af.create(agence);
		
	}

}
