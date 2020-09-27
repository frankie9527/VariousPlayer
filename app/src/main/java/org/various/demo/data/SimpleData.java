package org.various.demo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江雨寒 on 2020/9/27
 * Email：847145851@qq.com
 * func: https://blog.csdn.net/nnmmbb/article/details/91360305
 * https://blog.csdn.net/weixin_41010198/article/details/88055078?utm_medium=distribute.pc_relevant.none-task-blog-title-4&spm=1001.2101.3001.4242
 */
public class SimpleData {
    public static final String url = "https://mov.bn.netease.com/open-movie/nos/mp4/2017/05/31/SCKR8V6E9_hd.mp4";
    public static final String url1 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    public static final String url2 = "http://gslb.miaopai.com/stream/oxX3t3Vm5XPHKUeTS-zbXA__.mp4";
    public static final String url3 = "https://media.w3.org/2010/05/sintel/trailer.mp4";
    public static final String url4 = "http://vjs.zencdn.net/v/oceans.mp4";
    public static final String url5 = "http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4";
    public static final String url6 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    public static final String url7 = "http://vjs.zencdn.net/v/oceans.mp4";

    public static final  String smoothStreamUrl="https://playready.directtaps.net/smoothstreaming/SSWSS720H264/SuperSpeedway_720.ism/Manifest";
    public static List<Bean> dataList=new ArrayList<>();
    static  {
        dataList.add(new Bean("demo",url));
        dataList.add(new Bean("demo1",url1));
        dataList.add(new Bean("demo2",url2));
        dataList.add(new Bean("demo3",url3));
        dataList.add(new Bean("demo4",url4));
        dataList.add(new Bean("demo5",url5));
        dataList.add(new Bean("demo6",url6));
        dataList.add(new Bean("demo7",url7));

    }
}
