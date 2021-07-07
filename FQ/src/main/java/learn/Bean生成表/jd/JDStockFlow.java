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
 * @date 2021/7/1 14:36
 */
@Data
@Table(value = "jd_stock_flow")
public class JDStockFlow {
    @IsKey
    @Column
    private Long id;

    /** 订货单Id **/
    @Column
    // @Index
    private Integer uid;
    /** 订货单创建时间 **/
    @Column(type = MySqlTypeConstant.DATETIME)
    private Date createdDateTime;
    /** 进货 = 12, 调货 = 13, 退货 = 14, 调拨退货=16, 出库=17 **/
    @Column
    // @Index
    private Integer stockFlowTypeNumber;
    /** 货单从哪 **/
    @Column
    private String fromUserAppId;
    /** 货单去哪 **/
    @Column
    private String toUserAppId;

    /********************************************/

    /** 货单条目商品uid **/
    @Column
    private Long productUid;
    /** 货单条目商品分类uid **/
    @Column
    private Long categoryUid;
    /** 货单条目商品供应商uid **/
    @Column
    private Long supplierUid;
    /** 货单条目商品名称 **/
    @Column
    private String productName;
    /** 销售价 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal sellPrice;
    /** 按基准单位换算过的货量 **/
    @Column
    private Integer updateStock;
    /** 进货价 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal buyPrice;
    /** 货单条目商品条码 **/
    @Column
    private String barcode;
    /** 货单条目商品单位Uid **/
    @Column
    private Long productUnitUid;
    /** 按商品单位的货量 **/
    @Column
    private Integer unitQuantity;
    /** 按商品单位的进货价 **/
    @Column(type = MySqlTypeConstant.DECIMAL, length = 16, decimalLength = 4)
    private BigDecimal unitBuyPrice;
}
