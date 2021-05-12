package cn.fq.product.bean;

import lombok.Builder;
import lombok.Data;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/29 14:36
 */
@Data
@Builder
public class ProductVO {

    private Integer id;
    private String name;
    private Double price;

}
