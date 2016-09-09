package com.anyconfusionhere.spaceshipgame;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements AdHandler, GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, PlayServices {
	private static final String TAG = "AndroidLauncher";
	private GameHelper gameHelper;
	private final static int requestCode = 1;
	private final static int requestCode_easy = 1;
	private final static int requestCode_medium = 2;
	private final static int requestCode_hard = 3;

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.signOut();
				}
			});
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		String str = "Your PlayStore Link";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}


	@Override
	public void submitScore(int highScore, String level) {
		if (isSignedIn() == true) {
			if (level.equals("easy")) {
				Games.Leaderboards.submitScore(gameHelper.getApiClient(),
						getString(R.string.leaderboard_easy), highScore);
			} else if (level.equals("medium")) {
				Games.Leaderboards.submitScore(gameHelper.getApiClient(),
						getString(R.string.leaderboard_medium), highScore);
			} else if (level.equals("hard")){
				Games.Leaderboards.submitScore(gameHelper.getApiClient(),
						getString(R.string.leaderboard_hard), highScore);
			}
		}
	}


	@Override
	public void showScore(String level) {
		if (isSignedIn() == true) {
			if (level.equals("easy")) {
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
						getString(R.string.leaderboard_easy)), requestCode);
			} else if (level.equals("medium")) {
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
						getString(R.string.leaderboard_medium)), requestCode);
			} else if (level.equals("hard")){
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
						getString(R.string.leaderboard_hard)), requestCode);
			}
		} else {
			signIn();
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}


	@Override
	protected void onStart() {
		super.onStart();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		gameHelper.onStart(this);
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"AndroidLauncher Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.anyconfusionhere.spaceshipgame/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"AndroidLauncher Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.anyconfusionhere.spaceshipgame/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		gameHelper.onStop();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.disconnect();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onConnected(Bundle bundle) {

	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}

	@Override
	public void showAds(boolean show) {
		if (show) {
			handler.sendEmptyMessage(SHOW_BANNER_AD);
		} else {
			handler.sendEmptyMessage(HIDE_BANNER_AD);
		}
	}

	@Override
	public void showInterstitialAd(final Runnable then) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (then != null) {
					interstitialAd.setAdListener(new AdListener() {
						@Override
						public void onAdClosed() {
							Gdx.app.postRunnable(then);
							AdRequest.Builder builder = new AdRequest.Builder();
							AdRequest ad = builder.build();
							interstitialAd.loadAd(ad);
						}
					});
				}
				interstitialAd.show();
			}
		});
	}

	@Override
	public void toast(final String toastText) {
		handler.post(new Runnable()
		{

			@Override
			public void run() {
				Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();

			}

		});

	}


	protected AdView adView;
	InterstitialAd interstitialAd;

	private final int SHOW_BANNER_AD = 1;
	private final int HIDE_BANNER_AD = 0;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOW_BANNER_AD:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_BANNER_AD:
					adView.setVisibility(View.GONE);
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		super.onCreate(savedInstanceState);
		FrameLayout layout = new FrameLayout(this);


		View gameView = initializeForView(new CosmicManeuver(this, this), config);
		layout.addView(gameView);

		adView = new AdView(this);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				int visibility = adView.getVisibility();
				adView.setVisibility(adView.GONE);
				adView.setVisibility(visibility);
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-6219039438068114/6659409184");
		adView.setBackgroundColor(Color.BLACK);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-6219039438068114/4469495581");

		AdRequest.Builder builder = new AdRequest.Builder();
//		builder.addTestDevice("D132599DBD704E46C49042DE579F44AE");
//		builder.addTestDevice("7EF5FFDE1FE522484C94FF1AC9A52DA5");

		FrameLayout.LayoutParams adParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.RIGHT
		);
		layout.addView(adView, adParams);
		adView.loadAd(builder.build());
		interstitialAd.loadAd(builder.build());

		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInFailed() {
			}

			@Override
			public void onSignInSucceeded() {
			}
		};

		gameHelper.setup(gameHelperListener);


		setContentView(layout);
		config.useImmersiveMode = true;
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}
}
