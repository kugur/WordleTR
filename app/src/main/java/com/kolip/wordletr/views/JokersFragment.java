package com.kolip.wordletr.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kolip.wordletr.R;
import com.kolip.wordletr.dialog.GiveLetterDialog;
import com.kolip.wordletr.dialog.LetterCountJokerDialog;
import com.kolip.wordletr.dialog.WatchAdDialog;
import com.kolip.wordletr.games.AbstractGameActivity;
import com.kolip.wordletr.keyboard.CustomKeyboard;
import com.kolip.wordletr.manager.AdManager;
import com.kolip.wordletr.manager.DiamondManager;
import com.kolip.wordletr.manager.GameStates;
import com.kolip.wordletr.manager.GameStatusManager;
import com.kolip.wordletr.manager.LifeCycleManager;
import com.kolip.wordletr.manager.WordManager;

public class JokersFragment extends Fragment {
    private final int GIVEN_LETTER_COST = 2;
    private final int LETTER_COUNT_COST = 2;
    public static final int GIVE_LIFE_COST = 3;

    GiveLetterDialog giveLetterDialog;
    CustomKeyboard customKeyboard;
    WordManager wordManager;
    AdManager adManager;
    DiamondManager diamondManager;
    AbstractGameActivity gameActivity;
    GameStatusManager statusManager;
    LifeCycleManager lifeCycleManager;

    private boolean jokersVisibility = true;
    private boolean giveLifeVisible = false;

    public JokersFragment() {
        super(R.layout.view_jokers);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handleClickGiveLetterEvents();
        handleLetterCountsEvents();
        handleGiveLifeEvents();

        setVisibleOfJokers(jokersVisibility);
        setVisibilityOfGiveLife(giveLifeVisible);
    }

    private void handleClickGiveLetterEvents() {
        getView().findViewById(R.id.give_letter_joker).setOnClickListener(v -> {

            Log.d("Jokers", "give letter joker has been clicked.");
            String givenLetter = wordManager.getANotFoundLetter();

            if (!validatedEnoughDiamond(GIVEN_LETTER_COST)) return;

            if (giveLetterDialog == null) {
                giveLetterDialog = new GiveLetterDialog(givenLetter);
            } else {
                giveLetterDialog.setGivenLetter(givenLetter);
            }

            if (!givenLetter.isEmpty()) {
                lifeCycleManager.addGivenLetters(givenLetter);
                setGivenLetter(givenLetter);
                diamondManager.addDiamondScore(-GIVEN_LETTER_COST);
            }

            giveLetterDialog.show(getChildFragmentManager(), "ugur");
        });
    }

    private void handleLetterCountsEvents() {
        getView().findViewById(R.id.letter_counts_joker).setOnClickListener(v -> {
            Log.d("Jokers", "letter count  joker has been clicked.");

            if (!validatedEnoughDiamond(LETTER_COUNT_COST)) return;

            LetterCountJokerDialog letterCountJokerDialog = new LetterCountJokerDialog(wordManager.getWordCounts());
            letterCountJokerDialog.show(getChildFragmentManager(), "letterCountDialog");
            gameActivity.setJokerDescription(wordManager.getWordCounts());

            diamondManager.addDiamondScore(-LETTER_COUNT_COST);
        });
    }

    private void handleGiveLifeEvents() {
        getView().findViewById(R.id.give_life_joker).setOnClickListener(v -> {

            if (!validatedEnoughDiamond(GIVE_LIFE_COST)) return;

            statusManager.setStatus(GameStates.SECOND_CHANGE);
        });
    }

    private boolean validatedEnoughDiamond(int diamondCost) {
        if (diamondManager.getDiamondScore() < diamondCost) {
            WatchAdDialog watchAdDialog = new WatchAdDialog(adManager);
            watchAdDialog.show(getActivity().getSupportFragmentManager(), "watchAds");
            return false;
        }

        return true;
    }

    public void setDependencies(WordManager wordManager, CustomKeyboard customKeyboard, DiamondManager diamondManager,
                                AdManager adManager, GameStatusManager statusManager, AbstractGameActivity gameActivity,
                                LifeCycleManager lifeCycleManager) {
        this.wordManager = wordManager;
        this.customKeyboard = customKeyboard;
        this.diamondManager = diamondManager;
        this.adManager = adManager;
        this.statusManager = statusManager;
        this.gameActivity = gameActivity;
        this.lifeCycleManager = lifeCycleManager;
    }

    public void setVisibilityOfGiveLife(boolean visible) {
        giveLifeVisible = visible;
        if (getView() != null) {
            getView().findViewById(R.id.give_life_joker).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setVisibleOfJokers(boolean visible) {
        jokersVisibility = visible;
        if (getView() != null) {
            getView().findViewById(R.id.give_life_joker).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            getView().findViewById(R.id.letter_counts_joker).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            getView().findViewById(R.id.give_letter_joker).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setGivenLetter(String givenLetter) {
        wordManager.setNotCorrectPositionLetter(givenLetter);
        customKeyboard.setKeyStatus(givenLetter, BoxStatus.WRONG_POSITION);
    }
}
