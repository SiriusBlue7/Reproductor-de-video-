import java.io.*;
import java.net.*;
import java.nio.file.WatchKey;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Client{
    
    protected JTextField textField;
    private final JFrame frame;
    private JEditorPane website;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    private boolean web = false;
    private boolean pausado = false;
    
    public static void main(final String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client(args);
            }
        });
    }
    
    public Client(String[] args) {
        
        
        frame = new JFrame("Media Player");
        
        //TO DO! choose the correct arguments for the methods below. Add more method calls as necessary
        //frame.setLocation(...);
        //frame.setSize(...);
        //...
        frame.setLocation(500, 500);
        frame.setSize(800,600);
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        
        JPanel textPane = new JPanel();
        
        //Definicion del campo de texto en el que introducir la url
        //---------------------------
        textField = new JTextField(50);
        textPane.add(textField);
        contentPane.add(textPane, BorderLayout.NORTH);
        //---------------------------
        
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
        
        JPanel controlsPane = new JPanel();

        //deficinición del boton GO
        //----------------------
        JButton goButton = new JButton("Go");
        textPane.add(goButton);
        contentPane.add(textPane, BorderLayout.NORTH);

        //Handler for GO button
        //----------------------
        
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                
                
                try {
                    //Hay que preguntarle como hacer que el browser ovupe la posicion que tiene el media player
                    website = new JEditorPane(text);
                    website.setEditable(true);
                    JScrollPane jpane = new JScrollPane(website);
                    contentPane.remove(mediaPlayerComponent);
                    contentPane.add(website, BorderLayout.CENTER);
                    web = true;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                
            }
        });
        //----------------------

        //Definition of PLAY button
        //----------------------
        JButton playButton = new JButton("Play");
        controlsPane.add(playButton);
        contentPane.add(controlsPane, BorderLayout.SOUTH);
        
        //Handler for PLAY button
        //-----------------------
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // mediaPlayerComponent.getMediaPlayer().setPlaySubItems(true);
                if (web == true) {
                    contentPane.remove(website);
                    contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
                    web = false; 
                }
                if (pausado == true) {
                    mediaPlayerComponent.getMediaPlayer().pause();
                    pausado = false;
                } else {
                    String text = textField.getText();
                    mediaPlayerComponent.getMediaPlayer().playMedia(text);
                }
                
                
            }
        });
        
        //TO DO! implement a PAUSE button to pause video playback.
        //Definiution of Pause button
        JButton pauseButton = new JButton("Pause");
        controlsPane.add(pauseButton);
        contentPane.add(controlsPane, BorderLayout.SOUTH); 
        //

        //Handler for Pause button
        //-----------------------
        pauseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (pausado == true ) {
                    pausado = false;
                }else{
                    pausado = true;
                }
                mediaPlayerComponent.getMediaPlayer().pause();
            }
        });
        //
        //TO DO! implement a STOP button to stop video playback and exit the application.
        //Definition of Stop Button
        JButton stopButton = new JButton("Stop");
        controlsPane.add(stopButton);
        contentPane.add(controlsPane, BorderLayout.SOUTH);
        //
        
        //Handler for Stop button 
        //----------------------------
        stopButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                mediaPlayerComponent.getMediaPlayer().stop();
            }
        });
        //-----------------------------

    //Esto habria que mirar si se puede implementar luego
        // JButton rewindButton = new JButton("Rewind");
        // controlsPane.add(rewindButton);
        // contentPane.add(controlsPane, BorderLayout.SOUTH);

        // JButton skipButton = new JButton("Skip");
        // controlsPane.add(skipButton);
        // contentPane.add(controlsPane, BorderLayout.SOUTH);

        // Handler para ir delante y detrás
        // rewindButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         mediaPlayerComponent.getMediaPlayer().skip(-10000);
        //     }
        // });

        // skipButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         mediaPlayerComponent.getMediaPlayer().skip(10000);
        //     }
        // });

        //Makes visible the window
        frame.setContentPane(contentPane);
        frame.setVisible(true);
        
        
    }
    
}

