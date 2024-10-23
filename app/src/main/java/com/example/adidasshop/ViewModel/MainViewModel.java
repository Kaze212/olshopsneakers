package com.example.adidasshop.ViewModel;

import android.content.ClipData;
import android.transition.Slide;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.adidasshop.Model.CategoryModel;
import com.example.adidasshop.Model.ItemsModel;
import com.example.adidasshop.Model.SliderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

    private MutableLiveData<List<SliderModel>> _slider=new MutableLiveData<>();
    private MutableLiveData<List<CategoryModel>> _category=new MutableLiveData<>();
    private MutableLiveData<List<ItemsModel>> _bestSeller=new MutableLiveData<>();

    public LiveData<List<CategoryModel>> getCategory(){
        return _category;
    }
    public LiveData<List<ItemsModel>> getBestSeller(){
        return _bestSeller;
    }
    public LiveData<List<SliderModel>> getSlider(){
        return _slider;
    }

    public void loadSlider(){
        DatabaseReference ref=firebaseDatabase.getReference("Banner");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SliderModel> lists = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()){
                    SliderModel list = childSnapshot.getValue(SliderModel.class);
                    if (list != null){
                        lists.add(list);
                    }
                    _slider.setValue(lists);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadCategory(){
        DatabaseReference ref=firebaseDatabase.getReference("Category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CategoryModel> lists=new ArrayList<>();
                for (DataSnapshot childSnapshot:snapshot.getChildren()){
                    CategoryModel list=childSnapshot.getValue(CategoryModel.class);
                    if (list!=null){
                        lists.add(list);
                    }
                }
                _category.setValue(lists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void loadBestSeller(){
        DatabaseReference ref=firebaseDatabase.getReference("Items");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ItemsModel> lists=new ArrayList<>();
                for (DataSnapshot childSnapshot:snapshot.getChildren()){
                    ItemsModel list=childSnapshot.getValue(ItemsModel.class);
                    if (list!=null){
                        lists.add(list);
                    }
                }
                _bestSeller.setValue(lists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
