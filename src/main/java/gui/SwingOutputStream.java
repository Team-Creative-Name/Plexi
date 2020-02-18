package gui;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class SwingOutputStream extends OutputStream {

    private JTextArea newOutput;

    public SwingOutputStream(JTextArea newOutput) {
        this.newOutput = newOutput;
    }

    @Override
    public void write(int i) throws IOException {
        newOutput.append(String.valueOf((char) i));
        newOutput.setCaretPosition(newOutput.getDocument().getLength());
    }
}
