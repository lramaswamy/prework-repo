package com.codepath.mydayly;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lramaswamy on 4/25/2016.
 */
public class TodoItems implements Parcelable {
    //private int uuid = 0;
    private String category = "all";
    private String item_name = "itemName";
    private String priority = "high";
    private String due_by = "dueBy";

    public TodoItems(String category, String item_name, String priority, String due_by) {
        this.category = category;
        this.item_name = item_name;
        this.priority = priority;
        this.due_by = due_by;
    }

    public String getCategory() {
        return category;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getPriority() {
        return priority;
    }

    public String getDue_by() {
        return due_by;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(item_name);
        dest.writeString(priority);
        dest.writeString(due_by);
    }

    private TodoItems(Parcel in) {
        this.category = in.readString();
        this.item_name = in.readString();
        this.priority = in.readString();
        this.due_by = in.readString();
    }

    public static final Parcelable.Creator<TodoItems> CREATOR = new Creator<TodoItems>() {
        @Override
        public TodoItems createFromParcel(Parcel source) {
            return new TodoItems(source);
        }

        @Override
        public TodoItems[] newArray(int size) {
            return new TodoItems[size];
        }
    };
}
