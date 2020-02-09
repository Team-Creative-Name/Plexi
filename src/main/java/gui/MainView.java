package gui;

import discordBot.PlexiBot;
import settingsManager.Settings;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

public class MainView extends JFrame {

    //create objects to be in the GUI
    private JTextArea textArea;
    //This button will have different labels depending on current state of bot - "Start" or "Stop" - default to "Start"
    private JButton buttonState = new JButton("Start");

    public MainView() {
        //set title of window
        super("Plexi " + Settings.getVersion());

        //create textarea - console output (not editable)
        textArea = new JTextArea(50, 10);
        textArea.setEditable(false);
        PrintStream guiOut = new PrintStream(new SwingOutputStream(textArea));

        //set system output
        System.setErr(guiOut);
        System.setOut(guiOut);


        //Create GUI
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST; //I love how this uses cardinal directions instead of up/down/left/right

        //Add buttons
        add(buttonState, constraints);

        //add text box
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add(new JScrollPane(textArea), constraints);

        //add event handler for start button
        buttonState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startStopButton();
            }
        });

        //set default action to happen when closing window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set window properties
        setSize(400, 300);
        setLocationRelativeTo(null);

    }

    private void startStopButton() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (PlexiBot.isRunning()) {

                    //ask the user if they really want to shutdown
                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to shut down Plexi?");
                    if (choice == 0) {
                        PlexiBot.shutdownBot();
                        //now that the bot is off, change button label
                        buttonState.setText("Start");
                    }


                } else {
                    try {
                        PlexiBot.startBot();
                    } catch (LoginException e) {
                        System.out.println("It died");
                    }
                    //now that the bot is on, change button label
                    buttonState.setText("Stop");
                }


            }
        });
        thread.start();
    }

}
