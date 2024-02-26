package org.various.player.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Frankie on 2020/9/15
 * Email：847145851@qq.com
 * func:
 */
public class CanDragSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    public void setCanDrag(boolean canDrag) {
         this.canDrag = canDrag;
    }

    boolean canDrag=false;
    public CanDragSeekBar(Context context) {
        super(context);
    }

    public CanDragSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanDragSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canDrag){
            return super.onTouchEvent(event);
        }
        return true;
    }
}
