package com.anyconfusionhere.spaceshipgame;

public interface PlayServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void submitScore(int highScore, String level);
    public void showScore(String level);
    public boolean isSignedIn();
}
