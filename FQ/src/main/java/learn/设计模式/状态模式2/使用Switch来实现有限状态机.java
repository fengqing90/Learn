package learn.设计模式.状态模式2;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Objects;

import org.junit.Test;

import lombok.Data;

/**
 * <pre>
 * 5个Test Case:
 *
 * T01
 * Given：一个Locked的进站闸口
 * When: 投入硬币
 * Then：打开闸口
 * 
 * T02
 * Given：一个Locked的进站闸口
 * When: 通过闸口
 * Then：警告提示
 * 
 * T03
 * Given：一个Unocked的进站闸口
 * When: 通过闸口
 * Then：闸口关闭
 * 
 * T04
 * Given：一个Unlocked的进站闸口
 * When: 投入硬币
 * Then：退还硬币
 * 
 * T05
 * Given：一个闸机口
 * When: 非法操作
 * Then：操作失败
 * </pre>
 * 
 * 01.01 使用Switch来实现有限状态机
 * https://zhuanlan.zhihu.com/p/97442825
 * if(), swich语句都是switch语句，但是Switch是一种Code Bad
 * Smell，因为它本质上一种重复。当代码中有多处相同的switch时，会让系统变得晦涩难懂，脆弱，不易修改。
 * 上面的代码虽然出现了多层嵌套但是还算是结构简单，不过想通过并不能很清楚闸机口的逻辑还是化点时间。如果闸机口的状态等多一些，那就阅读、理解起来也就更加困难。
 * 
 * @author fengqing
 * @date 2021/7/21 18:24
 */
public class 使用Switch来实现有限状态机 {

    @Test
    public void should_be_unlocked_when_insert_coin_given_a_entrance_machine_with_locked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.LOCKED);

        String result = entranceMachine.execute(Action.INSERT_COIN);

        then(result).isEqualTo("opened");
        then(entranceMachine.getState())
            .isEqualTo(EntranceMachineState.UNLOCKED);
    }

    @Test
    public void should_be_locked_and_alarm_when_pass_given_a_entrance_machine_with_locked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.LOCKED);

        String result = entranceMachine.execute(Action.PASS);

        then(result).isEqualTo("alarm");
        then(entranceMachine.getState()).isEqualTo(EntranceMachineState.LOCKED);
    }

    @Test
    public void should_fail_when_execute_invalid_action_given_a_entrance_with_locked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.LOCKED);

        assertThatThrownBy(() -> entranceMachine.execute(null))
            .isInstanceOf(InvalidActionException.class);
    }

    @Test
    public void should_locked_when_pass_given_a_entrance_machine_with_unlocked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.UNLOCKED);

        String result = entranceMachine.execute(Action.PASS);

        then(result).isEqualTo("closed");
        then(entranceMachine.getState()).isEqualTo(EntranceMachineState.LOCKED);
    }

    @Test
    public void should_refund_and_unlocked_when_insert_coin_given_a_entrance_machine_with_unlocked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.UNLOCKED);

        String result = entranceMachine.execute(Action.INSERT_COIN);

        then(result).isEqualTo("refund");
        then(entranceMachine.getState())
            .isEqualTo(EntranceMachineState.UNLOCKED);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    @Data
    class EntranceMachine {

        private EntranceMachineState state;

        public EntranceMachine(EntranceMachineState state) {
            this.state = state;
        }

        public String execute(Action action) {
            if (Objects.isNull(action)) {
                throw new InvalidActionException();
            }

            if (EntranceMachineState.LOCKED.equals(this.state)) {
                switch (action) {
                    case INSERT_COIN:
                        this.setState(EntranceMachineState.UNLOCKED);
                        return this.open();
                    case PASS:
                        return this.alarm();
                }
            }

            if (EntranceMachineState.UNLOCKED.equals(this.state)) {
                switch (action) {
                    case PASS:
                        this.setState(EntranceMachineState.LOCKED);
                        return this.close();
                    case INSERT_COIN:
                        return this.refund();
                }
            }
            return null;
        }

        private String refund() {
            return "refund";
        }

        private String close() {
            return "closed";
        }

        private String alarm() {
            return "alarm";
        }

        private String open() {
            return "opened";
        }
    }

    enum Action {
        INSERT_COIN,
        PASS
    }

    enum EntranceMachineState {
        UNLOCKED,
        LOCKED
    }

    class InvalidActionException extends RuntimeException {
    }
}
