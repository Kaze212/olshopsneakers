package com.example.adidasshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.adidasshop.Adapter.CartAdapter;
import com.example.adidasshop.Helper.ChangeNumberItemsListener;
import com.example.adidasshop.Helper.ManagmentCart;
import com.example.adidasshop.R;
import com.example.adidasshop.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private ManagmentCart managmentCart;
    private double tax = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart=new ManagmentCart(this);

        setVariable();
        initCartList();
        calculatorCart();
        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        View decor=window.getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    private void initCartList() {
        binding.cartView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), this, this::calculatorCart));
    }

    private void calculatorCart(){
        double percentTax=0.2;
        double delivery=15.0;
        tax=Math.round(managmentCart.getTotalFee()*percentTax*100)/100.0;
        double total=Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100.0;
        double itemTotal= Math.round(managmentCart.getTotalFee()*100)/100.0;

        binding.totalFeeTxt.setText("$"+itemTotal);
        binding.taxTxt.setText("$"+tax);
        binding.deliveryTxt.setText("$"+delivery);
        binding.totalTxt.setText("$"+total);
    }
    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, MainActivity.class)));
    }
}