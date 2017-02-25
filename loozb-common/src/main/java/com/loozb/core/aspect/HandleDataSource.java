package com.loozb.core.aspect;

/**
 * @Author： 龙召碧
 * @Date: Created in 2017-2-25 19:34
 */
public class HandleDataSource {
    // 数据源名称线程池
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    public static void putDataSource(String datasource) {
        holder.set(datasource);
    }

    public static String getDataSource() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
