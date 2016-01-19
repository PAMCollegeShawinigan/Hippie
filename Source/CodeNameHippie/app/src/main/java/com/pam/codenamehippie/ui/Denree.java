package com.pam.codenamehippie.ui;

import java.util.Date;

/**
 * Created by BEG-163 on 2015-12-01.
 */


public class Denree {
    private String nomDenree;
    private String quantiteDenree;
    private String typeUnite;
    private StateDenree stateDenree;
    private TypeDenree typeDenree;
    private String datePeremption;


    public Denree(String nomDenree, String quantiteDenree, String typeUnite, String datePeremption, TypeDenree typeDenree) {
        this.nomDenree = nomDenree;
        this.quantiteDenree = quantiteDenree;
        this.typeUnite = typeUnite;
        this.datePeremption = datePeremption;
        this.typeDenree = typeDenree;
    }

    public String getTypeUnite() {
        return typeUnite;
    }

    public void setTypeUnite(String typeUnite) {
        this.typeUnite = typeUnite;
    }

    public TypeDenree getTypeDenree() {
        return typeDenree;
    }

    public void setTypeDenree(TypeDenree typeDenree) {
        this.typeDenree = typeDenree;
    }

    public StateDenree getStateDenree() {
        return stateDenree;
    }

    public void setStateDenree(StateDenree stateDenree) {
        this.stateDenree = stateDenree;
    }

    public String getNomDenree() {
        return nomDenree;
    }

    public void setNomDenree(String nonDenree) {
        this.nomDenree = nomDenree;
    }

    public String getQuantiteDenree() {
        return quantiteDenree;
    }

    public void setQuantiteDenree(String quantiteDenree) {
        this.quantiteDenree = quantiteDenree;
    }


    public String getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(String datePeremption) {
        this.datePeremption = datePeremption;
    }


public enum StateDenree {
    disponible,
    reserveee,
    collectee
}

public enum TypeDenree {

    fruit_legume,
    viande,
    laitier,
    surgele,
    perissable,
    boulangerie,
    non_comestible,
    non_perissable
}

public enum TypeUnite {
    kg,
    litre,
    unite
}
}
