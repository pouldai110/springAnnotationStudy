package spring.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName Page
 * @Author: pouldai
 * @Date: 2020/4/13 16:19
 * @Description:分页信息参数实体
 * @Version V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Page implements Serializable {
    private int pageNo;
    private int pageSize;
}
