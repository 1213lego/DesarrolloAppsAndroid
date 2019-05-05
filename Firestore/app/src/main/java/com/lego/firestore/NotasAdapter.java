package com.lego.firestore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.lego.firestore.model.Nota;

public class NotasAdapter extends FirestoreRecyclerAdapter<Nota, NotasAdapter.ViewHolderNota>
{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NotasAdapter(@NonNull FirestoreRecyclerOptions<Nota> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderNota holder, int position, @NonNull Nota model)
    {
        holder.titulo.setText(model.getTitulo());
        holder.contenido.setText(model.getContenido());
        //holder.fecha.setText(model.getFecha().toDate().toString());
    }

    @NonNull
    @Override
    public ViewHolderNota onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nota_item,
                viewGroup, false);
        return new ViewHolderNota(v);
    }

    public class ViewHolderNota extends RecyclerView.ViewHolder
    {
        private TextView titulo;
        private TextView contenido;
        private TextView fecha;
        public ViewHolderNota(@NonNull View itemView)
        {
            super(itemView);
            titulo=itemView.findViewById(R.id.titulo);
            contenido=itemView.findViewById(R.id.contenido);
            fecha=itemView.findViewById(R.id.fecha);
        }
    }
}
