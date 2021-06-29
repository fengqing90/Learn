package learn.Bean生成表.stockflow;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;

import lombok.Data;

/**
 * 货流产品明细
 *
 * @author fengqing
 * @date 2021/6/25 16:24
 */
@Data
@Table(value = "stock_flow_product")
public class StockFlowProduct {
    @IsKey
    private Long id;

    /** 货流id **/
    @Column
    private Long stockFlowId;

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
    @Column
    private Integer sellPrice;
    /** 按基准单位换算过的货量 **/
    @Column
    private Integer updateStock;
    /** 进货价 **/
    @Column
    private Integer buyPrice;
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
    @Column
    private Integer unitBuyPrice;
    /** 单品备注信息 **/
    @Column
    private String remarks;
}
