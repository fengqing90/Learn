package learn.设计模式.状态模式;

import lombok.Data;

/**
 * 机器上下文-持有所有状态，以及当前状态的引用
 */
@Data
public class MachineContext {
    /**
     * 持有所有状态
     */
    private MachineState noCoinState;
    private MachineState hasCoinState;
    private MachineState sellingState;
    private MachineState sellOutState;
    /**
     * 默认售罄状态
     */
    private MachineState currentState = noCoinState;
    /**
     * 记录肥宅水数量
     */
    private int count;

    //所有的行为都委托到当前状态类

    {
        noCoinState = new NoCoinState(this);
        hasCoinState = new HasCoinState(this);
        sellingState = new SellingState(this);
        sellOutState = new SellOutState(this);
        this.currentState = noCoinState;
    }

    public MachineContext(int count) {
        this.count = count;
        this.currentState.handleRequest();
    }

    /**
     * 投入硬币
     */
    public void putCoin() {
        this.currentState = hasCoinState;
        this.currentState.handleRequest();
    }
}
