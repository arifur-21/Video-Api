package com.example.streamvideoapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

private Context context;
private List<Video> allVideo;

    public VideoAdapter(Context context, List<Video> allVideo) {
        this.context = context;
        this.allVideo = allVideo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stream_video,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.titleText.setText(allVideo.get(position).getTitle());
        //holder.desText.setText(allVideo.get(position).getDescription());
        Picasso.get().load(allVideo.get(position).getImageUrl()).into(holder.imageView); 

        holder.vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      final Bundle bundle = new Bundle();
      bundle.putSerializable("videoData",allVideo.get(position));
      Intent intent = new Intent(context, Player.class);
      intent.putExtras(bundle);
      view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return allVideo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleText, desText;
        View vv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewId);
            titleText = itemView.findViewById(R.id.titleTextViewId);
            desText = itemView.findViewById(R.id.descriptionId);
            vv = itemView;
        }
    }
}
