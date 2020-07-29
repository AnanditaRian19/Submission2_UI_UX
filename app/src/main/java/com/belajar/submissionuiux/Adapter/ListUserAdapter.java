package com.belajar.submissionuiux.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.belajar.submissionuiux.Model.User;
import com.belajar.submissionuiux.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ListViewHolder> {

//    public Context context;
//    private List<User> mUser;
//
//    public ListUserAdapter(Context context, List<User> mUser) {
//        this.context = context;
//        this.mUser = mUser;
//    }

    private List<User> mUser = new ArrayList<>();

    public void setmUser(List<User> mUser) {
        this.mUser = mUser;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        User user = mUser.get(position);

        holder.bind(mUser.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), user.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private CircleImageView ivAvatar;
        private TextView tvName, tvLink;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvLink = itemView.findViewById(R.id.tvLink);
        }

        private void bind(User mUser) {
            Glide.with(itemView.getContext())
                    .load(mUser.getAvatarUrl())
                    .into(ivAvatar);
            tvName.setText(mUser.getName());
            tvLink.setText(mUser.getUrl());
        }
    }
}
