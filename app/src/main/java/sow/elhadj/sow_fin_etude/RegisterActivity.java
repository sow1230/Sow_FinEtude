package sow.elhadj.sow_fin_etude;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    TextView btn;
    private EditText inputUsername, inputEmail, inputPassword, inputConformPassword,inputAdresse, inputPhone;
    Members members;
    long maxid = 0;
    private DatabaseReference mDatabaseRef;
    Button btnRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        btn = findViewById(R.id.alreadyHaveAccount);
        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputAdresse= findViewById(R.id.inputAdresse);
        inputPhone = findViewById(R.id.inputPhone);
        inputPassword = findViewById(R.id.inputPassword);
        inputConformPassword = findViewById(R.id.inputConformPassword);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(RegisterActivity.this);

        members = new Members();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> checkCredentials());

        checkCredentials();


        btn.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
      //  String adresse = inputAdresse.getText().toString();
      //  String phone = inputPhone.getText().toString();
        String password = inputPassword.getText().toString();
        String ConformPassword = inputConformPassword.getText().toString();

        if (username.isEmpty() || username.length() < 5) {
            inputUsername.setError("Veuillez entrez au moins 5 caractères");
            inputUsername.setFocusable(true);
        } else if (email.isEmpty() || !email.contains("@") || !email.contains(".com") ) {
            inputEmail.setError("Adresse email invalide");
            inputEmail.setFocusable(true);
        } else if (password.isEmpty() || password.length() < 7) {
            inputPassword.setError("le mot de passe doit contenir au moins 7 caractères");
            inputPassword.setFocusable(true);
        } else if (ConformPassword.isEmpty() || !ConformPassword.equals(password)) {
            inputConformPassword.setError("le mot de passe ne correspond pas");
            inputConformPassword.setFocusable(true);
        } else {
            mLoadingBar.setTitle("Inscription");
            mLoadingBar.setMessage("svp veuillez patientez");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            members.setNomEtPrenom(inputUsername.getText().toString());
            members.setAdresse(inputAdresse.getText().toString());
            members.setTel(inputPhone.getText().toString());
            members.setEmail(inputEmail.getText().toString());
            members.setMotdePasse(inputPassword.getText().toString());


            mDatabaseRef.child(String.valueOf(maxid + 1)).setValue(members);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(RegisterActivity.this, "Inscription Reussi", Toast.LENGTH_SHORT).show();
                        mLoadingBar.dismiss();
                        startActivity(new Intent(RegisterActivity.this, UploadActivity.class));

                    } else {
                        Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}


