package Main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Screen {
	
	private Robot robot;
	
	private int[] x , y;
	private int size;
	private Color cor;
	private int MRGB[];
	private int L_TIME , R , G , B;
	private String COLOR_NAME;
		
	public Screen() {
		robot = new Robot();
		
		MRGB = new int[]{0, 0, 0};
		
		y = new int[512];
		x = new int[512];
		
		size = 0;
	}
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		Screen screen = new Screen();
		screen.getPixels();
		//wait(1000);
		while(true) {
			screen.showColors();
			//wait(3000);
		}
	}
	
	public void getPixels() throws InterruptedException, AWTException {
		print("Posicione o mouse sobre o meio da nota VERDE");
		wait(1300);
		//varredura();
		drawSquare();
	}
	
	public void varredura() throws AWTException, InterruptedException {
		for(int v = 0; v < 30; v++) {
			setMouseY(1);
			y[v] = getPixelCord('y');
			print("Y ["+v+"] = "+y[v]);
		}
		wait(1000);
		setMouseY(-15);
		for(int v = 0; v < 35; v++) {
			setMouseX(1);
			x[v] = getPixelCord('x');
			print("X ["+v+"] = "+x[v]);
		}
		wait(1000);
		setMouseX(-35);
		for(int v = 36; v < 71; v++) {
			setMouseX(-1);
			x[v] = getPixelCord('x');
			print("X ["+v+"] = "+x[v]);
		}
	}
	
	public void drawSquare() throws AWTException, InterruptedException {
		int x1 = getPixelCord('x');
		int x2 = x1 - 80;
		int y1 = getPixelCord('y');
		int y2 = y1 - 45;
		for(int H = x2; H < x1; H += 10){
			for(int V = y2; V < y1; V += 10) {
				int cor = robot.getPixelColor(H, V).getGreen();
				if(!(cor < 20 || cor == 74)) {
					robot.mouseMove(H, V);
					x[size] = H;
					y[size] = V;
					size++;
				}
			}
		}
	}
	
	public void showColors() throws AWTException {
		L_TIME = 0;
		for(int i = 0; i < x.length; i++) {
			if(x[i] == 0) {
				break;
			}
			//robot.mouseMove(x[i], y[i]);
			cor = robot.getPixelColor(x[i] , y[i]);
			//print("COR NA CORD [ X:" + x[i] + " ; Y:" + y[i] + " ] = " + getColor(x[i] , y[i]) );
			MRGB[0] += cor.getRed();
			MRGB[1] += cor.getGreen();
			MRGB[2] += cor.getBlue();
			L_TIME++;
		}
		R = MRGB[0] / L_TIME;
		G = MRGB[1] / L_TIME;
		B = MRGB[2] / L_TIME;
		COLOR_NAME = getColorName(R, G, B);
		print("["+R+"; "+G+"; "+B+" = "+COLOR_NAME);
		/*
		if(COLOR_NAME != "") {
			print("( " + L_TIME + ") + { " + MRGB[0] + " ; " + MRGB[1] + " ; " + MRGB[2] + " } [" + R + " ; " + G + " ; " + B + " = " + COLOR_NAME);
		}
		*/
		if(COLOR_NAME != "PRETO") {
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
		}
	}
	
	public String getColorName(int r , int g , int b) {
		if(r > 200 && g < 50 && b < 50){ return "VERMELHO";}
		if(r < 50 && g > 140 && b < 50){ return "VERDE";}
		if(r > 150 && g > 140 && b < 50){ return "AMARELO";}
		if(r < 50 && g > 100 && b > 150){ return "AZUL";}
		if(r > 210 && g > 210 && b > 210){ return "BRANCO";}
		if(r < 30 && g < 30 && b < 30){ return "PRETO";}
		return "";
	}
	
	public Color getColor(int x, int y) throws AWTException {
		return robot.getPixelColor(x, y);
	}
	
	public void setMouseY(int pixels) throws AWTException {
		robot.mouseMove(getMouseX(), (getMouseY()+pixels));
	}
		
	public void setMouseX(int pixels) throws AWTException {
		robot.mouseMove((getMouseX()+pixels), getMouseY());
	}
	
	public int getMouseX() {
		return MouseInfo.getPointerInfo().getLocation().x;
	}
	
	public int getMouseY() {
		return MouseInfo.getPointerInfo().getLocation().y;
	}
	
	public void print(String text) {
		System.out.println(text);
	}
		
	public int getPixelCord(char p) {
		if (p == 'x') {
			return MouseInfo.getPointerInfo().getLocation().x;
		}
		return MouseInfo.getPointerInfo().getLocation().y;
	}
}
