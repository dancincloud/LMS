/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.components;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Assignment;
import model.Instructor;
import model.Course;
import model.File;
import model.Student;
import model.User;
import ui.FileJPanel;
import ui.InstructorCourse;
import ui.Router;

/**
 *
 * @author Ke
 */
public class FileCell extends javax.swing.JPanel {

    private File file;

    /**
     * Creates new form CourseCell
     */
    public FileCell(File file) {
        initComponents();
        this.file = file;

        titleLabel.setText(file.getName());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                click(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(160, 190, 252));
        jPanel2.setPreferredSize(new java.awt.Dimension(320, 240));

        jPanel1.setBackground(new java.awt.Color(191, 210, 251));

        titleLabel.setBackground(new java.awt.Color(188, 209, 251));
        titleLabel.setFont(new java.awt.Font("STIXNonUnicode", 1, 16)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(51, 102, 255));
        titleLabel.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        titleLabel.getAccessibleContext().setAccessibleName("titleLabel");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void click(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_click
        int i = 1;
        if(i<=3){
            java.io.File file = new java.io.File(".\\Final Project\\src\\resource\\"+i+".ppt");
            i++;
            try {
            java.awt.Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(FileJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else {
            i =1;
            java.io.File file = new java.io.File(".\\Final Project\\src\\resource\\"+i+".ppt");
            i++;
            try {
            java.awt.Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(FileJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_click


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}