package com.omaar.bottomnavigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.parseColor;

public class BottomNavigation extends LinearLayout {

    // material_deep_teal_500
    static final int DEFAULT_TEXT = parseColor("#000000");
    static final int DEFAULT_TEXT_SELECTED = parseColor("#FFFFFF");
    static final int DEFAULT_BACKGROUND = parseColor("#EEEEEE");
    static final int DEFAULT_BACKGROUND_SELECTED = parseColor("#CCCCCC");

    //create int for the styles attribute given from the xml file
    private int bar_background;
    private int selected_tab_background;
    private int text_color;
    private int selected_text_color;

    //core fields
    private Context mContext;
    private AttributeSet attrs;
    private int styleAttr;
    private View view;

    //create the view pager object
    private ViewPager viewPager;

    private int size = 0;

    //create list of fragments that will hold all fragments
    List<Fragment> fragmentList = new ArrayList<>();

    //create list of vector drawables that will hold all fragments
    List<AnimatedVectorDrawable> vectorsList = new ArrayList<>();

    //create list of titles that will hold all fragments
    List<String> titlesList = new ArrayList<>();

    //create list of TabItem custom object that will hold all fragments
    List<TabItem> tabs = new ArrayList<>();

    //create list of TabItem custom object that will hold all fragments
    List<Integer> vectorRes = new ArrayList<>();

    public BottomNavigation(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public BottomNavigation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.attrs = attrs;
        initView();
    }

