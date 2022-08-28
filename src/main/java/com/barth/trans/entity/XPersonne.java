package com.barth.trans.entity;

import com.barth.trans.util.XenUtil.XenPersonFunction;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author barth
 */
@Entity
public class XPersonne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id 
    private String id;
    @NotNull(message = "Le nom ne peut etre vide !")
    @Size(min = 2, max = 15, message = "Le nom n'est pas valide !")
    private String nom;
    @NotNull(message = "Le prénom ne peut etre vide !")
    @Size(min = 2, max = 15, message = "Le prénom n'est pas valide !")
    private String prenom;
    @NotNull(message = "Le téléphone ne peut etre vide !")
    @Size(min = 6, max = 20, message = "Le téléphone n'est pas valide !")
    private String phone;
    @Pattern(regexp = "[\\w\\.]+@\\w+(\\.\\w+)+", message = "Cet email n'est pas valide !")
    private String email;
    @NotNull  
    private String mot_de_passe;
    @Enumerated(value = EnumType.STRING)
    private XenPersonFunction fonction;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "agenceId")
    private XAgence agence; 
    

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
        if (!(object instanceof XPersonne)) {
            return false;
        }
        XPersonne other = (XPersonne) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.barth.trans.entity.XPersonne[ id=" + id + " ]";
    }

    public XPersonne() {
    	super();
    } 
    

    public XPersonne(
			@NotNull(message = "Le nom ne peut etre vide !") @Size(min = 2, max = 15, message = "Le nom n'est pas valide !") String nom,
			@NotNull(message = "Le prénom ne peut etre vide !") @Size(min = 2, max = 15, message = "Le prénom n'est pas valide !") String prenom,
			@NotNull(message = "Le téléphone ne peut etre vide !") @Size(min = 6, max = 20, message = "Le téléphone n'est pas valide !") String phone,
			@Pattern(regexp = "[\\w\\.]+@\\w+(\\.\\w+)+", message = "Cet email n'est pas valide !") String email,
			@NotNull String mot_de_passe, XenPersonFunction fonction, XAgence agence) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.email = email;
		this.mot_de_passe = mot_de_passe;
		this.fonction = fonction;
		this.agence = agence;
	}

	public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public XenPersonFunction getFonction() {
        return fonction;
    }

    public void setFonction(XenPersonFunction fonction) {
        this.fonction = fonction;
    }

    public XAgence getAgence() {
        return agence;
    }

    public void setAgence(XAgence agence) {
        this.agence = agence;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }  

}
