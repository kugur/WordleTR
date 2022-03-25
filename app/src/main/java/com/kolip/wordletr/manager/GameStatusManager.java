package com.kolip.wordletr.manager;

import android.view.View;

import com.kolip.wordletr.R;
import com.kolip.wordletr.games.AbstractGameActivity;

public class GameStatusManager {

    private AbstractGameActivity gameActivity;

    private GameStates states;

    public GameStatusManager(AbstractGameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    //TODO(ugur) burasi statuslerin etkisi listener ile mi yapsak ???
    public void setStatus(GameStates status) {
        states = status;
        switch (status) {
            case READY:
                applyReady();
                break;
            case SECOND_CHANGE:
                applySecondChange();
                break;
            case BEFORE_FINISHED:
                applyBeforeFinished();
                break;
            case FINISHED:
                applyFinished();
            default:
                break;
        }
    }

    public GameStates getStates() {
        return states;
    }

    private void applyFinished() {
        gameActivity.getJokerView().setVisibleOfJokers(false);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
    }

    private void applyReady() {
        gameActivity.getLastRowView().setVisibility(View.INVISIBLE);
        gameActivity.getJokerView().setJokerDescription("");
        gameActivity.getJokerView().setVisibleOfJokers(true);
        gameActivity.getJokerView().setVisibilityOfGiveLife(false);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
    }

    private void applySecondChange() {
        gameActivity.getLastRowView().setVisibility(View.VISIBLE);
        gameActivity.getJokerView().setVisibilityOfGiveLife(false);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
    }

    private void applyBeforeFinished() {
        gameActivity.getJokerView().setVisibilityOfGiveLife(true);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
    }
}
