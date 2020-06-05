package sow.elhadj.sow_fin_etude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.MessageFormat;
import java.util.Objects;


public class UploadActivity extends AppCompatActivity {


   private  EditText Nom_Voiture, Num_Voiture,editTextFilename;
    Button btnsave;
    Member member;
    long maxid = 0;
 //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;
    ProgressBar progressBar,progressBarss,progressBars;
    Button UploadPdf,UploadPdfs,UploadPdfss;
    TextView textViewStatus;

    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Nom_Voiture = findViewById(R.id.inputNomV);
        Num_Voiture = findViewById(R.id.inputNumV);
        btnsave = findViewById(R.id.btnSave);
        firebaseAuth = FirebaseAuth.getInstance();
       user = firebaseAuth.getCurrentUser();


        member = new Member();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
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

        btnsave.setOnClickListener(v -> {

            member.setNom_Voitures(Nom_Voiture.getText().toString());
            member.setNum_Voitures(Num_Voiture.getText().toString());


            mDatabaseReference.child(String.valueOf(maxid)).setValue(member);
            Toast.makeText(UploadActivity.this, "Données enregistrées!!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UploadActivity.this, Main2Activity.class));

        });
        //getting the views
        editTextFilename = findViewById(R.id.editTextFileName);
         editTextFilename = findViewById(R.id.FileNames);
         editTextFilename = findViewById(R.id.FileName);


        textViewStatus = findViewById(R.id.textViewStatus);
        textViewStatus = findViewById(R.id.textViewStatuss);
        textViewStatus = findViewById(R.id.textViewStatusss);


        progressBar = findViewById(R.id.progressbar);
        progressBars =  findViewById(R.id.progressbars);
        progressBarss =  findViewById(R.id.progressbarss);


        UploadPdf = findViewById(R.id.buttonUploadFile);
         UploadPdfs= findViewById(R.id.buttonUploadFiles);
         UploadPdfss= findViewById(R.id.buttonUploadFiless);

        UploadPdf.setOnClickListener(v -> {

            //creating an intent for file chooser
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Selectionner un fichier"), PICK_PDF_CODE);

        });
        UploadPdfs.setOnClickListener(v -> {

            //creating an intent for file chooser
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Selectionner un fichier"), PICK_PDF_CODE);

        });
        UploadPdfss.setOnClickListener(v -> {

            //creating an intent for file chooser
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Selectionner un fichier"), PICK_PDF_CODE);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "Aucun fichier choisit", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(taskSnapshot -> {
                    progressBar.setVisibility(View.GONE);
                    textViewStatus.setText(R.string.s);
                    Toast.makeText(UploadActivity.this, "Téléchargé avec succès !!", Toast.LENGTH_LONG).show();
                    Upload upload = new Upload(editTextFilename.getText().toString().trim(), Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().toString());
                    mDatabaseReference.child(Objects.requireNonNull(mDatabaseReference.push().getKey())).setValue(upload);
                })
                .addOnFailureListener(exception -> Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show())
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    textViewStatus.setText(MessageFormat.format("{0}% Uploading...", (int) progress));
                });

    }

}


