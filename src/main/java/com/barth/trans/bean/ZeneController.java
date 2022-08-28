package com.barth.trans.bean;

import com.barth.trans.ejb.Xen;
import com.barth.trans.entity.XPersonne;
import com.barth.trans.util.XenUtil.XenPersonFunction;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author barth
 */
@Named(value = "zeneController")
@RequestScoped
public class ZeneController {

    @Inject
    private Xen xen;
    private XenPersonFunction fonction;

    private Optional<XPersonne> currentPersonne;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;

    @PostConstruct
    public void init() {
        String id = securityContext.getCallerPrincipal().getName(); 
        currentPersonne = xen.personne(id);
        currentPersonne.ifPresent(p -> {
            fonction = p.getFonction();
        });
    }

    public String logout() {
        try {
            ExternalContext ec = facesContext.getExternalContext();
            ((HttpServletRequest) ec.getRequest()).logout();
        } catch (ServletException ex) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le serveur a rencontrer un probleme lors de la déconnexion", null));
            Logger.getLogger(ZeneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/login.xhtml?faces-redirect=true";
    }

    public XPersonne getCurrentPersonne() {
        return currentPersonne.orElse(null);
    }

    public XenPersonFunction getFonction() {
        return fonction;
    }

    public void setFonction(XenPersonFunction fonction) {
        this.fonction = fonction;
    }

    public ZeneController() {
    }

}
