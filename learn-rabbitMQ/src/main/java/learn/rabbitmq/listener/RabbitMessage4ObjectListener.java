package learn.rabbitmq.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import learn.rabbitmq.demo.User;
import learn.rabbitmq.demo.UserDto;


@Component
public class RabbitMessage4ObjectListener implements MessageListener {

    @Autowired
    private MessageConverter contentTypeConverter;


    @Override
    public void onMessage(Message message) {
        User user = null;
        System.out.println("【RabbitMessage4ObjectListener】@@@@@@"
            + Thread.currentThread().getName() + "@@@@"
            + message.getMessageProperties().getHeaders().get("count"));
        System.out.println(message);
        System.out.println(message.getBody());

        // 1.反序列化对象
//        user = (User) SerializationUtils.deserialize(message.getBody());

        // 2.反序byte[]
        try {
            user = new ObjectMapper().readValue(message.getBody(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("convert byte[]- User :" + user);

        // 3.反序string
        try {
            user = new ObjectMapper()
                .readValue(new String(message.getBody(), "UTF-8"),
                User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("convert string- User :" + user);

        // 4.通用转换,xml配置 or @Bean  转换有点不优美，仅作学习研究用  https://docs.spring.io/spring-amqp/docs/1.7.7.RELEASE/reference/htmlsingle/#json-message-converter
        //  1>通过 message.getMessageProperties().getContentType() 找对应代理
        //  2>未设置转换类型 会报错java.lang.ClassCastException: learn.rabbitmq.demo.User cannot be cast to learn.rabbitmq.demo.UserDto
        //  3>重设转换类型message.getMessageProperties().getHeaders().get(DefaultClassMapper.DEFAULT_CLASSID_FIELD_NAME)
        System.out.println("DEFAULT_CLASSID_FIELD_NAME:"
            + message.getMessageProperties().getHeaders()
                .get(DefaultClassMapper.DEFAULT_CLASSID_FIELD_NAME));
        message.getMessageProperties().getHeaders().put(
            DefaultClassMapper.DEFAULT_CLASSID_FIELD_NAME,
            UserDto.class.getName());
        UserDto userDto = (UserDto) this.contentTypeConverter.fromMessage(message);
        System.out.println("convert contentType - User :" + userDto);

        System.out
            .println("^^^^^^^^^^^^^^^^^^^^"
                + message.getMessageProperties().getHeaders().get("count"));

    }
}
