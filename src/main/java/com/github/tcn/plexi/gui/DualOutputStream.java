package com.github.tcn.plexi.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//create an outputStream based obj that writes to a JTextArea and a file
public class DualOutputStream extends OutputStream {


    private final JTextArea newOutput;
    private BufferedWriter logWritter;


    public DualOutputStream(JTextArea newOutput) {
        //set values for console output
        newOutput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        this.newOutput = newOutput;

        //set values for textFile output
        try{
            logWritter = new BufferedWriter(new FileWriter(new File("Plexi Log - " + getTimestamp()), true) );
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "Unable to create log file! - localized message: " + e.getLocalizedMessage(), "Plexi - Can't Create Log File!", JOptionPane.INFORMATION_MESSAGE);
            //for now, just close plexi.
            System.exit(-1);
        }

    }

    @Override
    public void write(int i) throws IOException {
        //write to console
        newOutput.append(String.valueOf((char) i));
        newOutput.setCaretPosition(newOutput.getDocument().getLength());

        //write to file
        logWritter.append((char) i);
        logWritter.flush();
    }

    private String getTimestamp(){
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }
}

