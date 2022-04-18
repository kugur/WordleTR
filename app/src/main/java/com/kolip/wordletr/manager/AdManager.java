package com.kolip.wordletr.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdManager {
    private final String REWARD_UNIT_ID = "ca-app-pub-8084690313459333/1545286851";
    private final String FULL_SCREEN_UNIT_ID = "ca-app-pub-8084690313459333/1110493383";

    private AdView mAdView;
    private RewardedAd mRewardedAd;
    private AdRequest adRequest;
    private DiamondManager diamondManager;
    private InterstitialAd mInterstitialAd;

    public AdManager(Context context, AdView banner, DiamondManager diamondManager) {
        adRequest = new AdRequest.Builder().build();
        initializeBanner(banner, context);

        this.diamondManager = diamondManager;
        initializeReward(context);
        initializeFullScreenAd(context);

    }

    private void initializeBanner(AdView banner, Context context) {
        this.mAdView = banner;
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("wordle-ads", "banner has been initialized");
            }
        });
        mAdView.loadAd(adRequest);
    }

    public void initializeFullScreenAd(Context context) {
        InterstitialAd.load(context, FULL_SCREEN_UNIT_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.d("wordle-ads", "onAdLoaded");
                        initializeCallbackForFullScreenAd();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("wordle-ads", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


    }

    private void initializeCallbackForFullScreenAd() {
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mInterstitialAd = null;
                Log.d("TAG", "The ad was shown.");
            }
        });
    }

    public void initializeReward(Context context) {

        RewardedAd.load(context, REWARD_UNIT_ID,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("wordle-ads", loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d("wordle-ads", "Ad was loaded.");
                    }
                });
    }

    public void showRewardAd(Activity activity, Runnable onAdFinished) {
        if (mRewardedAd != null) {
            mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d("wordle-ads", "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    diamondManager.addDiamondScore(rewardAmount);
                    onAdFinished.run();
                    initializeReward(activity);
                }
            });
        } else {
            Log.d("wordle-ads", "The rewarded ad wasn't ready yet.");
        }
    }

    public void showFullScreenAd(Activity activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        }
    }
}
