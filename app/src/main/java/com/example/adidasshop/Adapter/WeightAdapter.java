package com.example.adidasshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adidasshop.R;
import com.example.adidasshop.databinding.ViewholderPiclistBinding;
import com.example.adidasshop.databinding.ViewholderWeightBinding;

import java.util.List;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.viewholder> {
    private List<String> items;
    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;
    private Context context;

    public WeightAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public WeightAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        ViewholderWeightBinding binding=ViewholderWeightBinding.inflate(
                LayoutInflater.from(context), parent,false);

        return new viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightAdapter.viewholder holder, int position) {
        holder.binding.weightTxt.setText(items.get(position));
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition=selectedPosition;
                selectedPosition=position;
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);
            }
        });

        if (selectedPosition==position){
            holder.binding.weightLayout.setBackgroundResource(R.drawable.pink_bg_2);
            holder.binding.weightTxt.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.binding.weightLayout.setBackgroundResource(R.drawable.grey_bg);
            holder.binding.weightTxt.setTextColor(context.getResources().getColor(R.color.black));

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ViewholderWeightBinding binding;
        public viewholder(ViewholderWeightBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
