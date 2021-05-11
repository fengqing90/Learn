package cn.fq.oauth.bean;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Random;

import lombok.Data;

/**
 * 图片信息
 *
 * @author fengqing
 * @date 2021/5/7 10:48
 */
@Data
public class ImageCode {
    private String code;
    private LocalDateTime expireTime;
    private BufferedImage image;

    public ImageCode(String code, int expireTime, BufferedImage image) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
        this.image = image;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expireTime);
    }

    /**
     * 创建图片验证码
     */
    public static ImageCode createImageCode() {
        int width = 67, height = 21;

        BufferedImage image = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);

        Random random = new Random();

        Graphics g = image.createGraphics();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("微软雅黑", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));

        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            // String rand = String.valueOf(random.nextInt(10));
            String rand = "1";
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110),
                20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(sRand.toString(), 60, image);
    }

    /**
     * 生成随机背景条纹
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
