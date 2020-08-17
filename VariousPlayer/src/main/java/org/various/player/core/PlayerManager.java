package org.various.player.core;



import org.various.player.PlayerConstants;
import org.various.player.PlayerConfig;

/**
 * Created by 江雨寒 on 2020/8/17
 * Email：847145851@qq.com
 * func:
 */
public class PlayerManager {
   private static IPlayer iPlayer;
    public static IPlayer getPlayer(){
        if (iPlayer==null){
            if (PlayerConfig.getPlayerCore()== PlayerConstants.EXO_CORE){
                iPlayer= new VariousExoPlayer();
            }else {
                iPlayer=new VariousIjkPlayer();
            }
        }
        return iPlayer;
    }



}
