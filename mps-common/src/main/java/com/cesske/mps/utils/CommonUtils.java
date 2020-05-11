package com.cesske.mps.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公用工具类
 */
public final class CommonUtils {
    private CommonUtils() {
    }

    /**
     *
     * 生成traceId，可以用来微服务之间互相调用使用
     * @param service   主动调用的微服务
     * @param activity  调用的方法/接口
     * @return
     */
    public static String genUUid(String service,String activity){
        String time  = String.valueOf(System.currentTimeMillis());
        return service+"-"+activity+"-"+time;
    }

    /**
     * 分割数组方法
     * @param source
     * @param splitSize 分页大小
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign2(List<T> source, int splitSize){
        List<List<T>> result=new ArrayList<List<T>>();
        if(source.size() <= splitSize){
            result.add(source);
        }else{
            int subSize = splitSize;
            int subCount = source.size();
            int subPageTotal = (subCount / subSize) + ((subCount % subSize > 0) ? 1 : 0);
            // 根据页码取数据
            for (int i = 0; i < subPageTotal; i++) {
                // 分页计算
                int fromIndex = i * subSize;
                int toIndex = ((i == subPageTotal-1) ? subCount : ((i + 1) * subSize));
                List<T> subResult = source.subList(fromIndex, toIndex);
                result.add(subResult);
            }
        }
        return result;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }


    /**
     * 生成日志字符串
     * @param log  日志，最好是key=value 这种格式，如果是class，直接用.toString()方法即可
     * @param action 动作，标识当前是什么操作，是一串字符，不要夹杂特殊字符，可以用_下划线分割
     * @param param 额外参数，如果需要记录额外的数据，用该字段
     * @return
     */
    public static String genLogString(String log, String action, Map<String,Object> param){
        //分隔符
        String splitSign = " ";
        String logStr = action+splitSign;
        //去回车等特殊字符
        log = log.replaceAll("\r\n","");
        //额外参数
        if(param != null){
//            for (Map.Entry<String,Object> entry : param.entrySet()) {
//                logStr += entry.getKey()+splitSign+ entry.getValue().toString();
//            }
            logStr += param.toString();
        }
        logStr += splitSign+log;
        return logStr;
    }

    public static void main(String[] args) {

        List<Integer> integers=new ArrayList<>();
        for(int i=0;i<10020;i++){
            integers.add(i);
        }
        List<List<Integer>> lists=averageAssign2(integers, 1000);
        System.out.println(lists);
//        System.out.println(genUUid("ms_order","save"));
    }

}
