package pam.yongshunli.hippiemaps;

/**
 * Created by BEG-163 on 2015-12-01.
 */
public class Denree {
    private  String nonDenree;
    private int quantiteDenree;
    private String unite="kg";

    public Denree(String nonDenree, int quantiteDenree, String unite) {
        this.nonDenree = nonDenree;
        this.quantiteDenree = quantiteDenree;
        this.unite = unite;
    }

    public String getNonDenree() {
        return nonDenree;
    }

    public void setNonDenree(String nonDenree) {
        this.nonDenree = nonDenree;
    }

    public int getQuantiteDenree() {
        return quantiteDenree;
    }

    public void setQuantiteDenree(int quantiteDenree) {
        this.quantiteDenree = quantiteDenree;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }
}
