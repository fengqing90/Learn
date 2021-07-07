package learn.Bean生成表.jd;

import java.math.BigDecimal;
import java.util.Date;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import lombok.Data;

/**
 * @author fengqing
 * @date 2021/7/1 15:24
 */
@Data
@Table(value = "jd_ticket")
public class JDTicket {
    @IsKey
    @Column
    private Long id;

    @Column
    private Long uid;
    /** 门店appId **/
    @Column
    private String appId;
    /** 单据序列号 **/
    @Column
    // @Index
    private String sn;
    /** 单据类型：SELL销售单据, SELL_RETURN退货单据。不区分大小写 **/
    @Column
    private String ticketType;
    /** 单据产生的时间，格式为yyyy-MM-dd hh:mm:ss **/
    @Column(type = MySqlTypeConstant.DATETIME)
    private Date datetime;
    /** 退货原始单据 **/
    @Column
    private String sellReturnOriginTicketSn;

    /**********************************************/
    /** 单据条目所对应的商品唯一标识 **/
    @Column
    private Long productUid;
    /** 单据条目商品名称 **/
    @Column
    private String name;
    /** 单据条目商品进货价 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal buyPrice;
    /** 单据条目商品销售价 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal sellPrice;
    /** 单据条目商品会员价 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal customerPrice;
    /** 单据条目销售的商品数量，如3.5箱 **/
    @Column
    private Integer quantity;
    /** 单据条目所打的折 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal discount;
    /** 单据条目所打的会员折扣 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal customerDiscount;
    /** 单据条目总价 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal totalAmount;
    /** 单据条目总利润 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal totalProfit;
    /** 数据为1时表求享受了会员折扣 **/
    @Column
    private Integer isCustomerDiscount;
    /** 商品的条形码 **/
    @Column
    private String productBarcode;
}
