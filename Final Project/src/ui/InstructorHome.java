/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import DBUtil.CourseDBUtil;
import DBUtil.InstructorDBUtil;
import ui.components.CourseCell;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.*;

/**
 *
 * @author Ke
 */

public class InstructorHome extends javax.swing.JPanel {
    public InstructorHome(Instructor instructor) {
        this.setSize(1024, 768);
        initComponents();

        this.nameLabel.setText(instructor.getName());
        this.emailLabel.setText(instructor.getEmail());

        initCourses(instructor.getCoursedirectory());
    }

    private void initCourses(CourseDirectory courseDir){
        contentPanel.removeAll();


        contentPanel.setLayout(new GridLayout(0,2));

        int cell_H = contentPanel.getWidth() * 4 / 3;

//        Thread t = new Thread(() -> {
//            for (Course course : courseDir){
//                CourseCell cell = new CourseCell(course);
//                contentPanel.add(cell);
//                cell.setPreferredSize(new Dimension(contentPanel.getWidth(), cell_H));
//            }
//
//            updateUI();
//        });
//
//        t.start();


        for (Course course : courseDir){
            CourseCell cell = new CourseCell(course);
            contentPanel.add(cell);
            cell.setPreferredSize(new Dimension(contentPanel.getWidth(), cell_H));
            updateUI();
        }

        scrollPanel.setSize(contentPanel.getWidth(), cell_H * courseDir.getList().size() / 2 + 1);

        contentPanel.setSize(contentPanel.getWidth(), cell_H * courseDir.getList().size() / 2 + 1);
//
        contentPanel.setPreferredSize(new Dimension(contentPanel.getWidth(), cell_H * courseDir.getList().size() / 2 + 1));

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPanel = new javax.swing.JScrollPane();
        contentPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        sidePanel = new javax.swing.JPanel();
        logoutButton = new javax.swing.JButton();
        nameLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        courseNameField = new javax.swing.JTextField();
        courseIDField = new javax.swing.JTextField();
        line = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1024, 768));

        contentPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 833, Short.MAX_VALUE)
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1996, Short.MAX_VALUE)
        );

        scrollPanel.setViewportView(contentPanel);

        titleLabel.setFont(new java.awt.Font("Skia", 1, 36)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(99, 148, 249));
        titleLabel.setText("DashBoard");

        sidePanel.setBackground(new java.awt.Color(33, 135, 251));
        sidePanel.setPreferredSize(new java.awt.Dimension(160, 768));

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        nameLabel.setFont(new java.awt.Font("PingFang HK", 1, 18)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(255, 255, 255));
        nameLabel.setText("name");

        emailLabel.setFont(new java.awt.Font("PingFang HK", 1, 16)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(255, 255, 255));
        emailLabel.setText("email");

        jPanel1.setBackground(new java.awt.Color(33, 135, 251));

        jLabel1.setText("Course Name");

        jLabel2.setText("Course ID");

        addButton.setLabel("Add Course");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        courseNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseNameFieldActionPerformed(evt);
            }
        });

        courseIDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseIDFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(courseNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(courseIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(courseNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(courseIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(addButton)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addComponent(emailLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                        .addGap(19, 19, 19))
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidePanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(emailLabel)
                .addGap(133, 133, 133)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(line))
                    .addComponent(scrollPanel))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 2003, Short.MAX_VALUE)
                .addContainerGap(42, Short.MAX_VALUE))
            .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 2133, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
        Global.getInstance().logout();
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void courseNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseNameFieldActionPerformed

    private void courseIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseIDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseIDFieldActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        String courseName = courseNameField.getText();
        String courseID = courseIDField.getText();

        Instructor instructor = (Instructor) Global.getInstance().getUser();

        Course course = new Course(courseID, courseName, instructor.getId());
        CourseDBUtil.add(course);

        List<String> ids = new ArrayList<>();
        ids.addAll(instructor.getCourseIDs());
        ids.add(courseID);

        instructor.setCourseIDs(ids);

        InstructorDBUtil.update(instructor);

        initCourses(instructor.getCoursedirectory());

    }//GEN-LAST:event_addButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JTextField courseIDField;
    private javax.swing.JTextField courseNameField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator line;
    private javax.swing.JButton logoutButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
