package learn.命令模式;

public class 简单样例 {

    public static void main(String[] args) {
        new Invoker(new ConcreteCommand(new Receiver())).call();
    }
}

abstract class Command {
    public abstract void execute();
}

class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    //业务方法，用于调用命令类的execute()方法
    public void call() {
        this.command.execute();
    }
}

class ConcreteCommand extends Command {
    private Receiver receiver; //维持一个对请求接收者对象的引用

    @Override
    public void execute() {
        this.receiver.action(); //调用请求接收者的业务处理方法action()
    }

    public ConcreteCommand(Receiver receiver) {
        super();
        this.receiver = receiver;
    }
}

class Receiver {
    public void action() {
        //具体操作
        System.out.println("具体操作");
    }
}