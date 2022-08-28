package com.barth.trans.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.barth.trans.ejb.Xen;
import com.barth.trans.entity.XFrais;
import com.barth.trans.entity.XGain;
import com.barth.trans.entity.XPersonne;
import com.barth.trans.util.XenUtil.XenPersonFunction;

@Named(value = "defaultConfig")
@ApplicationScoped
public class DefaultConfig {

    @Inject
    private Xen xen;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public void execute(@Observes @Initialized(ApplicationScoped.class) Object event) {
        if (xen.gerants().isEmpty()) {
            XPersonne p = new XPersonne();
            p.setId(xen.personneId());
            p.setNom("barthelemy");
            p.setPrenom("faye");
            p.setEmail("barth@gmail.com");
            p.setFonction(XenPersonFunction.ADMIN);
            p.setPhone("775561233");
            p.setMot_de_passe(passwordHash.generate("zene".toCharArray()));
            xen.addPersonne(p);

            List<XFrais> xf = new ArrayList<>();
            xf.add(new XFrais(0, 0, 0, 0));
            xf.add(new XFrais(1, 0, 5_000, 425));
            xf.add(new XFrais(2, 5_000, 10_000, 850));
            xf.add(new XFrais(3, 10_000, 15_000, 1_270));
            xf.add(new XFrais(4, 15_000, 20_000, 1_695));
            xf.add(new XFrais(5, 20_000, 50_000, 2_500));
            xf.add(new XFrais(6, 50_000, 60_000, 3_000));
            xf.add(new XFrais(7, 60_000, 75_000, 4_000));
            xf.add(new XFrais(8, 75_000, 120_000, 5_000));
            xf.add(new XFrais(9, 120_000, 150_000, 6_000));
            xf.add(new XFrais(10, 150_000, 200_000, 7_000));
            xf.add(new XFrais(11, 200_000, 250_000, 8_000));
            xf.add(new XFrais(12, 250_000, 300_000, 9_000));
            xf.add(new XFrais(13, 300_000, 400_000, 12_000));
            xf.add(new XFrais(14, 400_000, 750_000, 15_000));
            xf.add(new XFrais(15, 750_000, 900_000, 22_000));
            xf.add(new XFrais(16, 900_000, 1_000_000, 25_000));
            xf.add(new XFrais(17, 1_000_000, 1_125_000, 27_000));
            xf.add(new XFrais(18, 1_225_000, 2_000_000, 30_000));
            xen.updateFrais(xf);

            XGain gains = new XGain();
            gains.setId(1);
            xen.updateGains(gains);
        }

    }

    public Pbkdf2PasswordHash getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(Pbkdf2PasswordHash passwordHash) {
        this.passwordHash = passwordHash;
    }

    public DefaultConfig() {
    }

}
