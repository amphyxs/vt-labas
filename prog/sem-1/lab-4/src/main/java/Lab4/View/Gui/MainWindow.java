package Lab4.View.Gui;

import Lab4.View.IView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class MainWindow extends JFrame implements IWindow {
    private final GuiView view;
    private DefaultTableModel tableModel;
    private JButton nextBtn;

    public MainWindow(GuiView v) {
        super(v.getAppName());
        this.view = v;
    }

    public void addNewData(String type, String place, String character, String description, String[] targets) {
        String targetsStr = String.join(", ", targets);
        this.tableModel.addRow(new String[] {type, place, character, description, targetsStr});
    }

    public void onDataEnd() {
        this.nextBtn.setEnabled(false);
        JOptionPane.showMessageDialog(this,
        "Больше предложений нет",
        "История закончилась",
            JOptionPane.WARNING_MESSAGE
        );
    }

    @Override
    public void closeWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }


    @Override
    public void showWindow() {
        final String[] columnNames = {
                "Тип",
                "Место",
                "Кто или что",
                "Что делает",
                "Кого"
        };
        this.tableModel = new DefaultTableModel(columnNames, 0);
        JTable storyTable = new JTable(this.tableModel);
        storyTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.nextBtn = new JButton("Далее");
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                view.onNextBtnClick();
            }
        });
        nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(new JScrollPane(storyTable));
        add(Box.createVerticalGlue());
        add(Box.createVerticalStrut(10));
        add(nextBtn);
        add(Box.createVerticalStrut(10));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setSize(800, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
