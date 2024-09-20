package com.example.conductor_app.frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.conductor_app.backend.Service.PatenteService;
import com.example.conductor_app.backend.modelo.Patente;
import com.example.myapplication.R;

import java.util.List;

public class PatenteAdapter extends ArrayAdapter<Patente> {

    private final PatenteService patenteService;
    private final Context context;

    public PatenteAdapter(Context context, List<Patente> patentes) {
        super(context, 0, patentes);
        this.context = context;
        this.patenteService = new PatenteService(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_patente, parent, false);
        }

        Patente patente = getItem(position);

        TextView textViewPatente = convertView.findViewById(R.id.textViewPatente);
        ImageButton buttonDelete = convertView.findViewById(R.id.buttonDelete);
        ImageButton buttonEdit = convertView.findViewById(R.id.buttonEdit);

        textViewPatente.setText(patente.getCaracteres());

        buttonDelete.setOnClickListener(v -> {
            // Elimina la patente de la base de datos
            patenteService.deletePatente(patente.getId());
            // Elimina la patente de la lista actual en el frontend
            remove(patente);
            // Actualiza el adaptador
            notifyDataSetChanged();
        });

        buttonEdit.setOnClickListener(v -> {
            ((CRUD_Patentes_Activity) context).showEditPatenteActivity(patente);
        });

        return convertView;
    }
}
