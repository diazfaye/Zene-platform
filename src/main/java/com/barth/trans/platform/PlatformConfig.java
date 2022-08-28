package com.barth.trans.platform;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

/**
 *
 * @author barth
 */
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/zenedb",
        callerQuery = "select Mot_DE_PASSE from xpersonne where ID = ?",
        groupsQuery = "select FONCTION from xpersonne where ID = ?"
)
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/faces/login.xhtml",
                errorPage = "",
                useForwardToLogin = false
        )
)
@FacesConfig (version = Version.JSF_2_3)
@ApplicationScoped
public class PlatformConfig {

    public PlatformConfig() {

    }
}
