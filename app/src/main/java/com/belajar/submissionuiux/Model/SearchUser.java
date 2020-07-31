package com.belajar.submissionuiux.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchUser {

    @SerializedName("total_count")
    private long totalCount;

    private List<User> items;

    public SearchUser(long totalCount, List<User> items) {
        this.totalCount = totalCount;
        this.items = items;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }
}
