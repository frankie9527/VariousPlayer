# ui 点击刷新流程
## 点击播放图标暂停
- 图标在BaseCenterView 定义，在实现类初始化
- 在BaseControlView 类进行点击事件的逻辑处理

```java
  if (bottomView != null && view == centerView.getCenterPlayView()) {
            LogUtils.d(TAG,"onclick CenterPlayView");
            if (VariousPlayerManager.isPlaying()) {
                PlayerManager.getInstance().getPlayer().pause();
                return;
            }
        }
``` 
- exoplayer 执行暂停（VariousExoPlayer）
```java
    public void pause() {
        LogUtils.e(TAG, "pause");
        if (player != null && player.isPlaying()) {
            player.setPlayWhenReady(false);
        }
    }
``` 
## 播放器接口回掉，监听播放状态（VariousExoPlayer）
```java
    @Override
    public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
        Player.Listener.super.onPlayWhenReadyChanged(playWhenReady, reason);
        LogUtils.e(TAG, "onPlayWhenReadyChanged=" + playWhenReady + " reason=" + reason);
        notifyStatus(PlayerConstants.EXO_CORE, reason);

    }
``` 
reason =1 说明播放器现在是空闲状态

## 刷新ui
- 因为BaseVideoView 实现了PlayerStatusListener 所以可以监听播放器状态
- 代码如下
```java
  @Override
  public void statusChange(int status) {
        if (status == PlayerConstants.READY || status == PlayerConstants.IDLE) {
            control.stateReady();
        } else if (status == PlayerConstants.BUFFERING) {
            control.stateBuffering();
        } else if (status == PlayerConstants.END) {
            control.showComplete();
        } else if (status == PlayerConstants.ERROR) {
            control.showError();
        }
    }
```     
