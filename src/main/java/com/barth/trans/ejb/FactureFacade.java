package com.barth.trans.ejb;

import com.barth.trans.entity.Facture;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author com.barth.trans
 */
@Stateless
public class FactureFacade extends AbstractFacade<Facture> {

    @PersistenceContext
    private EntityManager em;

    public FactureFacade() {
        super(Facture.class);
    }

    public FactureFacade(Class<Facture> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
