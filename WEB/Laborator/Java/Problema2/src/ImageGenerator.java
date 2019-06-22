

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageGenerator {
    private int width;
    private int height;

    public ImageGenerator(int width, int height) throws IOException {
        this.height = height;
        this.width = width;
    }

    public String generateCaptchaString() {
        Random random = new Random();
        int length = 6;

        StringBuilder captchaStringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int baseCharNumber = Math.abs(random.nextInt()) % 62;
            int charNumber = 0;
            if (baseCharNumber < 26) {
                charNumber = 65 + baseCharNumber;
            } else if (baseCharNumber < 52) {
                charNumber = 97 + (baseCharNumber - 26);
            } else {
                charNumber = 48 + (baseCharNumber - 52);
            }
            captchaStringBuffer.append((char) charNumber);
        }
        return captchaStringBuffer.toString();
    }

    public String getCaptcha(String captcha) throws IOException {
        if (captcha.length() != 6) {
            throw new IOException("Lungimea trebuie sa fie egala cu 6");
        } else {
            BufferedImage awtImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = awtImage.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Courier New", Font.PLAIN, 60));
            g.drawChars(captcha.toCharArray(), 0, captcha.length(), 40, 90);
            Random rand = new Random();
            for (int i = 1; i <= 5; i++) {
                int randomX1 = rand.nextInt(width);
                int randomX2 = rand.nextInt(width);
                int randomY1 = rand.nextInt(height);
                int randomY2 = rand.nextInt(height);
                g.drawLine(randomX1, randomY1, randomX2, randomY2);
            }
            Kernel kernel = new Kernel(3, 3, new float[]{4f / 30f, 4f / 30f, 4f / 30f, 4f / 30f, 4f / 30f, 4f / 30f, 4f / 30f, 4f / 30f, 4f / 30f});
            BufferedImageOp op = new ConvolveOp(kernel);
            awtImage = op.filter(awtImage, null);


            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int yy = (int) (y + 2 * Math.sin(x * 2 * Math.PI / 3));
                    if (yy >= 0 && yy < height) {
                        awtImage.setRGB(x, y, awtImage.getRGB(x, yy));
                    }
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(awtImage, "png", baos);
            baos.flush();
            String base64Encoded = Base64.getEncoder().encodeToString(baos.toByteArray());
            baos.close();
            return base64Encoded;
        }
    }


}

