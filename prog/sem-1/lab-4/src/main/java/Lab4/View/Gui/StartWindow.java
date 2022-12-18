package Lab4.View.Gui;

import Lab4.View.IView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class StartWindow extends JFrame implements IWindow {
    private GuiView view;

    public StartWindow(GuiView v) {
        super(v.getAppName());
        this.view = v;
    }

    @Override
    public void closeWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void showWindow() {
        JLabel header = new JLabel("Лабораторная работа № 4");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setFont(new Font("Sans", Font.PLAIN, 36));
        JLabel description = new JLabel("Текст про трупы");
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setFont(new Font("Sans", Font.PLAIN, 16));
        JButton startBtn = new JButton("Старт");
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                view.onStartBtnClick();
            }
        });
        add(Box.createVerticalGlue());
        add(header);
        add(Box.createVerticalStrut(20));
        add(description);
        add(Box.createVerticalGlue());
        add(startBtn);
        add(Box.createVerticalGlue());
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setIconImage(new ImageIcon("src/main/resources/app-icon.png").getImage());
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
