package com.sirstrone.videoplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.sirstrone.videoplayer.R;
import com.sirstrone.videoplayer.activities.MainActivity;
import com.sirstrone.videoplayer.fragments.AllVideos;
import com.sirstrone.videoplayer.fragments.VideoFolders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class VideoFragment extends Fragment {
    private ViewPager vp_pages;
    private View rootView;

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fragment_video,container,false);
//        initView();
//        return rootView;
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView(){
        vp_pages = (ViewPager) rootView.findViewById(R.id.TabViewPager);
        PagerAdapter pagerAdapter = new FragmentAdapter(getFragmentManager());
        vp_pages.setAdapter(pagerAdapter);
        TabLayout tbl_pages = (TabLayout) rootView.findViewById(R.id.TabButtonBar);
        tbl_pages.setupWithViewPager(vp_pages);
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AllVideos();
                case 1:
                    return new VideoFolders();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                //
                //Your tab titles
                //
                case 0:
                    return "All Videos";
                case 1:
                    return "Folders";
                default:
                    return null;
            }
        }
    }
}
