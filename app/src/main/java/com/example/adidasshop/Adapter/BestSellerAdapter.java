package com.example.adidasshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.adidasshop.Model.ItemsModel;
import com.example.adidasshop.databinding.ViewholderBestSellerBinding;

import java.util.List;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.ViewHolder> {
    private List<ItemsModel> items;
    private Context context;

    public BestSellerAdapter(List<ItemsModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BestSellerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        ViewholderBestSellerBinding binding=ViewholderBestSellerBinding.inflate(
                LayoutInflater.from(context),parent,false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellerAdapter.ViewHolder holder, int position) {
        ItemsModel item=items.get(position);
        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.priceTxt.setText("$"+item.getPrice());
        holder.binding.ratingTxt.setText(String.valueOf(item.getRating()));

        Glide.with(context)
                .load(item.getPicUrl().get(0))
                .apply(new RequestOptions().transform(new CenterCrop()))
                .into(holder.binding.picBestSeller);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderBestSellerBinding binding;
        public ViewHolder(ViewholderBestSellerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}