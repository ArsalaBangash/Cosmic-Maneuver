package com.anyconfusionhere.spaceshipgame;

public interface AdHandler {
    public void showAds(boolean show);
    public void showInterstitialAd (Runnable then);
    public void toast(String toastText);

}
