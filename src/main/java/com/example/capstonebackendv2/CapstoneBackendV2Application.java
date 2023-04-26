package com.example.capstonebackendv2;

import com.example.capstonebackendv2.gui.GUI;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

@SpringBootApplication
public class CapstoneBackendV2Application {
    private static File file;
    private static FileChannel channel;
    private static FileLock lock;
    public static void main(String[] args) {
        try {
            file = new File("file.lock");
            if(file.exists()) {
                file.delete();
            }

            channel = new RandomAccessFile(file, "rw").getChannel();
            lock = channel.tryLock();

            if(lock == null) {
                channel.close();
                JOptionPane.showMessageDialog(null,"Only one server can be run at a time.","Server", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Only one server can be run at a time.");
            }
            Thread shutdown = new Thread(CapstoneBackendV2Application::unlock);
            Runtime.getRuntime().addShutdownHook(shutdown);

            SpringApplicationBuilder builder = new SpringApplicationBuilder(CapstoneBackendV2Application.class);

            builder.headless(false);

            ConfigurableApplicationContext context = builder.run(args);

            EventQueue.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                    context.getBean(GUI.class).setVisible(true);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void unlock() {
        try {
            if(lock != null) {
                lock.release();
                channel.close();
                file.delete();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
