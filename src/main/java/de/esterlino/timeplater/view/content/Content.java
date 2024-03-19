package de.esterlino.timeplater.view.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Content {
    public static final Map<Object, List<ContentListener>> listenerMap = new HashMap<>();

    public Object getContent();

    public void _setContent(final Object content);
    public default void setContent(final Object content) {
        _setContent(content);
        fireContentChanged(new ContentEvent(this, content));
    }

    public default void addContentListener(final ContentListener listener) {
        List<ContentListener> listeners;
        
        if (!(listenerMap.containsKey(this))) {
            listeners = new ArrayList<>();
            listenerMap.put(this, listeners);
        } else {
            listeners = listenerMap.get(this);
        }
        
        listeners.add(listener);
    }

    public default void removeContentListener(final ContentListener listener) {
        if (!(listenerMap.containsKey(this))) {
            return;
        }
        
        List<ContentListener> listeners = listenerMap.get(this);
        listeners.remove(listener);
    }

    public default void fireContentChanged(final ContentEvent ce) {
        List<ContentListener> listeners = listenerMap.get(this);
        if (listeners == null) {
            return;
        }
        
        for (ContentListener listener : listeners) {
            listener.onContentChanged(ce);
        }
    }
}
