package edu.practice.crudfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterHeroe extends RecyclerView.Adapter<AdapterHeroe.ViewHolder>{

    Context context;
    List<Heroe> heroes;

    public AdapterHeroe(Context context, List<Heroe> heroes) {
        this.context = context;
        this.heroes = heroes;
    }

    @NonNull
    @Override
    public AdapterHeroe.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_heroe, null, false);
        return new AdapterHeroe.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHeroe.ViewHolder holder, int position) {
        holder.tvNombre.setText(heroes.get(position).getNombre());
        holder.tvPoder.setText(heroes.get(position).getPoder());
        holder.tvEnemigo.setText(heroes.get(position).getEnemigo());
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvPoder, tvEnemigo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPoder = itemView.findViewById(R.id.tvPoder);
            tvEnemigo = itemView.findViewById(R.id.tvEnemigo);

        }
    }
}
