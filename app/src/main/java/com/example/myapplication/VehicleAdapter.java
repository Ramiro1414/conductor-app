package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private final List<String> vehicleList;
    private final OnItemClickListener listener;
    private final Context context;

    // Interfaz para manejar las acciones de editar y eliminar
    public interface OnItemClickListener {
        void onEditClick(String licensePlate, int position);
        void onDeleteClick(String licensePlate, int position);

    }

    public VehicleAdapter(List<String> vehicleList, OnItemClickListener listener, Context context) {
        this.vehicleList = vehicleList;
        this.listener = listener;
        this.context = context;  // Agrega esta línea para guardar el contexto
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle, parent, false);
        return new VehicleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        String licensePlate = vehicleList.get(position);
        holder.textViewLicensePlate.setText(licensePlate);

        holder.buttonEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditVehicleActivity.class);
            intent.putExtra("OLD_PATENTE", licensePlate);
            intent.putExtra("POSITION", position);
            context.startActivity(intent);
        });

        holder.buttonDelete.setOnClickListener(v -> {
            // Implementar lógica para eliminar
            listener.onDeleteClick(licensePlate, position);
        });
    }



    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    static class VehicleViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewLicensePlate;
        final ImageButton buttonEdit;
        final ImageButton buttonDelete;

        VehicleViewHolder(View itemView) {
            super(itemView);
            textViewLicensePlate = itemView.findViewById(R.id.textViewLicensePlate);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