    public BottomNavigation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.attrs = attrs;
        this.styleAttr = defStyleAttr;
        initView();
    }


    /**
     * A method called in the constructor to initialize the custom views
     */
    private void initView() {
        this.view = this;

        //Inflating the XML view
        inflate(mContext, R.layout.bottom_navigation, this);

        //initialize the view pager
        viewPager = findViewById(R.id.viewpager);

        //get the style arrays
        TypedArray arr = mContext.obtainStyledAttributes(attrs, R.styleable.BottomBarPager,
                styleAttr, 0);

        //get the bar background color
        try {
            bar_background = arr.getColor(R.styleable.BottomBarPager_bar_background, DEFAULT_BACKGROUND);

        } catch (NullPointerException e) {
            bar_background = DEFAULT_BACKGROUND;
        }

        //get the selected tab background color
        try {
            selected_tab_background = arr.getColor(R.styleable.BottomBarPager_selected_tab_background, DEFAULT_BACKGROUND_SELECTED);
        } catch (NullPointerException e) {
            selected_tab_background = DEFAULT_BACKGROUND_SELECTED;
        }

        //get the text color
        try {
            text_color = arr.getColor(R.styleable.BottomBarPager_text_color, DEFAULT_TEXT);
        } catch (NullPointerException e) {
            text_color = DEFAULT_TEXT;
        }

        //get the selected text color
        try {
            selected_text_color = arr.getColor(R.styleable.BottomBarPager_selected_text_color, DEFAULT_TEXT_SELECTED);
        } catch (NullPointerException e) {
            selected_text_color = DEFAULT_BACKGROUND_SELECTED;
        }

        //a method called to initialize listeners
        initListeners();

        //recycle
        arr.recycle();

    }

    /**
     * A method that is called whenever the user adds a new view
     */
    private void updateView() {

        //find the bottom navigation layout
        LinearLayout bottomnavigation = findViewById(R.id.bottom_bar);

        //update it's weight sum to the new number of the items
        bottomnavigation.setWeightSum(size);

        //create a new relative layout that will holds the new tab
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

        //create the text view element and give it the proper attributes
        TextView textview = new TextView(mContext);
        textview.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams textParams = (RelativeLayout.LayoutParams) textview.getLayoutParams();
        textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        textParams.setMargins(0, 0, 0, 8);
        textview.setTextSize(12);
        textview.setTextColor(BLACK);

        //create the image view element and give it the proper attributes
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        RelativeLayout.LayoutParams imageParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageParams.setMargins(0, 0, 0, 16);

        //set the new added icon
        imageView.setImageDrawable(vectorsList.get(size - 1));

        if (titlesList.get(size - 1) != null) {
            //if this tab has a title then set it
            textview.setText(titlesList.get(size - 1));

            //align the icon to the top of the parent
            imageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else {
            //if this tab does not has a title then set it's visibility to gone
            textview.setVisibility(GONE);

            //center the icon in the parent
            imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        }

        //add the image and the text view to the tab layout
        relativeLayout.addView(textview);
        relativeLayout.addView(imageView);

        //add the tab layout data Object to the tabs list
        TabItem item = new TabItem(relativeLayout, textview, imageView);
        tabs.add(item);

        //add the tab view to the bottom navigation layout
        bottomnavigation.addView(relativeLayout);

        //create and initialize a view pager adapter object with the three fragments
        ViewPagerAdapter viewPagerAadpter = new ViewPagerAdapter(((AppCompatActivity) mContext).getSupportFragmentManager()
                , fragmentList);

        //setup the viewpager with the adapter
        viewPager.setAdapter(viewPagerAadpter);

        //update the listeners
        updateListener();

        //initialize with the first fragment
        viewPager.setCurrentItem(0);

        //initialize tabs to view first tab initially
        setSelectedTabBackground(0);
        setTextColor(0);

        if (Build.VERSION.SDK_INT < 23)
            animateIconsLowerApi(0);
        else
            animateIcons(0);

    }

    /**
     * A method that accepts a fragment, vector and a title and create a new fragment view with this new info
     */
    public void add(Fragment fragment, int res, String title) {

        //if size is less than 5, add the items to the lists
        if (size < 5) {
            fragmentList.add(fragment);
            titlesList.add(title);
            vectorRes.add(res);
            vectorsList.add((AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources()
                    , res, null));
            size++;
            updateView();
        } else {
            //if size exceeded the 5 items limit, throw exception
            throw new ArrayIndexOutOfBoundsException("Only five views are allowed");
        }

    }

    /**
     * A method that accepts a fragment and a title and create a new fragment view with this new info
     */
    public void add(Fragment fragment, int res) {

        //if size is less than 5, add the items to the lists
        if (size < 5) {
            fragmentList.add(fragment);
            titlesList.add(null);
            vectorRes.add(res);
            vectorsList.add((AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources()
                    , res, null));
            size++;
            updateView();
        } else {
            //if size exceeded the 5 items limit, throw exception
            throw new ArrayIndexOutOfBoundsException("Only five views are allowed");
        }

    }

    /**
     * A method called to set the tabs backgrounds according to the selected one
     */
    private void setSelectedTabBackground(int index) {

        for (int i = 0; i < size; i++) {

            if (i == index) {
                //if this is the selected tab -> update it's background to selected tab background color
                tabs.get(i).getLayout().setBackgroundColor(selected_tab_background);
            } else {
                //if this is the not selected tab -> update it's background to normal tab background color
                tabs.get(i).getLayout().setBackgroundColor(bar_background);
            }

        }


    }

    /**
     * A method called to set the tabs text colors according to the selected tab
     */
    private void setTextColor(int index) {

        for (int i = 0; i < size; i++) {

            if (i == index && titlesList.get(i) != null) {
                //if this is the selected tab -> update it's text color to selected tab text color
                tabs.get(i).getTitle().setTextColor(selected_text_color);
            } else if (titlesList.get(i) != null) {
                //if this is the not selected tab -> update it's text color to normal tab text color
                tabs.get(i).getTitle().setTextColor(text_color);
            }

        }

    }


    /**
     * A method called to animate the icons according to the selected tab
     */
    private void animateIconsLowerApi(int index) {

        //if a tab is selected, animate it's vector and reset the other vectors
        for (int i = 0; i < size; i++) {
            if (i == index) {
                //if this is the selected tab -> start it's icon animation
                vectorsList.get(i).start();
            } else {
                //if this is the not selected tab -> reset it's icon animation
                vectorsList.set(i, (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources()
                        , vectorRes.get(i), null));
                tabs.get(i).getImage().setImageDrawable(vectorsList.get(i));
            }
        }

    }

    /**
     * A method called to animate the icons according to the selected tab
     */
    @RequiresApi(23)
    private void animateIcons(int index) {

        //if a tab is selected, animate it's vector and reset the other vectors
        for (int i = 0; i < size; i++) {
            if (i == index) {
                //if this is the selected tab -> start it's icon animation
                vectorsList.get(i).start();
            } else {
                //if this is the not selected tab -> reset it's icon animation
                vectorsList.get(i).reset();
            }
        }

    }


    /**
     * A method called to initialize the listeners
     * viewPagerChangeListener and the tabs clicks listener
     */
    private void initListeners() {

        //add listener to the view pager changes
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //when selected page changes

                //change tabs backgrounds according to the selected tab
                setSelectedTabBackground(position);

                //change tabs text colors according to the selected tab
                setTextColor(position);

                //animate and reset icons according to the selected tab
                if (Build.VERSION.SDK_INT < 23)
                    animateIconsLowerApi(position);
                else
                    animateIcons(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * A method called to update the listeners
     */
    private void updateListener() {

        //get the new added view
        final int currentItem = size - 1;

        tabs.get(currentItem).getLayout().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //change the selected tab to currentItem tab
                viewPager.setCurrentItem(currentItem);

                //change tabs backgrounds according to the selected tab
                setSelectedTabBackground(currentItem);

                //change tabs text colors according to the selected tab
                setTextColor(currentItem);

                //animate and reset icons according to the selected tab
                if (Build.VERSION.SDK_INT < 23)
                    animateIconsLowerApi(currentItem);
                else
                    animateIcons(currentItem);

            }
        });

    }

}
