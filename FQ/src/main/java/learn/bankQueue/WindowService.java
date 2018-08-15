package learn.bankQueue;

import java.util.Random;

public class WindowService {

    //    private Integer commonWaitTime = 5000;
    //    private Integer expressWaitTime = 2000;
    //    private Integer vipWaitTime = 10000;

    private CustomerType type;
    private Integer windowNumber;
    private String windowTitle;

    public CustomerType getType() {
        return this.type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public Integer getWindowNumber() {
        return this.windowNumber;
    }

    public void setWindowNumber(Integer windowNumber) {
        this.windowNumber = windowNumber;
    }

    public String getWindowTitle() {
        this.windowTitle = this.windowNumber + "号，<" + this.type.toString()
            + ">柜台";
        return this.windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    ///////////////////
    public void commonWindService() {
        Thread service = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (NumberManager.commonQueue.size() == 0) {
                            //							System.out.println(getWindowTitle()+"无人.");
                            Thread.sleep(500);
                        } else {
                            Object obj = NumberManager.getCommon();

                            System.out
                                .println(WindowService.this.getWindowTitle()
                                    + "-开始-服务,用户标识<" + obj + ">.");
                            Integer time = Math
                                .abs(new Random().nextInt(100000));
                            Thread.sleep(time);
                            System.out
                                .println(WindowService.this.getWindowTitle()
                                    + "-完成-服务,用户标识<" + obj + ">.用时"
                                    + time / 1000 + "秒");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        service.setName(this.getWindowTitle());
        service.start();
    }

    public void expressWindService() {
        Thread service = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (NumberManager.expressQueue.size() == 0) {
                            //							System.out.println(getWindowTitle()+"无人.");
                            Thread.sleep(500);
                        } else {
                            Object obj = NumberManager.getExpress();

                            System.out
                                .println(WindowService.this.getWindowTitle()
                                    + "-开始-服务,用户标识<" + obj + ">.");
                            Integer time = Math
                                .abs(new Random().nextInt(100000));
                            Thread.sleep(time);
                            System.out
                                .println(WindowService.this.getWindowTitle()
                                    + "-完成-服务,用户标识<" + obj + ">.用时"
                                    + time / 1000 + "秒");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        service.start();
    }

    public void vipWindService() {
        Thread service = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (NumberManager.vipQueue.size() == 0) {
                            //							System.out.println(getWindowTitle()+"无人.");
                            Thread.sleep(500);
                        } else {
                            Object obj = NumberManager.getVip();

                            System.out
                                .println(WindowService.this.getWindowTitle()
                                    + "-开始-服务,用户标识<" + obj + ">.");
                            Integer time = Math
                                .abs(new Random().nextInt(100000));
                            Thread.sleep(time);
                            System.out
                                .println(WindowService.this.getWindowTitle()
                                    + "-完成-服务,用户标识<" + obj + ">.用时"
                                    + time / 1000 + "秒");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        service.start();
    }

    public enum CustomerType {
        COMMON,
        EXPRESS,
        VIP;
        @Override
        public String toString() {
            String name = null;
            switch (this) {
                case COMMON:
                    name = "普通";
                    break;
                case EXPRESS:
                    name = "快速";
                    break;
                case VIP:
                    name = this.name();
                    break;
            }
            return name;
        }
    }
}
