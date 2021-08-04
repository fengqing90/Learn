package learn.设计模式.状态模式2;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Objects;

import org.junit.Test;

import lombok.Data;

/**
 * 01.04 使用Enum的来实现状态机
 * //////////////////////////////////////////////////////////////////////////////////////
 * 通过上面的代码，可以发现Action、EntranceMachineState两个枚举的复杂度都提升了。不单单是定义了常量那么简单。还提供了相应的逻辑处理。
 * 在EntranceMachineState.java的提交记录中，对进行了一次重构，将具体业务逻辑执行移动到EntranceMachine中，EntranceMachineState内每种状态的方法中只负责调度。这样能够通过EntranceMachineState相对直观的看清楚做了什么，状态变成了什么。
 * 缺陷就是，EntranceMachine 对外提供了public的setState方法，这也就意味着调用者在将来维护是，很有可能滥用setState方法。
 * 
 * @author fengqing
 * @date 2021/7/21 19:18
 */
public class 使用Enum的来实现状态机 {

    @Test
    void should_unlocked_when_insert_coin_given_a_entrance_machine_with_locked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.LOCKED);

        String result = entranceMachine.execute(Action.INSERT_COIN);

        then(result).isEqualTo("opened");
        then(entranceMachine.getState())
            .isEqualTo(EntranceMachineState.UNLOCKED);
    }

    @Test
    void should_alarm_when_pass_given_a_entrance_machine_with_locked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.LOCKED);

        String result = entranceMachine.execute(Action.PASS);

        then(result).isEqualTo("alarm");
        then(entranceMachine.getState()).isEqualTo(EntranceMachineState.LOCKED);
    }

    @Test
    void should_fail_when_execute_invalid_action_given_a_entrance_machine() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.LOCKED);

        assertThatThrownBy(() -> entranceMachine.execute(null))
            .isInstanceOf(InvalidActionException.class);
    }

    @Test
    void should_refund_when_insert_coin_given_a_entrance_machine_with_unlocked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.UNLOCKED);

        String result = entranceMachine.execute(Action.INSERT_COIN);

        then(result).isEqualTo("refund");
        then(entranceMachine.getState())
            .isEqualTo(EntranceMachineState.UNLOCKED);
    }

    @Test
    void should_closed_when_pass_given_a_entrance_machine_with_unlocked_state() {
        EntranceMachine entranceMachine = new EntranceMachine(
            EntranceMachineState.UNLOCKED);

        String result = entranceMachine.execute(Action.PASS);

        then(result).isEqualTo("closed");
        then(entranceMachine.getState()).isEqualTo(EntranceMachineState.LOCKED);

    }

    @Data
    public class EntranceMachine {

        private EntranceMachineState state;

        public EntranceMachine(EntranceMachineState state) {
            setState(state);
        }

        public String execute(Action action) {
            if (Objects.isNull(action)) {
                throw new InvalidActionException();
            }

            return action.execute(this, state);
        }

        public String open() {
            return "opened";
        }

        public String alarm() {
            return "alarm";
        }

        public String refund() {
            return "refund";
        }

        public String close() {
            return "closed";
        }
    }

    enum Action {
        PASS {
            @Override
            public String execute(EntranceMachine entranceMachine,
                    EntranceMachineState state) {
                return state.pass(entranceMachine);
            }
        },
        INSERT_COIN {
            @Override
            public String execute(EntranceMachine entranceMachine,
                    EntranceMachineState state) {
                return state.insertCoin(entranceMachine);
            }
        };

        public abstract String execute(EntranceMachine entranceMachine,
                EntranceMachineState state);
    }

    enum EntranceMachineState {
        LOCKED {
            @Override
            public String insertCoin(EntranceMachine entranceMachine) {
                entranceMachine.setState(UNLOCKED);
                return entranceMachine.open();
            }

            @Override
            public String pass(EntranceMachine entranceMachine) {
                entranceMachine.setState(this);
                return entranceMachine.alarm();
            }
        },
        UNLOCKED {
            @Override
            public String insertCoin(EntranceMachine entranceMachine) {
                entranceMachine.setState(this);
                return entranceMachine.refund();
            }

            @Override
            public String pass(EntranceMachine entranceMachine) {
                entranceMachine.setState(LOCKED);
                return entranceMachine.close();
            }
        };

        public abstract String insertCoin(EntranceMachine entranceMachine);

        public abstract String pass(EntranceMachine entranceMachine);
    }

    class InvalidActionException extends RuntimeException {
    }
}
