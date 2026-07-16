package com.dunno;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class Main extends JFrame {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

		Main main = new Main();
		main.add(new GamePanel());
		main.pack();

		main.setTitle("Jumper");
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main.setVisible(true);
	});
    }
}
