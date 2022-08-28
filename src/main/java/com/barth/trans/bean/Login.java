package com.barth.trans.bean;

import com.barth.trans.ejb.Xen;
import com.barth.trans.entity.Entreprise;
import com.barth.trans.entity.XAgence;
import com.barth.trans.entity.XPersonne;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author barth
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String login;
    private String password;
    private XAgence agence;
    private XPersonne gerant;
    private Entreprise entreprise;

    @EJB
    private Xen xen;

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void adderrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Login() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public XAgence getAgence() {
        return agence;
    }

    public void setAgence(XAgence agence) {
        this.agence = agence;
    }

    public XPersonne getGerant() {
        return gerant;
    }

    public void setGerant(XPersonne gerant) {
        this.gerant = gerant;
    }

    public Xen getXen() {
        return xen;
    }

    public void setXen(Xen xen) {
        this.xen = xen;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

}
