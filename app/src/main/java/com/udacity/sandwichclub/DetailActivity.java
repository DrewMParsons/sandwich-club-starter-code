package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String NO_DATA_FOUND = "No Data Found";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(binding.imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        binding.alsoKnownTv.setText(listToString(sandwich.getAlsoKnownAs()));
        binding.originTv.setText(nullCheck(sandwich.getPlaceOfOrigin()));
        binding.ingredientsTv.setText(listToString(sandwich.getIngredients()));
        binding.descriptionTv.setText(nullCheck(sandwich.getDescription()));


    }
    /*
    * Method used to check if Strings are null
    * if NUll: returns a String value of "No data Found"
    * if not NULL: returns original value*/

    private String nullCheck(String s){
        if(s.isEmpty() || s ==null){
            return NO_DATA_FOUND;
        }
        return s;
    }
    /*
     * Method used to check if Lists of Strings are null
     * if NUll: returns a String value of "No data Found"
     * if not NULL: returns original values as a string deliminated by ','*/

    private String listToString(List<String> list){
        StringBuilder sb = new StringBuilder();
        if(list.isEmpty()){
            return NO_DATA_FOUND;
        }else{
            for (String s: list){
                sb.append(s).append(", ");
            }
            return sb.substring(0, sb.length() - 2);
        }

    }
}
