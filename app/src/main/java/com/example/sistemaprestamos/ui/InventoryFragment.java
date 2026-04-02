package com.example.sistemaprestamos.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemaprestamos.R;
import com.example.sistemaprestamos.model.InventoryItem;
import com.example.sistemaprestamos.ui.adapter.InventoryAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment {

    private InventoryAdapter adapter;
    private TextView tvCount;

    public InventoryFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inventory, container, false);

        TextView title = v.findViewById(R.id.screen_title);
        TextView subtitle = v.findViewById(R.id.screen_subtitle);
        title.setText("Inventario");
        subtitle.setText("Gestión de libros y herramientas");

        tvCount = v.findViewById(R.id.tv_count);
        TextInputEditText etSearch = v.findViewById(R.id.et_search);

        RecyclerView rv = v.findViewById(R.id.rv_inventory);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<InventoryItem> items = sampleInventory();
        adapter = new InventoryAdapter(items, new InventoryAdapter.Listener() {
            @Override
            public void onEdit(InventoryItem item) {
                Toast.makeText(requireContext(), "Editar: " + item.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDelete(InventoryItem item) {
                Toast.makeText(requireContext(), "Eliminar: " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        rv.setAdapter(adapter);
        updateCount(adapter.getItemCountFiltered());

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
                updateCount(adapter.getItemCountFiltered());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        return v;
    }

    private void updateCount(int count) {
        tvCount.setText(count + " objetos encontrados");
    }

    private List<InventoryItem> sampleInventory() {
        List<InventoryItem> list = new ArrayList<>();
        list.add(new InventoryItem("Cálculo Diferencial e Integral", InventoryItem.Type.LIBRO, InventoryItem.Status.DISPONIBLE, 12));
        list.add(new InventoryItem("Física para Ciencias e Ingeniería", InventoryItem.Type.LIBRO, InventoryItem.Status.PRESTADO, 8));
        list.add(new InventoryItem("Química Orgánica", InventoryItem.Type.LIBRO, InventoryItem.Status.DISPONIBLE, 15));
        list.add(new InventoryItem("Taladro Eléctrico Bosch", InventoryItem.Type.HERRAMIENTA, InventoryItem.Status.PRESTADO, 20));
        list.add(new InventoryItem("Multímetro Digital", InventoryItem.Type.HERRAMIENTA, InventoryItem.Status.DISPONIBLE, 10));
        list.add(new InventoryItem("Circuitos Eléctricos (Schaum)", InventoryItem.Type.LIBRO, InventoryItem.Status.DISPONIBLE, 6));
        list.add(new InventoryItem("Osciloscopio Tektronix", InventoryItem.Type.HERRAMIENTA, InventoryItem.Status.DISPONIBLE, 22));
        list.add(new InventoryItem("Álgebra Lineal", InventoryItem.Type.LIBRO, InventoryItem.Status.PRESTADO, 9));
        list.add(new InventoryItem("Pinza Amperimétrica", InventoryItem.Type.HERRAMIENTA, InventoryItem.Status.DISPONIBLE, 5));
        list.add(new InventoryItem("Ecuaciones Diferenciales", InventoryItem.Type.LIBRO, InventoryItem.Status.DISPONIBLE, 11));
        return list;
    }
}
