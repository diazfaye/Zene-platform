package com.barth.trans.util;

import java.io.Serializable;

/**
 *
 * @author alhoussene
 */
public abstract class XenUtil implements Serializable {

    public enum XenPersonFunction {
        ADMIN, GERANT
    };

    public enum XenAgenceState {
        ACTIVE, INACTIVE
    };

    public enum XenTransactionType {
        TRANSFERT, RETRAIT, CREDITATION, DEMANDE_DE_CREDIT
    };

    public enum XenTransactionState {
        ATTENTE, EFFECTUEE
    };

    public enum XenCondition {
        CODE, PIECE, CODE_ET_PIECE
    };

    public enum FactureState {
        VALIDE, EXPIREE, PAYEE
    };

    public enum FactureType {
        ELECTRICITE, EAU
    };

}
