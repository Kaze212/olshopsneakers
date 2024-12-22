package com.example.adidasshop.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.adidasshop.Adapter.PicListAdapter;
import com.example.adidasshop.Adapter.WeightAdapter;
import com.example.adidasshop.Helper.ManagmentCart;
import com.example.adidasshop.Model.ItemsModel;
import com.example.adidasshop.databinding.ActivityDetailBinding;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity {
    private ActivityDetailBinding binding;
    private ItemsModel item;
    private int numberOrder=1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        getBundleExtra();
        initLists();

        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        View decor=window.getDecorView();
        decor.setSystemUiVisibility(0);
    }

    private void initLists() {
        ArrayList<String> weightList= new ArrayList<>();
        weightList.addAll(item.getSize());
        binding.weightList.setAdapter(new WeightAdapter(weightList));
        binding.weightList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ArrayList<String> piclist=new ArrayList<>(item.getPicUrl());


        Glide.with(this)
                .load(piclist.get(0))
                .into(binding.picMain);

        binding.picList.setAdapter(new PicListAdapter(piclist,binding.picMain));
        binding.picList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void getBundleExtra() {
        item= (ItemsModel) getIntent().getSerializableExtra("object");

        binding.titleTxt.setText(item.getTitle());
        binding.descriptionTxt.setText(item.getDescription());
        binding.priceTxt.setText("$"+item.getPrice());
        binding.ratingTxt.setText(item.getRating()+ " ");
        binding.sellerNameTxt.setText(item.getSellerName());

        binding.addToCartBtn.setOnClickListener(v -> {
            item.setNumberInCart(numberOrder);
            managmentCart.insertItems(item);
        });

        binding.backBtn.setOnClickListener(v -> startActivity(new Intent(DetailActivity.this, MainActivity.class)));
        binding.cartBtn.setOnClickListener(v ->{
                    if (!managmentCart.getListCart().isEmpty()){
                        startActivity(new Intent(DetailActivity.this, CartActivity.class));
                    }else {
                        Toast.makeText(DetailActivity.this, "Keranjang Anda Kosong", Toast.LENGTH_SHORT).show();
                    }
        }

        );

        Glide.with(this)
                .load(item.getSellerPic())
                .apply(new RequestOptions().transform(new CenterCrop()))
                .into(binding.picSeller);

        binding.msgToSellerBtn.setOnClickListener(v -> {
            Intent sendIntent=new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"+item.getSellerTell()));
            sendIntent.putExtra("sms_body","type your message");
            startActivity(sendIntent);
        });

        binding.callToSellerBtn.setOnClickListener(v -> {
            String phone=String.valueOf(item.getSellerTell());
            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",phone,null));
            startActivity(intent);
        });
    }
}