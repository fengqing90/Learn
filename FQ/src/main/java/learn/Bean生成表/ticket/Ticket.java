package learn.Bean生成表.ticket;

import java.util.Date;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;

import lombok.Data;

/**
 * @author fengqing
 * @date 2021/6/3 15:05
 */
@Data
@Table(comment = "ticket")
public class Ticket {
    @IsKey
    private Long uid;
    @Column
    private Integer customerUid;
    @Column
    private String sn;
    @Column
    private Date datetime;
    @Column
    private Integer totalAmount;
    @Column
    private Integer totalProfit;
    @Column
    private Integer discount;
    @Column
    private Integer rounding;
    @Column
    private String ticketType;
    @Column
    private Integer invalid;
    @Column
    private String remark;
    @Column
    private Long ticketProductId;
    @Column
    private Integer taxFee;
    @Column
    private Integer serviceFee;

    /** 支付code **/
    @Column
    private String paymentCode;
    /** 支付金额 **/
    @Column
    private Integer paymentAmount;

    @Column
    private Long cashierUid;
    @Column
    private String cashierJobNumber;
    @Column
    private String cashierName;

}
