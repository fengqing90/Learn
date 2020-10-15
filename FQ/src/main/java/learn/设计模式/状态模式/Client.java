package learn.设计模式.状态模式;

/**
 * 客户端模拟投入硬币调用自动售货机提供的接口
 */
public class Client {
    public static void main(String[] args) {
        MachineContext machineContext = new MachineContext(2);

        machineContext.getCurrentState();
        // 模拟投入硬币
        machineContext.putCoin();
        System.out.println("***************");

        machineContext.putCoin();
        System.out.println("***************");

        //这里会提示售罄，退回硬币
        machineContext.putCoin();
        System.out.println("***************");

        //模拟管理员添加汽水
        machineContext.setCount(1);
        machineContext.putCoin();
        System.out.println("***************");

    }
}