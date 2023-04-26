package com.example.capstonebackendv2.gui;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;

public class GUI extends JFrame {

    private final JPanel panel = new JPanel();
    private int archiveNumber = 0;

    @Autowired
    private Environment environment;

    public GUI() {
        setTitle("Computerized Sales And Inventory Server");
        setSize(400,200);
        setPanel();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });

        add(panel);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void setPanel() {
        try {
            InetAddress myIP = InetAddress.getLocalHost();
            JTextField ipTextField = new JTextField("SERVER IP: " + myIP.getHostAddress());
            JPanel top = new JPanel();
            JButton close = new JButton("CLOSE SERVER");
            JButton export = new JButton("Export All Data");
            JButton autoArchive = new JButton("Archive Every 1 Year");

            autoArchive.setBorder(BorderFactory.createRaisedBevelBorder());
            autoArchive.setFont(new Font("monospace",Font.BOLD,14));

            export.setBorder(BorderFactory.createRaisedBevelBorder());
            export.setFont(new Font("monospace",Font.BOLD,14));

            ipTextField.setHorizontalAlignment(JTextField.CENTER);
            ipTextField.setFont(new Font("Courier", Font.BOLD,18));
            ipTextField.setEditable(false);

            close.addActionListener(e -> {
                int num = JOptionPane.showConfirmDialog(null,"Do you want to close the server?","Server",JOptionPane.YES_NO_OPTION);
                if(num == 0) System.exit(0);
            });

            export.addActionListener(e -> doExport());
            setAutoArchive(autoArchive);

            top.setLayout(new GridLayout(1,2,10,5));
            top.add(export);
            top.add(autoArchive);
            panel.setLayout(new BorderLayout());
            panel.add(top,BorderLayout.NORTH);
            panel.add(close,BorderLayout.SOUTH);
            panel.add(ipTextField,BorderLayout.CENTER);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAutoArchive(JButton autoArchive) {
        deserialize(autoArchive);
        autoArchive.addActionListener(e -> {
            String text = autoArchive.getText();
            switch (text) {
                case "Archive Every 1 Year" -> {
                    autoArchive.setText("Archive Every 6 Months");
                    archiveNumber = 6;
                }
                case "Archive Every 6 Months" -> {
                    autoArchive.setText("Archive Every 3 Months");
                    archiveNumber = 3;
                }
                case "Archive Every 3 Months" -> {
                    autoArchive.setText("Archive Every 1 Months");
                    archiveNumber = 1;
                }
                default -> {
                    autoArchive.setText("Archive Every 1 Year");
                    archiveNumber = 0;
                }
            }
            serialize();
        });
    }

    private void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream("archive.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(archiveNumber);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void deserialize(JButton btn) {
        try {
            FileInputStream fileIn = new FileInputStream("archive.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            archiveNumber = (int) in.readObject();
            switch (archiveNumber) {
                case 1 -> btn.setText("Archive Every 1 Month");
                case 3 -> btn.setText("Archive Every 3 Months");
                case 6 -> btn.setText("Archive Every 6 Months");
                default -> btn.setText("Archive Every 1 Year");
            }
            in.close();
            fileIn.close();
        } catch (Exception e){
            serialize();
            deserialize(btn);
        }
    }

    private void doExport() {
        try {
            String documentPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            String filename = "\\hbc-all-data.sql";
            File file = new File(documentPath + filename);
            filename = createFile(file,documentPath,filename);
            if(backup(filename)) JOptionPane.showMessageDialog(null,
                    "All data is exported successfully.\nSaved path: " + documentPath + File.separator + filename);
            else JOptionPane.showMessageDialog(null,"Failed to export data.\nTry Again!!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Environment Path is not set for mysql.");
        }
    }

    private String createFile(@NotNull File file, String documentPath, String filename) throws IOException {
        int num = 1;
        while (!file.createNewFile()) {
            filename = String.format("\\hbc-all-data(%s).sql", num++);
            file = new File(documentPath + filename);
        }
        return filename;
    }

    private boolean backup(String filename) {
        String password = environment.getProperty("spring.datasource.password");
        String documentPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + filename;
        String command = "mysqldump -uroot -p" + password + " --add-drop-table --databases retail_management -r " + documentPath;

        int processComplete = 0;
        try {
            Process process = Runtime.getRuntime().exec(command);
            processComplete = process.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return processComplete == 0;
    }

    public int getArchiveNumber() {
        return archiveNumber;
    }
}
