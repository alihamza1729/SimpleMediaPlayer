package com.firehouse.simplemediaplayer.adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firehouse.simplemediaplayer.R;
import com.firehouse.simplemediaplayer.activities.MainActivity;
import com.firehouse.simplemediaplayer.activities.VideoListActivity;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {

    private Context context;
    ArrayList<File> videoList;


    public VideoAdapter(Context context, ArrayList<File> videoList) {
        this.context = context;
        this.videoList = videoList;
    }


    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder holder, int position) {
        holder.tvFileName.setText(VideoListActivity.fileArrayList.get(position).getName());
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.ivThumbnail.setImageBitmap(bitmap);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("position",holder.getAdapterPosition());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videoList.size() > 0) {
            return videoList.size();
        }
        return 1;
    }
}

class VideoHolder extends RecyclerView.ViewHolder {
    TextView tvFileName;
    ImageView ivThumbnail;
    CardView mCardView;

    public VideoHolder(@NonNull View itemView) {
        super(itemView);

        tvFileName = itemView.findViewById(R.id.tvVideoName);
        ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
        mCardView = itemView.findViewById(R.id.cardView);

    }
}
