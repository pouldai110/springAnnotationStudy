package spring.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    /**
     * 开始页数
     */
    private int pageNo = 1;
    /**
     * 本页数据在数据库中的起始位置
     */
    private Long start;

    /**
     * 数据库中总记录条数
     */
    private Long totalSize;
    /**
     * 本页大小
     */
    private int pageSize = 10;

    /**
     * 具体数据列表
     */
    private List<T> data;

    public PageResult(int pageNo, int startIndex, int size, int pageSize, List<T> objectArray) {
        this.pageNo = pageNo;
        this.start = new Long(startIndex);
        this.totalSize = new Long(size);
        this.pageSize = pageSize;
        this.data = objectArray;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "pageNo=" + pageNo +
                ", start=" + start +
                ", totalSize=" + totalSize +
                ", pageSize=" + pageSize +
                ", data=" + data +
                '}';
    }
}
