package com.pam.codenamehippie.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.pam.codenamehippie.modele.AlimentaireModeleDepot;
import com.pam.codenamehippie.modele.DescriptionModel;
import com.pam.codenamehippie.modele.TypeAlimentaireModele;
import com.pam.codenamehippie.ui.adapter.HippieSpinnerAdapter;
import com.pam.codenamehippie.ui.adapter.TypeAlimentaireModeleSpinnerAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Cette classe permet à un donneur d'ajouter et modifier des produits à la base de données
 * via l'interface utilisateur. La date du jour sera utilisée comme date de disponibilité.
 * Si un produit n'a pas de date de péremption, la date sera mise à null du côté du serveur.
 */
public class AjoutMarchandiseActivity extends HippieActivity
        implements ValidateurObserver {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.ajout_marchandise);
        this.httpClient = ((HippieApplication) this.getApplication()).getHttpClient();
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

        Spinner spinnerUniteMarchandise = (Spinner) this.findViewById(R.id.spinnerUniteMarchandise);
        this.validateurSpinnerUniteMarchandise =
                ValidateurDeSpinner.newInstance(spinnerUniteMarchandise);
        this.validateurSpinnerUniteMarchandise.registerObserver(this);
        AlimentaireModeleDepot alimentaireModeleDepot =
                ((HippieApplication) this.getApplication()).getAlimentaireModeleDepot();
        HippieSpinnerAdapter uniteAdapter =
                new HippieSpinnerAdapter(this, alimentaireModeleDepot.getListeUnitee());
        spinnerUniteMarchandise.setAdapter(uniteAdapter);

        EditText etValeurMarchandise = (EditText) this.findViewById(R.id.etValeurMarchandise);
        this.validateurValeur =
                ValidateurDeChampTexte.newInstance(this,
                                                   etValeurMarchandise,
                                                   true,
                                                   ValidateurDeChampTexte
                                                           .VALEUR_ALIMENTAIRE_LONGUEUR_MAX
                                                  );
        this.validateurValeur.registerObserver(this);

        Spinner spinnerTypeMarchandise = (Spinner) this.findViewById(R.id.spinnerTypeMarchandise);
        this.validateurSpinnerTypeMarchandise =
                ValidateurDeSpinner.newInstance(spinnerTypeMarchandise);
        this.validateurSpinnerTypeMarchandise.registerObserver(this);
        TypeAlimentaireModeleSpinnerAdapter typeAdapter =
                new TypeAlimentaireModeleSpinnerAdapter(this,
                                                        alimentaireModeleDepot
                                                                .getListeTypeAlimentaire()
                );
        spinnerTypeMarchandise.setAdapter(typeAdapter);
        this.tvDatePeremption = (TextView) this.findViewById(R.id.tvDatePeremption);
        this.datePeremption = (DatePicker) this.findViewById(R.id.datePicker);
        // Set la date minimale du date picker au moment présent.

        TextView tvAjoutMarchandise = (TextView) this.findViewById(R.id.tvAjoutMarchandise);
        tvAjoutMarchandise.setText(R.string.ajouter_marchandise);
        //tvAjoutMarchandise.setText("Ajout de marchandise");
        this.bAjoutMarchandise = (Button) this.findViewById(R.id.bAjoutMarchandise);
        // FIXME: mettre ressource string losrqu'elle sera disponible
        this.bAjoutMarchandise.setText(R.string.bouton_ajouter);
        //bAjoutMarchandise.setText("Ajouter");
        // Retrouve l'organisme id de shared pref. -1 signifie qu'il n'y a pas d'organisme.
        this.organismeId = this.sharedPreferences.getInt(this.getString(R.string.pref_org_id_key),
                                                         -1
                                                        );

        Bundle bundle = this.getIntent().getExtras();

        // Si le Bundle n'est pas null, il s'agit d'une modification à faire sur un don.
        if (bundle != null) {
            // FIXME: mettre ressource string losrqu'elle sera disponible
            // Modifier le TextView pour signifier une modification
            tvAjoutMarchandise.setText(R.string.modifier_marchandise);
            this.bAjoutMarchandise.setText(R.string.bouton_modifier);
            // Obtenir le id du produit à modifier

            this.idModele = bundle.getInt("id");
            etNomMarchandise.setText(bundle.getCharSequence("nom"));
            etDescMarchandise.setText(bundle.getCharSequence("description"));
            etQteeMarchandise.setText(bundle.getCharSequence("quantite"));
            etValeurMarchandise.setText(bundle.getCharSequence("valeur"));

            // Récupérer la position du spinnerUniteMarchandise selon la description
            String bundleDesc = bundle.getString("unite");

            if (bundleDesc != null) {
                for (int i = 0; i < uniteAdapter.getCount(); i++) {
                    String uniteDescription = uniteAdapter.getItem(i).getDescription();
                    if (bundleDesc.equalsIgnoreCase(uniteDescription)) {
                        spinnerUniteMarchandise.setSelection(i);
                        break;
                    }
                }
            }

            // Récupérer la position du spinnerTypeMarchandise selon la description
            String bundleTypeAlimentaire = bundle.getString("typeAlimentaire");
            if (bundleTypeAlimentaire != null) {
                for (int i = 0; i < typeAdapter.getCount(); i++) {
                    String typeDescription = typeAdapter.getItem(i).getDescription();
                    if (bundleTypeAlimentaire.equalsIgnoreCase(typeDescription)) {
                        spinnerTypeMarchandise.setSelection(i);
                        break;
                    }
                }
            }

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
        this.validateurSpinnerUniteMarchandise.onResume();
        this.validateurSpinnerTypeMarchandise.onResume();
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
    public void enValidatant(Validateur validateur, boolean estValide) {
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
        FormEncodingBuilder body =
                new FormEncodingBuilder().add("description_alimentaire", modele.getDescription())
                                         .add("nom", modele.getNom())
                                         .add("quantite", modele.getQuantite().toString())
                                         .add("valeur", modele.getValeur().toString())
                                         .add("type_alimentaire", typeAlimentaireId)
                                         .add("marchandise_unite", marchandiseUniteId)
                                         .add("marchandise_etat", "3")
                                         .add("date_peremption", dateString)
                                         .add("donneur_id", this.organismeId.toString());
        if (this.idModele != null) {
            body.add("id", this.idModele.toString());
            url = depot.getUrl().newBuilder().addPathSegment("modifier").build();
        }
        Request request = new Request.Builder().url(url).post(body.build()).build();
        this.httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AjoutMarchandiseActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(v, R.string.error_connection, Snackbar.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Response response) {
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
}
