package learn.设计模式.类适配器模式;

/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        SpeakTarget speakTarget = new SpeakAdapter();
        speakTarget.speakChinese();
        speakTarget.speakEnglish();
        speakTarget.speakFrench();
    }
}