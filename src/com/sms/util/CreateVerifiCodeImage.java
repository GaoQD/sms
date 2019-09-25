package com.sms.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CreateVerifiCodeImage {

	private static int WIDTH = 85;
	private static int HEIGHT = 29;
	private static int FONT_SIZE = 18;
	private static char[] verifiCode;
	private static BufferedImage verifiCodeImage;

	/**
	 * @Title: createImage
	 * @Description: ��ȡ��֤��ͼƬ
	 * @return: void
	 */
	public static BufferedImage getVerifiCodeImage() {
		verifiCodeImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);// create a image
		Graphics graphics = verifiCodeImage.getGraphics();

		verifiCode = generateCheckCode();
		drawBackground(graphics);
		drawRands(graphics, verifiCode);

		graphics.dispose();

		return verifiCodeImage;
	}

	/**
	 * @Title: getVerifiCode
	 * @Description: ��ȡ��֤��
	 * @return: char[]
	 */
	public static char[] getVerifiCode() {
		return verifiCode;
	}

	/**
	 * @Title: generateCheckCode
	 * @Description: ���������֤��
	 * @return: char[]
	 */
	private static char[] generateCheckCode() {
		String chars = "0123456789abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] rands = new char[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * (10 + 26 * 2));
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}

	/**
	 * @Title: drawRands
	 * @Description: ������֤��
	 * @return: void
	 */
	private static void drawRands(Graphics g, char[] rands) {
		g.setFont(new Font("Console", Font.BOLD, FONT_SIZE));

		for (int i = 0; i < rands.length; i++) {

			g.setColor(getRandomColor());
			g.drawString("" + rands[i], i * FONT_SIZE + 10, 22);
		}
	}

	/**
	 * @Title: drawBackground
	 * @Description: ����ͼƬ����
	 * @return: void
	 */
	private static void drawBackground(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// ������֤����ŵ�
		for (int i = 0; i < 200; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			g.setColor(getRandomColor());
			g.drawOval(x, y, 1, 1);

		}
	}

	/**
	 * @Title: getRandomColor
	 * @Description: ��ȡ�����ɫ
	 * @return: Color
	 */
	private static Color getRandomColor() {
		Random ran = new Random();
		return new Color(ran.nextInt(220), ran.nextInt(220), ran.nextInt(220));
	}
}
