package Logging;

import Erlang.AppModule;
import Password.PasswordReminder;
import Users.User;
import Website.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener{
    private JLabel titleLabel, loginLabel, passwordLabel;
    private JTextField loginTextField;
    private JButton signInButton, signUpButton, remindThePasswordButton;
    private JPasswordField passwordField;
    private JButton okButton;

    public Login(){
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");

        titleLabel = new JLabel("Menu");
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));

        loginLabel = new JLabel("Login:");
        passwordLabel = new JLabel("Password:");

        loginTextField = new JTextField();

        passwordField = new JPasswordField();

        signInButton = new JButton("Sign in");
        signUpButton = new JButton("Sign up");
        remindThePasswordButton = new JButton("Remind the password");
        okButton = new JButton("Ok");

        signInButton.addActionListener(this);
        signUpButton.addActionListener(this);
        remindThePasswordButton.addActionListener(this);
        okButton.addActionListener(this);

        titleLabel.setBounds(250, 30, 400, 30);
        loginLabel.setBounds(130, 70, 200, 30);
        passwordLabel.setBounds(130, 110, 200, 30);

        loginTextField.setBounds(300, 70,200,30);
        passwordField.setBounds(300, 110, 200, 30);

        signInButton.setBounds(80,200,100,30);
        signUpButton.setBounds(210,200,100,30);
        remindThePasswordButton.setBounds(340, 200,200,30);

        add(titleLabel);
        add(loginLabel);
        add(loginTextField);
        add(passwordLabel);
        add(passwordField);
        add(signInButton);
        add(signUpButton);
        add(remindThePasswordButton);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e){
        if(e.getSource() == signInButton){
            String login = loginTextField.getText();
            char [] passwordChar = passwordField.getPassword();
            String password = new String (passwordChar);
            AppModule appModule = new AppModule();
            appModule.sendLoginData(login,password);
            String token = appModule.receiveString();

            if(appModule.isReceivedSuccess()){
                setVisible(false);
                dispose();
                User user = new User(login,token);
                new Home(appModule,user);
            }

            else{
                JOptionPane.showMessageDialog(okButton, "Login or password is incorrect. Try again");
                appModule.disconnect();
            }
        }

        if (e.getSource() == signUpButton){
            setVisible(false);
            dispose();
            new Registration();
        }

        if(e.getSource() == remindThePasswordButton){
            setVisible(false);
            dispose();
            new PasswordReminder();
        }
    }
}
