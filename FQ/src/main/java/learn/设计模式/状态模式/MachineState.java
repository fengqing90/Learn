package learn.设计模式.状态模式;

/**
 * 售货机状态接口
 */
public interface MachineState {
    /**
     * 委托到不同具体实现类实现对应的行为
     */
    void handleRequest();
}