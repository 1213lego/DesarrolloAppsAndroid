package com.example.calculadorappa;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calculadorappa.model.Materia;

import java.util.List;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.MateriaHolder>
{
    private List<Materia> materias;
    //Se ejecuta al añadirse nuevo item
    //Crea una vista MateriaHolder y la conecta con los recursos definido en item_materia.xml
    @NonNull
    @Override
    public MateriaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_materia, viewGroup, false);
        return new MateriaHolder(itemView);
    }

    //Inyecta la informacion de los elementos en cada una de las vistas creadas
    @Override
    public void onBindViewHolder(@NonNull MateriaHolder materiaHolder, int i)
    {
        materiaHolder.creditos.setText(materias.get(i).getCreditos()+"");
        materiaHolder.nombre.setText(materias.get(i).getNombre());
        materiaHolder.nota.setText(materias.get(i).getNota()+"");
    }
    @Override
    public int getItemCount()
    {
        return materias.size();
    }

    public void setMaterias(List<Materia> materias)
    {
        this.materias = materias;
    }

    //Esta clase representa cada elemento de la coleccion del RecyclerView, item
    class MateriaHolder extends RecyclerView.ViewHolder
    {
        private TextView nombre;
        private TextView creditos;
        private TextView nota;
        public MateriaHolder(@NonNull View itemView)
        {
            super(itemView);
            nombre=itemView.findViewById(R.id.txtNombre);
            creditos=itemView.findViewById(R.id.txtCreditos);
            nota=itemView.findViewById(R.id.txtNota);

        }
    }
}
