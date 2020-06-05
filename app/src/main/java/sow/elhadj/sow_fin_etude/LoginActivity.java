package sow.elhadj.sow_fin_etude;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextView btn, forgotPass;
    EditText inputEmail, inputPassword;
    Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn=findViewById(R.id.textViewSignUp);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        forgotPass=findViewById(R.id.forgotPassword);
        btnLogin= findViewById(R.id.btnlogin);
        mAuth= FirebaseAuth.getInstance();
        mLoadingBar= new ProgressDialog(LoginActivity.this);

        btnLogin.setOnClickListener(v -> checkCredentials());
        btn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)));
        forgotPass.setOnClickListener(v ->startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class)));

    }

    private void checkCredentials() {

        String email= inputEmail.getText().toString();
        String password= inputPassword.getText().toString();


        if ( email.isEmpty() || !email.contains("@") || !email.contains(".com")){
            showError(inputEmail,"veuillez entrez une adresse email valide");
        }
        else if( password.isEmpty() || password.length()<7){
            showError(inputPassword,"le mot de passe doit contenir au moins 7 caractères");
        }
        else{
            mLoadingBar.setTitle("Connexion");
            mLoadingBar.setMessage("svp veuillez patientez");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    if(Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()) {

                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "svp Verifier votre adresse email"
                                , Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());


        }

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}


