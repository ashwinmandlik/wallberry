package com.abhinav.wallpapers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Category_adapter extends RecyclerView.Adapter<Category_adapter.ViewHolder> {
    private ArrayList<Category_Model> category_modelArrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public Category_adapter(ArrayList<Category_Model> category_modelArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.category_modelArrayList = category_modelArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public Category_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_rv_item,parent,false);
        return new Category_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_adapter.ViewHolder holder, int position) {
        Category_Model category_model = category_modelArrayList.get(position);
        holder.categoryTextView.setText(category_model.getCategory());
        Glide.with(context).load(category_model.getCategory_image_url()).into(holder.categoryImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return category_modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView categoryTextView;
        private ImageView categoryImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.TextViewCategory);
            categoryImageView = itemView.findViewById(R.id.ImageViewCategory);
        }
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int pos);
    }
}
