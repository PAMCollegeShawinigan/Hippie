package com.pam.codenamehippie.ui.view.trianglemenu;

import com.google.android.gms.maps.model.LatLng;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.UtilisateurModele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BEG-163 on 2016-01-18.
 * Cette classe est pour fabriquer les donnees pour tester les affichages de liste marchandises diponible,reservees
 * et toutes les organismes communautaire
 */
public class TestDonneeCentre {

    public static UtilisateurModele prepareDonnees_utilisateur() {
        UtilisateurModele utilisateur=new UtilisateurModele();
        OrganismeModele organisme=new OrganismeModele();
        organisme.setTelephone("81998989800")
                .setNom("IGA")

                .setNoEntreprise("qc764599")
                .setNoOsbl("2345");

        utilisateur.setCourriel("test@yahoo.ca")
             //   .setDernConnexion()
                .setMotDePasse("123456")
                .setNom("Yongshun")
                .setPrenom("Li")
                .setTelephone("8198668888")
                .setOrganisme(organisme);

        return utilisateur;
    }

    public static ArrayList<Organisme> prepareDonnees_reservees() {

        // preparer des entreprise et leurs liste denrees
        ArrayList<Denree> listDenree1 = new ArrayList<>();
        listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime1 = new HashMap<>();
        mapCollectTime1.put("lundi", "12:00-14:00");
        mapCollectTime1.put("mardi", "9:00-16:00");
        mapCollectTime1.put("mercredi", "8:00-13:00");
        mapCollectTime1.put("jeudi", "9:00-14:00");
        mapCollectTime1.put("vendredi", "9:00-17:00");
        mapCollectTime1.put("samdi", "9:00-13:00");
        mapCollectTime1.put("dimanche", "ferme");
        Organisme organisme1 = new Organisme("Maxi&Cie ", "6825 Chemin de la Cote-des-Neiges, Montreal,QC H3S 2B6", mapCollectTime1, "(514) 734-1280", listDenree1,"Tom Crouse");

        ArrayList<Denree> listDenree3 = new ArrayList<>();
        listDenree3.add(new Denree("carrot", "41", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime3 = new HashMap<>();
        mapCollectTime3.put("lundi", "9:00-14:00");
        mapCollectTime3.put("mardi", "9:00-11:00");
        mapCollectTime3.put("mercredi", "8:00-14:00");
        mapCollectTime3.put("jeudi", "13:00-14:00");
        mapCollectTime3.put("vendredi", "9:00-17:00");
        mapCollectTime3.put("samdi", "9:00-13:00");
        mapCollectTime3.put("dimanche", "ferme");
        Organisme organisme3 = new Organisme("Maxi ", "909 Boulevard Firestone, Joliette, QC J6E 2W4", mapCollectTime3, "(450) 752-0088", listDenree3,"Tom Crouse");

        ArrayList<Denree> listDenree4 = new ArrayList<>();
        listDenree4.add(new Denree("tuna", "90", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime4 = new HashMap<>();
        mapCollectTime4.put("lundi", "9:00-12:00");
        mapCollectTime4.put("mardi", "9:00-14:00");
        mapCollectTime4.put("mercredi", "10:00-14:00");
        mapCollectTime4.put("jeudi", "9:00-14:00");
        mapCollectTime4.put("vendredi", "9:00-16:00");
        mapCollectTime4.put("samdi", "9:00-13:00");
        mapCollectTime4.put("dimanche", "ferme");
        Organisme organisme4 = new Organisme("Maxi & Cie", "2478 Rue de la Laurentie, Sherbrooke, QC J1J 1L4", mapCollectTime4, "(819) 829-1000", listDenree4,"Tom Crouse");

        ArrayList<Denree> listDenree6 = new ArrayList<>();
        listDenree6.add(new Denree("tuna", "34", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        listDenree6.add(new Denree("beuf", "19", "kg", "9999-12-31", Denree.TypeDenree.viande,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime6 = new HashMap<>();
        mapCollectTime6.put("lundi", "9:00-10:00");
        mapCollectTime6.put("mardi", "11:00-14:00");
        mapCollectTime6.put("mercredi", "8:00-12:00");
        mapCollectTime6.put("jeudi", "9:00-16:00");
        mapCollectTime6.put("vendredi", "13:00-17:00");
        mapCollectTime6.put("samdi", "9:00-15:00");
        mapCollectTime6.put("dimanche", "ferme");
        Organisme organisme6 = new Organisme("Provigo", "4545 Boulevard Henri-Bourassa, Ville de Québec, QC G1H 7L9", mapCollectTime6, "(418) 622-7070", listDenree6,"Tom Crouse");
        //preparer les entreprise affichees sur carte
        ArrayList<Organisme> listOrganisme = new ArrayList<>();
        listOrganisme.add(organisme1);
        listOrganisme.add(organisme3);
        listOrganisme.add(organisme4);
        listOrganisme.add(organisme6);
        return listOrganisme;
    }

    /**
     * preparer les donnees pour liste marchandise disponible
     * @return
     */
    public static ArrayList<Organisme> prepareDonnees_disponible() {

        // preparer des entreprise et leurs liste denrees
        ArrayList<Denree> listDenree1 = new ArrayList<>();
        listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));   listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));   listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));   listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));   listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));   listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime1 = new HashMap<>();
        mapCollectTime1.put("lundi", "12:00-14:00");
        mapCollectTime1.put("mardi", "9:00-16:00");
        mapCollectTime1.put("mercredi", "8:00-13:00");
        mapCollectTime1.put("jeudi", "9:00-14:00");
        mapCollectTime1.put("vendredi", "9:00-17:00");
        mapCollectTime1.put("samdi", "9:00-13:00");
        mapCollectTime1.put("dimanche", "ferme");
        Organisme organisme1 = new Organisme("Maxi&Cie ", "6825 Chemin de la Cote-des-Neiges, Montreal,QC H3S 2B6", mapCollectTime1, "(514) 734-1280", listDenree1,"Tom Crouse");

        ArrayList<Denree> listDenree2 = new ArrayList<Denree>();
        listDenree2.add(new Denree("pomme", "33", "kg", "9999-12-31", Denree.TypeDenree.boulangerie,"en bon etat,importee de usa"));
        listDenree2.add(new Denree("avocat", "8", "unite", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree2.add(new Denree("rasin", "7", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        listDenree2.add(new Denree("patate", "21", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime2 = new HashMap<>();
        mapCollectTime2.put("lundi", "9:00-14:00");
        mapCollectTime2.put("mardi", "8:00-14:00");
        mapCollectTime2.put("mercredi", "8:00-18:00");
        mapCollectTime2.put("jeudi", "9:00-14:00");
        mapCollectTime2.put("vendredi", "10:00-17:00");
        mapCollectTime2.put("samdi", "9:00-13:00");
        mapCollectTime2.put("dimanche", "ferme");
        Organisme organisme2 = new Organisme("Rona", "7750 Boulevard des Hêtres, Shawinigan, QC G9N 4X4", mapCollectTime2, "(819) 537-3113", listDenree2,"Tom Crouse");

        ArrayList<Denree> listDenree3 = new ArrayList<>();
        listDenree3.add(new Denree("croissant", "7", "unite", "9999-12-31", Denree.TypeDenree.boulangerie,"on bon etat,importee de usa"));
        listDenree3.add(new Denree("mais", "40", "kg", "9999-12-31", Denree.TypeDenree.perissable,"on bon etat,importee de usa"));
        listDenree3.add(new Denree("carrot", "41", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime3 = new HashMap<>();
        mapCollectTime3.put("lundi", "9:00-14:00");
        mapCollectTime3.put("mardi", "9:00-11:00");
        mapCollectTime3.put("mercredi", "8:00-14:00");
        mapCollectTime3.put("jeudi", "13:00-14:00");
        mapCollectTime3.put("vendredi", "9:00-17:00");
        mapCollectTime3.put("samdi", "9:00-13:00");
        mapCollectTime3.put("dimanche", "ferme");
        Organisme organisme3 = new Organisme("Maxi ", "909 Boulevard Firestone, Joliette, QC J6E 2W4", mapCollectTime3, "(450) 752-0088", listDenree3,"Tom Crouse");

        ArrayList<Denree> listDenree4 = new ArrayList<>();
        listDenree4.add(new Denree("rasin", "37", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        listDenree4.add(new Denree("tuna", "90", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime4 = new HashMap<>();
        mapCollectTime4.put("lundi", "9:00-12:00");
        mapCollectTime4.put("mardi", "9:00-14:00");
        mapCollectTime4.put("mercredi", "10:00-14:00");
        mapCollectTime4.put("jeudi", "9:00-14:00");
        mapCollectTime4.put("vendredi", "9:00-16:00");
        mapCollectTime4.put("samdi", "9:00-13:00");
        mapCollectTime4.put("dimanche", "ferme");
        Organisme organisme4 = new Organisme("Marché IGA Bellevue ", " 560 Boulevard des Bois Francs S, Victoriaville, QC G6P 5X4", mapCollectTime4, "(819) 357-2241", listDenree4,"Tom Crouse");

        ArrayList<Denree> listDenree5 = new ArrayList<>();
        listDenree5.add(new Denree("tuna", "34", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        listDenree5.add(new Denree("avocat", "27", "unite", "9999-12-31", Denree.TypeDenree.perissable,"on bon etat,importee de usa"));
        listDenree5.add(new Denree("orange", "56", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        listDenree5.add(new Denree("samon", "34", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        listDenree5.add(new Denree("beuf", "9", "kg", "9999-12-31", Denree.TypeDenree.viande,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime5 = new HashMap<>();
        mapCollectTime5.put("lundi", "9:00-10:00");
        mapCollectTime5.put("mardi", "11:00-14:00");
        mapCollectTime5.put("mercredi", "8:00-14:00");
        mapCollectTime5.put("jeudi", "9:00-16:00");
        mapCollectTime5.put("vendredi", "13:00-17:00");
        mapCollectTime5.put("samdi", "9:00-10:00");
        mapCollectTime5.put("dimanche", "ferme");
        Organisme organisme5 = new Organisme("Tigre Géant", " 800 Boulevard Thibeau, Trois-Rivières, QC G8T 7A6", mapCollectTime5, "(819) 697-3833", listDenree5,"Tom Crouse");

        ArrayList<Denree> listDenree6 = new ArrayList<>();
        listDenree6.add(new Denree("tuna", "34", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        listDenree6.add(new Denree("avocat", "27", "kg", "9999-12-31", Denree.TypeDenree.perissable,"on bon etat,importee de usa"));
        listDenree6.add(new Denree("orange", "23", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        listDenree6.add(new Denree("samon", "4", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        listDenree6.add(new Denree("beuf", "19", "kg", "9999-12-31", Denree.TypeDenree.viande,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime6 = new HashMap<>();
        mapCollectTime6.put("lundi", "9:00-10:00");
        mapCollectTime6.put("mardi", "11:00-14:00");
        mapCollectTime6.put("mercredi", "8:00-12:00");
        mapCollectTime6.put("jeudi", "9:00-16:00");
        mapCollectTime6.put("vendredi", "13:00-17:00");
        mapCollectTime6.put("samdi", "9:00-15:00");
        mapCollectTime6.put("dimanche", "ferme");
        Organisme organisme6 = new Organisme("Provigo", "4545 Boulevard Henri-Bourassa, Ville de Québec, QC G1H 7L9", mapCollectTime6, "(418) 622-7070", listDenree6,"Tom Crouse");

        //preparer les entreprise affichees sur carte
        ArrayList<Organisme> listOrganisme = new ArrayList<>();
        listOrganisme.add(organisme1);
        listOrganisme.add(organisme2);
        listOrganisme.add(organisme3);
        listOrganisme.add(organisme4);
        listOrganisme.add(organisme5);
        listOrganisme.add(organisme6);
        return listOrganisme;

    }

    public static ArrayList<Organisme> prepareDonnees_organismes() {

        // preparer des entreprise et leurs liste denrees
        ArrayList<Denree> listDenree1 = new ArrayList<>();
        listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));  listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));  listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));  listDenree1.add(new Denree("orange", "2", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("poulet", "3", "unite", "9999-12-31", Denree.TypeDenree.perissable,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("prune", "13", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime1 = new HashMap<>();
        mapCollectTime1.put("lundi", "12:00-14:00");
        mapCollectTime1.put("mardi", "9:00-16:00");
        mapCollectTime1.put("mercredi", "8:00-13:00");
        mapCollectTime1.put("jeudi", "9:00-14:00");
        mapCollectTime1.put("vendredi", "9:00-17:00");
        mapCollectTime1.put("samdi", "9:00-13:00");
        mapCollectTime1.put("dimanche", "ferme");
        Organisme organisme1 = new Organisme("Maxi&Cie ", "6825 Chemin de la Cote-des-Neiges, Montreal,QC H3S 2B6", mapCollectTime1, "(514) 734-1280", listDenree1,"Tom Crouse");

        ArrayList<Denree> listDenree2 = new ArrayList<Denree>();
        listDenree2.add(new Denree("pomme", "33", "kg", "9999-12-31", Denree.TypeDenree.boulangerie,"en bon etat,importee de usa"));
        listDenree2.add(new Denree("avocat", "8", "unite", "9999-12-31", Denree.TypeDenree.fruit_legume,"en bon etat,importee de usa"));
        listDenree2.add(new Denree("rasin", "7", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        listDenree2.add(new Denree("patate", "21", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime2 = new HashMap<>();
        mapCollectTime2.put("lundi", "9:00-14:00");
        mapCollectTime2.put("mardi", "8:00-14:00");
        mapCollectTime2.put("mercredi", "8:00-18:00");
        mapCollectTime2.put("jeudi", "9:00-14:00");
        mapCollectTime2.put("vendredi", "10:00-17:00");
        mapCollectTime2.put("samdi", "9:00-13:00");
        mapCollectTime2.put("dimanche", "ferme");
        Organisme organisme2 = new Organisme("Rona", "7750 Boulevard des Hêtres, Shawinigan, QC G9N 4X4", mapCollectTime2, "(819) 537-3113", listDenree2,"Tom Crouse");

        ArrayList<Denree> listDenree3 = new ArrayList<>();
        listDenree3.add(new Denree("croissant", "7", "unite", "9999-12-31", Denree.TypeDenree.boulangerie,"on bon etat,importee de usa"));
        listDenree3.add(new Denree("mais", "40", "kg", "9999-12-31", Denree.TypeDenree.perissable,"on bon etat,importee de usa"));
        listDenree3.add(new Denree("carrot", "41", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime3 = new HashMap<>();
        mapCollectTime3.put("lundi", "9:00-14:00");
        mapCollectTime3.put("mardi", "9:00-11:00");
        mapCollectTime3.put("mercredi", "8:00-14:00");
        mapCollectTime3.put("jeudi", "13:00-14:00");
        mapCollectTime3.put("vendredi", "9:00-17:00");
        mapCollectTime3.put("samdi", "9:00-13:00");
        mapCollectTime3.put("dimanche", "ferme");
        Organisme organisme3 = new Organisme("Maxi ", "909 Boulevard Firestone, Joliette, QC J6E 2W4", mapCollectTime3, "(450) 752-0088", listDenree3,"Tom Crouse");

        ArrayList<Denree> listDenree4 = new ArrayList<>();
        listDenree4.add(new Denree("rasin", "37", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        listDenree4.add(new Denree("tuna", "90", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime4 = new HashMap<>();
        mapCollectTime4.put("lundi", "9:00-12:00");
        mapCollectTime4.put("mardi", "9:00-14:00");
        mapCollectTime4.put("mercredi", "10:00-14:00");
        mapCollectTime4.put("jeudi", "9:00-14:00");
        mapCollectTime4.put("vendredi", "9:00-16:00");
        mapCollectTime4.put("samdi", "9:00-13:00");
        mapCollectTime4.put("dimanche", "ferme");
        Organisme organisme4 = new Organisme("Marché IGA Bellevue ", " 560 Boulevard des Bois Francs S, Victoriaville, QC G6P 5X4", mapCollectTime4, "(819) 357-2241", listDenree4,"Tom Crouse");

        ArrayList<Denree> listDenree5 = new ArrayList<>();
        listDenree5.add(new Denree("tuna", "34", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        listDenree5.add(new Denree("avocat", "27", "unite", "9999-12-31", Denree.TypeDenree.perissable,"on bon etat,importee de usa"));
        listDenree5.add(new Denree("orange", "56", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        listDenree5.add(new Denree("samon", "34", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        listDenree5.add(new Denree("beuf", "9", "kg", "9999-12-31", Denree.TypeDenree.viande,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime5 = new HashMap<>();
        mapCollectTime5.put("lundi", "9:00-10:00");
        mapCollectTime5.put("mardi", "11:00-14:00");
        mapCollectTime5.put("mercredi", "8:00-14:00");
        mapCollectTime5.put("jeudi", "9:00-16:00");
        mapCollectTime5.put("vendredi", "13:00-17:00");
        mapCollectTime5.put("samdi", "9:00-10:00");
        mapCollectTime5.put("dimanche", "ferme");
        Organisme organisme5 = new Organisme("Tigre Géant", " 800 Boulevard Thibeau, Trois-Rivières, QC G8T 7A6", mapCollectTime5, "(819) 697-3833", listDenree5,"Tom Crouse");

        ArrayList<Denree> listDenree6 = new ArrayList<>();
        listDenree6.add(new Denree("tuna", "34", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        listDenree6.add(new Denree("avocat", "27", "kg", "9999-12-31", Denree.TypeDenree.perissable,"on bon etat,importee de usa"));
        listDenree6.add(new Denree("orange", "23", "kg", "9999-12-31", Denree.TypeDenree.fruit_legume,"on bon etat,importee de usa"));
        listDenree6.add(new Denree("samon", "4", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        listDenree6.add(new Denree("beuf", "19", "kg", "9999-12-31", Denree.TypeDenree.viande,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime6 = new HashMap<>();
        mapCollectTime6.put("lundi", "9:00-10:00");
        mapCollectTime6.put("mardi", "11:00-14:00");
        mapCollectTime6.put("mercredi", "8:00-12:00");
        mapCollectTime6.put("jeudi", "9:00-16:00");
        mapCollectTime6.put("vendredi", "13:00-17:00");
        mapCollectTime6.put("samdi", "9:00-15:00");
        mapCollectTime6.put("dimanche", "ferme");
        Organisme organisme6 = new Organisme("Provigo", "4545 Boulevard Henri-Bourassa, Ville de Québec, QC G1H 7L9", mapCollectTime6, "(418) 622-7070", listDenree6,"Tom Crouse");


        ArrayList<Denree> listDenree7 = new ArrayList<>();
        listDenree7.add(new Denree("tuna", "90", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime7 = new HashMap<>();
        mapCollectTime7.put("lundi", "9:00-12:00");
        mapCollectTime7.put("mardi", "9:00-14:00");
        mapCollectTime7.put("mercredi", "10:00-14:00");
        mapCollectTime7.put("jeudi", "9:00-14:00");
        mapCollectTime7.put("vendredi", "9:00-16:00");
        mapCollectTime7.put("samdi", "9:00-13:00");
        mapCollectTime7.put("dimanche", "ferme");
        Organisme organisme7 = new Organisme("Maxi & Cie", "2478 Rue de la Laurentie, Sherbrooke, QC J1J 1L4", mapCollectTime4, "(819) 829-1000", listDenree4,"Tom Crouse");


        ArrayList<Denree> listDenree8 = new ArrayList<>();
        listDenree8.add(new Denree("tuna", "90", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime8 = new HashMap<>();
        mapCollectTime8.put("lundi", "9:00-12:00");
        mapCollectTime8.put("mardi", "9:00-14:00");
        mapCollectTime8.put("mercredi", "10:00-14:00");
        mapCollectTime8.put("jeudi", "9:00-14:00");
        mapCollectTime8.put("vendredi", "9:00-16:00");
        mapCollectTime8.put("samdi", "9:00-13:00");
        mapCollectTime8.put("dimanche", "ferme");
        Organisme organisme8 = new Organisme("Super C", " 3460 Boul. St-Francois, Jonquière, QC G7X 8L3", mapCollectTime4, "(819) 829-1000", listDenree4,"Tom Crouse");

        ArrayList<Denree> listDenree9 = new ArrayList<>();
        listDenree9.add(new Denree("tuna", "90", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime9 = new HashMap<>();
        mapCollectTime9.put("lundi", "9:00-12:00");
        mapCollectTime9.put("mardi", "9:00-14:00");
        mapCollectTime9.put("mercredi", "10:00-14:00");
        mapCollectTime9.put("jeudi", "9:00-14:00");
        mapCollectTime9.put("vendredi", "9:00-16:00");
        mapCollectTime9.put("samdi", "9:00-13:00");
        mapCollectTime9.put("dimanche", "ferme");
        Organisme organisme9 = new Organisme("Maxi & Cie", "327 Boulevard de York S, Gaspé, QC G4X 2L2", mapCollectTime4, "(819) 829-1000", listDenree4,"Tom Crouse");

        ArrayList<Denree> listDenree10 = new ArrayList<>();
        listDenree10.add(new Denree("tuna", "90", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime10 = new HashMap<>();
        mapCollectTime10.put("lundi", "9:00-12:00");
        mapCollectTime10.put("mardi", "9:00-14:00");
        mapCollectTime10.put("mercredi", "10:00-14:00");
        mapCollectTime10.put("jeudi", "9:00-14:00");
        mapCollectTime10.put("vendredi", "9:00-16:00");
        mapCollectTime10.put("samdi", "9:00-13:00");
        mapCollectTime10.put("dimanche", "ferme");
        Organisme organisme10 = new Organisme("IGA", "720 Montée Paiement, Gatineau, QC J8R 2S8", mapCollectTime4, "(819) 829-1000", listDenree4,"Tom Crouse");






        //preparer les entreprise affichees sur carte
        ArrayList<Organisme> listOrganisme = new ArrayList<>();
        listOrganisme.add(organisme1);
        listOrganisme.add(organisme2);
        listOrganisme.add(organisme3);
        listOrganisme.add(organisme4);
        listOrganisme.add(organisme5);
        listOrganisme.add(organisme6);
        listOrganisme.add(organisme7);
        listOrganisme.add(organisme8);
        listOrganisme.add(organisme9);
       listOrganisme.add(organisme10);
        return listOrganisme;

    }

    /**
     * Created by BEG-163 on 2015-12-01.
     */


    public static class Denree {
        private String nomDenree;
        private String quantiteDenree;
        private String typeUnite;
        private StateDenree stateDenree;
        private TypeDenree typeDenree;
        private String datePeremption;
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Denree(String nomDenree, String quantiteDenree, String typeUnite, String datePeremption, TypeDenree typeDenree,String description) {
            this.nomDenree = nomDenree;
            this.quantiteDenree = quantiteDenree;
            this.typeUnite = typeUnite;
            this.datePeremption = datePeremption;
            this.typeDenree = typeDenree;
            this.description=description;
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

    /**
     * Created by BEG-163 on 2015-12-01.
     */
    public static class Organisme {
        private String nomOrganisme;
        private String addresse;
        private AddressOrganisme add;
        private HashMap<String, String> mapCollectTime;
        private String telephone;
        private ArrayList<Denree> listDenree;
        private LatLng mLatLng;
        private String contact;

        public AddressOrganisme getAdd() {
            return add;
        }

        public void setAdd(AddressOrganisme add) {
            this.add = add;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public LatLng getmLatLng() {
            return mLatLng;
        }

        public void setmLatLng(LatLng mLatLng) {
            this.mLatLng = mLatLng;
        }

        public void setMapCollectTime(HashMap<String, String> mapCollectTime) {
            this.mapCollectTime = mapCollectTime;
        }

        public Organisme(String nomOrganisme, String addresse, HashMap<String, String> mapCollectTime, String telephone, ArrayList<Denree> listDenree,String contact) {
            this.nomOrganisme = nomOrganisme;
            this.addresse = addresse;
            this.mapCollectTime = mapCollectTime;
            this.telephone = telephone;
            this.listDenree = listDenree;
            this.contact=contact;
        }

        public HashMap<String, String> getMapCollectTime() {
            return mapCollectTime;
        }

        public void setMapCollectTime(ArrayList<Map<String, String>> listCollectTime) {
            this.mapCollectTime = mapCollectTime;
        }

        public String getNomOrganisme() {
            return nomOrganisme;
        }

        public void setNomOrganisme(String nomOrganisme) {
            this.nomOrganisme = nomOrganisme;
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

    /**
     * Created by BEG-163 on 2016-01-12.
     */
    public static class AddressOrganisme {

        private Integer noCivique;

        private String typeRue;

        private String nom;

        private String app;

        private String ville;

        private String province;

        private String codePostal;

        private String pays;

        public Integer getNoCivique() {
            return this.noCivique;
        }

        public AddressOrganisme setNoCivique(Integer noCivique) {
            this.noCivique = noCivique;
            return this;
        }

        public String getTypeRue() {
            return this.typeRue;
        }

        public AddressOrganisme setTypeRue(String typeRue) {
            this.typeRue = typeRue;
            return this;
        }

        public String getNom() {
            return this.nom;
        }

        public AddressOrganisme setNom(String nom) {
            this.nom = nom;
            return this;
        }

        public String getApp() {
            return this.app;
        }

        public AddressOrganisme setApp(String app) {
            this.app = app;
            return this;
        }

        public String getVille() {
            return this.ville;
        }

        public AddressOrganisme setVille(String ville) {
            this.ville = ville;
            return this;
        }

        public String getProvince() {
            return this.province;
        }

        public AddressOrganisme setProvince(String province) {
            this.province = province;
            return this;
        }

        public String getCodePostal() {
            return this.codePostal;
        }

        public AddressOrganisme setCodePostal(String codePostal) {
            this.codePostal = codePostal;
            return this;
        }

        public String getPays() {
            return this.pays;
        }

        public AddressOrganisme setPays(String pays) {
            this.pays = pays;
            return this;
        }


    }
}
