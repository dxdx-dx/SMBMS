package cn.bdqn.smbms.util;

/**
 * 分页工具类
 */
public class PageSupport {

    // 当前页
    private int currentPageNo = 1;
    // 页大小
    private int pageSize = 1;
    // 总条数
    private int totalCount = 1;
    // 总页数
    private int totalPageNo;

    // 计算总页数
    // public void totalPageNo() {
    // this.setTotalPageNo((this.getTotalCount() % this.getPageSize() == 0)
    // ? (this.getTotalCount() / this.getPageSize()) : (this.getTotalCount() /
    // this.getPageSize() + 1));
    // // if (this.getTotalCount() % this.getPageSize() == 0) {
    // // this.setTotalPageNo(this.getTotalCount() / this.getPageSize());
    // // } else {
    // // this.setTotalPageNo(this.getTotalCount() / this.getPageSize() + 1);
    // // }
    // }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.setTotalPageNo((this.getTotalCount() % this.getPageSize() == 0)
                ? (this.getTotalCount() / this.getPageSize()) : (this.getTotalCount() / this.getPageSize() + 1));
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPageNo() {
        return totalPageNo;
    }

    public void setTotalPageNo(int totalPageNo) {
        this.totalPageNo = totalPageNo;
    }

}
