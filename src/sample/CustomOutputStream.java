package sample;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by assarl on 2016-09-16.
 */
public class CustomOutputStream extends OutputStream{


        private TextArea textArea;

        public CustomOutputStream(TextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            // redirects data to the text area
            textArea.appendText(String.valueOf((char)b));
            // scrolls the text area to the end of data
        }

    }


