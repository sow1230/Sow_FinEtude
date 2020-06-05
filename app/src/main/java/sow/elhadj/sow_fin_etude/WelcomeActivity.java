package sow.elhadj.sow_fin_etude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {

    Button btnver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnver = findViewById(R.id.VerifierEmail);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        btnver.setOnClickListener(v -> {
            assert user != null;
            user.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(WelcomeActivity.this, "Demande envoyée. Verifiez votre adresse email pour valider",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }else{
                    Toast.makeText(WelcomeActivity.this,  Objects.requireNonNull(task.getException()).getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        });
    }
}
