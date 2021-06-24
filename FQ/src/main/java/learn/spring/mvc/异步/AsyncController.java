package learn.spring.mvc.异步;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 17:02
 */

import java.util.UUID;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
public class AsyncController {

    @ResponseBody
    @RequestMapping("/createOrder")
    public DeferredResult<Object> createOrder() {
        /*
         * 在创建DeferredResult对象时，可以像下面这样传入一些参数哟！
         * 第一个参数（timeout）：
         * 超时时间。限定（请求？）必须在该时间内执行完，如果超出时间限制，那么就会返回一段错误的提示信息（timeoutResult）
         * 第二个参数（timeoutResult）：超出时间限制之后，返回的错误提示信息
         */
        DeferredResult<Object> deferredResult = new DeferredResult<>(
            (long) 3000, "create fail...");
        DeferredResultQueue.save(deferredResult);
        return deferredResult;
    }

    @ResponseBody
    @RequestMapping("/create")
    public String create() {
        // 在这模拟创建订单
        String order = UUID.randomUUID().toString();
        /*
         * 如果我们想在上一个请求（即createOrder）中使用订单，那么该怎么办呢？从临时保存DeferredResult对象的地方获取
         * 到刚才保存的DeferredResult对象，然后调用其setResult方法设置结果，例如设置订单的订单号
         */
        DeferredResult<Object> deferredResult = DeferredResultQueue.get();
        deferredResult.setResult(order);
        // 这儿给客户端直接响应"success===>订单号"这样的字符串，不要再跳转页面了
        return "success===>" + order;
    }

    @ResponseBody
    @RequestMapping("/async01")
    public Callable<String> async01() {
        System.out.println("主线程开始..." + Thread.currentThread() + "==>"
            + System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println("副线程开始..." + Thread.currentThread() + "==>"
                    + System.currentTimeMillis());
                Thread.sleep(2000); // 我们来睡上2秒 
                System.out.println("副线程结束..." + Thread.currentThread() + "==>"
                    + System.currentTimeMillis());
                // 响应给客户端一串字符串，即"Callable<String> async01()"
                return "Callable<String> async01()";
            }

        };
        System.out.println("主线程结束..." + Thread.currentThread() + "==>"
            + System.currentTimeMillis());
        return callable;
    }

}