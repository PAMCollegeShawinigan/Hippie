package com.pam.codenamehippie.ui.view.trianglemenu;

import com.pam.codenamehippie.ui.Denree;
import com.pam.codenamehippie.ui.Organisme;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BEG-163 on 2016-01-18.
 */
public class TestDonneeCentre {
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
        Organisme organisme1 = new Organisme("Maxi&Cie ", "6825 Chemin de la Cote-des-Neiges, Montreal,QC H3S 2B6", mapCollectTime1, "(514) 734-1280", listDenree1);

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
        Organisme organisme3 = new Organisme("Maxi ", "909 Boulevard Firestone, Joliette, QC J6E 2W4", mapCollectTime3, "(450) 752-0088", listDenree3);

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
        Organisme organisme4 = new Organisme("Maxi & Cie", "2478 Rue de la Laurentie, Sherbrooke, QC J1J 1L4", mapCollectTime4, "(819) 829-1000", listDenree4);

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
        Organisme organisme6 = new Organisme("Provigo", "4545 Boulevard Henri-Bourassa, Ville de Québec, QC G1H 7L9", mapCollectTime6, "(418) 622-7070", listDenree6);
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
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime1 = new HashMap<>();
        mapCollectTime1.put("lundi", "12:00-14:00");
        mapCollectTime1.put("mardi", "9:00-16:00");
        mapCollectTime1.put("mercredi", "8:00-13:00");
        mapCollectTime1.put("jeudi", "9:00-14:00");
        mapCollectTime1.put("vendredi", "9:00-17:00");
        mapCollectTime1.put("samdi", "9:00-13:00");
        mapCollectTime1.put("dimanche", "ferme");
        Organisme organisme1 = new Organisme("Maxi&Cie ", "6825 Chemin de la Cote-des-Neiges, Montreal,QC H3S 2B6", mapCollectTime1, "(514) 734-1280", listDenree1);

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
        Organisme organisme2 = new Organisme("Rona", "7750 Boulevard des Hêtres, Shawinigan, QC G9N 4X4", mapCollectTime2, "(819) 537-3113", listDenree2);

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
        Organisme organisme3 = new Organisme("Maxi ", "909 Boulevard Firestone, Joliette, QC J6E 2W4", mapCollectTime3, "(450) 752-0088", listDenree3);

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
        Organisme organisme4 = new Organisme("Marché IGA Bellevue ", " 560 Boulevard des Bois Francs S, Victoriaville, QC G6P 5X4", mapCollectTime4, "(819) 357-2241", listDenree4);

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
        Organisme organisme5 = new Organisme("Tigre Géant", " 800 Boulevard Thibeau, Trois-Rivières, QC G8T 7A6", mapCollectTime5, "(819) 697-3833", listDenree5);

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
        Organisme organisme6 = new Organisme("Provigo", "4545 Boulevard Henri-Bourassa, Ville de Québec, QC G1H 7L9", mapCollectTime6, "(418) 622-7070", listDenree6);

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
        listDenree1.add(new Denree("lait", "6", "litre", "9999-12-31", Denree.TypeDenree.laitier,"en bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime1 = new HashMap<>();
        mapCollectTime1.put("lundi", "12:00-14:00");
        mapCollectTime1.put("mardi", "9:00-16:00");
        mapCollectTime1.put("mercredi", "8:00-13:00");
        mapCollectTime1.put("jeudi", "9:00-14:00");
        mapCollectTime1.put("vendredi", "9:00-17:00");
        mapCollectTime1.put("samdi", "9:00-13:00");
        mapCollectTime1.put("dimanche", "ferme");
        Organisme organisme1 = new Organisme("Maxi&Cie ", "6825 Chemin de la Cote-des-Neiges, Montreal,QC H3S 2B6", mapCollectTime1, "(514) 734-1280", listDenree1);

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
        Organisme organisme2 = new Organisme("Rona", "7750 Boulevard des Hêtres, Shawinigan, QC G9N 4X4", mapCollectTime2, "(819) 537-3113", listDenree2);

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
        Organisme organisme3 = new Organisme("Maxi ", "909 Boulevard Firestone, Joliette, QC J6E 2W4", mapCollectTime3, "(450) 752-0088", listDenree3);

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
        Organisme organisme4 = new Organisme("Marché IGA Bellevue ", " 560 Boulevard des Bois Francs S, Victoriaville, QC G6P 5X4", mapCollectTime4, "(819) 357-2241", listDenree4);

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
        Organisme organisme5 = new Organisme("Tigre Géant", " 800 Boulevard Thibeau, Trois-Rivières, QC G8T 7A6", mapCollectTime5, "(819) 697-3833", listDenree5);

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
        Organisme organisme6 = new Organisme("Provigo", "4545 Boulevard Henri-Bourassa, Ville de Québec, QC G1H 7L9", mapCollectTime6, "(418) 622-7070", listDenree6);


        ArrayList<Denree> listDenree7 = new ArrayList<>();
        listDenree4.add(new Denree("tuna", "90", "kg", "9999-12-31", Denree.TypeDenree.surgele,"on bon etat,importee de usa"));
        HashMap<String, String> mapCollectTime7 = new HashMap<>();
        mapCollectTime7.put("lundi", "9:00-12:00");
        mapCollectTime7.put("mardi", "9:00-14:00");
        mapCollectTime7.put("mercredi", "10:00-14:00");
        mapCollectTime7.put("jeudi", "9:00-14:00");
        mapCollectTime7.put("vendredi", "9:00-16:00");
        mapCollectTime7.put("samdi", "9:00-13:00");
        mapCollectTime7.put("dimanche", "ferme");
        Organisme organisme7 = new Organisme("Maxi & Cie", "2478 Rue de la Laurentie, Sherbrooke, QC J1J 1L4", mapCollectTime4, "(819) 829-1000", listDenree4);
        //preparer les entreprise affichees sur carte
        ArrayList<Organisme> listOrganisme = new ArrayList<>();
        listOrganisme.add(organisme1);
        listOrganisme.add(organisme2);
        listOrganisme.add(organisme3);
        listOrganisme.add(organisme4);
        listOrganisme.add(organisme5);
        listOrganisme.add(organisme6);
        listOrganisme.add(organisme7);
        return listOrganisme;

    }
}
