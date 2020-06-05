package sow.elhadj.sow_fin_etude;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;

public class ResetPasswordActivity extends AppCompatActivity {


    private EditText edtEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtEmail =findViewById(R.id.edt_reset_email);
        Button btnResetPassword =findViewById(R.id.btn_reset_password);
        Button btnBack = findViewById(R.id.btn_back);

        mAuth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(v -> {

            String email = edtEmail.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, "Verifier votre adresse email !!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Erreur !!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnBack.setOnClickListener(v ->{
            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
        });
    }
}