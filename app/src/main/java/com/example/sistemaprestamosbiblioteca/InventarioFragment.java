package com.example.sistemaprestamosbiblioteca;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Fragment para la vista de catálogo/inventario.
 * Permite agregar libros/herramientas y visualizar su disponibilidad.
 */
public class InventarioFragment extends Fragment {

    private DatabaseReference inventarioRef;
    private ListView listViewInventario;
    private Button btnAgregar;

    private final ArrayList<Libro> inventario = new ArrayList<>();
    private ArrayAdapter<Libro> adapter;

    public InventarioFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inventario, container, false);

        inventarioRef = FirebaseDatabase.getInstance().getReference("inventario");

        listViewInventario = view.findViewById(R.id.listViewInventario);
        btnAgregar = view.findViewById(R.id.btnAgregar);

        adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                inventario
        );
        listViewInventario.setAdapter(adapter);

        btnAgregar.setOnClickListener(v -> mostrarDialogoAgregar());

        cargarInventario();

        return view;
    }

    private void cargarInventario() {
        inventarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                inventario.clear();

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Libro item = itemSnapshot.getValue(Libro.class);
                    if (item != null) {
                        inventario.add(item);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(),
                        "Error al cargar inventario: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDialogoAgregar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Agregar al catálogo");

        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_agregar_libro, null);

        Spinner spTipo = dialogView.findViewById(R.id.spTipo);
        EditText etNombre = dialogView.findViewById(R.id.etNombre);
        EditText etAutor = dialogView.findViewById(R.id.etAutor);

        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Libro", "Herramienta"}
        );
        spTipo.setAdapter(tipoAdapter);

        builder.setView(dialogView);

        builder.setPositiveButton("Guardar", null);
        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String tipo = spTipo.getSelectedItem().toString();
            String nombre = etNombre.getText().toString().trim();
            String autor = etAutor.getText().toString().trim();

            if (nombre.isEmpty() || autor.isEmpty()) {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = inventarioRef.push().getKey();
            if (id == null) {
                Toast.makeText(requireContext(), "No se pudo generar el ID", Toast.LENGTH_SHORT).show();
                return;
            }

            Libro item = new Libro(id, tipo, nombre, autor, true);
            inventarioRef.child(id).setValue(item)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(requireContext(), "Elemento agregado correctamente", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e -> Toast.makeText(requireContext(),
                            "Error al guardar: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show());
        }));

        dialog.show();
    }
}
