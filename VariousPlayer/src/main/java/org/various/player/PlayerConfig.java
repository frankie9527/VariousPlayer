package org.various.player;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.util.SparseIntArray;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

/**
 * Created by Frankie on 2020/8/18
 * Email：847145851@qq.com
 * func: 播放器配置相关
 */
public class PlayerConfig {
    public static final String PIC_PATH = Environment.getExternalStorageDirectory().getPath() + "/Video/pic/";
    public static final String GIF_PAth = Environment.getExternalStorageDirectory().getPath() + "/Video/gif";
    private static @PlayerConstants.PlayerCore
    int currentCore = PlayerConstants.EXO_CORE;
    @SuppressLint({"StaticFieldLeak"})
    private static Application mContext;
    public static volatile Handler applicationHandler;
    protected static String userAgent;
    private static Cache downloadCache;

    private static final SparseIntArray exoStatus = new SparseIntArray();

    public static void init(@NonNull Application app) {
        mContext = app;
        applicationHandler = new Handler(mContext.getMainLooper());
        userAgent = Util.getUserAgent(mContext, "org.Various.player");
        exoStatus.put(Player.STATE_IDLE, PlayerConstants.IDLE);
        exoStatus.put(Player.STATE_READY, PlayerConstants.READY);
        exoStatus.put(Player.STATE_BUFFERING, PlayerConstants.BUFFERING);
        exoStatus.put(Player.STATE_ENDED, PlayerConstants.END);
    }

    public static Application getContext() {
        return mContext;
    }

    public static void setPlayerCore(@PlayerConstants.PlayerCore int core) {
        currentCore = core;
    }

    public static @PlayerConstants.PlayerCore int getPlayerCore() {
        return currentCore;
    }


    public static DataSource.Factory buildDataSourceFactory() {
        DefaultDataSource.Factory upstreamFactory =
                new DefaultDataSource.Factory(mContext, new DefaultHttpDataSource.Factory());
        return buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache());
    }

    protected static CacheDataSource.Factory buildReadOnlyCacheDataSource(
            DataSource.Factory upstreamFactory, Cache cache) {
        CacheDataSource.Factory factory = new CacheDataSource.Factory();
        factory.setCache(cache);
        factory.setUpstreamDataSourceFactory(upstreamFactory);
        factory.setCacheReadDataSourceFactory(new FileDataSource.Factory());
        factory.setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
        return factory;
    }

    protected static synchronized Cache getDownloadCache() {
        if (downloadCache == null) {
            File downloadContentDirectory = new File(mContext.getExternalFilesDir(null), "VariousCache");
            downloadCache =
                    new SimpleCache(downloadContentDirectory, new LeastRecentlyUsedCacheEvictor(50 * 1024 * 1024), new StandaloneDatabaseProvider(mContext));
        }
        return downloadCache;
    }


    public static int changStatus2Normal(@PlayerConstants.PlayerCore int core, int status) {
        if (core == PlayerConstants.EXO_CORE) {
            return exoStatus.get(status);
        }
        return -1;
    }
}

