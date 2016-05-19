package com.example.rafaelrodrigues.myapplication;

/**
 * Created by rafael.rodrigues on 17/05/2016.
 */
public interface IGPSActivity {
    public void locationChanged(double longitude, double latitude);
    public void displayGPSSettingsDialog();
}
