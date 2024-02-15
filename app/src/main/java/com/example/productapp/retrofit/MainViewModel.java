package com.example.productapp.retrofit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.productapp.Model.Product;

import java.util.List;


public class MainViewModel extends AndroidViewModel {

    private final AppRepository appRepository;

    private static LiveData<List<Product>> allProductlist;

    private LiveData<String> liveMessage, sapLiveMessage;
    private LiveData<Boolean> liveProgress;

    public MainViewModel(@NonNull Application application) {
        super(application);

        this.appRepository = new AppRepository(application);
        this.allProductlist = appRepository.getProductList();
        this.liveMessage = appRepository.getMutableMessage();
        this.liveProgress = appRepository.getMutableProgress();
        this.sapLiveMessage = appRepository.getSapMutableMessage();

    }



    public void getProductdata(){
        appRepository.GetProductdata();
    }
    public void GetSingleProduct(int id){
        appRepository.GetSingleProduct(id);
    }



    public LiveData<List<Product>>getProductlistdata() {
        return allProductlist;
    }



    public LiveData<String> getLiveMessage() {
        return liveMessage;
    }

    public LiveData<Boolean> getLiveProgress() {
        return liveProgress;
    }


}

