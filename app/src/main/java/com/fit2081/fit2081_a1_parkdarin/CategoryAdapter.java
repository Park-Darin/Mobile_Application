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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>{
    Context categoryContext;
    private List<EventCategory> categoriesData;

    public CategoryAdapter() {}
    public CategoryAdapter(Context context , ArrayList<EventCategory> categoriesArrayList) {
        this.categoryContext = context;
        this.categoriesData = categoriesArrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_layout, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvCategoryId.setText(String.valueOf(categoriesData.get(position).getCategoryId()));
        holder.tvCategoryName.setText(String.valueOf(categoriesData.get(position).getCategoryName()));
        holder.tvCategoryCount.setText(String.valueOf(categoriesData.get(position).getCount()));
        if(categoriesData.get(position).isActive()){
            holder.tvCategoryActive.setText("Yes");
        }else {
            holder.tvCategoryActive.setText("No");
        }

        holder.itemView.setOnClickListener(v -> {
            String eventLocation = categoriesData.get(position).getEventLocation();

            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, GoogleMapActivity.class);
            intent.putExtra("EventLocation", eventLocation);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (categoriesData != null){
            return categoriesData.size();
        }
        return 0;
    }

    public void setCategories(List<EventCategory> newData) {
        categoriesData=newData;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategoryId;
        public TextView tvCategoryName;
        public TextView tvCategoryCount;
        public TextView tvCategoryActive;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryId = itemView.findViewById(R.id.event_category_id_output);
            tvCategoryName= itemView.findViewById(R.id.category_name_output);
            tvCategoryCount = itemView.findViewById(R.id.category_event_count_output);
            tvCategoryActive = itemView.findViewById(R.id.category_active_output);
        }
    }
}
