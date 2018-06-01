package Password;

import Erlang.AppModule;
import Logging.Login;
import Users.User;
import Website.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordChanger extends JFrame implements ActionListener {
    JLabel titleLabel, passwordLabel, confirmPasswordLabel;
    JPasswordField passwordField, confirmPasswordField;
    JButton okButton, cancelButton;
    boolean isFromHome;
    User user;

    public PasswordChanger(boolean isFromHome, User user){
        this.isFromHome=isFromHome;
        this.user=user;

        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Change password");

        titleLabel = new JLabel("Change password");
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        passwordLabel = new JLabel("New password:");
        confirmPasswordLabel = new JLabel("Confirm new password:");

        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        titleLabel.setBounds(200,30,400,30);
        passwordLabel.setBounds(130,70,200,30);
        confirmPasswordLabel.setBounds(130,110,200,30);

        passwordField.setBounds(300, 70,200,30);
        confirmPasswordField.setBounds(300,110,200,30);

        okButton.setBounds(170, 200, 100, 30 );
        cancelButton.setBounds(320,200,100,30);

        add(titleLabel);
        add(passwordLabel);
        add(confirmPasswordLabel);
        add(passwordField);
        add(confirmPasswordField);
        add(okButton);
        add(cancelButton);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==okButton){
            char[] passwordChar = passwordField.getPassword();
            char[] confirmedPasswordChar = confirmPasswordField.getPassword();
            String password = new String(passwordChar);
            String confirmedPassword = new String(confirmedPasswordChar);

            if(password.equals("")){
                JOptionPane.showMessageDialog(okButton, "Enter the password");
            }

            else if (password.equals(confirmedPassword))
            {
                AppModule appModule = new AppModule();
                appModule.sendPasswordChangerData(user.geteMail(),password,user.getToken());
                appModule.receiveIsSuccess();
                if(appModule.isReceivedSuccess()){
                    JOptionPane.showMessageDialog(okButton, "Password changed successfully");
                    setVisible(false);
                    dispose();
                    if(isFromHome) new Home(user);
                    else new Login();
                }
                else{
                    JOptionPane.showMessageDialog(okButton, "Some problem with new password occured. Try again");
                }


            }

            else
            {
                JOptionPane.showMessageDialog(okButton, "Password does not match");
            }
        }

        if (e.getSource()==cancelButton){
            setVisible(false);
            dispose();
            if(isFromHome) new Home(user);
            else new Login();
        }
    }
}
