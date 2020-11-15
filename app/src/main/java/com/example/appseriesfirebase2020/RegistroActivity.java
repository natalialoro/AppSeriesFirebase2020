package com.example.appseriesfirebase2020;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appseriesfirebase2020.models.SerieModel;
import com.example.appseriesfirebase2020.models.SerieModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegistroActivity extends AppCompatActivity {

    private EditText et_registro_titulo, et_registro_contenido;
    private Button btn_registro_guardar;
    private SerieModel model;
    protected FirebaseFirestore db;
    private String TAG = "LFSR";
    final private String collection = "series";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = FirebaseFirestore.getInstance();
        et_registro_contenido = findViewById(R.id.et_registro_contenido);
        et_registro_titulo = findViewById(R.id.et_registro_titulo);
        btn_registro_guardar = findViewById(R.id.btn_registro_guardar);

        btn_registro_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contenido, titulo;
                titulo = et_registro_titulo.getText().toString();
                contenido = et_registro_contenido.getText().toString();

                model = new SerieModel (titulo, contenido);


                db.collection(collection)
                        .add(model)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_LONG).show();
                                goToMain();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(getApplicationContext(), "No se guardó: " + e.getMessage() , Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }

    private void goToMain(){
        Intent listar = new Intent(this, MainActivity.class);
        startActivity(listar);
    }
}