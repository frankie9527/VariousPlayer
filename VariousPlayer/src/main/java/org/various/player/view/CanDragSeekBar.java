package org.various.player.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 江雨寒 on 2020/9/15
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
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canDrag){
            return super.onTouchEvent(event);
        }
        return true;
    }
}
