package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.image_iv) ImageView ingredientsIv;
    @BindView(R.id.also_known_tv) TextView alsoKnownAsTv;
    @BindView(R.id.origin_tv) TextView placeOfOriginTv;
    @BindView(R.id.ingredients_tv) TextView ingredientsTv;
    @BindView(R.id.description_tv) TextView descriptionTv;

    @BindDrawable(R.drawable.default_placeholder) Drawable placeHolder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        //ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {

            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            Log.d(TAG, "EXTRA POSITION not found in intent= " + DEFAULT_POSITION);
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        Log.d(TAG, "onCreate: Parsed sandwich = " + sandwich);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // add .placeholder and error. review suggestion
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Comment the following because I'm using now ButterKnife
        //TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        alsoKnownAsTv.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));

        //TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());

        //TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        ingredientsTv.setText(TextUtils.join(", ", sandwich.getIngredients()));

        //TextView descriptionTv = (TextView) findViewById(R.id.description_tv);
        descriptionTv.setText(sandwich.getDescription());


    }
}
