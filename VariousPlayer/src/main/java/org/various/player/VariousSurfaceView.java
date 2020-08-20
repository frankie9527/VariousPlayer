package org.various.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.core.PlayerManager;
import org.various.player.core.VariousExoPlayer;
import org.various.player.core.IPlayer;

public class VariousSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    protected IPlayer player;

    public VariousSurfaceView(Context context) {
        super(context);
        init();
    }

    public VariousSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VariousSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        player = PlayerManager.getPlayer();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        player.setVideoSurface(surfaceHolder.getSurface());
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        player.clearVideoSurface();
    }


}
