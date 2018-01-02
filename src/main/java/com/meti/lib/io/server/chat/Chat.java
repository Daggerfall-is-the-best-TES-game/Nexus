package com.meti.lib.io.server.chat;

import com.meti.app.client.view.View;
import com.meti.lib.io.client.Client;
import com.meti.lib.io.server.command.Argument;
import com.meti.lib.io.server.command.ListCommand;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;

import static com.meti.app.Main.console;
import static com.meti.app.Main.getAppState;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class Chat implements View {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    @Override
    public void init() throws IOException {
        Client client = getAppState().getClient();

        List<?> chats = (List<?>) client.runCommand(new ListCommand(Argument.CHAT));
        chats.forEach((Consumer<Object>) o -> {
            if (o instanceof Message) {
                newChat((Message) o);
            }
        });

        new ChatTimer().start();
    }

    private void newChat(Message message) {
        output.appendText("[" + message.getName() + "]: " + message.getContent() + "\n");
    }

    @FXML
    public void nextInput() {
        try {
            String value = input.getText();
            Client client = getAppState().getClient();
            Message message = new Message(client.getState().getProperties().getProperty("username"), value);

            client.write(new ChatChange(message));
            client.flush();

            //clear the input
            input.setText("");
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    private class ChatTimer extends AnimationTimer {
        @Override
        public void handle(long now) {
            Client client = getAppState().getClient();
            if (client.hasClass(ChatChange.class)) {
                ChatChange chatChange = client.readClass(ChatChange.class);
                Message content = chatChange.getContent();
                newChat(content);
            }
        }
    }
}
