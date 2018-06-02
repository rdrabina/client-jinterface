package Password;

import Erlang.AppModule;
import Logging.Login;
import Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordReminder extends JFrame implements ActionListener {
    private JLabel titleLabel, loginLabel;
    private JTextField loginTextField;
    private JButton okButton, cancelButton;
    private AppModule appModule;

    public PasswordReminder(){
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Remind password");

        titleLabel = new JLabel("Remind password");
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));

        loginLabel = new JLabel("Login:");

        loginTextField = new JTextField();

        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        titleLabel.setBounds(200,30,400,30);
        loginLabel.setBounds(130,70,200,30);

        loginTextField.setBounds(300,70,200,30);

        okButton.setBounds(170, 150, 100, 30 );
        cancelButton.setBounds(320,150,100,30);

        add(titleLabel);
        add(loginLabel);
        add(loginTextField);
        add(okButton);
        add(cancelButton);
    }


    public void actionPerformed(ActionEvent e){
        if(e.getSource()==okButton){
            String login = loginTextField.getText();
            if (login.equals("")) login =" ";
            appModule = new AppModule();
            appModule.sendLeadingQuestionQuery(login);
            String leadingQuestion = appModule.receiveString();

            if(leadingQuestion!=null){
                new LeadingQuestion(leadingQuestion, appModule,new User(login));
                setVisible(false);
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(okButton, "Login does not exist. Try again");
                loginTextField.setText("");
                appModule.disconnect();
            }
        }

        else if(e.getSource()==cancelButton){
            setVisible(false);
            dispose();
            appModule.disconnect();
            new Login();
        }
    }
}
