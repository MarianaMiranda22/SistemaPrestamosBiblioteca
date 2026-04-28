package com.example.sistemaprestamosbiblioteca;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Fragment para registrar préstamos y validar disponibilidad.
 */
public class PrestamosFragment extends Fragment {

    private EditText etUsuario;
    private EditText etFechaPrestamo;
    private EditText etFechaDevolucion;
    private Spinner spInventarioDisponible;
    private Button btnRegistrarPrestamo;
    private Button btnCancelarPrestamo;
    private ListView listViewPrestamos;

    private DatabaseReference inventarioRef;
    private DatabaseReference prestamosRef;

    private final ArrayList<Libro> itemsDisponibles = new ArrayList<>();
    private final ArrayList<Prestamo> prestamos = new ArrayList<>();

    private ArrayAdapter<Libro> inventarioAdapter;
    private ArrayAdapter<Prestamo> prestamosAdapter;

    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public PrestamosFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prestamos, container, false);

        inventarioRef = FirebaseDatabase.getInstance().getReference("inventario");
        prestamosRef = FirebaseDatabase.getInstance().getReference("prestamos");

        etUsuario = view.findViewById(R.id.etUsuario);
        etFechaPrestamo = view.findViewById(R.id.etFechaPrestamo);
        etFechaDevolucion = view.findViewById(R.id.etFechaDevolucion);
        spInventarioDisponible = view.findViewById(R.id.spInventarioDisponible);
        btnRegistrarPrestamo = view.findViewById(R.id.btnRegistrarPrestamo);
        btnCancelarPrestamo = view.findViewById(R.id.btnCancelarPrestamo);
        listViewPrestamos = view.findViewById(R.id.listViewPrestamos);

        inventarioAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                itemsDisponibles
        );
        spInventarioDisponible.setAdapter(inventarioAdapter);

        prestamosAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                prestamos
        );
        listViewPrestamos.setAdapter(prestamosAdapter);

        colocarFechaActual();

        etFechaPrestamo.setOnClickListener(v -> mostrarDatePicker(etFechaPrestamo));
        etFechaDevolucion.setOnClickListener(v -> mostrarDatePicker(etFechaDevolucion));

        btnRegistrarPrestamo.setOnClickListener(v -> registrarPrestamo());
        btnCancelarPrestamo.setOnClickListener(v -> limpiarFormulario());

        cargarInventarioDisponible();
        cargarPrestamos();

        return view;
    }

    private void colocarFechaActual() {
        Calendar calendar = Calendar.getInstance();
        etFechaPrestamo.setText(formatoFecha.format(calendar.getTime()));
    }

    private void mostrarDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar fechaSeleccionada = Calendar.getInstance();
                    fechaSeleccionada.set(year, month, dayOfMonth);
                    editText.setText(formatoFecha.format(fechaSeleccionada.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void cargarInventarioDisponible() {
        inventarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsDisponibles.clear();

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Libro item = itemSnapshot.getValue(Libro.class);
                    if (item != null && item.isDisponible()) {
                        itemsDisponibles.add(item);
                    }
                }

                inventarioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(),
                        "Error al cargar disponibilidad: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarPrestamos() {
        prestamosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prestamos.clear();

                for (DataSnapshot prestamoSnapshot : snapshot.getChildren()) {
                    Prestamo prestamo = prestamoSnapshot.getValue(Prestamo.class);
                    if (prestamo != null) {
                        prestamos.add(prestamo);
                    }
                }

                prestamosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(),
                        "Error al cargar préstamos: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarPrestamo() {
        String usuario = etUsuario.getText().toString().trim();
        String fechaPrestamo = etFechaPrestamo.getText().toString().trim();
        String fechaDevolucion = etFechaDevolucion.getText().toString().trim();

        if (usuario.isEmpty() || fechaPrestamo.isEmpty() || fechaDevolucion.isEmpty()) {
            Toast.makeText(requireContext(), "Complete todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (itemsDisponibles.isEmpty()) {
            Toast.makeText(requireContext(), "No hay libros/herramientas disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        Libro itemSeleccionado = (Libro) spInventarioDisponible.getSelectedItem();
        if (itemSeleccionado == null) {
            Toast.makeText(requireContext(), "Seleccione un elemento disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!itemSeleccionado.isDisponible()) {
            Toast.makeText(requireContext(), "El elemento seleccionado no está disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        String idPrestamo = prestamosRef.push().getKey();
        if (idPrestamo == null) {
            Toast.makeText(requireContext(), "No se pudo generar el préstamo", Toast.LENGTH_SHORT).show();
            return;
        }

        Prestamo prestamo = new Prestamo(
                idPrestamo,
                usuario,
                itemSeleccionado.getId(),
                itemSeleccionado.getNombre(),
                itemSeleccionado.getTipo(),
                fechaPrestamo,
                fechaDevolucion,
                "Activo"
        );

        prestamosRef.child(idPrestamo).setValue(prestamo)
                .addOnSuccessListener(unused -> inventarioRef.child(itemSeleccionado.getId())
                        .child("disponible")
                        .setValue(false)
                        .addOnSuccessListener(unused2 -> {
                            Toast.makeText(requireContext(), "Préstamo registrado correctamente", Toast.LENGTH_SHORT).show();
                            limpiarFormulario();
                        })
                        .addOnFailureListener(e -> Toast.makeText(requireContext(),
                                "Préstamo guardado, pero no se actualizó disponibilidad: " + e.getMessage(),
                                Toast.LENGTH_LONG).show()))
                .addOnFailureListener(e -> Toast.makeText(requireContext(),
                        "Error al registrar préstamo: " + e.getMessage(),
                        Toast.LENGTH_LONG).show());
    }

    private void limpiarFormulario() {
        etUsuario.setText("");
        etFechaDevolucion.setText("");
        colocarFechaActual();
    }
}
