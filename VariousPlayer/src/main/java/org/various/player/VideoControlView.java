package org.various.player;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class VideoControlView extends RelativeLayout implements IVideoControl {


    public VideoControlView(Context context) {
        super(context);
    }

    public VideoControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideloading() {

    }

    @Override
    public void showTopAndBottom() {

    }

    @Override
    public void hideTopAndBootom() {

    }
}
