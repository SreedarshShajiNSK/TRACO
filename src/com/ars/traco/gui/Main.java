/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ars.traco.gui;

import com.ars.traco.databeans.n414.Sectie;
import com.ars.traco.filehandler.FileClassifier;
import com.ars.traco.xlsxController.N414XlsxController;
import com.ars.traco.xmlparser.XMLn414Parser;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Sreedarshs
 */
public class Main extends javax.swing.JFrame {

    private boolean fileSelected = false;
    private boolean filepProcessed = false;
    private boolean completed = false;

    public Main() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Traco OWN tool");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            System.out.println("com.ars.trackown.gui.Main.<init>() " + ex);
        } catch (InstantiationException ex) {
            System.out.println("com.ars.trackown.gui.Main.<init>() " + ex);
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println("com.ars.trackown.gui.Main.<init>() " + ex);
        } catch (IllegalAccessException ex) {
            System.out.println("com.ars.trackown.gui.Main.<init>() " + ex);
        }

        waitingForFileThread();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ars/traco/gui/icons/icon2.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setText("Select XML");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ars/traco/gui/icons/icon3.png"))); // NOI18N
        jLabel3.setEnabled(false);

        jLabel4.setText("Waiting For File");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();

        //In response to a button click:
        int returnVal = fc.showOpenDialog(this);

        //Checks if the file is chosen
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            fileSelected = true;
            filepProcessed = false;

            //check if correct file is chosen
            String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length());
            System.out.println(extension);

            //Validating fileType
            if (!extension.equals("xml")) {
                JOptionPane.showMessageDialog(this, "Application accepts XML files.\n Found " + extension + ".", "File Type mismatch", JOptionPane.WARNING_MESSAGE);
                jLabel3.setEnabled(false);
            }

            //Classify the file category
            String fileCategory=new FileClassifier().fileClassifier(file.getAbsolutePath());
            System.out.println("Selected file of category :"+fileCategory);
            
            
            //Enable the second button
            //jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ars/trackown/gui/icons/icon1.png")));
            
            
            
            jLabel3.setEnabled(true);
            jLabel4.setText("Processing File...");
            
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName() + ".");
            System.out.println("Opening: " + file.getAbsolutePath() + ".");
            
            /*Main Business Logic*/
            
            XMLn414Parser xmlp=new XMLn414Parser(file.getAbsolutePath());
        	Sectie sectie=xmlp.getSectie();
        	N414XlsxController n414XlsxController=new N414XlsxController();
        	boolean writtenSuccessfully= n414XlsxController.handleXlsx(file.getAbsolutePath(), sectie);
        	if(writtenSuccessfully)
        	{
        		JOptionPane.showMessageDialog(this, "Data written successfully.\n\n Please see the source folder for the result.", "Success", JOptionPane.INFORMATION_MESSAGE);
        		jLabel3.setEnabled(true);
        		jLabel4.setText("Data Added");
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(this, "Unable to write data. \n See if input file is correct and the output file is not open in excel..", "Error", JOptionPane.WARNING_MESSAGE);
        		jLabel4.setText("Failed");
        	}
            /**/
            
          

        } else {
            System.out.println("Open command cancelled by user.");
            fileSelected = false;
            filepProcessed = true;//Disable the processing file from taking off
            waitingForFileThread();
        }


    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    /* Custom methods */
    public void waitingForFileThread() {
        Thread t = new Thread() {
            public void run() {
                int counter = 0;
                String text = "";
                jLabel3.setEnabled(false);

                while (!fileSelected) {
                    if (counter == 0) {
                        text = "Waiting for file";
                        counter++;
                    } else if (counter == 1) {
                        text = "Waiting for file .";
                        counter++;
                    } else if (counter == 2) {
                        text = "Waiting for file . .";
                        counter++;
                    } else if (counter == 3) {
                        text = "Waiting for file . . .";
                        counter = 0;
                    }

                    jLabel4.setText(text);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        System.err.println(ex);
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        t.start();
    }

    public void processingFileThread() {
    	jLabel3.setEnabled(true);
    	
        Thread t = new Thread() {
            public void run() {
                int counter = 0;
                String text = "";
                

                while (!filepProcessed) {
                    if (counter == 10) {
                        filepProcessed=true;
                        jLabel4.setText("File Processing Completed");
                        break;
                    }

                    jLabel4.setText("File Processing...");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        System.err.println(ex);
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        t.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
