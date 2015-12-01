package pam.yongshunli.hippiemaps;

import java.util.ArrayList;

/**
 * Created by BEG-163 on 2015-12-01.
 */
public class Entreprise {
    private String nomEntreprise;
    private String addresse;
    private String heuresCollect="9:00-15:00";
    private String telephone;
    private ArrayList<Denree> listDenree;

    public Entreprise(String nomEntreprise, String addresse, String heuresCollect, String telephone, ArrayList<Denree> listDenree) {
        this.nomEntreprise = nomEntreprise;
        this.addresse = addresse;
        this.heuresCollect = heuresCollect;
        this.telephone = telephone;
        this.listDenree = listDenree;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getHeuresCollect() {
        return heuresCollect;
    }

    public void setHeuresCollect(String heuresCollect) {
        this.heuresCollect = heuresCollect;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public ArrayList<Denree> getListDenree() {
        return listDenree;
    }

    public void setListDenree(ArrayList<Denree> listDenree) {
        this.listDenree = listDenree;
    }
}
