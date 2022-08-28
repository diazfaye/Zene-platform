package com.barth.trans.ejb;

import com.barth.trans.entity.XTransaction;
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
public class TransactionFacade extends AbstractFacade<XTransaction> {

    @PersistenceContext
    private  EntityManager em;

    public TransactionFacade() {
        super(XTransaction.class);
    }

    public TransactionFacade(Class<XTransaction> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
