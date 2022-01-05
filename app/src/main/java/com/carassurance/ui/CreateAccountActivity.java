package com.carassurance.ui;

import static com.carassurance.database.DatabaseInitializer.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.entity.UserEntityF;
import com.carassurance.database.repository.UserRepositoryF;
import com.carassurance.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private EditText name;
    private EditText firstname;
    private EditText email;
    private EditText password;
    private Button createAccount;
    private UserRepositoryF repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        repository = ((BaseApp) getApplication()).getUserRepositoryF();

        //mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.createName);
        firstname = findViewById(R.id.createFirstname);
        email = findViewById(R.id.createEmailAddress);
        password = findViewById(R.id.createPassword);
        createAccount = findViewById(R.id.createAccountButton);

        createAccount .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount(email.getText().toString(),password.getText().toString(), firstname.getText().toString(),name.getText().toString());

            }
        });

    }

    private void createAccount(String mail, String pwd, String firstName, String lastName) {
        // [START create_user_with_email]
      /*  mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]*/
        if (pwd.length() < 6) {
            password.setError(getString(R.string.error_invalid_password));
            password.requestFocus();
            password.setText("");

            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError(getString(R.string.error_invalid_email));
            email.requestFocus();
            return;
        }
        UserEntityF newClient = new UserEntityF(mail, firstName, lastName, pwd);

        repository.register(newClient, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createUserWithEmail: success");
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createUserWithEmail: failure", e);
                setResponse(false);
            }
        });

    }

    private void setResponse(Boolean response) {
        if (response) {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            email.setError(getString(R.string.error_used_email));
            email.requestFocus();
        }
    }

}