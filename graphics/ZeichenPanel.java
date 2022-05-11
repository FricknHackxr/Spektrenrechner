package graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Spektren.Farben;

/**
 * Auf diesem Panel wird gezeichnet. Er unterstützt mehrere Ebenen (Layer) und
 * Transparenz über die Klasse GColor.
 * 
 * @author Gube
 *
 */
public class ZeichenPanel extends Canvas implements Runnable {

	private Thread thread; // in diesem Thread läuft die Spielfläche

	private BufferStrategy bs;
	private BufferedImage image;

	private int width = 780;
	private int height = 300;
	private int scale = 1;

	private int nextFrame = (int) ((1.0 / 30) * 1000);

	private String name;

	private JPanel panel = new JPanel();

	private int[] spektrum;

	private boolean running, repaint;

	public ZeichenPanel(String name, int[] spektrum) {
		this.spektrum = spektrum;
		this.create(name, this.scale);
	}

	public ZeichenPanel(String name, int scale) {
		this.create(name, scale);
	}

	private void create(String newName, int newScale) {
		this.name = newName;
		this.width = 600;
		this.height = 300;
		this.scale = newScale;

		Dimension dim = new Dimension(this.width * this.scale, this.height * this.scale);

		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.setPreferredSize(dim);

		panel.add(this);
	}

	public synchronized void stop() {
		running = false;
	}

	/* die Startroutine, mit der die Spielfläche inizialisiert wird */
	public synchronized void start() {

		JFrame frame = new JFrame(name);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		running = true;
		repaint = true;

		image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

		thread = new Thread(this);
		thread.start();
	}

	/* die Hauptschleife, in der die Zeichenfläche regelmäßig neu gezeichnet wird */
	public synchronized void run() {
		// Warmlaufphase
		for (int i = 0; i < 3; i++) {
			render();

			try {
				Thread.sleep(this.nextFrame);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Die eigentliche Renderschleife
		while (running) {

			// System.out.println(this.name + " " + this.repaint);

			if (repaint) {
				render();
				repaint = false;
			} else {
				try {
					Thread.sleep(this.nextFrame);
					this.repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void repaint() {
		this.repaint = true;
	}

	public JPanel getImage() {
		JPanel ausgabe = new JPanel();
		ausgabe.add(this);
		return ausgabe;
	}

	private void render() {

		bs = getBufferStrategy(); // Buffer der einzelnen Bilder
		if (bs == null) {
			createBufferStrategy(3); // doppelte Bufferung, das Bild wird geflippt während das neue Bild bereits
										// gezeichnet wird
			return; // nötig, da das ErrechtsRunter();stellen der BS etwas Zeit benötigt, sonst
					// Fehler durch
					// NullPointer
		}

		// Hier wird das fertige, überlagerte Bild dann an das fertige Bild pixelweise
		// übergeben.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// Hintergrund
				int r = 0;
				int g = 0;
				int b = 0;

				GColor summe2 = new GColor(r, g, b);
				Color col = new Color(summe2.getR(), summe2.getG(), summe2.getB());
				int wert = (col.getRGB() & 0xFFFFFF);
				image.setRGB(x, y, wert);
			}
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, width * scale, height * scale, null);

		// Spektrum zeichnen
		g.setColor(new Color(255, 255, 255));
		for (int wellenlänge : spektrum) {
			if (390 < wellenlänge && wellenlänge < 780) {
				g.setColor(Farben.getFarbe(wellenlänge));
				g.drawLine((wellenlänge - 400) * 2, 0, (wellenlänge - 400) * 2, this.height);
			}
		}

		g.dispose();
		bs.show();
	}
}