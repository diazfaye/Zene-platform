package com.barth.trans.ejb;

import com.barth.trans.entity.XGain;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author barth
 */
@Stateless
public class GainFacade extends AbstractFacade<XGain> {

    @PersistenceContext
    private EntityManager em;

    public GainFacade() {
        super(XGain.class);
    }

    public GainFacade(Class<XGain> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
