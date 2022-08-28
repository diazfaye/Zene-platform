package com.barth.trans.ejb;

import com.barth.trans.entity.XAgence;
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
public class AgenceFacade extends AbstractFacade<XAgence> {

    @PersistenceContext
    private EntityManager em;

    public AgenceFacade() {
        super(XAgence.class);
    }

    public AgenceFacade(Class<XAgence> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
