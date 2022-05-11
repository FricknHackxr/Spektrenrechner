package graphics;

/**
 * Eigene Klasse zur Verwaltung von Farben im Alpha - Rot - Grün - Blau Format.
 * Alle 4 Farbparameter werden einzeln als Integer (0 - 255) gespeichert.
 * 
 * Der Standartwert ist Weiß ohne Transparenz. *
 * 
 * @author Herr Gube
 * @since 0.3.2.6
 *
 */
public class GColor {

	private double alpha;
	private int rot;
	private int grun;
	private int blau;

	/**
	 * Standart Weiß ohne Transparenz.
	 */
	public GColor() {
		setA(1);
		setR(255);
		setG(255);
		setB(255);
	}

	/**
	 * Farbe ohne Transparenz im RGB Format.
	 * 
	 * @param r Rot
	 * @param g Grün
	 * @param b Blau
	 */
	public GColor(int r, int g, int b) {
		setA(1);
		setR(r);
		setG(g);
		setB(b);
	}

	/**
	 * Farbe mit Transparenz im RGB Format.
	 * 
	 * @param r Rot
	 * @param g Grün
	 * @param b Blau
	 * @param a Alpha
	 */
	public GColor(int r, int g, int b, double a) {
		setA(a);
		setR(r);
		setG(g);
		setB(b);
	}

	/**
	 * Addiert zwei Farben unter Berücksichtigung der Transparenz der zweiten Farbe.
	 * Hat man keine Transparenz, so wird die Hintergrundfarbe vollständig
	 * überschrieben.
	 * 
	 * @param farbe Die Farbe, welche die dahinter liegende Farbe überdeckt.
	 */
	public GColor addiere(GColor farbe) {

		if (farbe == null) {
			return new GColor(getR(), getG(), getB(), getA());
		}

		GColor ret = new GColor();

		int newR = getR() + (int) ((farbe.getR() - getR()) * farbe.getA());
		ret.setR(newR);

		int newG = getG() + (int) ((farbe.getG() - getG()) * farbe.getA());
		ret.setG(newG);

		int newB = getB() + (int) ((farbe.getB() - getB()) * farbe.getA());
		ret.setB(newB);

		return ret;
	}

	public void setA(double a) {
		if (a >= 0 && a <= 1) {
			this.alpha = a;
		} else {
			this.alpha = 1;
		}
	}

	public void setR(int r) {
		if (r >= 0 && r < 256) {
			this.rot = r;
		} else {
			this.rot = 255;
		}
	}

	public void setG(int g) {
		if (g >= 0 && g < 256) {
			this.grun = g;
		} else {
			this.grun = 255;
		}
	}

	public void setB(int b) {
		if (b >= 0 && b < 256) {
			this.blau = b;
		} else {
			this.blau = 255;
		}
	}

	public double getA() {
		return alpha;
	}

	public int getR() {
		return rot;
	}

	public int getG() {
		return grun;
	}

	public int getB() {
		return blau;
	}

	/**
	 * Gibt den aktuellen Farbwert im ARGB Format aus. Sollte das RufferedImage nur
	 * RGB unterstützen wird der Alpha-Wert ignoriert.
	 * 
	 * @return Farbwert im 0xAARRGGBB Format
	 */
	public int getInt() {

		int wert = ((int) (getA() * 255) << 24) | (getR() << 16) | (getG() << 8) | getB();
		// System.out.println(""+wert);

		return wert;
		// return 0x00FF0000*rot/256 + 0x0000FF00*grun/256 + 0x000000FF*blau/256;
	}

	public boolean equals(GColor color) {
		if (getR() == color.getR() && getG() == color.getG() && getB() == color.getB())
			return true;
		return false;
	}

	public String toString() {
		return "Farbe: R=" + getR() + " G=" + getG() + " B=" + getB();
	}

}
