package com.aline.View;

import com.aline.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Menu extends JFrame {
	private boolean on = false;
	private ApplicationContext api = null;
	private final ExecutorService pThread = Executors.newFixedThreadPool(2);

	public Menu(String[] args) {
		super("LibrasAPI");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (api != null && on) SpringApplication.exit(api);
				System.exit(0);
			}
		});

		JTextArea log = new JTextArea();
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEnabled(false);
		log.setDisabledTextColor(Color.BLACK);
		log.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));

		JButton toggle = new JButton("Ligar API");
		toggle.setForeground(Color.GREEN);
		toggle.addActionListener(act -> pThread.execute(() -> {
			on = !on;
			if (on) {
				toggle.setText("Desligar API");
				toggle.setForeground(Color.RED);
				api = SpringApplication.run(Main.class, args);
			} else {
				toggle.setText("Ligar API");
				toggle.setForeground(Color.GREEN);
				SpringApplication.exit(api);
				log.append("---------------------------------------------------------------------");
			}
		}));

		StringBuilder sb = new StringBuilder();
		PrintStream stream = new PrintStream(new OutputStream() {
			@Override
			public void write(int b) {
				if ((char) b == '\n') {
					if (sb.length() > 180 && sb.toString().startsWith("\u001B")) {
						String type = sb.toString().contains("INFO") ? "INFO" : sb.toString().contains("WARN") ? "WARN" : "ERROR";

						log.append(type + " | " + sb.toString().substring(180) + "\n");
						log.setCaretPosition(log.getDocument().getLength());
					}
					sb.setLength(0);
				} else sb.append((char) b);
			}
		});
		//System.setOut(stream);

		add(new JScrollPane(log), BorderLayout.CENTER);
		add(toggle, BorderLayout.SOUTH);
		setVisible(true);
		setSize(500, 600);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}
