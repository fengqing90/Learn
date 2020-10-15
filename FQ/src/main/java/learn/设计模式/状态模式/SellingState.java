package learn.设计模式.状态模式;

/**
 * 售出货物状态
 */
public class SellingState implements MachineState {

    private MachineContext machineContext;

    public SellingState(MachineContext machineContext) {
        this.machineContext = machineContext;
    }

    @Override
    public void handleRequest() {
        int count = machineContext.getCount();

        --count;
        machineContext.setCount(count);
        if (count >= 0) {
            System.out.println("正在售出汽水，请取走。");
            //恢复到没有硬币状态
            machineContext.setCurrentState(machineContext.getNoCoinState());
        } else {
            //进入售罄状态
            machineContext.setCurrentState(machineContext.getSellOutState());
            machineContext.getCurrentState().handleRequest();
        }

    }
}
