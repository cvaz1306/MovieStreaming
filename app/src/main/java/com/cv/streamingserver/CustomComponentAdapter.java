package com.cv.streamingserver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso; // Import Picasso library
import java.util.List;

public class CustomComponentAdapter extends RecyclerView.Adapter<CustomComponentAdapter.CustomComponentViewHolder> {

    private List<String> imageUrls; // List of image URLs
    private List<String> data; // List of image URLs

    private List<String> vids;

    public CustomComponentAdapter(List<String> imageUrls, List<String> data, List<String> vids) {
        this.imageUrls = imageUrls;
        this.data=data;
        this.vids=vids;
    }

    @NonNull
    @Override
    public CustomComponentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_component, parent, false);
        return new CustomComponentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomComponentViewHolder holder, int position) {
        // Bind data to CustomComponent or customize as needed
        CustomComponent customComponent = holder.itemView.findViewById(R.id.customComponent);

        // Load image into CustomComponent using Picasso
        Picasso p=Picasso.get();
        p.setLoggingEnabled(true);
        p.load(imageUrls.get(position)).into(customComponent.getImage());
        customComponent.setText(data.get(position));
        customComponent.setVidUrl(vids.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class CustomComponentViewHolder extends RecyclerView.ViewHolder {

        public CustomComponentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

