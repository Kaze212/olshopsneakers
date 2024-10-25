package com.example.adidasshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.adidasshop.Adapter.BestSellerAdapter;
import com.example.adidasshop.Adapter.CategoryAdapter;
import com.example.adidasshop.Adapter.SliderAdapter;
import com.example.adidasshop.Helper.ManagmentCart;
import com.example.adidasshop.Model.SliderModel;
import com.example.adidasshop.R;
import com.example.adidasshop.ViewModel.MainViewModel;
import com.example.adidasshop.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel=new MainViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        initBanners();
        initCategory();
        initBestSeller();
        bottomNavigation();
        

        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        View decor=window.getDecorView();
        decor.setSystemUiVisibility(0);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void bottomNavigation() {
        binding.cartBtn.setOnClickListener(v -> {
            ManagmentCart managmentCart= new ManagmentCart(MainActivity.this);
            if (!managmentCart.getListCart().isEmpty()){
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }else {
                Toast.makeText(MainActivity.this, "Keranjang Anda Kosong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBestSeller() {
        binding.progressBestSeller.setVisibility(View.VISIBLE);
        viewModel.getBestSeller().observe(this,items->{
            binding.recyclerBestSeller.setLayoutManager(new GridLayoutManager(this,2));
            binding.recyclerBestSeller.setAdapter(new BestSellerAdapter(items));
            binding.progressBestSeller.setVisibility(View.GONE);
        });
        viewModel.loadBestSeller();
    }

    private void initCategory() {
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        viewModel.getCategory().observe(this,items->{
            binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewCategory.setAdapter(new CategoryAdapter(items));
            binding.progressBarCategory.setVisibility(View.GONE);
        });
        viewModel.loadCategory();
    }

    private void initBanners() {
        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.getSlider().observe(this,banners->{
            setupBanners(banners);
            binding.progressBar.setVisibility(View.GONE);
        });
        viewModel.loadSlider();
    }

    private void setupBanners(List<SliderModel> images){
        binding.viewPager2.setAdapter(new SliderAdapter(images,binding.viewPager2));
        binding.viewPager2.setClipToPadding(false);
        binding.viewPager2.setClipChildren(false);
        binding.viewPager2.setOffscreenPageLimit(3);
        binding.viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer=new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        binding.viewPager2.setPageTransformer(transformer);

        if (images.size()>1){
            binding.dotIndicator.setVisibility(View.VISIBLE);
            binding.dotIndicator.attachTo(binding.viewPager2);
        }
    }

}