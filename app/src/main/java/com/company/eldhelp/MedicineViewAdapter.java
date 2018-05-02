package com.company.eldhelp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.eldhelp.Interface.MedicineOnClickListener;

import java.util.List;

/**
 * Created by fatih on 2018-05-02.
 */

public class MedicineViewAdapter extends RecyclerView.Adapter<MedicineViewAdapter.Viewholder>{

    Context context;
    List<Medicine>medicines;
    MedicineOnClickListener listener;

    public MedicineViewAdapter(Context context, List<Medicine> medicines, MedicineOnClickListener listener) {
        this.context = context;
        this.medicines = medicines;
        this.listener = listener;
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        public TextView medicineName, medicineTime;
        public RelativeLayout medicine_row;

        public Viewholder(final View itemView) {
            super(itemView);

            medicineName=itemView.findViewById(R.id.textView_medicine_name);
            medicineTime=itemView.findViewById(R.id.textView_medicine_time);
            medicine_row=itemView.findViewById(R.id.medicine_row);

        }
    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_row_list,parent,false);
        final  Viewholder viewholder=new Viewholder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v,viewholder.getLayoutPosition());

            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        Medicine medicine=medicines.get(position);
        holder.medicineName.setText(medicine.getName());
        holder.medicineTime.setText(medicine.getTime());
        holder.medicine_row.setTag(holder);

    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }


}
