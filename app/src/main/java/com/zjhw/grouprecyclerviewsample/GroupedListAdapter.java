package com.zjhw.grouprecyclerviewsample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class GroupedListAdapter extends GroupedRecyclerViewAdapter {

    AppCompatActivity context;
    public GroupedListAdapter(Context context) {
        super(context);
        this.context = (AppCompatActivity) context;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0)
            return 80;
        return 1;
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        if (groupPosition == 0)
            return false;
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.item_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        if (viewType == 1)
            return R.layout.item_child;
        return R.layout.child_recyclerview;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {


    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        if (groupPosition == 1) {
            TabLayoutMediator mediator = new TabLayoutMediator(headerViewHolder.tabLayout, customViewHolder.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                }
            });
            //要执行这一句才是真正将两者绑定起来
            mediator.attach();
        }
    }

    CustomViewHolder customViewHolder;
    HeaderViewHolder headerViewHolder;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            View view = LayoutInflater.from(context).inflate(R.layout.child_recyclerview, parent, false);
            return customViewHolder = new CustomViewHolder(view, context);
        }
        else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return headerViewHolder = new HeaderViewHolder(view, context);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public int getChildViewType(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return 1;
        }
        return 2;
    }

    class HeaderViewHolder extends BaseViewHolder {

        TabLayout tabLayout;
        public HeaderViewHolder(View itemView, AppCompatActivity context) {
            super(itemView);
            tabLayout = itemView.findViewById(R.id.tabLayout);
        }
    }

    class CustomViewHolder extends BaseViewHolder {

        ViewPager2 viewPager;
        ArrayList<Fragment> fragments;
        public CustomViewHolder(@NonNull View itemView, AppCompatActivity context) {
            super(itemView);

            viewPager = itemView.findViewById(R.id.pageViewer);
            fragments = new ArrayList<Fragment>();
            fragments.add(new RVFragment(50));
            fragments.add(new SecondFragment(10));
            viewPager.setAdapter(new FragmentStateAdapter(context.getSupportFragmentManager(), context.getLifecycle()) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    return CustomViewHolder.this.fragments.get(position);
                }

                @Override
                public int getItemCount() {
                    return 2;
                }
            });
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);

                    Fragment fragment = CustomViewHolder.this.fragments.get(position);
                    updatePagerHeightForChild(fragment.getView(), viewPager);
                }
            });
        }

        void updatePagerHeightForChild(View view, ViewPager2 pager) {
            view.post(() -> {
                int wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY);
                int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                view.measure(wMeasureSpec, hMeasureSpec);
                if (pager.getLayoutParams().height != view.getMeasuredHeight()) {
                    pager.setLayoutParams(new LinearLayout.LayoutParams(pager.getLayoutParams().width, view.getMeasuredHeight()));
                }
            });
        }
    }
}
