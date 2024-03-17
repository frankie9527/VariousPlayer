package org.various.player.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.various.player.NotificationCenter;
import org.various.player.core.IPlayer;
import org.various.player.core.PlayerManager;
import org.various.player.utils.LogUtils;


/**
 * Created by Frankie on 2020/9/15
 * Email：847145851@qq.com
 * func:
 */
public class VariousTextureView extends TextureView implements NotificationCenter.NotificationCenterDelegate {
    public void setPlayer(IPlayer newPlayer) {
        if (newPlayer != null && this.player != newPlayer) {
            player = newPlayer;
        }
    }

    protected IPlayer player;
    protected SurfaceTextureListener listener;

    public VariousTextureView(@NonNull Context context) {
        super(context);
        init();
    }

    public VariousTextureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VariousTextureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        player = PlayerManager.getInstance().getPlayer();
        listener = new VariousSurfaceTextureListener();
        setSurfaceTextureListener(listener);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.user_onclick_take_pic);
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        if (id == NotificationCenter.user_onclick_take_pic) {
            NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.user_onclick_take_pic_data, getBitmap());
        }
    }


    protected class VariousSurfaceTextureListener implements SurfaceTextureListener {

        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
            LogUtils.d("VariousTextureView", "onSurfaceTextureAvailable");
            player.setVideoSurface(new Surface(surface));
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
            LogUtils.d("VariousTextureView", "onSurfaceTextureDestroyed");
            player.clearVideoSurface();
            NotificationCenter.getGlobalInstance().removeObserver(VariousTextureView.this, NotificationCenter.user_onclick_take_pic);
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

        }
    }


}
