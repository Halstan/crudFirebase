package edu.practice.crudfirebase;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etPoder, etEnemigo;
    Button btnAgregar, btnConsultar, btnConsultarHeroe, btnEditar, btnBorrar;
    RecyclerView recyclerView;

    AdapterHeroe adapterHeroe;

    DatabaseReference databaseReference;

    List<Heroe> heroes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etPoder = findViewById(R.id.etPoder);
        etEnemigo = findViewById(R.id.etEnemigo);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultarHeroe = findViewById(R.id.btnConsultarUsuario);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnEliminar);

        recyclerView = findViewById(R.id.rvHeroes);
        
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this), LinearLayoutManager.VERTICAL));
        databaseReference = FirebaseDatabase.getInstance().getReference();

        obtenerHeroes();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarHeroe();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHeroes();
            }
        });

        btnConsultarHeroe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHeroe();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarHeroe();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarHeroe();
            }
        });

    }

    private void eliminarHeroe() {
        heroes.clear();
        String heroe = etNombre.getText().toString();

        Query query = databaseReference.child("heroes").orderByChild("nombre").equalTo(heroe);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot object : snapshot.getChildren()){
                    object.getRef().removeValue();
                }
                Toast.makeText(MainActivity.this, "Se eliminó al heroe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        limpiarCampos();
    }

    private void editarHeroe() {
        heroes.clear();

        final Heroe heroe =  new Heroe(
                etNombre.getText().toString(), etPoder.getText().toString(), etEnemigo.getText().toString()
        );

        Query query = databaseReference.child("heroes").orderByChild("nombre").equalTo(heroe.getNombre());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = "";
                for (DataSnapshot object : snapshot.getChildren()){
                    key = object.getKey();
                }
                assert key != null;
                databaseReference.child("heroes").child(key).setValue(heroe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void obtenerHeroe() {
        heroes.clear();
        String heroe = etNombre.getText().toString();

        Query query = databaseReference.child("heroes").orderByChild("nombre").equalTo(heroe);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot object : snapshot.getChildren()){
                    heroes.add(object.getValue(Heroe.class));
                }
                adapterHeroe = new AdapterHeroe(MainActivity.this, heroes);
                recyclerView.setAdapter(adapterHeroe);
                limpiarCampos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void obtenerHeroes() {
        databaseReference.child("heroes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                heroes.clear();
                for (DataSnapshot object : snapshot.getChildren()){
                    heroes.add(object.getValue(Heroe.class));
                }

                adapterHeroe = new AdapterHeroe(MainActivity.this, heroes);
                recyclerView.setAdapter(adapterHeroe);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        limpiarCampos();
    }

    private void agregarHeroe() {

        heroes.clear();

        Heroe heroe = new Heroe(
                etNombre.getText().toString(),
                etPoder.getText().toString(),
                etEnemigo.getText().toString());

        databaseReference.child("heroes").push().setValue(heroe,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(MainActivity.this, "Heroe añadido", Toast.LENGTH_SHORT).show();
                    }
                });
        limpiarCampos();
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etPoder.setText("");
        etEnemigo.setText("");
    }
}