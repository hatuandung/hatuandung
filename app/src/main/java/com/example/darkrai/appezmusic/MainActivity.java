package com.example.darkrai.appezmusic;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    ImageView imageHinh;
    ImageButton btnPrev, btnPlay, btnStop, btnNext;

    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();

        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);

        KhoiTaoMediaPlayer();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > arraySong.size() - 1){
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause1);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position < 0){
                    position = arraySong.size() - 1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause1);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();//Giai phong, dung hoan toan khong phat nua
                btnPlay.setImageResource(R.drawable.play1);
                KhoiTaoMediaPlayer();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    //Neu dang phat -> pause -> doi hinh play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play1);
                }else{
                    //Dang ngung -> phat -> doi hinh pause
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause1);

                }
                SetTimeTotal();
                UpdateTimeSong();
                imageHinh.startAnimation(animation);

            }

        });

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //keo thi cap nhat gia tri lien tuc
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            //vua cham vao thi lay khoanh khac do
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            //khi di chuyen khong co gi xay ra, khi tha ra moi lay gia tri
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }
    private void UpdateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangTg = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinhDangTg.format(mediaPlayer.getCurrentPosition()));
                //update
                skSong.setProgress(mediaPlayer.getCurrentPosition());
                //kiem tra thoi gian ai hat neu ket thuc se chuyen bai
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arraySong.size() - 1){
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause1);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        },100);

    }

    private void SetTimeTotal(){
        SimpleDateFormat dinhDangTg = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(dinhDangTg.format(mediaPlayer.getDuration()));
        //Gan max cua skSong = mediaPlayer.getDuration()
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void KhoiTaoMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }

    private void AddSong(){
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Em dạo này",R.raw.em_dao_nay_ngot));
    }

    private void AnhXa(){
        txtTimeSong   = (TextView) findViewById(R.id.textViewTimeSong);
        txtTimeTotal  = (TextView) findViewById(R.id.textViewTimeTotal);
        txtTitle      = (TextView) findViewById(R.id.textviewTitle);
        skSong        = (SeekBar) findViewById(R.id.seekBarSong);
        btnNext       = (ImageButton) findViewById(R.id.imageButtonNext);
        btnPlay       = (ImageButton) findViewById(R.id.imageButtonPlay);
        btnPrev       = (ImageButton) findViewById(R.id.imageButtonPre);
        btnStop       = (ImageButton) findViewById(R.id.imageButtonPause);
        imageHinh     = (ImageView) findViewById(R.id.imageViewCd);

    }


}
