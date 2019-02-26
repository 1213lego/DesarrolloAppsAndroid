package com.example.islas.calculadorappa.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.islas.calculadorappa.R;
import com.example.islas.calculadorappa.entities.Asignatura;
import com.example.islas.calculadorappa.servicios.ServicioCalPPA;

import java.util.ArrayList;


public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.MateriaHolder>
{
    private ArrayList<Asignatura> materias;
    private OnItemClickListener listener;
    //Se ejecuta al añadirse nuevo item
    //Crea una vista MateriaHolder y la conecta con los recursos definido en item_materia.xml
    @NonNull
    @Override
    public MateriaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.asignatura_item, viewGroup, false);
        return new MateriaHolder(itemView);
    }

    //Inyecta la informacion de los elementos en cada una de las vistas creadas
    @Override
    public void onBindViewHolder(@NonNull MateriaHolder materiaHolder, int i)
    {
        materiaHolder.docente.setText(materias.get(i).getNombreDocente());
        materiaHolder.creditos.setText(materias.get(i).getCreditos()+"");
        materiaHolder.nombre.setText(materias.get(i).getNombreAsignatura());
        materiaHolder.nota.setText(materias.get(i).getNotafinal()+"");
    }
    @Override
    public int getItemCount()
    {
        return materias.size();
    }

    public void setMaterias(ArrayList<Asignatura> materias)
    {
        this.materias = materias;
    }

    public interface OnItemClickListener
    {
        void onItemClick(Asignatura asignatura);
    }

    public  void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        listener=onItemClickListener;
    }



    //Esta clase representa cada elemento de la coleccion del RecyclerView, item
    class MateriaHolder extends RecyclerView.ViewHolder
    {
        private TextView nombre;
        private TextView docente;
        private TextView creditos;
        private TextView nota;
        private ProgressBar progressBar;
        public MateriaHolder(@NonNull View itemView)
        {
            super(itemView);
            nombre=itemView.findViewById(R.id.txtNAsignatura);
            docente=itemView.findViewById(R.id.txtNDocente);
            creditos=itemView.findViewById(R.id.txtCreditos);
            nota=itemView.findViewById(R.id.txtNota);
            progressBar=itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos=getAdapterPosition();
                    if(listener!=null && pos!=RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(materias.get(pos));
                    }

                }
            });
            if(getAdapterPosition()!=RecyclerView.NO_POSITION)
            {
                progressBar.setProgress(ServicioCalPPA.SERVICIOPPA.progresoAsignatura(getAdapterPosition()));
                nota.setText(ServicioCalPPA.SERVICIOPPA.darNotaAsignatura(getAdapterPosition())+"");

            }


        }
    }
}
