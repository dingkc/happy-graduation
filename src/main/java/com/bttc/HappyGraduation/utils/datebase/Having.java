package com.bttc.HappyGraduation.utils.datebase;

public class Having<T> extends Filter {

    /**
     * 函数标记
     */
    private String methidFunction;

    private Filter filter;

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getMethidFunction() {
        return methidFunction;
    }

    public void setMethidFunction(String methidFunction) {
        this.methidFunction = methidFunction;
    }


    /**
     * 构造方法
     */
    public Having() {
    }

    /**
     * 构造方法
     *
     * @param filter 属性
     */
    public Having(Filter filter, String methidFunction) {
        this.filter = filter;
        this.methidFunction = methidFunction;
    }

    public static Having count(Filter filter) {
        return new Having(filter, "count");
    }

    public static Having avg(String property, Object value) {
        return new Having(new Filter(property, Filter.Operator.eq, value), "avg");
    }
}
