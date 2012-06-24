package org.nsesa.editor.gwt.core.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTServiceAsync {
    void getPrincipal(AsyncCallback<String> callback);
}
