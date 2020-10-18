package learn.learnspringBatch.listener;

import org.springframework.batch.item.ItemProcessor;

/**
 * KEP-TODO
 *
 * @ClassName MessageProcessor
 * @Author FengQing
 * @Date 2019/9/23 14:50
 */
public class MessageProcessor implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        return null;
    }
}
