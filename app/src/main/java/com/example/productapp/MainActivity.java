package com.example.productapp;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.productapp.Adapter.OnItemClickListener;
import com.example.productapp.Adapter.ProductListAdapter;
import com.example.productapp.Model.Product;
import com.example.productapp.retrofit.MainViewModel;
import com.example.productapp.utils.GlobalProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements OnItemClickListener {

    private MainViewModel mainViewModel;
    ProductListAdapter productListAdapter;
    RecyclerView productlist;

    private List<Product> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        productlist=findViewById(R.id.productlist);
        data  = new ArrayList<>();

        if (data.size() > 0){

            productListAdapter.notifyItemChanged(data.size());
        }

        getProductlist();

        mainViewModel.getLiveProgress().observe(this, progress-> {
            if (progress != null){
                if (progress){
                    GlobalProgressDialog.showProgress(this,"Please wait...");
                } else {
                    GlobalProgressDialog.dismissProgress();
                }
            }
        });

        mainViewModel.getLiveMessage().observe(this, message-> {

            if (message != null){
                if (!message.equals("Success")){
                    Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                    Log.e("message=",""+message);

                }
            }
        });


        mainViewModel.getProductlistdata().observe(this,productList -> {
            if (productList.size() != 0){
                Log.e("categorylistmain",""+productList.toString());

                if(data.size()>0)
                {
                    data.clear();
                }
                data=productList;

                productListAdapter = new ProductListAdapter(data,this,this);
                productlist.setLayoutManager(new LinearLayoutManager(this));
                productlist.setItemAnimator(null);
                productListAdapter.setOnItemClickListener(this);
                productlist.setAdapter(productListAdapter);



            }
        });



    }

    private void getProductlist() {
        mainViewModel.getProductdata();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);



    }

    @Override
    public void Onselected(int id) {

        Log.e("id",""+id);

        Intent intent = new Intent(this, ProdcutActivity.class);
        intent.putExtra("key", ""+id);
        startActivity(intent);
    }
}