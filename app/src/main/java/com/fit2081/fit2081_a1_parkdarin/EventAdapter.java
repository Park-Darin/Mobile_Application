package com.fit2081.fit2081_a1_parkdarin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CustomViewHolderEvent>{
    Context eventContext;
    List<Event> eventData;

    public EventAdapter(Context context , ArrayList<Event> eventArrayList) {
        this.eventContext = context;
        this.eventData = eventArrayList;
    }

    @NonNull
    @Override
    public CustomViewHolderEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false);
        EventAdapter.CustomViewHolderEvent viewHolder = new CustomViewHolderEvent (v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderEvent holder, int position) {
        holder.tvEventId.setText(String.valueOf(eventData.get(position).getEventId()));
        holder.tvEventName.setText(String.valueOf(eventData.get(position).getEventName()));
        holder.tvEventTicketsCount.setText(String.valueOf(eventData.get(position).getTicketsAvailable()));
        holder.tvEventCategoryId.setText(String.valueOf(eventData.get(position).getCategoryId()));
        if(eventData.get(position).isActive()){
            holder.tvEventActive.setText("Active");
        }else{
            holder.tvEventActive.setText("InActive");
        }

        holder.itemView.setOnClickListener(v -> {
            String eventName = eventData.get(position).getEventName();

            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, EventGoogleResult.class);
            intent.putExtra("eventName", eventName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (eventData != null){
            return eventData.size();
        }
        return 0;
    }

    public void setEvents(List<Event> newData) {
        eventData=newData;
    }

    public class CustomViewHolderEvent extends RecyclerView.ViewHolder {

        public TextView tvEventId;
        public TextView tvEventName;
        public TextView tvEventTicketsCount;
        public TextView tvEventCategoryId;
        public TextView tvEventActive;

        public CustomViewHolderEvent(@NonNull View itemView) {
            super(itemView);
            tvEventId = itemView.findViewById(R.id.event_id_output);
            tvEventName= itemView.findViewById(R.id.event_name_output);
            tvEventTicketsCount = itemView.findViewById(R.id.event_tickets_output);
            tvEventCategoryId = itemView.findViewById(R.id.event_category_id_output);
            tvEventActive = itemView.findViewById(R.id.event_active_output);
        }
    }
}

