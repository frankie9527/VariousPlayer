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
    public static final String url1 = "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear2/prog_index.m3u8";
    public static final String url2 = "http://flv3.bn.netease.com/045abf1a2fbe1f58474b3eef0fa4063a4a9e25747fe375b716c502cd9fc68365d83b118ccb30ef4e28c86a8c54ce5bae454f115fb36cd9a13bfa7736b6c730454c12ffc6928d5ababdbb7d4e04d57fcf6d89361af6eb851762759d32a8021610cfd89f325490b0569422e4f65cd17365f72f425370f6a296.mp4";
    public static final String url3 = "https://media.w3.org/2010/05/sintel/trailer.mp4";
    public static final String url4 = "http://vjs.zencdn.net/v/oceans.mp4";
    public static final String url5 = "http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4";
    public static final String url6 = "https://www.w3schools.com/html/movie.mp4";

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

    }
}
