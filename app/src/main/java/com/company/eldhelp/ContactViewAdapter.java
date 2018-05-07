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

public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.Viewholder>  {

    Context context;
    List<Contact> contacts;
    MedicineOnClickListener listener;

    public ContactViewAdapter(Context context, List<Contact> contacts, MedicineOnClickListener listener) {
        this.context = context;
        this.contacts = contacts;
        this.listener = listener;
    }

    @Override
    public ContactViewAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row_list, parent, false);
        final ContactViewAdapter.Viewholder viewholder = new ContactViewAdapter.Viewholder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, viewholder.getLayoutPosition());

            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ContactViewAdapter.Viewholder holder, int position) {

        Contact contact = contacts.get(position);
        holder.contactName.setText(contact.getName());
        holder.contactNumber.setText(contact.getNumber());
        holder.contact_row.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public TextView contactName, contactNumber;
        public RelativeLayout contact_row;

        public Viewholder(final View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.textView_contact_name);
            contactNumber = itemView.findViewById(R.id.textView_contact_number);
            contact_row= itemView.findViewById(R.id.contact_row);


        }
    }
}
