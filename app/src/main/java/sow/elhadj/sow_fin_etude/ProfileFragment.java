package sow.elhadj.sow_fin_etude;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static androidx.constraintlayout.widget.Constraints.TAG;




public class ProfileFragment extends Fragment {

   // private ImageView avatar ;

    private TextView emailTv, nomTv, phoneTv, nomVs, numVs;






    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_profile, container, false);

       // FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
       // FirebaseUser user = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("1");
        DatabaseReference reference=  firebaseDatabase.getReference("Users").child("2");




        Button edit = view.findViewById(R.id.edit);
       emailTv = view.findViewById(R.id.inputMail);
       nomTv = view.findViewById(R.id.inputNoms);
       phoneTv = view.findViewById(R.id.inputTel);
       Button viewup = view.findViewById(R.id.listView);




       nomVs= view.findViewById(R.id.Nomv);
       numVs = view.findViewById(R.id.Numv);



       edit.setOnClickListener(v -> editprofil());

       viewup.setOnClickListener(v ->startActivity(new Intent(getActivity(), ViewUploadsActivity.class)));



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = Objects.requireNonNull(dataSnapshot.child("nomEtPrenom").getValue()).toString();
                String email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                String tel = Objects.requireNonNull(dataSnapshot.child("tel").getValue()).toString();

                nomTv.setText(name);
                emailTv.setText(email);
                phoneTv.setText(tel);

            }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            // Failed to read value
              Log.w(TAG, "Failed to read value.", error.toException());
        }
    });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String nomV =Objects.requireNonNull(dataSnapshot.child("nom_Voitures").getValue()).toString();
                String numV = Objects.requireNonNull(dataSnapshot.child("num_Voitures").getValue()).toString();

                nomVs.setText(nomV);
                numVs.setText(numV);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        return view ;
    }

    private void editprofil() {
        String[] option = { "Changer le Nom&Prenom", "Changer l'adresse email", "Changer le N° de Tel", "Changer le Nom de la voiture", "Changer le numero d'identification du vehicule"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choisir une Action");

        builder.setItems(option, (dialog, which) -> {

        });
        builder.create().show();
    }

}



