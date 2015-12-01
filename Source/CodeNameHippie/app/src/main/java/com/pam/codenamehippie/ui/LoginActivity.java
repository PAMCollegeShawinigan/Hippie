package com.pam.codenamehippie.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pam.codenamehippie.R;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    /**
     * Regex pour vérifier la validité de la syntaxe du champ courriel.
     */
    private final Pattern courrielPattern = Pattern.compile("^(\\w.+)@+(\\w.+)");
    private EditText courrielEditText;
    private EditText passwordEditText;
    private TextInputLayout courrielTextInputLayout;
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
        this.setSupportActionBar(toolbar);
        this.courrielEditText = ((EditText) this.findViewById(R.id.etCourriel));
        this.passwordEditText = (EditText) this.findViewById(R.id.etPassword);
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
        }
    }

    private void validerMotDePasse() {
        if ((this.passwordEditText != null) && (this.passwordTextInputLayout != null)) {
            Editable text = this.passwordEditText.getText();
            boolean isEmpty = TextUtils.isEmpty(text);
            String errorMessage =
              (isEmpty) ? this.getString(R.string.error_invalid_password) : null;
            // TODO: Checker les contraintes de mots de passe.
            this.passwordTextInputLayout.setError(errorMessage);
        }
    }
}
