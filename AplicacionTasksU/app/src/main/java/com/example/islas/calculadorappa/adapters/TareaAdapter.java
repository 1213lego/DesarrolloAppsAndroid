package com.example.islas.calculadorappa.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.islas.calculadorappa.R;
import com.example.islas.calculadorappa.entities.Tarea;

import java.util.ArrayList;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaHolder>
{

    private ArrayList<Tarea> tareas;
    private OnItemClickListener listener;
    //Se ejecuta al añadirse nuevo item
    //Crea una vista MateriaHolder y la conecta con los recursos definido en item_materia.xml
    @NonNull
    @Override
    public TareaAdapter.TareaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarea_item, viewGroup, false);
        return new TareaAdapter.TareaHolder(itemView);
    }

    //Inyecta la informacion de los elementos en cada una de las vistas creadas
    @Override
    public void onBindViewHolder(@NonNull TareaAdapter.TareaHolder tareaHolder, int i)
    {

        tareaHolder.nombre.setText(tareas.get(i).getNombre());
        tareaHolder.descripcion.setText(tareas.get(i).getDescripcion());
        tareaHolder.fecha.setText(tareas.get(i).getFecha()+"");
        tareaHolder.porcentaje.setText(tareas.get(i).getPorcentaje()+"");
        tareaHolder.nota.setText(tareas.get(i).getNota()+"");
    }
    @Override
    public int getItemCount()
    {
        return tareas.size();
    }

    public void setTareas(ArrayList<Tarea> tareas)
    {
        this.tareas = tareas;
    }

    public interface OnItemClickListener
    {
        public void onItemClick(int posTarea);
    }
    public void setOnClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }

    //Esta clase representa cada elemento de la coleccion del RecyclerView, item
    class TareaHolder extends RecyclerView.ViewHolder
    {
        private TextView nombre;
        private TextView descripcion;
        private TextView fecha;
        private TextView porcentaje;
        private TextView nota;

        public TareaHolder(@NonNull View itemView)
        {
            super(itemView);
            nombre=itemView.findViewById(R.id.txtNTarea);
            descripcion=itemView.findViewById(R.id.txtDescripcion);
            fecha=itemView.findViewById(R.id.txtFecha);
            porcentaje=itemView.findViewById(R.id.txtPorcentaje);
            nota=itemView.findViewById(R.id.txtCVTNota);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos=getAdapterPosition();
                    if(listener!=null && pos!=RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(pos);
                    }
                }
            });

        }
    }
}
