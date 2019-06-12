package learn.rabbitmq.demo;

import java.io.Serializable;

public class UserDto implements Serializable {

    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer age;
    private String time;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserDto() {
        super();
    }

    public UserDto(String name, Integer age, String time) {
        super();
        this.name = name;
        this.age = age;
        this.time = time;
    }


}
