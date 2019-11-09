package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Recognizer {
	
	private Robot robot;
	private int[] x , y;
		
	public Recognizer() {
		robot = new Robot();
		y = new int[512];
		x = new int[512];
	}
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		Recognizer recognizer = new Recognizer();
		recognizer.getPixels();
		//wait(1000);
		while(true) {
			recognizer.showColors();
			//wait(3000);
		}
	}
	
	public void getPixels() throws InterruptedException, AWTException {
		System.out.println("Posicione o mouse sobre o meio da nota VERDE");
		wait(1300);
		//varredura();
		drawSquare();
	}
	
	public void varredura() throws AWTException, InterruptedException {
		for(int v = 0; v < 30; v++) {
			setMouseY(1);
			y[v] = getMouseY();
			System.out.println("Y ["+v+"] = "+y[v]);
		}
		wait(1000);
		setMouseY(-15);
		for(int v = 0; v < 35; v++) {
			setMouseX(1);
			x[v] = getMouseX();
			System.out.println("X ["+v+"] = "+x[v]);
		}
		wait(1000);
		setMouseX(-35);
		for(int v = 36; v < 71; v++) {
			setMouseX(-1);
			x[v] = getMouseX();
			System.out.println("X ["+v+"] = "+x[v]);
		}
	}
	
	public void drawSquare() throws AWTException, InterruptedException {
		Point mouse = new Point(MouseInfo.getPointerInfo().getLocation());
		
		int x, y, greenGradient, turn;
		
		for(x = mouse.x - 80; x < mouse.x; x += 10) {
			for(y = mouse.y - 45; y < mouse.x; y += 10) {
				greenGradient = robot.getPixelColor(x, y).getGreen();
				
				if(!(greenGradient < 20 || greenGradient == 74)) {
					robot.mouseMove(x, y);
					
					this.x[turn] = x
					this.y[turn] = y
					
					turn++;
				}
			}
		}
	}
	
	public void showColors() throws AWTException {
		Color color;
		String colorName;
		int r, g, b, turn;
		
		for(int i = 0; i < x.length; i++) {
			if(x[i] == 0)
				break;
			
			color = robot.getPixelColor(x[i], y[i]);
			
			r += color.getRed();
			g += color.getGreen();
			b += color.getBlue();
			
			turn++;
		}
		
		r = r / turn;
		g = g / turn;
		b = b / turn;
		
		colorName = getColorName(r, g, b);
		System.out.println("["+r+", "+g+", "+b+"] = "+colorName);
		
		if(!colorName.contentEquals("PRETO") {
			robot.keyPress(KetEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
		}
	}
	
	public String getColorName(int r, int g, int b) {
		if(r > 200 && g < 50  && b < 50 ){ return "VERMELHO";}
		if(r < 50  && g > 140 && b < 50 ){ return "VERDE";}
		if(r > 150 && g > 140 && b < 50 ){ return "AMARELO";}
		if(r < 50  && g > 100 && b > 150){ return "AZUL";}
		if(r > 210 && g > 210 && b > 210){ return "BRANCO";}
		if(r < 30  && g < 30  && b < 30 ){ return "PRETO";}
		return "";
	}
	
	private void setMouseX(int pixels) throws AWTException {
		robot.mouseMove(getMouseX()+pixels, getMouseY());
	}
	
	private void setMouseY(int pixels) throws AWTException {
		robot.mouseMove(getMouseX(), getMouseY()+pixels);
	}
	
	private int getMouseX() {
		return MouseInfo.getPointerInfo().getLocation().x;
	}
	
	private int getMouseY() {
		return MouseInfo.getPointerInfo().getLocation().y;
	}
	
}
