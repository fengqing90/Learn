package learn.servlet3;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 15:48
 */
interface HelloService {
}

interface HelloServiceExt extends HelloService {

}

abstract class AbstractHelloService implements HelloService {

}

class HelloServiceImpl implements HelloService {

}