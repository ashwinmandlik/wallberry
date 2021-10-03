package com.abhinav.wallpapers;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Category_adapter.CategoryClickInterface {

    private EditText searchEdit;
    private ImageView searchButton;
    private RecyclerView categoryRV;
    private RecyclerView wallpaperRV;
    private ProgressBar loadingPB;
    private ArrayList<String> wallpaperArrayList;
    private ArrayList<Category_Model> category_modelArrayList;
    private Category_adapter category_adapter;
    private Wallpaper_adapter wallpaper_adapter;
    //API key - 563492ad6f9170000100000178903c8eefae47488738eb7584146a10
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        searchEdit = findViewById(R.id.Search_bar);
        searchButton = findViewById(R.id.search_button);
        categoryRV = findViewById(R.id.Category_recycler_view);
        wallpaperRV = findViewById(R.id.wallpapers_recycler_view);
        loadingPB = findViewById(R.id.SHOW_PROGRESS);
        wallpaperArrayList = new ArrayList<>();
        category_modelArrayList = new ArrayList<>();

        //for category recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);
        categoryRV.setLayoutManager(linearLayoutManager);
        category_adapter = new Category_adapter(category_modelArrayList,this,this::onCategoryClick);
        categoryRV.setAdapter(category_adapter);

        //for wallpaper recycle view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,2);
        wallpaperRV.setLayoutManager(gridLayoutManager);
        wallpaper_adapter = new Wallpaper_adapter(wallpaperArrayList,this);
        wallpaperRV.setAdapter(wallpaper_adapter);


        //this function will help to search for wallpapers
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = searchEdit.getText().toString();
                if(searchString.isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter your choice",Toast.LENGTH_SHORT).show();
                }else{
                    getWallpapersBySearch(searchString);
                }
            }
        });

        //function to get categories
        getCategories();

        //function to get all wallpapers from API
        getWallpapers();


    }

    private void getWallpapersBySearch(String str) {
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/search?query="+str+"&per_page=100&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray photoArray = null;
                loadingPB.setVisibility(View.GONE);
                try {
                    photoArray = response.getJSONArray("photos");
                    for(int i=0;i<photoArray.length();++i){
                        JSONObject photoObject = photoArray.getJSONObject(i);
                        String imgUrl = photoObject.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    wallpaper_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Authorization","563492ad6f9170000100000178903c8eefae47488738eb7584146a10");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    private void getCategories() {
        category_modelArrayList.add(new Category_Model("Technology","https://images.unsplash.com/photo-1485827404703-89b55fcc595e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MXwxfDB8MXxhbGx8fHx8fHx8fA&ixlib=rb-1.2.1&q=80&w=1080&utm_source=unsplash_source&utm_medium=referral&utm_campaign=api-credit"));
        category_modelArrayList.add(new Category_Model("Programming","https://images.unsplash.com/photo-1605379399642-870262d3d051?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxfDB8MXxhbGx8fHx8fHx8fHwxNjE5MTcyMzE2&ixlib=rb-1.2.1&q=80&w=1080&utm_source=unsplash_source&utm_medium=referral&utm_campaign=api-credit"));
        category_modelArrayList.add(new Category_Model("Nature","https://images.unsplash.com/photo-1447752875215-b2761acb3c5d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MXwxfDB8MXxhbGx8fHx8fHx8fA&ixlib=rb-1.2.1&q=80&w=1080&utm_source=unsplash_source&utm_medium=referral&utm_campaign=api-credit"));
        category_modelArrayList.add(new Category_Model("Travel","https://images.unsplash.com/photo-1503220317375-aaad61436b1b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MXwxfDB8MXxhbGx8fHx8fHx8fA&ixlib=rb-1.2.1&q=80&w=1080&utm_source=unsplash_source&utm_medium=referral&utm_campaign=api-credit"));
        category_modelArrayList.add(new Category_Model("Architecture","https://images.unsplash.com/photo-1538378918848-29dbc0910082?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxfDB8MXxyYW5kb218MHx8fHx8fHx8MTYzMjU3NDIxMA&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1080"));
        category_modelArrayList.add(new Category_Model("Arts","https://images.unsplash.com/photo-1460661419201-fd4cecdf8a8b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MXwxfDB8MXxhbGx8fHx8fHx8fA&ixlib=rb-1.2.1&q=80&w=1080&utm_source=unsplash_source&utm_medium=referral&utm_campaign=api-credit"));
        category_modelArrayList.add(new Category_Model("Music","https://images.unsplash.com/photo-1505740420928-5e560c06d30e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MXwxfDB8MXxhbGx8fHx8fHx8fA&ixlib=rb-1.2.1&q=80&w=1080&utm_source=unsplash_source&utm_medium=referral&utm_campaign=api-credit"));
        category_modelArrayList.add(new Category_Model("Sports","https://images.unsplash.com/photo-1461896836934-ffe607ba8211?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MXwxfDB8MXxhbGx8fHx8fHx8fA&ixlib=rb-1.2.1&q=80&w=1080&utm_source=unsplash_source&utm_medium=referral&utm_campaign=api-credit"));
        category_modelArrayList.add(new Category_Model("Cars","https://images.unsplash.com/photo-1503376780353-7e6692767b70?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MXwxfDB8MXxhbGx8fHx8fHx8fA&ixlib=rb-1.2.1&q=80&w=1080&utm_source=unsplash_source&utm_medium=referral&utm_campaign=api-credit"));
        category_modelArrayList.add(new Category_Model("Abstract","https://images.unsplash.com/photo-1541701494587-cb58502866ab?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MXwxfDB8MXxhbGx8fHx8fHx8fA&ixlib=rb-1.2.1&q=80&w=1080&utm_source=unsplash_source&utm_medium=referral&utm_campaign=api-credit"));
        category_adapter.notifyDataSetChanged();
//        category_modelArrayList.add(new Category_Model("Flowers",""));
//        category_modelArrayList.add(new Category_Model("Fashion",""));
//        category_modelArrayList.add(new Category_Model("Superheroes",""));
//        category_modelArrayList.add(new Category_Model("Animals",""));
//        category_modelArrayList.add(new Category_Model("History",""));
//        category_modelArrayList.add(new Category_Model("Interiors",""));

    }

    private void getWallpapers(){
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=100&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try {
                    JSONArray photoArray = response.getJSONArray("photos");
                    for(int i=0;i<photoArray.length();++i){
                        JSONObject photoObject = photoArray.getJSONObject(i);
                        String imgUrl = photoObject.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    wallpaper_adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Some error occurred",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Authorization","563492ad6f9170000100000178903c8eefae47488738eb7584146a10");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);


    }

    @Override
    public void onCategoryClick(int pos) {
        String category = category_modelArrayList.get(pos).getCategory();
        getWallpapersBySearch(category);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}