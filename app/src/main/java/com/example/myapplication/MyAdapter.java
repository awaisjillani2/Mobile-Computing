package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final int CODE_ = 10;
    List<Book> data;
    Context context;
    public MyAdapter (List<Book> data, Context c){
        this.data = data;
        this.context = c;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.mylayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.descTitle.setText(data.get(position).getLevel());
        try {
            InputStream ims = context.getAssets().open(data.get(position).getCover());
            Drawable d = Drawable.createFromStream(ims, null);
            holder.cover.setImageDrawable(d);
        }
        catch (IOException e){}
        if(data.get(position).getInfo().length() > 70)
            holder.desc.setText(data.get(position).getInfo().substring(0, 70));
        else
            holder.desc.setText(data.get(position).getInfo());
        String temp = data.get(position).getUrl();
        final String type = temp.substring(temp.length()-3);
        if(type.equals("zip") || type.equals("pdf")) {
            holder.readDown.setText("Save File");
            holder.readDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED){
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            ActivityCompat.requestPermissions((Activity)context,permissions,CODE_);
                        }
                        else{
                            downloadFile(data.get(position).getUrl(), data.get(position).getTitle()+'.'+type);
                        }
                    }
                    else{
                        downloadFile(data.get(position).getUrl(),data.get(position).getTitle()+'.'+type);
                    }
                }
            });
        }
        else{
            holder.readDown.setText("Open in Browser");
            holder.readDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(position).getUrl()));
                    context.startActivity(intent);
                }
            });
        }
    }

    private void downloadFile(String url, String name){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(name);
        request.setDescription("Saving File...");
        request.setAllowedOverMetered(true);
        request.setAllowedOverRoaming(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast.makeText(context, "Download Started..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView descTitle;
        TextView desc;
        Button readDown;
        ImageView cover;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.titleText);
            desc = (TextView)itemView.findViewById(R.id.description);
            descTitle = (TextView) itemView.findViewById(R.id.descTitle);
            readDown = (Button) itemView.findViewById(R.id.read_down_btn);
            cover = (ImageView) itemView.findViewById(R.id.cover);
        }
    }
}
