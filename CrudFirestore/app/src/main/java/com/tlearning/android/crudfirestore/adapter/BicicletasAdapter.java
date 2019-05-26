package com.tlearning.android.crudfirestore.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.tlearning.android.crudfirestore.R;
import com.tlearning.android.crudfirestore.callbacks.OnClickItemListener;
import com.tlearning.android.crudfirestore.model.Bicicleta;

public class BicicletasAdapter extends FirestoreRecyclerAdapter<Bicicleta,BicicletasAdapter.ViewHolderBicicleta> {

    private OnClickItemListener<Bicicleta> onClickItemListener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BicicletasAdapter(@NonNull FirestoreRecyclerOptions<Bicicleta> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderBicicleta holder, int position, @NonNull Bicicleta model) {
        holder.serial.setText(model.getSerial());
        holder.marca.setText(model.getMarca());
        holder.velocidades.setText(String.valueOf(model.getVelocidades()));
        holder.rin.setText(String.valueOf(model.getRin()));
        holder.peso.setText(String.valueOf(model.getPeso()));
    }

    @NonNull
    @Override
    public ViewHolderBicicleta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bicicleta,
                viewGroup,false);
        return new ViewHolderBicicleta(view);
    }


    public void setOnClickItemListener(OnClickItemListener<Bicicleta> onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public class ViewHolderBicicleta extends RecyclerView.ViewHolder
    {
        private TextView serial;
        private TextView marca;
        private TextView rin;
        private TextView velocidades;
        private TextView peso;

        public ViewHolderBicicleta(@NonNull View itemView) {
            super(itemView);
            serial= itemView.findViewById(R.id.textViewSerial);
            marca= itemView.findViewById(R.id.textViewMarca);
            rin= itemView.findViewById(R.id.textViewRin);
            velocidades= itemView.findViewById(R.id.textViewVelocidades);
            peso= itemView.findViewById(R.id.textViewPeso);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if(onClickItemListener!=null && pos!=RecyclerView.NO_POSITION)
                    {
                        onClickItemListener.onClickItem(getItem(pos));
                    }
                }
            });
        }
    }
}
