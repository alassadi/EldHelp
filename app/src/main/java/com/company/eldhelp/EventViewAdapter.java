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
 * Created by fatih on 2018-05-07.
 */

public class EventViewAdapter extends RecyclerView.Adapter<EventViewAdapter.Viewholder> {

    Context context;
    List<Event>events;
    MedicineOnClickListener listener;

    public EventViewAdapter(Context context, List<Event> events, MedicineOnClickListener listener) {
        this.context = context;
        this.events = events;
        this.listener = listener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row_list, parent, false);
        final EventViewAdapter.Viewholder viewholder = new EventViewAdapter.Viewholder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, viewholder.getLayoutPosition());

            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        Event event = events.get(position);
        holder.eventName.setText(event.getName());
        holder.eventTime.setText(event.getTime());
        holder.eventDate.setText(event.getDate());
        holder.event_row.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public TextView eventName, eventTime, eventDate;
        public RelativeLayout event_row;

        public Viewholder(final View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.textView_event_name);
            eventTime = itemView.findViewById(R.id.textView_event_time);
            eventDate = itemView.findViewById(R.id.textView_event_name_date);
            event_row= itemView.findViewById(R.id.event_row);


        }
    }
}
