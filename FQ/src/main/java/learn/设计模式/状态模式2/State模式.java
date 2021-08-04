package learn.设计模式.状态模式2;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Objects;

import org.junit.Test;

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
 * State模式和Proxy模式类似，但是在State模式中EntranceMachineState持有EntranceMachine实例的引用。
 * 我们发现EntranceMachine的execute()方法的逻辑变的简单，但是代码复杂度升高了。因为每个state实例都提供了两个动作实现insertCoin()和pass()。这个地方本人认为并不够表意，因为作出的动作被添加到两个状态上，虽然能够实现业务业务，但是并不利于理解清楚业务意思。
 * State模式，虽然能够将逻辑进行拆分，但是那些状态的顺序，以及有几种状态，都不是很直观的观察到。
 * 不过在实际业务中，State模式也是一种很好的实现方式，毕竟他避免了switch的堆积问题。
 *
 * @author fengqing
 * @date 2021/7/21 18:33
 */
public class State模式 {

    @Test
    public void should_be_unlocked_when_insert_coin_given_a_entrance_machine_with_locked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            new LockedEntranceMachineState());

        String result = entranceMachine.execute(Action.INSERT_COIN);

        then(result).isEqualTo("opened");
        then(entranceMachine.isUnlocked()).isTrue();
    }

    @Test
    public void should_be_locked_and_alarm_when_pass_given_a_entrance_machine_with_locked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            new LockedEntranceMachineState());

        String result = entranceMachine.execute(Action.PASS);

        then(result).isEqualTo("alarm");
        then(entranceMachine.isLocked()).isTrue();
    }

    @Test
    public void should_fail_when_execute_invalid_action_given_a_entrance_with_locked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            new LockedEntranceMachineState());

        assertThatThrownBy(() -> entranceMachine.execute(null))
            .isInstanceOf(InvalidActionException.class);
    }

    @Test
    public void should_locked_when_pass_given_a_entrance_machine_with_unlocked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            new UnlockedEntranceMachineState());

        String result = entranceMachine.execute(Action.PASS);

        then(result).isEqualTo("closed");
        then(entranceMachine.isLocked()).isTrue();
    }

    @Test
    public void should_refund_and_unlocked_when_insert_coin_given_a_entrance_machine_with_unlocked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            new UnlockedEntranceMachineState());

        String result = entranceMachine.execute(Action.INSERT_COIN);

        then(result).isEqualTo("refund");
        then(entranceMachine.isUnlocked()).isTrue();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    interface EntranceMachineState {

        String insertCoin(EntranceMachine entranceMachine);

        String pass(EntranceMachine entranceMachine);
    }

    class LockedEntranceMachineState implements State模式.EntranceMachineState {

        @Override
        public String insertCoin(EntranceMachine entranceMachine) {
            return entranceMachine.open();
        }

        @Override
        public String pass(EntranceMachine entranceMachine) {
            return entranceMachine.alarm();
        }
    }

    class UnlockedEntranceMachineState implements State模式.EntranceMachineState {

        @Override
        public String insertCoin(EntranceMachine entranceMachine) {
            return entranceMachine.refund();
        }

        @Override
        public String pass(EntranceMachine entranceMachine) {
            return entranceMachine.close();
        }
    }

    enum Action {
        PASS,
        INSERT_COIN
    }

    class EntranceMachine {

        private State模式.EntranceMachineState locked = new LockedEntranceMachineState();

        private State模式.EntranceMachineState unlocked = new UnlockedEntranceMachineState();

        private State模式.EntranceMachineState state;

        public EntranceMachine(State模式.EntranceMachineState state) {
            this.state = state;
        }

        public String execute(Action action) {
            if (Objects.isNull(action)) {
                throw new InvalidActionException();
            }

            if (Action.PASS.equals(action)) {
                return this.state.pass(this);
            }

            return this.state.insertCoin(this);
        }

        public boolean isUnlocked() {
            return this.state == this.unlocked;
        }

        public boolean isLocked() {
            return this.state == this.locked;
        }

        public String open() {
            this.setState(this.unlocked);
            return "opened";
        }

        public String alarm() {
            this.setState(this.locked);
            return "alarm";
        }

        public String refund() {
            this.setState(this.unlocked);
            return "refund";
        }

        public String close() {
            this.setState(this.locked);
            return "closed";
        }

        private void setState(EntranceMachineState state) {
            this.state = state;
        }
    }

    class InvalidActionException extends RuntimeException {
    }
}
