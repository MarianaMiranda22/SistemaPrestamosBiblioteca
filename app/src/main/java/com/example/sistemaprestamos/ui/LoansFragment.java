package com.example.sistemaprestamos.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sistemaprestamos.R;

public class LoansFragment extends Fragment {

    public LoansFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loans, container, false);

        TextView title = v.findViewById(R.id.screen_title);
        TextView subtitle = v.findViewById(R.id.screen_subtitle);
        title.setText("Préstamos");
        subtitle.setText("Registrar un préstamo / ver historial");

        return v;
    }
}
