package com.belajar.submissionuiux.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.belajar.submissionuiux.Model.User;
import com.belajar.submissionuiux.Network.Const;
import com.belajar.submissionuiux.R;
import com.belajar.submissionuiux.Ui.Activity.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ListUserSearchAdapter extends RecyclerView.Adapter<ListUserSearchAdapter.ListViewHolder> {

    private Context mContext;
    private List<User> mUser;
    private static final String TAG = "ListUserSearchAdapter";

    public ListUserSearchAdapter(Context mContext, List<User> mUser) {
        this.mContext = mContext;
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(mUser.get(position));
        User user = mUser.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent mIntent = new Intent(mContext, DetailActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle mBundle = new Bundle();
                mBundle.putString(Const.BUNDLE_DATA, mUser.get(position).getName());
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);

                Toast.makeText(mContext, user.getName(), Toast.LENGTH_SHORT).show();
                */
                onItemClickCallback.onItemClicked(mUser.get(holder.getAdapterPosition()), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout rootLayout;
        private ImageView ivAvatar;
        private TextView tvName, tvType, tvId;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvType = itemView.findViewById(R.id.tvType);
            tvId = itemView.findViewById(R.id.tvId);
        }

        private void bind(User mUser) {
            Glide.with(itemView.getContext())
                    .load(mUser.getAvatarUrl())
                    .apply(new RequestOptions().override(50, 50))
                    .into(ivAvatar);
            tvName.setText(mUser.getName());
            tvId.setText(String.valueOf(mUser.getId()));
            tvType.setText(mUser.getType());
        }
    }

    public void setData(List<User> mUser) {
        this.mUser = mUser;
        notifyDataSetChanged();
    }

    public void clearList(List<User> clearListUser) {
        this.mUser = clearListUser;
        this.mUser.clear();
        notifyDataSetChanged();
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(User user, int posistion);
    }
}
