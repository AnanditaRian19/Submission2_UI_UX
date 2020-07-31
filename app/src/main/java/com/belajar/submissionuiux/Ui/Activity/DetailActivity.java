package com.belajar.submissionuiux.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.belajar.submissionuiux.Adapter.ViewPagerAdapter;
import com.belajar.submissionuiux.Model.UserDetail;
import com.belajar.submissionuiux.Network.Const;
import com.belajar.submissionuiux.R;
import com.belajar.submissionuiux.ViewModel.DetailViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private DetailViewModel detailViewModel;
    private TextView tvName, tvType, tvLocation, tvCompany, tvHtmlUrl, tvFollowers, tvFollowing, tvRepos;
    private ShimmerFrameLayout shimmerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i(TAG, "onCreate");

        initViews();
        getDetailUser();

    }

    private void initViews() {
        ivAvatar = findViewById(R.id.ivAvatar);
        tvName = findViewById(R.id.tvName);
        tvType = findViewById(R.id.tvType);
        tvLocation = findViewById(R.id.tvLocation);
        tvCompany = findViewById(R.id.tvCompany);
        tvHtmlUrl = findViewById(R.id.tvHtmlUrl);
        tvFollowers = findViewById(R.id.tvFollowers);
        tvFollowing = findViewById(R.id.tvFollowing);
        tvRepos = findViewById(R.id.tvRepos);

        shimmerLayout = findViewById(R.id.shimmerLayout);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        try {
            if (getSupportActionBar() != null) {
                String username = getIntent().getStringExtra(Const.BUNDLE_DATA);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(username);
            }
        } catch (Exception e) {
            Log.e("initViews: ", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void getDetailUser() {
        Log.i(TAG, "getDetailUser");

        String username = getIntent().getStringExtra(Const.BUNDLE_DATA);
        detailViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);

        detailViewModel.getUserDetail().observe(this, new Observer<UserDetail>() {
            @Override
            public void onChanged(UserDetail userDetail) {
                Glide.with(getApplicationContext())
                        .load(userDetail.getAvatarUrl())
                        .apply(new RequestOptions().override(85, 85))
                        .into(ivAvatar);
                tvName.setText(userDetail.getName());
                tvType.setText(userDetail.getType());
                tvFollowers.setText(String.valueOf(userDetail.getFollowers()));
                tvFollowing.setText(String.valueOf(userDetail.getFollowing()));
                tvRepos.setText(String.valueOf(userDetail.getRepos()));

                if (userDetail.getLocation() == null && userDetail.getCompany() == null) {
                    tvLocation.setVisibility(View.GONE);
                    tvCompany.setVisibility(View.GONE);
                } else if (userDetail.getLocation() == null || userDetail.getCompany() == null) {
                    if (userDetail.getLocation() == null) {
                        tvLocation.setVisibility(View.GONE);
                        tvCompany.setText(userDetail.getCompany());
                    } else if (userDetail.getCompany() == null) {
                        tvCompany.setVisibility(View.GONE);
                        tvLocation.setText(userDetail.getLocation());
                    }
                } else {
                    tvLocation.setText(userDetail.getLocation());
                    tvCompany.setText(userDetail.getCompany());
                }

                tvHtmlUrl.setText(userDetail.getHtmlUrl());
                tvHtmlUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(userDetail.getHtmlUrl()));
                        startActivity(intent);
                    }
                });

                shimmerLayout.setVisibility(View.INVISIBLE);
            }
        });
        detailViewModel.setUserDetail(username);
    }
}
