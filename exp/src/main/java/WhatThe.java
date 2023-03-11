import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

public class WhatThe extends JFrame {

    private JLabel titleLabel;
    private JLabel messageLabel;
    private JProgressBar progressBar;
    private JButton cancelButton;
    private Timer timer;
    private int progressValue;

    public WhatThe() {
        setTitle("Windows Update");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.WEST;
        titleLabel = new JLabel("Working on updates");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        messageLabel = new JLabel("Please don't turn off your computer");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(messageLabel, gbc);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.add(progressBar, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        bottomPanel.add(cancelButton, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        progressValue = 0;
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (progressValue == 10000) {
                    timer.stop();
                    dispose();
                } else {
                    progressValue++;
                    progressBar.setValue(progressValue);
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        WhatThe updateScreen = new WhatThe();
        updateScreen.setVisible(true);
    }
}
