package learn.rabbitmq.config;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author FengQing
 * @date 2018年12月12日 下午2:23:07
 */
public class AccountRabbitMQConfig {

    public static class AccountExchange {
        /**
         * 死信exchange
         */
        public static final String DEAD_EXCHANGE = "DLE.exchange";

        public static final String KEPLER_EXCHANGE = "exchange.kepler.direct";

    }

    public static class AccountRoutingKey {
        /**
         * 死信队列key
         */
        public static final String DEAD_QUEUE_KEY = "DLQ_key_direct_kepler";

        /**
         * 还款key
         */
        public static final String REPAY_KEY = "key_direct_autopay";
    }

    public static class AccountQueueName {

        public static final String DLQ_QUEUE_KEPLER = "DLQ.queue.kepler";

        /**
         * 还款队列
         */
        public static final String AUTOPAY_QUEUE = "queue.kepler.direct.autopay";

    }


    public enum AccountListennerConfig {

        DLQ_QUEUE_KEPLER(AccountQueueName.DLQ_QUEUE_KEPLER),
        AUTOPAY_QUEUE(AccountQueueName.AUTOPAY_QUEUE, 5, 20);

        private final String queueName;
        private final int maxConcurrentConsumers;
        private final int concurrentConsumers;

        private AccountListennerConfig(String queueName,
                int concurrentConsumers, int maxConcurrentConsumers) {
            this.queueName = queueName;
            this.concurrentConsumers = concurrentConsumers;
            this.maxConcurrentConsumers = maxConcurrentConsumers;
        }

        private AccountListennerConfig(String queueName) {
            this.queueName = queueName;
            this.concurrentConsumers = 1;
            this.maxConcurrentConsumers = 3;
        }

        public String getQueueName() {
            return this.queueName;
        }

        public int getMaxConcurrentConsumers() {
            return this.maxConcurrentConsumers;
        }

        public int getConcurrentConsumers() {
            return this.concurrentConsumers;
        }

        @Override
        public String toString() {
            return this.queueName;
        }

        public static Map<String, AccountListennerConfig> getAllMapping() {
            return Stream.of(AccountListennerConfig.values())
                .collect(Collectors.toMap(i -> i.toString(), i -> i));
        }

    }

}
