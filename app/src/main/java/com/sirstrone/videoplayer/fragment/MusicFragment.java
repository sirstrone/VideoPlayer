package com.sirstrone.videoplayer.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.sirstrone.videoplayer.R;
import com.sirstrone.videoplayer.adapters.MusicAdapters;
import com.sirstrone.videoplayer.models.MusicModel;
import com.sirstrone.videoplayer.utils.MyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class MusicFragment extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private View rootView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_music,container,false);
        return rootView;
    }

    private void initView() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        List<MusicModel> musicModelList;
        RecyclerView recyclerView;
        MusicAdapters adapter;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.rec_music, container, false);
            int id = getArguments().getInt(ARG_SECTION_NUMBER, 0);
            musicModelList = new ArrayList<>();
            recyclerView = rootView.findViewById(R.id.musicRecycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            parseAllVideo();
            sortList(id);
            adapter = new MusicAdapters(getContext(), musicModelList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            return rootView;
        }


        private void parseAllVideo() {
            try {
                String[] proj = {MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST,
                };
                Cursor audiocursor = getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        proj, null, null, null);
                if (audiocursor != null) {
                    if (audiocursor.moveToFirst()) {
                        int name_index = audiocursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                        int duration_index = audiocursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
                        int path_index = audiocursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                        int album_index = audiocursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                        int artist_index = audiocursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                        int music_id = 0;
                        do {
                            String name = audiocursor.getString(name_index);
                            String filepath = audiocursor.getString(path_index);
                            String duration = MyUtils.milisecondToHour(audiocursor.getLong(duration_index));
                            String album = audiocursor.getString(album_index);
                            String artist = audiocursor.getString(artist_index);
                            MusicModel audio = new MusicModel();
                            audio.setId(music_id);
                            audio.setTitle(name);
                            audio.setDuration(duration);
                            audio.setAlbum(album);
                            audio.setArtist(artist);
                            audio.setPath(filepath);
                            musicModelList.add(audio);
                            music_id++;
                        } while (audiocursor.moveToNext());
                    }
                    audiocursor.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sortList(int n) {
            switch (n) {
                case 2:
                    Collections.sort(musicModelList, new Comparator<MusicModel>() {
                        @Override
                        public int compare(MusicModel m1, MusicModel m2) {
                            return m1.getAlbum().compareTo(m2.getAlbum());
                        }
                    });
                    break;
                case 3:
                    Collections.sort(musicModelList, new Comparator<MusicModel>() {
                        @Override
                        public int compare(MusicModel m1, MusicModel m2) {
                            return m1.getArtist().compareTo(m2.getArtist());
                        }
                    });
                    break;
                default:
                    return;
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }


}
