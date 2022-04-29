package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UseCase3 extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UseCase3() {

        setTitle("登录窗体");
        setBounds(300, 200, 300, 150);
        Container cp = getContentPane();
        cp.setLayout(null);
        JLabel jl = new JLabel("用户名：");
        jl.setBounds(10, 10, 200, 18);
        final JTextField name = new JTextField();
        name.setBounds(80, 10, 150, 18);
        JLabel jl2 = new JLabel("密码：");
        jl2.setBounds(10, 50, 200, 18);
        final JPasswordField password = new JPasswordField();
        password.setBounds(80, 50, 150, 18);
        cp.add(jl);
        cp.add(name);
        cp.add(jl2);
        cp.add(password);
        JButton jb = new JButton("确定");
        ClientTCP A =new ClientTCP();
        try {
            A.setSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                String  username=name.getText();
                System.out.println(username);
                String pass= new String(password.getPassword());
                System.out.println(pass);
                try {
                    A.dengluyanzheng(username,pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (A.flag.equals("2")) {
                    JOptionPane.showMessageDialog(null, "是新用户并完成了注册");
                    return;
                }
                if (A.flag.equals("1")) {
                    JOptionPane.showMessageDialog(null, "有此用户并且完成了一次验证可以用的次数减一");
                    return;
                }
                if (A.flag.equals("3")) {
                    JOptionPane.showMessageDialog(null, "密码错误重新输入");
                    return;
                }
                if (A.flag.equals("0")) {
                    JOptionPane.showMessageDialog(null, "有此用户但是验证的次数用光已经为你重新生成新的验证数据");
                    return;
                }
                if (name.getText().trim().equals("mr")
                        && new String(password.getPassword()).trim()
                        .equals("mrsoft")) {
                    JOptionPane.showMessageDialog(null, "登录成功");
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误");
                }
            }
        });
        jb.setBounds(80, 80, 60, 18);
        cp.add(jb);

        final JButton button = new JButton();
        button.setText("重置");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // TODO 自动生成方法存根
                name.setText("");
                password.setText("");
            }
        });
        button.setBounds(150, 80, 60, 18);
        getContentPane().add(button);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new UseCase3();

    }

}
