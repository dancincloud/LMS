/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;


import java.awt.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.*;

/**
 * @author adinanibijiang
 */
public class MainJFrame extends javax.swing.JFrame {
    //private StudentDirectory studentDirectory = new StudentDirectory();
    private Data data = new Data();

    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initComponents();
        this.setSize(1024, 768);
        Router.getInstance(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        userProcessContainer = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        LoginJPanel = new javax.swing.JPanel();
        loginJButton = new javax.swing.JButton();
        userNameJTextField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        loginJLabel = new javax.swing.JLabel();
        logoutJButton = new javax.swing.JButton();
        accountTypeCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(150);

        userProcessContainer.setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Skia", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(99, 148, 249));
        jLabel3.setText("WELCOME");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(280, 200, 280, 120);

        userProcessContainer.add(jPanel1, "card2");

        jSplitPane1.setRightComponent(userProcessContainer);

        LoginJPanel.setBackground(new java.awt.Color(51, 153, 255));
        LoginJPanel.setLayout(null);

        loginJButton.setText("Login");
        loginJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginJButtonActionPerformed(evt);
            }
        });
        LoginJPanel.add(loginJButton);
        loginJButton.setBounds(50, 300, 92, 37);
        LoginJPanel.add(userNameJTextField);
        userNameJTextField.setBounds(10, 170, 118, 26);
        LoginJPanel.add(passwordField);
        passwordField.setBounds(10, 240, 118, 26);

        jLabel1.setFont(new java.awt.Font("PingFang HK", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("User Name");
        LoginJPanel.add(jLabel1);
        jLabel1.setBounds(10, 140, 90, 20);

        jLabel2.setFont(new java.awt.Font("PingFang HK", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Password");
        LoginJPanel.add(jLabel2);
        jLabel2.setBounds(10, 210, 70, 20);
        LoginJPanel.add(loginJLabel);
        loginJLabel.setBounds(132, 588, 0, 0);

        logoutJButton.setText("Logout");
        logoutJButton.setEnabled(false);
        logoutJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutJButtonActionPerformed(evt);
            }
        });
        LoginJPanel.add(logoutJButton);
        logoutJButton.setBounds(50, 360, 92, 38);

        accountTypeCheckBox.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        accountTypeCheckBox.setText("I'm an instructor");
        accountTypeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountTypeCheckBoxActionPerformed(evt);
            }
        });
        LoginJPanel.add(accountTypeCheckBox);
        accountTypeCheckBox.setBounds(10, 270, 130, 23);

        jSplitPane1.setLeftComponent(LoginJPanel);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutJButtonActionPerformed
        logoutJButton.setEnabled(false);
        userNameJTextField.setEnabled(true);
        passwordField.setEnabled(true);
        loginJButton.setEnabled(true);

        userNameJTextField.setText("");
        passwordField.setText("");

        userProcessContainer.removeAll();
        JPanel blankJP = new JPanel();
        userProcessContainer.add("blank", blankJP);
        CardLayout crdLyt = (CardLayout) userProcessContainer.getLayout();
        crdLyt.next(userProcessContainer);

    }//GEN-LAST:event_logoutJButtonActionPerformed


    private void loginJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginJButtonActionPerformed

        String userName = userNameJTextField.getText();
        char[] passwordCharArray = passwordField.getPassword();
        String password = String.valueOf(passwordCharArray);

        User user = data.authenticateUser(userName, password, isInstructor ? 1 : 0);

        if (user == null) {
            JOptionPane.showMessageDialog(null, "Invalid credentials");
            return;
        } else {
            Global.getInstance().login(user);
            if(user.getType() == 1){
                // Instructor
                InstructorHome home = new InstructorHome((Instructor) user);
                Router.getInstance(null).go(home);
            }else{
                StudentHome mainJPanel = new StudentHome(userProcessContainer,(Student)user);
                changeContentPane(mainJPanel);
            }
        }

//        loginJButton.setEnabled(false);
//        logoutJButton.setEnabled(true);
//        userNameJTextField.setEnabled(false);
//        passwordField.setEnabled(false);
    }//GEN-LAST:event_loginJButtonActionPerformed


    // change content
    public void changeContentPane(Container contentPane) {
        Router.getInstance(null).go(contentPane);
    }

    private boolean isInstructor = false;

    private void accountTypeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountTypeCheckBoxActionPerformed
        // TODO add your handling code here:
        isInstructor = accountTypeCheckBox.isSelected();
    }//GEN-LAST:event_accountTypeCheckBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LoginJPanel;
    private javax.swing.JCheckBox accountTypeCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton loginJButton;
    private javax.swing.JLabel loginJLabel;
    private javax.swing.JButton logoutJButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField userNameJTextField;
    private javax.swing.JPanel userProcessContainer;
    // End of variables declaration//GEN-END:variables
}
