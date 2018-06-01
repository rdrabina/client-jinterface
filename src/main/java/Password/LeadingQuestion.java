package Password;

import Erlang.AppModule;
import Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeadingQuestion extends JFrame implements ActionListener {
    private JLabel titleLabel, questionLabel,answerLabel;
    private JTextField answerTextField;
    private JButton okButton, cancelButton;
    private AppModule appModule;
    private User user;


    public LeadingQuestion(String question,AppModule appModule, User user){
        this.appModule=appModule;
        this.user = user;

        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Leading question");

        titleLabel = new JLabel("Leading question");
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        questionLabel = new JLabel(question);
        answerLabel = new JLabel("Answer:");

        answerTextField = new JTextField();

        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        titleLabel.setBounds(200,30,400,30);
        questionLabel.setBounds(130,70,400,30);
        answerLabel.setBounds(130,110,200,30);

        answerTextField.setBounds(300,110,200,30);

        okButton.setBounds(170, 200, 100, 30 );
        cancelButton.setBounds(320,200,100,30);

        add(titleLabel);
        add(questionLabel);
        add(answerLabel);
        add(answerTextField);
        add(okButton);
        add(cancelButton);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==okButton){
            String answer = answerTextField.getText();
            appModule.sendAnswer(answer);
            String token = appModule.receiveString();
            if(appModule.isReceivedSuccess()){
                setVisible(false);
                dispose();
                user.setToken(token);
                new PasswordChanger(false,appModule, user);
            }

            else {
                JOptionPane.showMessageDialog(okButton, "Your answer is incorrect. Try again");
            }
        }

        else if(e.getSource()==cancelButton){
            setVisible(false);
            dispose();
            appModule.disconnect();
            new PasswordReminder();
        }
    }
}
