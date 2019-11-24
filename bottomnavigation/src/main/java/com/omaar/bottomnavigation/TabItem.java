package com.omaar.bottomnavigation;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TabItem {

    private RelativeLayout layout;
    private TextView title;
    private ImageView image;

    public TabItem() {
    }

    public TabItem(RelativeLayout tab1, TextView tab1_textview, ImageView tab1_imageview) {
        this.layout = tab1;
        this.title = tab1_textview;
        this.image = tab1_imageview;
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    public void setLayout(RelativeLayout layout) {
        this.layout = layout;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
