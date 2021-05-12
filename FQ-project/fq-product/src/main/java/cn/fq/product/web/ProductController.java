package cn.fq.product.web;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fq.product.bean.ProductVO;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/29 14:35
 */
@RestController
public class ProductController {

    @RequestMapping("/product/list")
    public List<ProductVO> productList() {
        return Stream.iterate(1, i -> ++i).limit(3)
            .map(i -> ProductVO.builder().id(i).name(i + "")
                .price(new Random().nextDouble()).build())
            .collect(Collectors.toList());

    }
}
