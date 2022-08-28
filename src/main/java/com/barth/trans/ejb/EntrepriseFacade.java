package com.barth.trans.ejb;

import com.barth.trans.entity.Entreprise;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author acom.barth.trans
 */
@Stateless
public class EntrepriseFacade extends AbstractFacade<Entreprise> {

    @PersistenceContext
    private EntityManager em;

    public EntrepriseFacade() {
        super(Entreprise.class);
    }

    public EntrepriseFacade(Class<Entreprise> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
