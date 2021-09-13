package com.sirstrone.videoplayer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sirstrone.videoplayer.R;
import com.sirstrone.videoplayer.activities.MusicPlayerActivity;
import com.sirstrone.videoplayer.models.MusicModel;
import com.sirstrone.videoplayer.utils.TempMusicList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class MusicAdapters extends RecyclerView.Adapter<MusicAdapters.ViewHolder> {

    private final Context mContext;
    private final List<MusicModel> AudioList;

    public MusicAdapters(Context mContext, List<MusicModel> AudiolList) {
        this.mContext = mContext;
        this.AudioList = AudiolList;
    }

    @NonNull
    @Override
    public MusicAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.music_single_item, viewGroup, false);
        return new MusicAdapters.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapters.ViewHolder viewHolder, int i) {
        final MusicModel Audio = AudioList.get(i);
        final int subposition = i;

        viewHolder.title.setText(Audio.getTitle());
        viewHolder.duration.setText(Audio.getDuration());
        viewHolder.AudioCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TempMusicList.getInstance().setData(AudioList);
                Intent intent = new Intent(mContext, MusicPlayerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("music_id", subposition);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return AudioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, duration;
        CardView AudioCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.audio_title);
            duration = itemView.findViewById(R.id.audio_duration);
            AudioCard = itemView.findViewById(R.id.audio_card);
        }
    }
}
