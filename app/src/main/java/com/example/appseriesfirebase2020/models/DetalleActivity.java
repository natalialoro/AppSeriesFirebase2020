package com.example.appseriesfirebase2020.models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appnotasfirebase20202.models.NotaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetalleActivity extends AppCompatActivity {

    private EditText tv_detalle_titulo, tv_detalle_contenido;
    protected FirebaseFirestore db;
    private String TAG = "LFNOT";
    final private String collection = "notes";
    private NotaModel model;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        db = FirebaseFirestore.getInstance();

        tv_detalle_titulo = findViewById(R.id.tv_detalle_titulo);
        tv_detalle_contenido = findViewById(R.id.tv_detalle_contenido);

        String id = getIntent().getStringExtra("id");
        if(id != null){
            tv_detalle_titulo.setText(id);
            updateUI(id);
        }else{
            tv_detalle_titulo.setText("No recibimos nada");
        }




    }

    private void updateUI(String id){
        documentReference = db.collection(collection).document(id);
        documentReference.get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    model = document.toObject(NotaModel.class);
                    model.setFbId(document.getId());
                    if(model != null){
                        tv_detalle_titulo.setText(model.getTitulo());
                        tv_detalle_contenido.setText(model.getContenido());
                    }

                    updateModel(model);
                }
            }
        });


    }

    private void updateModel(NotaModel model){
        model.setTitulo(tv_detalle_titulo.getText().toString());

        String cont = tv_detalle_titulo.getText().toString();
        model.setContenido(cont);
        documentReference = db.collection(collection).document(model.getFbId());
        documentReference.set(model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "Dio");
                        }
                    }
                });
    }
}