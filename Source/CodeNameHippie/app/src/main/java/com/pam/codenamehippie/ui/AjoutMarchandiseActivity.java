package com.pam.codenamehippie.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.validation.Validateur;
import com.pam.codenamehippie.controleur.validation.ValidateurDeChampTexte;
import com.pam.codenamehippie.controleur.validation.ValidateurDeSpinner;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.http.exception.HttpReponseException;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.DescriptionModel;
import com.pam.codenamehippie.modele.OrganismeModele;
import com.pam.codenamehippie.modele.TypeAlimentaireModele;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot.PeuplerListesDeSpinnerListener;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.ui.adapter.HippieSpinnerAdapter;
import com.pam.codenamehippie.ui.adapter.TypeAlimentaireModeleSpinnerAdapter;
import com.pam.codenamehippie.ui.view.dialog.CalendarPickerViewDialogFragment;
import com.pam.codenamehippie.ui.view.dialog.CalendarPickerViewDialogFragment.OnDismissListener;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cette classe permet à un donneur d'ajouter et modifier des produits à la base de données
 * via l'interface utilisateur. La date du jour sera utilisée comme date de disponibilité.
 * Si un produit n'a pas de date de péremption, la date sera mise à null du côté du serveur.
 */
public class AjoutMarchandiseActivity extends HippieActivity
        implements ValidateurObserver,
                   PeuplerListesDeSpinnerListener,
                   ObservateurDeDepot<AlimentaireModele>,
                   OnDateSelectedListener,
                   OnDismissListener {

    public static final String MODELE_ID = "id";
    private static final String SELECTED_SPINNER_TYPE_POSITION = "position_type";
    private static final String SELECTED_SPINNER_UNITE_POSITION = "position_unite";
    private static final String SAVED_MODEL = "modele";
    private ValidateurDeChampTexte validateurNom;
    private boolean nomEstValide;
    private ValidateurDeChampTexte validateurDescription;
    private boolean descriptionEstValide;
    private ValidateurDeChampTexte validateurQuantite;
    private boolean quantiteEstValide;
    private ValidateurDeChampTexte validateurValeur;
    private boolean valeurEstValide;
    private ValidateurDeSpinner validateurSpinnerUniteMarchandise;
    private ValidateurDeSpinner validateurSpinnerTypeMarchandise;
    private CalendarPickerViewDialogFragment datePeremptionFragment;
    private Button bAjoutMarchandise;
    private boolean spinnerUniteMarchandiseEstValide;
    private boolean spinnerTypeMarchandiseEstValide;
    private boolean datePeremptionEstValide;
    private TextView datePicker;
    private TextView tvDatePeremption;
    // Id de alimentaire pour sélection route modifier ou ajouter
    private AlimentaireModele modele;
    // Id de l'organisme dont l'utilisateur est membre.
    private Integer organismeId;
    private int selectedSpinnerUnitePosition;
    private int selectedSpinnerTypePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ajout_marchandise);

        // *********************************************************************************
        // Chaque EditText passe par le ValidateurDeChampTexte et doit répondre à certains *
        // critères dont la longeur max et si le champ est requis                          *
        // *********************************************************************************
        EditText etNomMarchandise = (EditText) this.findViewById(R.id.etNomMarchandise);
        this.validateurNom = ValidateurDeChampTexte.newInstance(this, etNomMarchandise, true,
                                                                ValidateurDeChampTexte
                                                                        .NOM_ALIMENTAIRE_LONGUEUR_MAX);
        this.validateurNom.registerObserver(this);
        EditText etDescMarchandise = (EditText) this.findViewById(R.id.etDescMarchandise);
        this.validateurDescription =
                ValidateurDeChampTexte.newInstance(this, etDescMarchandise, true,
                                                   ValidateurDeChampTexte
                                                           .DESCRIPTION_ALIMENTAIRE_LONGUEUR_MAX);
        this.validateurDescription.registerObserver(this);
        EditText etQteeMarchandise = (EditText) this.findViewById(R.id.etQteeMarchandise);
        this.validateurQuantite =
                ValidateurDeChampTexte.newInstance(this, etQteeMarchandise, true,
                                                   ValidateurDeChampTexte
                                                           .QUANTITE_ALIMENTAIRE_LONGUEUR_MAX);
        this.validateurQuantite.setEstNumerique(true);
        this.validateurQuantite.registerObserver(this);
        EditText etValeurMarchandise = (EditText) this.findViewById(R.id.etValeurMarchandise);
        this.validateurValeur = ValidateurDeChampTexte.newInstance(this, etValeurMarchandise, true,
                                                                   ValidateurDeChampTexte
                                                                           .VALEUR_ALIMENTAIRE_LONGUEUR_MAX);
        this.validateurValeur.setEstNumerique(true);
        this.validateurValeur.registerObserver(this);
        // ***************************************************************************************
        // Chaque Spinner passe par le ValidateurDeSpinner et le choix doit être différent de la *
        // valeur par défaut ("Faites votre choix...") pour qu'il soit valide                    *
        // ***************************************************************************************
        Spinner spinnerUniteMarchandise = (Spinner) this.findViewById(R.id.spinnerUniteMarchandise);
        this.validateurSpinnerUniteMarchandise =
                ValidateurDeSpinner.newInstance(spinnerUniteMarchandise);
        this.validateurSpinnerUniteMarchandise.registerObserver(this);
        // Binder les descriptions d'uniteMarchandise au spinnerUniteMarchandise
        HippieSpinnerAdapter uniteAdapter = new HippieSpinnerAdapter(this);
        spinnerUniteMarchandise.setAdapter(uniteAdapter);

        Spinner spinnerTypeMarchandise = (Spinner) this.findViewById(R.id.spinnerTypeMarchandise);
        this.validateurSpinnerTypeMarchandise =
                ValidateurDeSpinner.newInstance(spinnerTypeMarchandise);
        this.validateurSpinnerTypeMarchandise.registerObserver(this);
        // Binder les descriptions de typeMarchandise au spinnerTypeMarchandise
        TypeAlimentaireModeleSpinnerAdapter typeAdapter =
                new TypeAlimentaireModeleSpinnerAdapter(this);
        spinnerTypeMarchandise.setAdapter(typeAdapter);
        this.datePicker = (TextView) this.findViewById(R.id.datePicker);
        this.tvDatePeremption = (TextView) this.findViewById(R.id.tvDatePeremption);
        TextView tvAjoutMarchandise = (TextView) this.findViewById(R.id.tvAjoutMarchandise);
        tvAjoutMarchandise.setText(R.string.ajouter_marchandise);
        this.bAjoutMarchandise = (Button) this.findViewById(R.id.bAjoutMarchandise);
        this.bAjoutMarchandise.setText(R.string.bouton_ajouter);

        // Provient de l'Intent de ListeMesDonsActivity lors du clic sur modifier un produit
        Bundle bundle = this.getIntent().getExtras();
        // Si le Bundle n'est pas null, il s'agit d'une modification à faire sur un don.
        int id = 0;
        if (bundle != null) {
            // Modifier le TextView pour signifier une modification
            tvAjoutMarchandise.setText(R.string.modifier_marchandise);
            this.bAjoutMarchandise.setText(R.string.bouton_modifier);
            // Obtenir le id du produit à modifier
            id = bundle.getInt(MODELE_ID, 0);
        }
        if (savedInstanceState != null) {
            this.selectedSpinnerTypePosition =
                    savedInstanceState.getInt(SELECTED_SPINNER_TYPE_POSITION, 0);
            this.selectedSpinnerUnitePosition =
                    savedInstanceState.getInt(SELECTED_SPINNER_UNITE_POSITION, 0);
            String json = savedInstanceState.getString(SAVED_MODEL);
            if (json != null) {
                Log.d("Test", json);
                this.modele = DepotManager.getInstance().getAlimentaireModeleDepot().fromJson(json);
            }
        }
        this.modele = (this.modele == null) ? new AlimentaireModele() : this.modele;
        UtilisateurModele uc = this.authentificateur.getUtilisateur();
        OrganismeModele org = (uc != null) ? uc.getOrganisme() : null;
        this.modele.setId(id).setOrganisme(org);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AlimentaireModeleDepot depot = DepotManager.getInstance().getAlimentaireModeleDepot();
        depot.peuplerLesListesDeSpinners(this);
        if (this.modele.getId() != 0) {
            depot.rechercherParId(this.modele.getId());
        }
        this.afficherModele(this.modele);
        this.validateurNom.onResume();
        this.validateurDescription.onResume();
        this.validateurQuantite.onResume();
        this.validateurValeur.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.validateurNom.onPause();
        this.validateurDescription.onPause();
        this.validateurQuantite.onPause();
        this.validateurValeur.onPause();
        this.validateurSpinnerUniteMarchandise.onPause();
        this.validateurSpinnerTypeMarchandise.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_SPINNER_UNITE_POSITION,
                        this.validateurSpinnerUniteMarchandise.getSelectedItemPosition());
        outState.putInt(SELECTED_SPINNER_TYPE_POSITION,
                        this.validateurSpinnerTypeMarchandise.getSelectedItemPosition());
        if (this.modele != null) {
            String json =
                    DepotManager.getInstance().getAlimentaireModeleDepot().toJson(this.modele);
            outState.putString(SAVED_MODEL, json);
        }
    }

    /**
     * Méthode pour valider les différentes composantes de l'interface utilisateur selon
     * le type de validation et les valeurs inscrites.
     *
     * @param validateur
     *         Type de validateur. Ex: ValidateurDeChampTexte, ValidateurDeSpinner etc...
     * @param estValide
     *         Retourne True or False selon la validation.
     */
    @Override
    public void enValidant(Validateur validateur, boolean estValide) {
        if (validateur.equals(this.validateurNom)) {
            this.nomEstValide = estValide;
            if (estValide) {
                this.modele.setNom(this.validateurNom.getTextString());
            }
        } else if (validateur.equals(this.validateurDescription)) {
            this.descriptionEstValide = estValide;
        } else if (validateur.equals(this.validateurQuantite)) {
            this.quantiteEstValide = (estValide);
        } else if (validateur.equals(this.validateurValeur)) {
            this.valeurEstValide = estValide;
        } else if (validateur.equals(this.validateurSpinnerUniteMarchandise)) {
            this.spinnerUniteMarchandiseEstValide = estValide;
        } else if (validateur.equals(this.validateurSpinnerTypeMarchandise)) {
            // Mettre invisible le DatePicker si un produit est non perissable
            TypeAlimentaireModele item =
            (TypeAlimentaireModele) this.validateurSpinnerTypeMarchandise.getSelectedItem())
            if ((item != null && item.getEstPerissable()) ||
                this.validateurSpinnerTypeMarchandise.getSelectedItemId() == 0) {
                this.tvDatePeremption.setVisibility(View.VISIBLE);
                this.datePicker.setVisibility(View.VISIBLE);
            } else {
                this.tvDatePeremption.setVisibility(View.GONE);
                this.datePicker.setVisibility(View.GONE);
            }
            this.spinnerTypeMarchandiseEstValide = estValide;
        }
        // Check si on fait parti d'un organisme...
        // FIXME: Ce check devrait etre fait au serveur.
        boolean hasOrganismeid = (this.modele.getOrganisme() != null);

        // Mettre le bouton pour ajouter la marchandise actif si tous les champs requis
        // respecte les conditions des validateurs.
        this.bAjoutMarchandise.setEnabled(this.nomEstValide &&
                                          this.descriptionEstValide &&
                                          this.quantiteEstValide &&
                                          this.valeurEstValide &&
                                          this.spinnerUniteMarchandiseEstValide &&
                                          this.spinnerTypeMarchandiseEstValide &&
                                          hasOrganismeid);
    }

    @Override
    public void surDebut() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();

    }

    @Override
    public void surChangementDeDonnees(List<AlimentaireModele> modeles) {
        if ((modeles != null) && (modeles.size() != 0)) {
            this.modele = modeles.get(0);
            if (this.modele != null) {
                this.afficherModele(this.modele);
            }
        }
    }

    @Override
    public void surFinDeRequete() {
        this.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
        Snackbar snackbar;
        if (e instanceof HttpReponseException) {
            switch (((HttpReponseException) e).getCode()) {
                case 404:
                    snackbar = Snackbar.make(this.viewSwitcher, R.string.error_http_404,
                                             Snackbar.LENGTH_SHORT);
                    break;
                case 500:
                    snackbar = Snackbar.make(this.viewSwitcher, R.string.error_http_500,
                                             Snackbar.LENGTH_SHORT);
                    break;
                default:
                    snackbar = Snackbar.make(this.viewSwitcher, R.string.error_connection,
                                             Snackbar.LENGTH_SHORT);
                    break;

            }
        } else {
            snackbar = Snackbar.make(this.viewSwitcher, R.string.error_connection,
                                     Snackbar.LENGTH_SHORT);
        }
        snackbar.show();
    }

    @Override
    public void surListeUnite(ArrayList<DescriptionModel> items) {
        Spinner spinner = this.validateurSpinnerUniteMarchandise.getSpinner();
        HippieSpinnerAdapter adapter = ((HippieSpinnerAdapter) spinner.getAdapter());
        adapter.setItems(items);
        Bundle bundle = this.getIntent().getExtras();
        // Récupérer la position du spinnerUniteMarchandise selon la description si mode modifier
        if (bundle != null) {
            String s = bundle.getString("unite");
            if (s != null) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    String description = adapter.getItem(i).getDescription();
                    if (s.equalsIgnoreCase(description)) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        } else {
            spinner.setSelection(this.selectedSpinnerUnitePosition);
        }
    }

    @Override
    public void surListeType(ArrayList<TypeAlimentaireModele> items) {
        Spinner spinner = this.validateurSpinnerTypeMarchandise.getSpinner();
        TypeAlimentaireModeleSpinnerAdapter adapter =
                ((TypeAlimentaireModeleSpinnerAdapter) spinner.getAdapter());
        adapter.setItems(items);
        // Récupérer la position du spinnerTypeMarchandise selon la description si mode modifier
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            String s = bundle.getString("typeAlimentaire");
            if (s != null) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    String description = adapter.getItem(i).getDescription();
                    if (s.equalsIgnoreCase(description)) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        } else {
            spinner.setSelection(this.selectedSpinnerTypePosition);
        }
    }

    @Override
    public void surFin() {
        this.cacherLaProgressbar();
        this.validateurSpinnerUniteMarchandise.onResume();
        this.validateurSpinnerTypeMarchandise.onResume();
    }

    @Override
    public void onDateSelected(Date date) {
        DateFormat df = android.text.format.DateFormat.getLongDateFormat(this);
        this.datePicker.setText(df.format(date));
        if (this.modele != null) {
            this.modele.setDatePeremption(date);
        }
    }

    @Override
    public void onDismiss(CalendarPickerViewDialogFragment fragment, DialogInterface dialog) {
        this.onDateSelected(fragment.dateSelectionee());
    }

    @Override
    public void onDateUnselected(Date date) {

    }

    public void surDatePickerClick(View v) {
        if (this.datePeremptionFragment == null) {
            Date date = (this.modele.getDatePeremption() != null)
                        ? this.modele.getDatePeremption()
                        : new Date();
            this.datePeremptionFragment = this.newDatePicker(date);
        }
        if (!this.datePeremptionFragment.isVisible()) {
            this.datePeremptionFragment.show(this.getSupportFragmentManager(), null);
        }
    }

    /**
     * Méthode pour soumettre une requête afin d'ajouter un produit dans la base de données sur le
     * serveur.
     *
     * @param v
     *         Vue qui a été cliquêe.
     */
    public void soumettreMarchandise(final View v) {
        DescriptionModel typeAlimentaire =
                ((DescriptionModel) this.validateurSpinnerTypeMarchandise.getSelectedItem());
        String typeAlimentaireId =
                String.valueOf(this.validateurSpinnerTypeMarchandise.getSelectedItemId());
        String marchandiseUniteId =
                String.valueOf(this.validateurSpinnerUniteMarchandise.getSelectedItemId());
        // FIXME: Gérer l'état de marchandise. On mets 3(neuf) en attendant
        this.modele.setNom(this.validateurNom.getTextString())
                   .setDescription(this.validateurDescription.getTextString())
                   .setValeur(Double.parseDouble(this.validateurValeur.getTextString()))
                   .setQuantite(Double.parseDouble(this.validateurQuantite.getTextString()))
                   .setUniteDeQuantite(marchandiseUniteId)
                   .setEtat("3")
                   .setTypeAlimentaire(typeAlimentaireId);
        if (((TypeAlimentaireModele) this.validateurSpinnerTypeMarchandise.getSelectedItem())
                    .getEstPerissable() && this.modele.getDatePeremption() == null) {
            this.modele.setDatePeremption(new Date());
        }
        AlimentaireModeleDepot depot = DepotManager.getInstance().getAlimentaireModeleDepot();
        if ((this.modele.getId() != null) && (this.modele.getId() != 0)) {
            // Si le id est différent de null, il s'agit d'une modification sur le produit
            // et il faut agir en conséquence.
            depot.modifierModele(this.modele, new Runnable() {
                @Override
                public void run() {
                    Snackbar snackbar = Snackbar.make(v, R.string.msg_produit_modifie,
                                                      Snackbar.LENGTH_SHORT);

                    snackbar.setCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                AjoutMarchandiseActivity.this.finish();
                            }
                        }
                    }).show();
                }
            });
        } else {
            depot.ajouterModele(this.modele, new Runnable() {
                @Override
                public void run() {
                    // C'est un ajout de produit
                    Snackbar.make(v, R.string.msg_produit_ajoute, Snackbar.LENGTH_SHORT)
                            .show();
                    AjoutMarchandiseActivity.this.effacerFormulaire();
                }
            });
        }
    }

    /**
     * Méthode pour réinitialiser les champs du formulaire.
     */
    private void effacerFormulaire() {
        this.validateurNom.setText(null);
        this.validateurDescription.setText(null);
        this.validateurQuantite.setText(null);
        this.validateurSpinnerUniteMarchandise.setSelectedItemId(0);
        this.validateurValeur.setText(null);
        this.validateurSpinnerTypeMarchandise.setSelectedItemId(0);
        this.tvDatePeremption.setVisibility(View.VISIBLE);
        this.datePicker.setVisibility(View.VISIBLE);
        this.onDateSelected(new Date());
    }

    private void afficherModele(@NonNull AlimentaireModele modele) {
        this.validateurNom.setText(modele.getNom());
        this.validateurDescription.setText(modele.getDescription());
        this.validateurQuantite.setText(modele.getQuantite().toString());
        this.validateurValeur.setText(modele.getValeur().toString());
        DateFormat df = android.text.format.DateFormat.getLongDateFormat(this);
        Date date = (modele.getDatePeremption() != null)
                    ? modele.getDatePeremption()
                    : new Date();
        this.datePicker.setText(df.format(date));
        this.datePeremptionFragment = this.newDatePicker(date);

    }

    private CalendarPickerViewDialogFragment newDatePicker(@NonNull Date date) {
        return CalendarPickerViewDialogFragment.assigneUnNouveauFragment()
                                               .avecCetteDateSelectionnee(date)
                                               .pisCestTout()
                                               .setOnDateSelectedListener(this)
                                               .setOnDismissListener(this);
    }
}
