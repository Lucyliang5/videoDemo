package com.example.liangxiao.videotest;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.video_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new VideoAdapter(this));

        init(recyclerView);
    }

    public void init(RecyclerView recyclerView) {
        recyclerView.scrollToPosition(1);
//        ((VideoViewHolder)recyclerView.findViewHolderForAdapterPosition(1)).play();
    }

    public static class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
        private String[] urls = {"file:///android_asset/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        };
        private Context context;
        private int initPlayPosition = 1;

        public VideoAdapter(Context context) {
            this.context = context;
        }

        @Override
        public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);

            return new VideoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(VideoViewHolder holder, int position) {
            String uri = "android.resource://" + context.getPackageName() + "/" + R.raw.big_buck_bunny;
            //String uri ="http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
            holder.videoView.setVideoPath(uri);
            if (position == initPlayPosition) {
                holder.play();
            }
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        private RecyclerView recyclerView;

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            this.recyclerView = recyclerView;
        }

        @Override
        public void onViewAttachedToWindow(VideoViewHolder holder) {
            super.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(VideoViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            boolean playing = holder.videoView.isPlaying();
            if (!playing) {
                return;
            }
            holder.videoView.pause();

            int position = holder.getAdapterPosition();
            if (holder.itemView.getTop() < 0){
                position++;
            }else {
                position--;
            }

            VideoViewHolder next = (VideoViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            if (next != null) {
                next.play();
            }
        }
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        private VideoView videoView;
        private Button playButton;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view);
            playButton = itemView.findViewById(R.id.play_btn);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (videoView.isPlaying()) {
                        pause();
                    } else {
                        play();

                    }
                }
            });
            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playButton.setVisibility(View.VISIBLE);
                }
            });
        }

        public void play() {
            if (videoView.isPlaying()) {
                return;
            }
            videoView.start();
            playButton.setText("暂停");
            hideButton();

        }

        public void pause() {
            if (!videoView.isPlaying()) {
                return;
            }
            videoView.pause();
            playButton.setText("播放");
            hideButton();
        }

        public void hideButton(){
            CountDownTimer timer =new CountDownTimer(5000,5000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    playButton.setVisibility(View.GONE);
                }
            };
            timer.start();

        }
    }

}
