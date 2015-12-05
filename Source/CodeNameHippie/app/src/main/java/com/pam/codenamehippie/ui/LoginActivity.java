package com.pam.codenamehippie.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.UtilisateurModele;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * Regex pour vérifier la validité de la syntaxe du champ courriel.
     */
    private final Pattern courrielPattern = Patterns.EMAIL_ADDRESS;
    private EditText courrielEditText;
    private EditText passwordEditText;
    private TextInputLayout courrielTextInputLayout;
    private SharedPreferences sharedPreferences;
    private final TextWatcher courrielTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            LoginActivity.this.validerCourriel();
        }
    };
    private TextInputLayout passwordTextInputLayout;
    private final TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            LoginActivity.this.validerMotDePasse();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.setSupportActionBar(toolbar);
        this.courrielEditText = ((EditText) this.findViewById(R.id.etCourriel));
        String rememberedEmail =
          this.sharedPreferences.getString(this.getString(R.string.pref_email_key), null);
        if ((rememberedEmail != null) && (this.courrielEditText != null)) {
            this.courrielEditText.setText(rememberedEmail);
        }
        String rememberedPassword =
          this.sharedPreferences.getString(this.getString(R.string.pref_password_key), null);
        this.passwordEditText = (EditText) this.findViewById(R.id.etPassword);
        if ((this.passwordEditText != null) && (rememberedPassword != null)) {
            this.passwordEditText.setText(rememberedPassword);
        }
        this.courrielTextInputLayout = (TextInputLayout) this.findViewById(R.id.tilCourriel);
        this.passwordTextInputLayout = (TextInputLayout) this.findViewById(R.id.tilPassword);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.courrielEditText != null) {
            this.courrielEditText.addTextChangedListener(this.courrielTextWatcher);
        }
        if (this.passwordEditText != null) {
            this.passwordEditText.addTextChangedListener(this.passwordTextWatcher);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.validerCourriel();
        this.validerMotDePasse();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.courrielEditText != null) {
            this.courrielEditText.removeTextChangedListener(this.courrielTextWatcher);
        }
        if (this.passwordEditText != null) {
            this.passwordEditText.removeTextChangedListener(this.passwordTextWatcher);
        }
    }

    private void validerCourriel() {
        if ((this.courrielEditText != null) && (this.courrielTextInputLayout != null)) {
            Editable text = this.courrielEditText.getText();
            String errorMessage = (!this.courrielPattern.matcher(text).matches()) ?
                                  this.getString(R.string.error_invalid_email) :
                                  null;
            this.courrielTextInputLayout.setError(errorMessage);
            if (errorMessage == null && !TextUtils.isEmpty(text)) {
                this.sharedPreferences.edit()
                                      .putString(this.getString(R.string.pref_email_key),
                                                 text.toString()
                                                )
                                      .commit();
            }
        }
    }

    private void validerMotDePasse() {
        if ((this.passwordEditText != null) && (this.passwordTextInputLayout != null)) {
            Editable text = this.passwordEditText.getText();
            boolean isEmpty = TextUtils.isEmpty(text);
            String errorMessage =
              (isEmpty) ? this.getString(R.string.error_invalid_password) : null;
            // TODO: Checker les contraintes de mots de passe.
            if (errorMessage == null && !TextUtils.isEmpty(text)) {
                this.sharedPreferences.edit()
                                      .putString(this.getString(R.string.pref_password_key),
                                                 text.toString()
                                                )
                                      .commit();
            }
            this.passwordTextInputLayout.setError(errorMessage);

        }
    }

    public void onClickLogin(final View v) {
        OkHttpClient client = ((HippieApplication) this.getApplication()).getHttpClient();
        RequestBody requestBody =
          new FormEncodingBuilder().add("courriel", this.courrielEditText.getText().toString())
                                   .add("mot_de_passe", this.passwordEditText.getText().toString())
                                   .build();
        Request request =
          new Request.Builder().url(HippieApplication.baseUrl)
                               .post(requestBody)
                               .build();
        final String prefPasswordKey = this.getString(R.string.pref_password_key);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(v, "Échec de connexion", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
                // On oublie le mot de passe. Parce qu'on a échoué.
                LoginActivity.this.sharedPreferences.edit()
                                                    .remove(prefPasswordKey)
                                                    .commit();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    UtilisateurModele modele = new Gson().fromJson(json, UtilisateurModele.class);
                    Log.d(TAG, "Json: " + json);
                    Log.d(TAG, "Modele: " + modele.toString());

                } else {
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(v, "Échec de connexion", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    });
                    // On oublie le mot de passe. Parce qu'on a échoué.
                    LoginActivity.this.sharedPreferences.edit()
                                                        .remove(prefPasswordKey)
                                                        .commit();
                }
            }
        });
    }
}
