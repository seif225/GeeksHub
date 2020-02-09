package com.example.geekshub.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.geekshub.R;
import com.example.geekshub.data.BookModel;
import com.example.geekshub.data.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<BookModel> bookModelArrayList;
    private Context context;

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    public RecyclerViewAdapter(List<BookModel> bookModels, Context context) {
        this.bookModelArrayList = bookModels;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        BookModel bookModel = bookModelArrayList.get(position);
        holder.bookName.setText(bookModel.getName());


        if (bookModel.getPicture() != null)
            Picasso.get().load(bookModel.getPicture()).resize(600,800).into(holder.book_img);
    }

    @Override
    public int getItemCount() {
        if (bookModelArrayList == null) return 0;
        return bookModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rootLayout;
        ImageView book_img;
        TextView bookName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            book_img = itemView.findViewById(R.id.book_img);
            bookName = itemView.findViewById(R.id.bookName);
        }
    }
}
