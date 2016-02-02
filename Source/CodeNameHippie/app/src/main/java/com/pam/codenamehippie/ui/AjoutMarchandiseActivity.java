package com.pam.codenamehippie.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.controleur.validation.Validateur;
import com.pam.codenamehippie.controleur.validation.ValidateurDeChampTexte;
import com.pam.codenamehippie.controleur.validation.ValidateurDeSpinner;
import com.pam.codenamehippie.controleur.validation.ValidateurObserver;
import com.pam.codenamehippie.modele.AlimentaireModele;
import com.pam.codenamehippie.modele.DescriptionModel;
import com.pam.codenamehippie.modele.TypeAlimentaireModele;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.depot.AlimentaireModeleDepot.PeuplerListesDeSpinnerListener;
import com.pam.codenamehippie.ui.adapter.HippieSpinnerAdapter;
import com.pam.codenamehippie.ui.adapter.TypeAlimentaireModeleSpinnerAdapter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Cette classe permet à un donneur d'ajouter et modifier des produits à la base de données
 * via l'interface utilisateur. La date du jour sera utilisée comme date de disponibilité.
 * Si un produit n'a pas de date de péremption, la date sera mise à null du côté du serveur.
 */
public class AjoutMarchandiseActivity extends HippieActivity
        implements ValidateurObserver, PeuplerListesDeSpinnerListener {

    private static final String SELECTED_SPINNER_TYPE_POSITION = "position_type";
    private static final String SELECTED_SPINNER_UNITE_POSITION = "position_unite";

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
    private DatePicker datePeremption;
    private Button bAjoutMarchandise;
    private boolean spinnerUniteMarchandiseEstValide;
    private boolean spinnerTypeMarchandiseEstValide;
    private boolean datePeremptionEstValide;
    private TextView tvDatePeremption;
    // Id de alimentaire pour sélection route modifier ou ajouter
    private Integer idModele = null;
    // Id de l'organisme dont l'utilisateur est membre.
    private Integer organismeId;
    private int selectedSpinnerUnitePosition;
    private int selectedSpinnerTypePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.ajout_marchandise);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
        // *********************************************************************************
        // Chaque EditText passe par le ValidateurDeChampTexte et doit répondre à certains *
        // critères dont la longeur max et si le champ est requis                          *
        // *********************************************************************************
        EditText etNomMarchandise = (EditText) this.findViewById(R.id.etNomMarchandise);
        this.validateurNom =
                ValidateurDeChampTexte.newInstance(this,
                                                   etNomMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .NOM_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurNom.registerObserver(this);
        EditText etDescMarchandise = (EditText) this.findViewById(R.id.etDescMarchandise);
        this.validateurDescription =
                ValidateurDeChampTexte.newInstance(this,
                                                   etDescMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .DESCRIPTION_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurDescription.registerObserver(this);
        EditText etQteeMarchandise = (EditText) this.findViewById(R.id.etQteeMarchandise);
        this.validateurQuantite =
                ValidateurDeChampTexte.newInstance(this,
                                                   etQteeMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .QUANTITE_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurQuantite.registerObserver(this);
        EditText etValeurMarchandise = (EditText) this.findViewById(R.id.etValeurMarchandise);
        this.validateurValeur =
                ValidateurDeChampTexte.newInstance(this,
                                                   etValeurMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .VALEUR_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
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
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
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
        this.tvDatePeremption = (TextView) this.findViewById(R.id.tvDatePeremption);
        this.datePeremption = (DatePicker) this.findViewById(R.id.datePicker);
        // Set la date minimale du date picker au moment présent.

        TextView tvAjoutMarchandise = (TextView) this.findViewById(R.id.tvAjoutMarchandise);
        tvAjoutMarchandise.setText(R.string.ajouter_marchandise);
        this.bAjoutMarchandise = (Button) this.findViewById(R.id.bAjoutMarchandise);
        this.bAjoutMarchandise.setText(R.string.bouton_ajouter);

        // Retrouve l'organisme id de shared pref. -1 signifie qu'il n'y a pas d'organisme.
        this.organismeId = this.sharedPreferences.getInt(this.getString(R.string.pref_org_id_key),
                                                         -1
                                                        );

        // Provient de l'Intent de ListeMesDonsActivity lors du clic sur modifier un produit
        Bundle bundle = this.getIntent().getExtras();
        // Si le Bundle n'est pas null, il s'agit d'une modification à faire sur un don.
        if (bundle != null) {
            // Modifier le TextView pour signifier une modification
            tvAjoutMarchandise.setText(R.string.modifier_marchandise);
            this.bAjoutMarchandise.setText(R.string.bouton_modifier);
            // Obtenir le id du produit à modifier
            this.idModele = bundle.getInt("id");
            etNomMarchandise.setText(bundle.getCharSequence("nom"));
            etDescMarchandise.setText(bundle.getCharSequence("description"));
            etQteeMarchandise.setText(bundle.getCharSequence("quantite"));
            etValeurMarchandise.setText(bundle.getCharSequence("valeur"));

            // Récupérer la datePeremption du bundle pour fixer la date du DatePicker
            String dateString = bundle.getString("datePeremption");
            if (dateString != null) {
                DateFormat df = android.text.format.DateFormat.getLongDateFormat(this);
                Date date = null;
                try {
                    date = df.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    this.datePeremption.init(calendar.get(Calendar.YEAR),
                                             calendar.get(Calendar.MONTH),
                                             calendar.get(Calendar.DAY_OF_MONTH),
                                             null
                                            );

                }
            }
        }
        if (savedInstanceState != null) {
            this.selectedSpinnerTypePosition =
                    savedInstanceState.getInt(SELECTED_SPINNER_TYPE_POSITION, 0);
            this.selectedSpinnerUnitePosition =
                    savedInstanceState.getInt(SELECTED_SPINNER_UNITE_POSITION, 0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_SPINNER_UNITE_POSITION,
                        this.validateurSpinnerUniteMarchandise.getSelectedItemPosition()
                       );
        outState.putInt(SELECTED_SPINNER_TYPE_POSITION,
                        this.validateurSpinnerTypeMarchandise.getSelectedItemPosition()
                       );
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
    protected void onResume() {
        super.onResume();
        this.validateurNom.onResume();
        this.validateurDescription.onResume();
        this.validateurQuantite.onResume();
        this.validateurValeur.onResume();
        AlimentaireModeleDepot depot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        depot.peuplerLesListesDeSpinners(this);
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
        } else if (validateur.equals(this.validateurDescription)) {
            this.descriptionEstValide = estValide;
        } else if (validateur.equals(this.validateurQuantite)) {
            this.quantiteEstValide = estValide;
        } else if (validateur.equals(this.validateurValeur)) {
            this.valeurEstValide = estValide;
        } else if (validateur.equals(this.validateurSpinnerUniteMarchandise)) {
            this.spinnerUniteMarchandiseEstValide = estValide;
        } else if (validateur.equals(this.validateurSpinnerTypeMarchandise)) {
            // Mettre invisible le DatePicker si un produit est non perissable
            if (((TypeAlimentaireModele) this.validateurSpinnerTypeMarchandise.getSelectedItem())
                        .getEstPerissable() ||
                this.validateurSpinnerTypeMarchandise.getSelectedItemId() == 0) {
                this.tvDatePeremption.setVisibility(View.VISIBLE);
                this.datePeremption.setVisibility(View.VISIBLE);
            } else {
                this.tvDatePeremption.setVisibility(View.GONE);
                this.datePeremption.setVisibility(View.GONE);
            }
            this.spinnerTypeMarchandiseEstValide = estValide;
        }
        // Check si on fait parti d'un organisme...
        // FIXME: Ce check devrait etre fait au serveur.
        boolean hasOrganismeid = (this.organismeId != -1);

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

    /**
     * Méthode pour soumettre une requête afin d'ajouter un produit dans la base de données sur le
     * serveur.
     *
     * @param v
     */
    public void soumettreMarchandise(final View v) {
        //TODO: soumettre la marchandise au serveur selon les paramètres TransactionModele
        // Prend la date du jour pour soumettre l'ajout ou modification d'un item
        Calendar date = Calendar.getInstance();
        date.set(this.datePeremption.getYear(),
                 this.datePeremption.getMonth(),
                 this.datePeremption.getDayOfMonth()
                );

        DescriptionModel typeAlimentaire =
                ((DescriptionModel) this.validateurSpinnerTypeMarchandise.getSelectedItem());
        AlimentaireModele modele =
                new AlimentaireModele().setNom(this.validateurNom.getTextString())
                                       .setDescription(this.validateurDescription.getTextString())
                                       .setValeur(Integer.valueOf(this.validateurValeur
                                                                          .getTextString()))
                                       .setQuantite(Double.valueOf(this.validateurQuantite
                                                                           .getTextString()))
                                       .setTypeAlimentaire(typeAlimentaire.getDescription())
                                       .setDatePeremption(date.getTime());

        String typeAlimentaireId =
                String.valueOf(this.validateurSpinnerTypeMarchandise.getSelectedItemId());
        String marchandiseUniteId =
                String.valueOf(this.validateurSpinnerUniteMarchandise.getSelectedItemId());

        @SuppressLint("SimpleDateFormat")
        String dateString =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(modele.getDatePeremption());
        AlimentaireModeleDepot depot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        // Construction du url pour un ajout de marchandise
        HttpUrl url = depot.getUrl().newBuilder().addPathSegment("ajout").build();
        // FIXME: Gérer l'état de marchandise. On mets 3(neuf) en attendant
        FormBody.Builder body =
                new FormBody.Builder().add("description_alimentaire", modele.getDescription())
                                      .add("nom", modele.getNom())
                                      .add("quantite", modele.getQuantite().toString())
                                      .add("valeur", modele.getValeur().toString())
                                      .add("type_alimentaire", typeAlimentaireId)
                                      .add("marchandise_unite", marchandiseUniteId)
                                      .add("marchandise_etat", "3")
                                      .add("date_peremption", dateString)
                                      .add("donneur_id", this.organismeId.toString());

        if (this.idModele != null) {
            // Si le idModele est différent de null, il s'agit d'une modification sur le produit
            // et il faut ajouter ce idModele à la requête et modifier le url en conséquence
            body.add("id", this.idModele.toString());
            url = depot.getUrl().newBuilder().addPathSegment("modifier").build();
        }
        // Construire et envoyer la requête au serveur avec un Callback
        Request request = new Request.Builder().url(url).post(body.build()).build();
        this.httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AjoutMarchandiseActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(v, R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    switch (response.code()) {
                        default:
                            AjoutMarchandiseActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(v,
                                                  R.string.error_connection,
                                                  Snackbar.LENGTH_SHORT
                                                 )
                                            .show();
                                }
                            });
                            break;
                    }
                } else {
                    AjoutMarchandiseActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (AjoutMarchandiseActivity.this.idModele != null) {
                                // C'est une modification au produit
                                Snackbar snackbar = Snackbar.make(v,
                                                                  R.string.msg_produit_modifie,
                                                                  Snackbar.LENGTH_SHORT
                                                                 );

                                snackbar.setCallback(new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(Snackbar snackbar, int event) {
                                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                            AjoutMarchandiseActivity.this.finish();
                                        }
                                    }
                                }).show();

                            } else {
                                // C'est un ajout de produit
                                Snackbar.make(v,
                                              R.string.msg_produit_ajoute,
                                              Snackbar.LENGTH_SHORT
                                             ).show();
                                AjoutMarchandiseActivity.this.effacerFormulaire();
                            }
                        }
                    });
                }
            }
        });

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
        Calendar calendar = Calendar.getInstance();
        this.datePeremption.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                       calendar.get(Calendar.DAY_OF_MONTH)
                                      );
        this.tvDatePeremption.setVisibility(View.VISIBLE);
        this.datePeremption.setVisibility(View.VISIBLE);
    }

    @Override
    public void surDebut() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surErreur(IOException e) {
        Log.e(this.getClass().getSimpleName(), "Erreur peuplement spinner", e);
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
}
