import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Spektren.Rechner;
import graphics.ZeichenPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

public class GUI extends JFrame implements ActionListener {

    private JButton button1, button2, button3;
    private JTextField n1, n2, E_i;

    public GUI() {
        this.setTitle("Spektrenrechner");
        this.setSize(800, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(5, 2));

        JLabel label1 = new JLabel("Ionisationsenergie");
        this.add(label1);
        E_i = new JTextField();
        this.add(E_i);
        JLabel label2 = new JLabel("Schale 1");
        this.add(label2);
        n1 = new JTextField();
        this.add(n1);
        JLabel label3 = new JLabel("Schale 2");
        this.add(label3);
        n2 = new JTextField();
        this.add(n2);

        button1 = new JButton("Wellenlänge berechnen");
        button1.addActionListener(this);
        this.add(button1);
        button2 = new JButton("Energie berechnen");
        button2.addActionListener(this);
        this.add(button2);
        button3 = new JButton("Spektrum zeichnen");
        button3.addActionListener(this);
        this.add(button3);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.button1) {
            JDialog dialog = new JDialog(this, "Wellenlänge");
            dialog.setSize(300, 200);
            dialog.add(new JLabel("Wellenlänge: " + Rechner.wellenlänge(Integer.parseInt(this.n1.getText()),
                    Integer.parseInt(this.n2.getText()), Double.parseDouble(this.E_i.getText())) * 1_000_000_000
                    + " nm"));
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setVisible(true);
        } else if (e.getSource() == this.button2) {
            JDialog dialog = new JDialog(this, "Energie");
            dialog.setSize(300, 200);
            dialog.add(new JLabel("Energie: " + Rechner.energie(Integer.parseInt(this.n1.getText()),
                    Integer.parseInt(this.n2.getText()), Double.parseDouble(this.E_i.getText())) + " J"));
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setVisible(true);
        } else if (e.getSource() == this.button3) {
            int[] spektrum = Rechner.spektrum(Double.parseDouble(this.E_i.getText()), 6);
            ZeichenPanel spektrumBild = new ZeichenPanel("Spektrum für die Ionisationsenergie " + E_i.getText() + " J",
                    spektrum);
            spektrumBild.start();
        }
    }
}
