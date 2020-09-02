package com.project.polishedlms.webServices;

public interface OnResponseInterface {
    void onApiResponse(Object response);
    void onApiFailure(String message);
}
