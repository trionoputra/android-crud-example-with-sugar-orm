package com.trionoputra.simplecrud.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.trionoputra.simplecrud.R;
import com.trionoputra.simplecrud.object.People;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyViewHolder> {

    private List<People> dtList = new ArrayList<People>();
    private Context mContext;

    public PeopleAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public PeopleAdapter(Context mContext, List<People> list) {
        this.mContext = mContext;
        this.dtList = list;
    }

    // create custom interface for item click
    public interface OnItemClickListener {
        void onItemClick(People item);
    }

    private OnItemClickListener listener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(dtList.get(i), listener,this);
    }

    public void set(List<People> data)
    {
        this.dtList = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return dtList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, email, address;
        public ImageButton btnDelete;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.txtName);
            email =  view.findViewById(R.id.txtEmail);
            address = view.findViewById(R.id.txtAddress);
            btnDelete = view.findViewById(R.id.buttonDelete);

        }

        public void bind(final People data, final OnItemClickListener listener,final PeopleAdapter adapter) {
            name.setText(data.getName());
            email.setText(data.getEmail());
            address.setText(data.getAddress());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if(listener != null)
                        listener.onItemClick(data);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Confirmation");
                    builder.setMessage("You will lose your data");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            People people = People.findById(People.class, data.getId());
                            people.delete();

                            List<People> data = People.listAll(People.class);
                            adapter.set(data);
                            Toast.makeText(mContext, "Data deleted", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
        }
    }

    public void setItemClickListener(OnItemClickListener clickListener) {
        listener = clickListener;
    }
}
