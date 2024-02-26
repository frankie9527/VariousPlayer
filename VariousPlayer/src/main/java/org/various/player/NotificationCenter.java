package org.various.player;

import android.util.SparseArray;

import androidx.annotation.UiThread;

import java.util.ArrayList;

/**
 * Created by Frankie on 2020/8/18
 * Email：847145851@qq.com
 * func:
 */
public class NotificationCenter {
    private static int totalEvents = 1;
//    public static final int user_onclick_video_err_retry = totalEvents++;
    public static final int user_onclick_take_pic = totalEvents++;
    public static final int user_onclick_take_pic_data = totalEvents++;
    public static final int recycler_video_view_reset = totalEvents++;

    private final SparseArray<ArrayList<NotificationCenterDelegate>> observers = new SparseArray<>();
    private final SparseArray<ArrayList<NotificationCenterDelegate>> removeAfterBroadcast = new SparseArray<>();
    private final SparseArray<ArrayList<NotificationCenterDelegate>> addAfterBroadcast = new SparseArray<>();
    private final ArrayList<DelayedPost> delayedPosts = new ArrayList<>(10);
    private final ArrayList<DelayedPost> delayedPostsTmp = new ArrayList<>(10);
    private final ArrayList<PostponeNotificationCallback> postponeCallbackList = new ArrayList<>(10);
    private static volatile NotificationCenter globalInstance;
    private final int currentAccount;
    private int broadcasting = 0;

    @UiThread
    public static NotificationCenter getGlobalInstance() {
        NotificationCenter localInstance = globalInstance;
        if (localInstance == null) {
            synchronized (NotificationCenter.class) {
                localInstance = globalInstance;
                if (localInstance == null) {
                    globalInstance = localInstance = new NotificationCenter(-1);
                }
            }
        }
        return localInstance;
    }
    public NotificationCenter(int account) {
        currentAccount = account;
    }
    public void postNotificationName(int id, Object... args){
        if (Thread.currentThread() != PlayerConfig.applicationHandler.getLooper().getThread()) {
            throw new RuntimeException("postNotificationName allowed only from MAIN thread");
        }
        if (!postponeCallbackList.isEmpty()) {
            for (int i = 0; i < postponeCallbackList.size(); i++) {
                if (postponeCallbackList.get(i).needPostpone(id, currentAccount, args)) {
                    delayedPosts.add(new DelayedPost(id, args));
                    return;
                }
            }
        }
        broadcasting++;
        ArrayList<NotificationCenterDelegate> objects = observers.get(id);
        if (objects != null && !objects.isEmpty()) {
            for (int a = 0; a < objects.size(); a++) {
                NotificationCenterDelegate obj = objects.get(a);
                obj.didReceivedNotification(id, currentAccount, args);
            }
        }
        broadcasting--;
        if (broadcasting == 0) {
            if (removeAfterBroadcast.size() != 0) {
                for (int a = 0; a < removeAfterBroadcast.size(); a++) {
                    int key = removeAfterBroadcast.keyAt(a);
                    ArrayList<NotificationCenterDelegate> arrayList = removeAfterBroadcast.get(key);
                    for (int b = 0; b < arrayList.size(); b++) {
                        removeObserver(arrayList.get(b), key);
                    }
                }
                removeAfterBroadcast.clear();
            }
            if (addAfterBroadcast.size() != 0) {
                for (int a = 0; a < addAfterBroadcast.size(); a++) {
                    int key = addAfterBroadcast.keyAt(a);
                    ArrayList<NotificationCenterDelegate> arrayList = addAfterBroadcast.get(key);
                    for (int b = 0; b < arrayList.size(); b++) {
                        addObserver(arrayList.get(b), key);
                    }
                }
                addAfterBroadcast.clear();
            }
        }
    }
    public void addObserver(NotificationCenterDelegate observer, int id){
        if (Thread.currentThread() != PlayerConfig.applicationHandler.getLooper().getThread()) {
            throw new RuntimeException("addObserver allowed only from MAIN thread");
        }
        if (broadcasting != 0) {
            ArrayList<NotificationCenterDelegate> arrayList = addAfterBroadcast.get(id);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                addAfterBroadcast.put(id, arrayList);
            }
            arrayList.add(observer);
            return;
        }
        ArrayList<NotificationCenterDelegate> objects = observers.get(id);
        if (objects == null) {
            observers.put(id, (objects = new ArrayList<>()));
        }
        if (objects.contains(observer)) {
            return;
        }
        objects.add(observer);
    }
    public void removeObserver(NotificationCenterDelegate observer, int id) {
        if (Thread.currentThread() != PlayerConfig.applicationHandler.getLooper().getThread()) {
            throw new RuntimeException("removeObserver allowed only from MAIN thread");
        }
        if (broadcasting != 0) {
            ArrayList<NotificationCenterDelegate> arrayList = removeAfterBroadcast.get(id);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                removeAfterBroadcast.put(id, arrayList);
            }
            arrayList.add(observer);
            return;
        }
        ArrayList<NotificationCenterDelegate> objects = observers.get(id);
        if (objects != null) {
            objects.remove(observer);
        }
    }
    public void addPostponeNotificationsCallback(PostponeNotificationCallback callback) {
        if (Thread.currentThread() != PlayerConfig.applicationHandler.getLooper().getThread()) {
            throw new RuntimeException("PostponeNotificationsCallback allowed only from MAIN thread");
        }
        if (!postponeCallbackList.contains(callback)) {
            postponeCallbackList.add(callback);
        }
    }
    public interface PostponeNotificationCallback {
        boolean needPostpone(int id, int currentAccount, Object[] args);
    }
    public interface NotificationCenterDelegate {
        void didReceivedNotification(int id, int account, Object... args);
    }
    private static class DelayedPost {

        private DelayedPost(int id, Object[] args) {
            this.id = id;
            this.args = args;
        }

        private int id;
        private Object[] args;
    }
}
