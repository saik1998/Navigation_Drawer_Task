package com.firstapp.navigation_drawer_task.ui.calllog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.navigation_drawer_task.R;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ImageHolder> {
        Context context;
        int[] images;

public SliderAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
        }

@NonNull
@Override
public SliderAdapter.ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_image,parent,false);

        return new ImageHolder(root);
        }

@Override
public void onBindViewHolder(@NonNull SliderAdapter.ImageHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);

        }

@Override
public int getItemCount() {
        return images.length;
        }

public class ImageHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    public ImageHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageview);

    }
}
}