package com.example.productapp.retrofit;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;


import com.example.productapp.Model.Product;
import com.example.productapp.Model.ProductListModel;
import com.example.productapp.utils.Global;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppRepository {

    private Application application;

    private MutableLiveData<List<Product>> ProductList;


    private MutableLiveData<String> mutableMessage, sapMutableMessage;
    private MutableLiveData<Boolean> mutableProgress;

    public AppRepository(Application application) {
        this.application = application;
        this.ProductList = new MutableLiveData<>();
        this.mutableMessage = new MutableLiveData<>();
        this.sapMutableMessage = new MutableLiveData<>();
        this.mutableProgress = new MutableLiveData<>();
    }

    //Additional


    // updated 24-01-23 for credit memo module

    public void GetProductdata(){
        mutableProgress.postValue(true);

        if (!Global.isOnline(application.getApplicationContext())) {
            mutableProgress.postValue(false);
            mutableMessage.postValue("No internet");
        } else {


            RetrofitInstance.getInstance().getApiInterface().GetProductList()
                    .enqueue(new Callback<ProductListModel>() {
                        @Override
                        public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (!response.body().getProducts().isEmpty()) {
                                    List<Product> getProductlist = new ArrayList<>();

                                    getProductlist=response.body().getProducts();

                                    ProductList.postValue(getProductlist);

                                } else {
                                    mutableMessage.postValue("Data not found");
                                }
                                mutableProgress.postValue(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductListModel> call, Throwable t) {

                            if (t instanceof IOException) {

                                mutableMessage.postValue("Network Failure");
                            }
                            else {

                                mutableMessage.postValue(t.getMessage());
                            }

                            mutableProgress.postValue(false);
                        }
                    });



        }
    }



    public void GetSingleProduct(int id){
        mutableProgress.postValue(true);

        if (!Global.isOnline(application.getApplicationContext())) {
            mutableProgress.postValue(false);
            mutableMessage.postValue("No internet");
        } else {


            RetrofitInstance.getInstance().getApiInterface().getSingleProduct(id)
                    .enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body()!=null) {

                                    List<Product> productList = Collections.singletonList(response.body());
                                    ProductList.postValue(productList);

//                                    ProductList.postValue(getProductlist);

                                } else {
                                    mutableMessage.postValue("Data not found");
                                }
                                mutableProgress.postValue(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {

                            if (t instanceof IOException) {

                                mutableMessage.postValue("Network Failure");
                            }
                            else {

                                mutableMessage.postValue(t.getMessage());
                            }

                            mutableProgress.postValue(false);
                        }
                    });



        }
    }














    public MutableLiveData<String> getSapMutableMessage() {
        return sapMutableMessage;
    }


    public MutableLiveData<List<Product>> getProductList() {
        return ProductList;
    }


    public MutableLiveData<String> getMutableMessage() {
        return mutableMessage;
    }

    public MutableLiveData<Boolean> getMutableProgress() {
        return mutableProgress;
    }


}
