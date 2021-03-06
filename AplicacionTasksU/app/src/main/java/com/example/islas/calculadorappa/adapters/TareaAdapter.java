package com.example.islas.calculadorappa.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.islas.calculadorappa.R;
import com.example.islas.calculadorappa.entities.Tarea;

import java.text.DateFormat;
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
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(tareaHolder.itemView.getContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(tareaHolder.itemView.getContext());
        tareaHolder.fecha.setText(dateFormat.format(tareas.get(i).getFecha())+ " "+timeFormat.format(tareas.get(i).getPorcentaje()));
        tareaHolder.porcentaje.setText(tareas.get(i).getPorcentaje()+" " );
        tareaHolder.nota.setText(tareas.get(i).getNota()+"");
        if(tareas.get(i).isEsParcial())
        {
            tareaHolder.constraintLayout.setBackgroundResource(R.drawable.vector_es_parcial_background);
        }

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
        private ConstraintLayout constraintLayout;
        public TareaHolder(@NonNull View itemView)
        {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.constraint_item_tarea_background);
            nombre=itemView.findViewById(R.id.txtNTarea);
            descripcion=itemView.findViewById(R.id.txtDescripcion);
            descripcion.setInputType(InputType.TYPE_NULL);
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
