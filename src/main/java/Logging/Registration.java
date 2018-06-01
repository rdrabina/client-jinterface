package Logging;

import Erlang.AppModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Registration extends JFrame implements ActionListener {
    JLabel titleLabel, nameLabel, surnameLabel, eMailLabel, passwordLabel, confirmPasswordLabel, addressLabel, phoneNumberLabel, isNecessaryLabel, leadingQuestionLabel, answerLabel;
    JTextField nameTextField, surnameTextField, eMailTextField, addressTextField, phoneNumberTextField, answerTextField;
    JButton okButton, clearButton, cancelButton;
    JPasswordField passwordField, confirmPasswordField;
    JComboBox leadingQuestion;
    JCheckBox terms;

    public Registration()
    {
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Registration");

        titleLabel = new JLabel("Registration");
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));

        nameLabel = new JLabel("Name:");
        surnameLabel = new JLabel("Surname:");
        eMailLabel = new JLabel("E-mail*:");
        passwordLabel = new JLabel("Password*:");
        confirmPasswordLabel = new JLabel("Confirm password*:");
        addressLabel = new JLabel("Address:");
        phoneNumberLabel = new JLabel("Phone number:");
        leadingQuestionLabel = new JLabel("Leading question:");
        answerLabel = new JLabel("Answer:");
        isNecessaryLabel = new JLabel("* is necessary.");
        isNecessaryLabel.setFont(new Font("Serif", Font.BOLD, 12));

        String leadingQuestionArray[] = new String[]{"What is your first girlfriend name?", "What do you do first if you are awake?","Where were you born?"};

        nameTextField = new JTextField();
        surnameTextField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        eMailTextField = new JTextField();
        addressTextField = new JTextField();
        phoneNumberTextField = new JTextField();
        answerTextField = new JTextField();

        leadingQuestion= new JComboBox(leadingQuestionArray);

        terms = new JCheckBox("I accept the terms and conditions*");

        okButton = new JButton("Ok");
        clearButton = new JButton("Clear");
        cancelButton = new JButton("Cancel");

        okButton.addActionListener(this);
        clearButton.addActionListener(this);
        cancelButton.addActionListener(this);

        titleLabel.setBounds(150, 30, 400, 30);
        nameLabel.setBounds(80, 70, 200, 30);
        surnameLabel.setBounds(80, 110, 200, 30);
        eMailLabel.setBounds(80, 150, 200, 30);
        passwordLabel.setBounds(80, 190, 200, 30);
        confirmPasswordLabel.setBounds(80, 230, 200, 30);
        addressLabel.setBounds(80, 270, 200, 30);
        phoneNumberLabel.setBounds(80, 310, 200, 30);
        leadingQuestionLabel.setBounds(80,350,400,30);
        answerLabel.setBounds(80,390,200,30);
        isNecessaryLabel.setBounds(80, 470, 400, 30);

        nameTextField.setBounds(300, 70, 200, 30);
        surnameTextField.setBounds(300, 110, 200, 30);
        eMailTextField.setBounds(300, 150, 200, 30);
        passwordField.setBounds(300, 190, 200, 30);
        confirmPasswordField.setBounds(300, 230, 200, 30);
        addressTextField.setBounds(300, 270, 200, 30);
        phoneNumberTextField.setBounds(300, 310, 200, 30);
        answerTextField.setBounds(300, 390, 200, 30);

        leadingQuestion.setBounds(300,350,400,30);

        terms.setBounds(80, 430, 400,30);

        okButton.setBounds(150, 550, 100, 30);
        clearButton.setBounds(300, 550, 100, 30);
        cancelButton.setBounds(450,550,100,30);

        add(titleLabel);
        add(nameLabel);
        add(nameTextField);
        add(surnameLabel);
        add(surnameTextField);
        add(eMailLabel);
        add(eMailTextField);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(addressLabel);
        add(addressTextField);
        add(phoneNumberLabel);
        add(phoneNumberTextField);
        add(leadingQuestion);
        add(leadingQuestionLabel);
        add(answerLabel);
        add(answerTextField);
        add(terms);
        add(isNecessaryLabel);
        add(okButton);
        add(clearButton);
        add(cancelButton);
    }


    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == cancelButton){
            setVisible(false);
            dispose();
            new Login();
        }
        else if (e.getSource()==clearButton){
            nameTextField.setText("");
            surnameTextField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
            eMailTextField.setText("");
            addressTextField.setText("");
            phoneNumberTextField.setText("");
            answerTextField.setText("");
            terms.setSelected(false);
        }
        else if (e.getSource() == okButton) {
            if (terms.isSelected()==true) {
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                char[] passwordChar = passwordField.getPassword();
                char[] confirmedPasswordChar = confirmPasswordField.getPassword();
                String password = new String(passwordChar);
                String confirmedPassword = new String(confirmedPasswordChar);
                String eMail = eMailTextField.getText();
                String address = addressTextField.getText();
                String phoneNumber = phoneNumberTextField.getText();
                String question = (String) leadingQuestion.getSelectedItem();
                String answer = answerTextField.getText();


                if (!eMail.contains("@") || !eMail.contains(".")) {
                    JOptionPane.showMessageDialog(okButton, "Enter the valid email");
                    terms.setSelected(false);
                }

                else if (password.equals("")) {
                    JOptionPane.showMessageDialog(okButton, "Enter the password");
                }

                else if (password.equals(confirmedPassword) && eMail.contains("@") && eMail.contains(".")) {
                    AppModule appModule = new AppModule();
                    appModule.sendRegistrationData(name, surname,password,eMail,address,phoneNumber,question,answer);
                    appModule.receiveIsSuccess();
                    if(appModule.isReceivedSuccess()){
                        JOptionPane.showMessageDialog(okButton, "Registered successfully");
                        setVisible(false);
                        dispose();
                        new Login();
                    }
                    else{
                        JOptionPane.showMessageDialog(okButton, "Entered e-mail is already used. Try again.");
                    }
                }

                else {
                    JOptionPane.showMessageDialog(okButton, "Password does not match");
                    terms.setSelected(false);
                }

            }
            else {
                JOptionPane.showMessageDialog(okButton, "Please accept the terms and condition.");
            }
        }
    }
}

