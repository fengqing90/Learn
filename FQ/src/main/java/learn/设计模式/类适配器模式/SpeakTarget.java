package learn.设计模式.类适配器模式;

/**
 * 目标接口类:英语、法语、汉语
 */
public interface SpeakTarget {
    void speakEnglish();

    void speakChinese();

    void speakFrench();
}