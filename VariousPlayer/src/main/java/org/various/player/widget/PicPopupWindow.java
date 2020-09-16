package org.various.player.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import org.various.player.PlayerConfig;
import org.various.player.R;
import org.various.player.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * Created by 江雨寒 on 2020/9/14
 * Email：847145851@qq.com
 * func:
 */
public class PicPopupWindow implements View.OnClickListener {
    protected  Context context;
    protected PopupWindow mPicPopup;

    protected View localView;
    protected  int offsetX,  offsetY;
    protected ImageView img_pic;
    protected  String picPath;
    protected Bitmap currentBitmap;
    public PicPopupWindow(Context context) {
        this.context = context;
    }
    public void showPic(View view, Bitmap bitmap){
        if (mPicPopup == null) {
            localView = LayoutInflater.from(context).inflate(R.layout.various_dialog_pic, null);
            mPicPopup = getPopupWindow(localView);
            img_pic=localView.findViewById(R.id.img_pic);
            localView.findViewById(R.id.img_close).setOnClickListener(this);
            localView.findViewById(R.id.img_save).setOnClickListener(this);
        }
        localView.measure(0,0);
        offsetX=(view.getWidth()-localView.getMeasuredWidth())/2;
        offsetY=-(view.getHeight()+localView.getMeasuredHeight())/2;
        if (bitmap==null){
            ToastUtils.show("屏幕截取失败");
            return;
        }
        currentBitmap=bitmap;
        if (!mPicPopup.isShowing())
            mPicPopup.showAsDropDown(view, offsetX, offsetY);
        img_pic.setImageBitmap(bitmap);

    }
    private PopupWindow getPopupWindow(View popupView) {
        PopupWindow mPopupWindow = new PopupWindow(popupView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        mPopupWindow.setAnimationStyle(R.style.popup_brightness_volume_anim);
        return mPopupWindow;
    }
    public void dismissPop(){
        if (mPicPopup!=null&&mPicPopup.isShowing()){
            mPicPopup.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.img_close){
            dismissPop();
            return;
        }
        if (v.getId()== R.id.img_save){
            saveImg2Local();
            dismissPop();
        }
    }
    public void saveImg2Local(){
        try {
            picPath = saveBitmap(currentBitmap);
            File dir = new File(picPath);
            if (!dir.exists()) {
                ToastUtils.show( "保存失败，图片不存在！");
                return;
            }
            //发广播告诉相册有图片需要更新，这样可以在图册下看到保存的图片了
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(dir);
            intent.setData(uri);
            PlayerConfig.getContext().sendBroadcast(intent);
            ToastUtils.show( "图片保存成功");
        } catch (Exception e) {
            ToastUtils.show( "保存失败");
        }
    }
    private String saveBitmap(Bitmap bitmap) throws FileNotFoundException {
        File dir = new File(PlayerConfig.PIC_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = null;
        if (bitmap != null) {
            file = new File(PlayerConfig.PIC_PATH, "Img-" + System.currentTimeMillis() + ".jpg");
            OutputStream outputStream;
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            bitmap.recycle();
        }
        //发广播告诉相册有图片需要更新，这样可以在图册下看到保存的图片了

        return file.getAbsolutePath();
    }
}
