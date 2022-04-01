package com.kolip.wordletr.manager;

import android.view.View;

import com.kolip.wordletr.R;
import com.kolip.wordletr.games.AbstractGameActivity;

public class GameStatusManager {

    private AbstractGameActivity gameActivity;

    private GameStates states;
    private LifeCycleManager lifeCycleManager;

    public GameStatusManager(AbstractGameActivity gameActivity, LifeCycleManager lifeCycleManager) {
        this.gameActivity = gameActivity;
        this.lifeCycleManager = lifeCycleManager;
    }

    public void setStatus(GameStates status) {
        states = status;
        lifeCycleManager.setStatus(status);
        switch (status) {
            case READY:
                applyReady();
                break;
            case PLAYING:
                applyPlaying();
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
//        gameActivity.getLastRowView().setVisibility(View.VISIBLE);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
    }

    private void applyReady() {
        lifeCycleManager.setSecondChangeUsed(false);
        gameActivity.getLastRowView().setVisibility(View.INVISIBLE);
//        gameActivity.setJokerDescription("");
        gameActivity.getJokerView().setVisibleOfJokers(true);
        gameActivity.getJokerView().setVisibilityOfGiveLife(false);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
    }

    private void applyPlaying() {
//        gameActivity.getLastRowView().setVisibility(View.INVISIBLE);
        gameActivity.getJokerView().setVisibleOfJokers(true);
        gameActivity.getJokerView().setVisibilityOfGiveLife(false);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
    }

    private void applySecondChange() {
        gameActivity.getLastRowView().setVisibility(View.VISIBLE);
        lifeCycleManager.setSecondChangeUsed(true);
        gameActivity.getJokerView().setVisibilityOfGiveLife(false);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
    }

    private void applyBeforeFinished() {
        gameActivity.getJokerView().setVisibilityOfGiveLife(true);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
    }
}
