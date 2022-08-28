package com.barth.trans.ejb;

import com.barth.trans.entity.XFrais;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author barth
 */
@Stateless
@Startup
public class FraisFacade extends AbstractFacade<XFrais> {

    @PersistenceContext
    private  EntityManager em;

    public FraisFacade() {
        super(XFrais.class);
    }

    public FraisFacade(Class<XFrais> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
