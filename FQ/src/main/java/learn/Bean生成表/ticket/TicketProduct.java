package learn.Bean生成表.ticket;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;

import lombok.Data;

/**
 * @author fengqing
 * @date 2021/6/3 15:04
 */
@Data
@Table(comment = "ticket_product")
public class TicketProduct {
    @IsKey
    @Column
    private Long productUid;
    @Column
    private String name;
    @Column
    private Integer buyPrice;
    @Column
    private Integer sellPrice;
    @Column
    private Integer customerPrice;
    @Column
    private Integer quantity;
    @Column
    private Integer discount;
    @Column
    private Integer customerDiscount;
    @Column
    private Integer totalAmount;
    @Column
    private Integer totalProfit;
    @Column
    private Integer isCustomerDiscount;
    @Column
    private String productBarcode;
    @Column
    private Integer isWeighing;
    @Column
    private String ticketitemattributes;
    @Column
    private String discountDetails;
    @Column
    private String saleGuiderList;

}
