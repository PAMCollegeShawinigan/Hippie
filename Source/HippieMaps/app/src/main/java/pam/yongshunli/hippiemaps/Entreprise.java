package pam.yongshunli.hippiemaps;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BEG-163 on 2015-12-01.
 */
public class Entreprise {
    private String nomEntreprise;
    private String addresse;
    private HashMap<String,String> mapCollectTime;
    private String telephone;
    private CollectDay collectDay;
    private ArrayList<Denree> listDenree;

    private LatLng mLatLng;

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public void setmLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    public void setMapCollectTime(HashMap<String, String> mapCollectTime) {
        this.mapCollectTime = mapCollectTime;
    }


    public Entreprise(String nomEntreprise, String addresse,LatLng mLatLng, HashMap<String,String> mapCollectTime, String telephone, ArrayList<Denree> listDenree) {
        this.nomEntreprise = nomEntreprise;
        this.addresse = addresse;
        this.mLatLng=mLatLng;;
        this.mapCollectTime = mapCollectTime;
        this.telephone = telephone;
        this.listDenree = listDenree;
    }

    public HashMap<String,String> getMapCollectTime() {
        return mapCollectTime;
    }

    public void setMapCollectTime(ArrayList<Map<String, String>> listCollectTime) {
        this.mapCollectTime = mapCollectTime;
    }

    public CollectDay getCollectDay() {
        return collectDay;
    }

    public void setCollectDay(CollectDay collectDay) {
        this.collectDay = collectDay;
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

enum CollectDay{
    lundi{
        String getTime(){
            return "9:00-11:00";
        }

    },
    mardi{
        String getTime(){
            return "9:00-11:00";}
    },
    mercredi{
        String getTime(){
            return "9:00-11:00";}
    },
    jeudi{
        String getTime(){
            return "9:00-11:00";}
    },
    vendredi{
        String getTime(){
            return "9:00-11:00";}
    },
    samdi{
        String getTime(){
            return "9:00-11:00";}
    },
   dimanche {
       String getTime(){
           return "fermee";}
    }
}
