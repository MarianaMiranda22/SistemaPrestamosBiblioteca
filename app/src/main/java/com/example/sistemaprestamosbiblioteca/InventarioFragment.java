package com.example.sistemaprestamosbiblioteca;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Fragment encargado de gestionar el inventario de libros.
 * Permite agregar libros y guardarlos en Firebase Realtime Database.
 */
public class InventarioFragment extends Fragment {


    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private Button btnAgregar;

    public InventarioFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inventario, container, false);

        // Referencia al nodo "inventario" en Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("inventario");

        // Vincular elementos del XML
        recyclerView = view.findViewById(R.id.recyclerInventario);
        btnAgregar = view.findViewById(R.id.btnAgregar);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Evento botón Agregar
        btnAgregar.setOnClickListener(v -> mostrarDialogoAgregar());

        return view;
    }

    /**
     * Muestra un diálogo para ingresar nombre y autor del libro.
     */
    private void mostrarDialogoAgregar() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Agregar Libro");

        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_agregar_libro, null);

        EditText etNombre = dialogView.findViewById(R.id.etNombre);
        EditText etAutor = dialogView.findViewById(R.id.etAutor);

        builder.setView(dialogView);

        builder.setPositiveButton("Guardar", (dialog, which) -> {

            String nombre = etNombre.getText().toString().trim();
            String autor = etAutor.getText().toString().trim();

            if (!nombre.isEmpty() && !autor.isEmpty()) {

                // Generar ID único
                String id = databaseReference.push().getKey();

                // Crear objeto Libro
                Libro libro = new Libro(id, nombre, autor);

                // Guardar en Firebase
                databaseReference.child(id).setValue(libro);

                Toast.makeText(requireContext(),
                        "Libro agregado correctamente",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(),
                        "Complete todos los campos",
                        Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);

        builder.show();
    }
}