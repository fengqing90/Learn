package cn.fengqing.learnmybatis.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/30 11:23
 */
@Data
public class Foo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableLogic //逻辑删除
    private Integer deleted;
    @Version
    private Integer version;
}
