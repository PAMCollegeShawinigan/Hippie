package pam.yongshunli.hippiemaps;

/**
 * Created by BEG-163 on 2015-12-01.
 */


public class Denree {
    private String nomDenree;
    private String quantiteDenree;
    private String unite = "kg";
    private StateDenree stateDenree;
    private TypeDenree typeDenree;

    public Denree(String nomDenree, String quantiteDenree, String unite, StateDenree stateDenree, TypeDenree typeDenree) {
        this.nomDenree = nomDenree;
        this.quantiteDenree = quantiteDenree;
        this.unite = unite;
        this.stateDenree = stateDenree;
        this.typeDenree = typeDenree;
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

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }
}

enum StateDenree {
    disponible,
    reserveee,
    collectee
}

enum TypeDenree {

    fruit_legume,
    viande,
    laitier,
    surgele,
    perissable,
    boulangerie,
    non_perissable
}
