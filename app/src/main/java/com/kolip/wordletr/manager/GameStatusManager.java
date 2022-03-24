package com.kolip.wordletr.manager;

import android.view.View;

import com.kolip.wordletr.R;
import com.kolip.wordletr.games.AbstractGameActivity;
import com.kolip.wordletr.views.JokersFragment;

public class GameStatusManager {

    private AbstractGameActivity gameActivity;
    private JokersFragment jokersFragment;

    private GameStates states;

    public GameStatusManager(AbstractGameActivity gameActivity, JokersFragment jokersFragment) {
        this.gameActivity = gameActivity;
        this.jokersFragment = jokersFragment;
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
        jokersFragment.setVisibleOfJokers(false);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
    }

    private void applyReady() {
        gameActivity.getLastRowView().setVisibility(View.INVISIBLE);
        jokersFragment.setJokerDescription("");
        jokersFragment.setVisibleOfJokers(true);
        jokersFragment.setVisibilityOfGiveLife(false);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
    }

    private void applySecondChange() {
        gameActivity.getLastRowView().setVisibility(View.VISIBLE);
        jokersFragment.setVisibilityOfGiveLife(false);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
    }

    private void applyBeforeFinished() {
        jokersFragment.setVisibilityOfGiveLife(true);
        gameActivity.findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
    }
}
