package com.example.adidasshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adidasshop.Helper.ChangeNumberItemsListener;
import com.example.adidasshop.Helper.ManagmentCart;
import com.example.adidasshop.Model.ItemsModel;
import com.example.adidasshop.databinding.ViewholderCartBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private ArrayList<ItemsModel> ListItemSelected;
    private ManagmentCart managmentCart;
    private ChangeNumberItemsListener changeNumberItemsListener;


    public CartAdapter(ArrayList<ItemsModel> listItemSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.ListItemSelected = listItemSelected;
        this.changeNumberItemsListener = changeNumberItemsListener;
        this.managmentCart =new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCartBinding binding =ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        ItemsModel item=ListItemSelected.get(position);
        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.feeEachItem.setText("$"+item.getPrice());
        holder.binding.totalEachItem.setText("$"+Math.round(item.getNumberInCart()*item.getPrice()));
        holder.binding.numberItemTxt.setText(String.valueOf(item.getNumberInCart()));

        Glide.with(holder.itemView.getContext())
                .load(item.getPicUrl().get(0))
                .apply(new RequestOptions().centerCrop())
                .into(holder.binding.picCart);

        holder.binding.plusCartBtn.setOnClickListener(v -> managmentCart.plusItem(ListItemSelected, position, () -> {
            notifyDataSetChanged();
            if (changeNumberItemsListener!=null){
                changeNumberItemsListener.onChanged();
            }
        }));
        holder.binding.minusCartBtn.setOnClickListener(v -> managmentCart.minusItem(ListItemSelected, position, () -> {
            notifyDataSetChanged();;
            if (changeNumberItemsListener!=null){
                changeNumberItemsListener.onChanged();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return ListItemSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCartBinding binding;
        public ViewHolder(ViewholderCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
