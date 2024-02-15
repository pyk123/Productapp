package com.example.productapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.example.productapp.Model.Product;
import com.example.productapp.imageslider.SliderAdapter;
import com.example.productapp.imageslider.SliderData;
import com.example.productapp.retrofit.MainViewModel;
import com.example.productapp.utils.GlobalProgressDialog;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProdcutActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    ArrayList<SliderData> sliderDataArrayList;
    String value;
    int Id;
    SliderView sliderView;
    TextView  Productname,ProuctPrice,Productdis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodcut);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Intent intent = getIntent();
        value = intent.getStringExtra("key");
         Log.e("Id",""+value);
         Id= Integer.valueOf(value);
         sliderView = findViewById(R.id.slider);
        Productname = findViewById(R.id.tvProductname);
        ProuctPrice = findViewById(R.id.tvProductPrice);
        Productdis = findViewById(R.id.tvProductdiscription);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        ImageView ivBack = toolbar.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        sliderDataArrayList  = new ArrayList<>();

        mainViewModel.GetSingleProduct(Id);


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
                Log.e("data",""+productList.toString());

                List<String> imageUrls = productList.get(0).getImages();

                for (String imageUrl : imageUrls) {
                    sliderDataArrayList.add(new SliderData(imageUrl));
                }
                loadimageslider();

                Productname.setText(""+productList.get(0).getTitle());
                Productdis.setText(""+productList.get(0).getDescription());
                ProuctPrice.setText("₹ "+productList.get(0).getPrice());

            }
        });
    }

    private void loadimageslider() {




        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

    }

}