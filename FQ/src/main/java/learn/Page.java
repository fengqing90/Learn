package learn;

import java.util.List;

/**
 * @describle 分页工具类
 * @author AlfredXiong
 * @time 2013-4-17 下午1:49:26
 */
public class Page {

    // 排序方式
    public enum OrderType {
        asc,
        desc
    }

    /** 查询方式 **/
    public enum QueryType {
        LIKE,
        EQUALS
    }

    public String getDataSymbolsByQueryType() {
        return this.getDataSymbolsByQueryType(this.queryType);
    }

    public String getDataSymbolsByQueryType(QueryType type) {
        String dataSymbols = null;
        switch (type) {
            case LIKE:
                dataSymbols = "like";
                break;
            case EQUALS:
                dataSymbols = "=";
                break;
        }
        return dataSymbols;
    }

    // 每页最大记录数限制
    public static final Integer MAX_PAGE_SIZE = 500;
    // 当前页码
    private Integer pageNumber = 1;
    // 每页记录数
    private Integer pageSize = 20;
    // 总记录数
    private Integer totalCount = 0;
    // 总页数
    private Integer pageCount = 0;
    // // 查找属性名称
    // private String property;
    // // 查找关键字
    // private String keyword;
    // 模糊查询对象
    private Object likeObject;
    // 排序方式
    private OrderType orderType = OrderType.desc;
    // 指定列名进行排序, 排序字段
    private String[] columnName;
    // 数据List
    private List<?> list;

    private QueryType queryType = QueryType.EQUALS;

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize < 1) {
            pageSize = 1;
        } else if (pageSize > Page.MAX_PAGE_SIZE) {
            pageSize = Page.MAX_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageCount() {
        this.pageCount = this.totalCount / this.pageSize;
        if (this.totalCount % this.pageSize > 0) {
            this.pageCount++;
        }
        return this.pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Object getLikeObject() {
        return this.likeObject;
    }

    public void setLikeObject(Object likeObject) {
        this.likeObject = likeObject;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public List<?> getList() {
        return this.list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String[] getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String[] columnName) {
        this.columnName = columnName;
    }

    public QueryType getQueryType() {
        return this.queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    @Override
    public String toString() {
        return "Pager [pageNumber=" + this.pageNumber + ", pageSize="
            + this.pageSize + ", totalCount=" + this.totalCount + ", pageCount="
            + this.pageCount + ", likeObject=" + this.likeObject
            + ", orderType=" + this.orderType + ", columnName="
            + this.columnName + ", list=" + this.list + "]";
    }

}
