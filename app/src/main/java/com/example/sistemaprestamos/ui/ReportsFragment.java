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

public class ReportsFragment extends Fragment {

    public ReportsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reports, container, false);

        TextView title = v.findViewById(R.id.screen_title);
        TextView subtitle = v.findViewById(R.id.screen_subtitle);
        title.setText("Reportes Administrativos");
        subtitle.setText("Análisis y métricas del sistema");

        // Datos ejemplo (mockup)
        int total = 205;
        double onTimePercent = 88.8;
        double overduePercent = 11.2;

        int onTime = (int) Math.round(total * (onTimePercent / 100.0));
        int overdue = Math.max(0, total - onTime);

        TextView tvTotal = v.findViewById(R.id.tv_total);
        TextView tvOnTimePercent = v.findViewById(R.id.tv_on_time_percent);
        TextView tvOverduePercent = v.findViewById(R.id.tv_overdue_percent);

        TextView tvOnTimeValue = v.findViewById(R.id.tv_on_time_value);
        TextView tvOverdueValue = v.findViewById(R.id.tv_overdue_value);

        tvTotal.setText(String.valueOf(total));
        tvOnTimePercent.setText(onTimePercent + "%");
        tvOverduePercent.setText(overduePercent + "%");

        tvOnTimeValue.setText(String.valueOf(onTime));
        tvOverdueValue.setText(String.valueOf(overdue));

        View barOnTime = v.findViewById(R.id.bar_on_time);
        View barOverdue = v.findViewById(R.id.bar_overdue);

        // Ajuste de ancho con porcentaje (después de medir)
        v.post(() -> {
            int maxWidth = ((View) barOnTime.getParent()).getWidth();
            setBarWidth(barOnTime, maxWidth, onTimePercent);
            setBarWidth(barOverdue, maxWidth, overduePercent);
        });

        return v;
    }

    private void setBarWidth(View bar, int maxWidth, double percent) {
        int width = (int) Math.round(maxWidth * (percent / 100.0));
        ViewGroup.LayoutParams lp = bar.getLayoutParams();
        lp.width = Math.max(width, 12); // para que se vea aunque sea chiquito
        bar.setLayoutParams(lp);
    }
}
