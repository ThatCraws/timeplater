package de.esterlino.timeplater.view.content;

public class ContentEvent {
    private Object source;
    private Object content;

    public ContentEvent(final Object source, final Object content) {
        this.source = source;
        this.content = content;
    }

    public Object getSource() {
        return source;
    }

    public Object getContent() {
        return content;
    }
}
