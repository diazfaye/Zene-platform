package com.barth.trans.ejb;

import com.barth.trans.entity.XPersonne;
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
public class PersonFacade extends AbstractFacade<XPersonne> {

    @PersistenceContext
    public EntityManager em;

    public PersonFacade() {
        super(XPersonne.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
