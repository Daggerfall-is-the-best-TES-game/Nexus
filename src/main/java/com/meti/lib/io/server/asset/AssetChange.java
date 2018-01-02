package com.meti.lib.io.server.asset;

import com.meti.lib.io.server.Change;
import com.meti.lib.io.server.ServerLoop;
import com.meti.lib.io.server.ServerState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public abstract class AssetChange<T> implements Change {
    //TODO: convert to array
    private final List<Addition<T>> additions = new ArrayList<>();
    private final List<Deletion<T>> deletions = new ArrayList<>();

    public List<Addition<T>> getAdditions() {
        return additions;
    }

    public List<Deletion<T>> getDeletions() {
        return deletions;
    }

    @Override
    public void update(ServerState state) {
        //TODO: asset updating state

        for (ServerLoop serverLoop : state.getServerLoops()) {
            try {
                serverLoop.getClient().write(this);
                serverLoop.getClient().flush();
            } catch (IOException e) {
                state.getConsole().log(Level.WARNING, e);
            }
        }
    }

    //algorithm
    public void index(T[] oldArray, T[] newArray) {
        int counter = 0;
        for (int o = 0; o < oldArray.length; o++) {
            if (oldArray[o].equals(newArray[counter])) {
                counter++;
            } else {
                int index = -1;
                for (int k = counter; k < newArray.length; k++) {
                    if (oldArray[o].equals(newArray[k])) index = k;
                }
                if (index == -1) {
                    deletions.add(new Deletion<>(oldArray[o], o));
                } else {
                    for (int c = counter; c < index; c++) {
                        additions.add(new Addition<>(newArray[c], c));
                    }

                    counter = index + 1;
                }
            }
        }
    }
}
