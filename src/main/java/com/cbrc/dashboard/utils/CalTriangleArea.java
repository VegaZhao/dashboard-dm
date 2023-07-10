package com.cbrc.dashboard.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.utils
 * @author: herry
 * @date: 2018-06-20  下午9:27
 * @Description: TODO
 */


public class CalTriangleArea extends JFrame implements ActionListener, FocusListener {
    int buttonNum = 12;
    JPanel panel1, panel2, panel3, panel4, panel5, downpanel, pane;
    JLabel lable1, lable2, lable3;
    JTextField tf1, tf2, tf3;
    JButton okbutton, cancelbutton;
    List<JButton> b = new ArrayList<>();
    boolean isTf1 = false, isTf2 = false;

    public CalTriangleArea() {
        init();
        this.setSize(400, 300);
        this.setLocation(100, 100);
        this.setTitle("三角形的面积");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void init() {
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        pane = new JPanel();
        downpanel = new JPanel();

        lable1 = new JLabel("输入三角形的高");
        lable2 = new JLabel("输入三角形的底");
        lable3 = new JLabel("三角形的面积");

        okbutton = new JButton("确定");
        cancelbutton = new JButton("取消");

        tf1 = new JTextField(10);
        tf2 = new JTextField(10);
        tf3 = new JTextField(10);

        tf1.addFocusListener(this); //添加焦点事件
        tf2.addFocusListener(this); //添加焦点事件

        panel5.setLayout(new GridLayout(4, 4, 4, 4));
        String name[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", ".", "0", "."};
        for (int i = 0; i < buttonNum; i++) {
            b.add(new JButton(name[i]));
            panel5.add(b.get(i));
            b.get(i).addActionListener(this);
        }

        panel1.add(lable1);
        panel1.add(tf1);

        panel2.add(lable2);
        panel2.add(tf2);

        panel3.add(lable3);
        panel3.add(tf3);

        panel4.add(okbutton);
        panel4.add(cancelbutton);

        downpanel.setLayout(new BorderLayout());
        downpanel.add(panel2, BorderLayout.NORTH);
        downpanel.add(panel3, BorderLayout.SOUTH);
        pane.setLayout(new BorderLayout());
        pane.add(panel5, BorderLayout.NORTH);
        pane.add(panel4, BorderLayout.SOUTH);

        this.add(panel1, BorderLayout.NORTH);
        this.add(downpanel, BorderLayout.CENTER);
        this.add(pane, BorderLayout.SOUTH);

        okbutton.addActionListener(this);
        cancelbutton.addActionListener(this);
    }

    /**
     * 焦点事件响应函数，根据判断事件源，进行不同处理
     *
     * @param e
     */
    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == tf1) {
            isTf1 = true;
            isTf2 = false;
        } else if (e.getSource() == tf2) {
            isTf1 = false;
            isTf2 = true;
        }
    }

    /**
     * 焦点释放响应函数
     *
     * @param e
     */
    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isTf1 || isTf2) {
            for (int i = 0; i < buttonNum; i++) {
                if (e.getSource() == b.get(i)) {
                    if (isTf1) {
                        tf1.setText(tf1.getText() + ((JButton) e.getSource()).getText());
                    } else if (isTf2) {
                        tf2.setText(tf2.getText() + ((JButton) e.getSource()).getText());
                    }
                    return;
                }
            }
        }
        if (e.getSource() == okbutton) {
            String q = tf1.getText();
            String w = tf2.getText();
            double x = Double.parseDouble(q);
            double y = Double.parseDouble(w);
            double area = x * y / 2;

            tf3.setText("" + area);

        } else if (e.getSource() == cancelbutton) {
            dispose();
        }

    }


    public static void main(String[] args) {
        CalTriangleArea m = new CalTriangleArea();
    }
}