package com.barth.trans.bean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;




import com.barth.trans.ejb.Xen;
import com.barth.trans.entity.XPersonne;
import com.barth.trans.util.XenUtil.XenAgenceState;
import com.barth.trans.util.XenUtil.XenPersonFunction;

/**
 *
 * @author barth
 */
@Named(value = "loginController")
@RequestScoped
public class LoginController {

    @NotEmpty
    private String identifiant;

    @NotEmpty
    private String password;
    @Inject
    private Xen xen;

    @Inject
    private FacesContext facesContext;

    @Inject
    private SecurityContext securityContext;

   

    public void execute() {
        switch (procesAuthenticationStatus()) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Identifiant ou mot de passe incorrect !", null));
                break;
            case SUCCESS:
                XPersonne p;
                try {
                    p = xen.gerant(identifiant);
                    if (p.getFonction().equals(XenPersonFunction.ADMIN)) {
                        getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/faces/patron/home.xhtml");
                    } else {
                        if (p.getAgence().getEtat().equals(XenAgenceState.INACTIVE)) {
                            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cette agence est suspendue!", null));
                        } else {
                            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/faces/gerant/home.xhtml");
                        }
                    }
                } catch (NullPointerException e) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Identifiant ou mot de passe incorrect !", null));
                } catch (IOException ie) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Le serveur a rencontré un problème ! ", null));
                    Logger.getLogger(LoginController.class.getName()).log(Level.INFO, "Erreur lors de l''authentification : {0}", ie.getMessage());
                }

                break;
        }
    }

    private ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    }

    private AuthenticationStatus procesAuthenticationStatus() {
        ExternalContext ec = getExternalContext();
        return securityContext.authenticate((HttpServletRequest) ec.getRequest(),
                (HttpServletResponse) ec.getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(identifiant, password)));
    }

    public LoginController() {
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
