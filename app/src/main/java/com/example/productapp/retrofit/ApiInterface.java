package com.example.productapp.retrofit;


import com.example.productapp.Model.Product;
import com.example.productapp.Model.ProductListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface
{
    //String BASE_URL = "http://192.168.1.100:90/";
    String BASE_URL = "https://dummyjson.com/";

    @GET("products")
    Call<ProductListModel> GetProductList();


    @GET("products/{id}")
    Call<Product> getSingleProduct(@Path("id") int id);

}
