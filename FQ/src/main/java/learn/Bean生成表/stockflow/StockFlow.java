package learn.Bean生成表.stockflow;

/**  **/

import java.util.Date;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;

import lombok.Data;

/**
 * 货流
 *
 * @author fengqing
 * @date 2021/6/25 16:24
 */
@Data
@Table(value = "stock_flow")
public class StockFlow {

    @IsKey
    @Column
    private Long id;

    /** 订货单Id **/
    @Column
    private Integer uid;
    /** 货单指向账号的appId **/
    @Column
    private String toUserAppId;
    /** 货单指向账号的账号 **/
    @Column
    private String toUserAccount;
    /**  **/
    @Column
    private String toUserCompany;
    /** 生成货单账号的appId **/
    @Column
    private String operatorUserAppId;
    /** 生成货单账号的账号 **/
    @Column
    private String operatorUserAccount;
    /**  **/
    @Column
    private String operatorUserCompany;
    // /** 货单条目 **/
    // @Column private List<StockFlowProduct> items;

    /** 是否需要确认货流，1：需要确认，0：不需要确认 **/
    @Column
    private Integer confirmationRequired;
    /** 订货单创建时间 **/
    @Column
    private Date createdDateTime;
    /** 进货 = 12, 出货 = 13, 退货 = 14, 调拨退货=16 **/
    @Column
    private Integer stockflowTypeNumber;
    /** 0- 未确认，1- 已确认， 2- 拒绝进货/出货，3- 被拒绝收货 **/
    @Column
    private Integer confirmed;
    /** 确认进货时，货流差异时，是否需要确认。出货单和退货单无效1：需要确认，0：不需要确认 **/
    @Column
    private Integer varianceConfirmation;
    /** 确认货流时间，格式为yyyy-MM-dd hh:mm:ss **/
    @Column
    private Date confirmedTime;
    /** 备注信息 **/
    @Column
    private String remarks;/**  **/

}
