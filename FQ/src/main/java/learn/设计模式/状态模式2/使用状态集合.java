package learn.设计模式.状态模式2;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 01.03 使用状态集合
 * 状态集合是将一组描述状态变化的事务元素组成的集合。
 * 集合中的每一个元素包含4个属性：当前的状态，事件，下一个状态，触发的动作。
 * 使用时遍历集合根据动作找到特定的元素，并更具元素上的属性和事件来完成业务逻辑。
 * ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 相比于Switch的实现方式，状态集合的实现方式对状态规则的描述更加直观。且扩展性更强，不需求修改实现路基，只需要添加相关的状态描述即可。
 * 我们知道日常工作中读代码和写代码比例在10:1，有些场景下甚至到了20:1。Switch需要我们每次在脑子中组织一次状态的顺序和规则，而集合能够很直观的表达出这个规则。
 * 
 * @author fengqing
 * @date 2021/7/21 19:00
 */
public class 使用状态集合 {
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
    public void should_be_alarm_when_pass_given_a_entrance_machine_with_locked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.LOCKED);

        String result = entranceMachine.execute(Action.PASS);

        then(result).isEqualTo("alarm");
        then(entranceMachine.getState()).isEqualTo(EntranceMachineState.LOCKED);
    }

    @Test
    public void should_fail_when_execute_invalid_action_given_a_entrance_machine() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.LOCKED);

        assertThatThrownBy(() -> entranceMachine.execute(null))
            .isInstanceOf(InvalidActionException.class);

    }

    @Test
    public void should_closed_when_pass_given_a_entrance_machine_with_unlocked() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.UNLOCKED);

        String result = entranceMachine.execute(Action.PASS);

        then(result).isEqualTo("closed");
        then(entranceMachine.getState()).isEqualTo(EntranceMachineState.LOCKED);
    }

    @Test
    public void should_refund_when_insert_coin_given_a_entrance_machine_with_unlocked() {
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
    enum Action {
        PASS,
        INSERT_COIN
    }

    enum EntranceMachineState {
        LOCKED,
        UNLOCKED
    }

    @Data
    class EntranceMachine {

        List<EntranceMachineTransaction> entranceMachineTransactionList = Arrays
            .asList(
                EntranceMachineTransaction.builder()
                    .currentState(EntranceMachineState.LOCKED)
                    .action(Action.INSERT_COIN)
                    .nextState(EntranceMachineState.UNLOCKED)
                    .event(new OpenEvent()).build(),
                EntranceMachineTransaction.builder()
                    .currentState(EntranceMachineState.LOCKED)
                    .action(Action.PASS).nextState(EntranceMachineState.LOCKED)
                    .event(new AlarmEvent()).build(),
                EntranceMachineTransaction.builder()
                    .currentState(EntranceMachineState.UNLOCKED)
                    .action(Action.PASS).nextState(EntranceMachineState.LOCKED)
                    .event(new CloseEvent()).build(),
                EntranceMachineTransaction.builder()
                    .currentState(EntranceMachineState.UNLOCKED)
                    .action(Action.INSERT_COIN)
                    .nextState(EntranceMachineState.UNLOCKED)
                    .event(new RefundEvent()).build());

        private EntranceMachineState state;

        public EntranceMachine(EntranceMachineState state) {
            setState(state);
        }

        public String execute(Action action) {
            Optional<EntranceMachineTransaction> transactionOptional = entranceMachineTransactionList
                .stream()
                .filter(transaction -> transaction.getAction().equals(action)
                    && transaction.getCurrentState().equals(state))
                .findFirst();

            if (!transactionOptional.isPresent()) {
                throw new InvalidActionException();
            }

            EntranceMachineTransaction transaction = transactionOptional.get();
            setState(transaction.getNextState());
            return transaction.getEvent().execute();
        }
    }

    interface Event {

        String execute();
    }

    class OpenEvent implements Event {
        @Override
        public String execute() {
            return "opened";
        }
    }

    class AlarmEvent implements Event {
        @Override
        public String execute() {
            return "alarm";
        }
    }

    class CloseEvent implements Event {
        @Override
        public String execute() {
            return "closed";
        }
    }

    class RefundEvent implements Event {
        @Override
        public String execute() {
            return "refund";
        }
    }

    class InvalidActionException extends RuntimeException {
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class EntranceMachineTransaction {

    private 使用状态集合.EntranceMachineState currentState;

    private 使用状态集合.Action action;

    private 使用状态集合.EntranceMachineState nextState;

    private 使用状态集合.Event event;
}