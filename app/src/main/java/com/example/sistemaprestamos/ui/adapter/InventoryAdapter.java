package com.example.sistemaprestamos.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemaprestamos.R;
import com.example.sistemaprestamos.model.InventoryItem;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.VH> {

    public interface Listener {
        void onEdit(InventoryItem item);
        void onDelete(InventoryItem item);
    }

    private final List<InventoryItem> original;
    private final List<InventoryItem> filtered;
    private final Listener listener;

    public InventoryAdapter(List<InventoryItem> items, Listener listener) {
        this.original = new ArrayList<>(items);
        this.filtered = new ArrayList<>(items);
        this.listener = listener;
    }

    public int getItemCountFiltered() {
        return filtered.size();
    }

    public void filter(String query) {
        filtered.clear();
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);

        if (q.isEmpty()) {
            filtered.addAll(original);
        } else {
            for (InventoryItem it : original) {
                String name = it.getName().toLowerCase(Locale.ROOT);
                String type = it.getType().name().toLowerCase(Locale.ROOT);
                if (name.contains(q) || type.contains(q)) {
                    filtered.add(it);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        InventoryItem item = filtered.get(position);

        h.tvNumber.setText("#" + (position + 1));
        h.tvName.setText(item.getName());
        h.tvLoans.setText(item.getLoansCount() + " préstamos");

        // Type chip
        if (item.getType() == InventoryItem.Type.LIBRO) {
            h.chipType.setText("Libro");
            h.chipType.setChipBackgroundColorResource(R.color.chip_green_bg);
            h.chipType.setTextColor(h.itemView.getResources().getColor(R.color.brand_green, null));
        } else {
            h.chipType.setText("Herramienta");
            h.chipType.setChipBackgroundColorResource(R.color.chip_orange_bg);
            h.chipType.setTextColor(h.itemView.getResources().getColor(R.color.chip_orange_fg, null));
        }

        // Status chip
        if (item.getStatus() == InventoryItem.Status.DISPONIBLE) {
            h.chipStatus.setText("Disponible");
            h.chipStatus.setChipBackgroundColorResource(R.color.chip_green_bg);
            h.chipStatus.setTextColor(h.itemView.getResources().getColor(R.color.brand_green, null));
        } else {
            h.chipStatus.setText("Prestado");
            h.chipStatus.setChipBackgroundColorResource(R.color.chip_gray_bg);
            h.chipStatus.setTextColor(h.itemView.getResources().getColor(android.R.color.white, null));
        }

        h.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(item);
        });

        h.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(item);
        });
    }

    @Override
    public int getItemCount() {
        return filtered.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNumber, tvName, tvLoans;
        Chip chipType, chipStatus;
        ImageButton btnEdit, btnDelete;

        VH(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLoans = itemView.findViewById(R.id.tv_loans);
            chipType = itemView.findViewById(R.id.chip_type);
            chipStatus = itemView.findViewById(R.id.chip_status);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
