package Website;

import Erlang.AppModule;
import Logging.Login;
import Password.PasswordChanger;
import Users.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {
    JLabel titleLabel, textLabel;
    JButton nameAndSurnameButton, changePasswordButton, signOutButton, okButton;
    User user;

    public Home(User user){
        this.user=user;

        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Erlang jinterface website");

        titleLabel = new JLabel("Welcome to Erlang jinterface website");
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        textLabel = new JLabel("Some text");
        nameAndSurnameButton = new JButton("Get name and surname");
        changePasswordButton = new JButton("Change your password");
        signOutButton = new JButton("Sign out");
        okButton = new JButton("Ok");

        nameAndSurnameButton.addActionListener(this);
        changePasswordButton.addActionListener(this);
        signOutButton.addActionListener(this);
        okButton.addActionListener(this);

        titleLabel.setBounds(130, 30, 500, 30);
        textLabel.setBounds(250,70,400,30);

        nameAndSurnameButton.setBounds(10,70,200,30);
        changePasswordButton.setBounds(10,110,200,30);
        signOutButton.setBounds(10,150,200,30);

        add(titleLabel);
        add(textLabel);
        add(nameAndSurnameButton);
        add(changePasswordButton);
        add(signOutButton);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==nameAndSurnameButton){
            AppModule appModule = new AppModule();
            appModule.sendNameAndSurnameQuery(user.geteMail(),user.getToken());
            String nameAndSurname = appModule.receiveNameAndSurname();
            if(nameAndSurname!=null){
                JOptionPane.showMessageDialog(okButton, "You are "+nameAndSurname);
            }

            else {
                JOptionPane.showMessageDialog(okButton, "Some problem occured. Try again later");
            }

        }

        if(e.getSource()==changePasswordButton){
            setVisible(false);
            dispose();
            new PasswordChanger(true,user);
        }

        if(e.getSource()==signOutButton){
            JOptionPane.showMessageDialog(okButton, "Signed out");
            setVisible(false);
            dispose();
            new Login();
        }
    }
}
