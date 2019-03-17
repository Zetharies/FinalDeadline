package screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class About {

    Table table; //creates a table to store text in
    Label line1, line2, line3; //initialises each new line for text

    /**
     * construct labels and set to table 
     * @param skin 
     */
    public About(Skin skin) {
        table = new Table();
        line1 = new Label("FinalDeadline is a RPG based horror game.", skin);
        line2 = new Label("You spawn in a University looking for answers and the ability to survive.", skin);
        line3 = new Label("Make your way throughout the map solving puzzles, battling zombies and completing the quest.", skin);
        table.add(line1);
        table.row();
        table.add(line2);
        table.row();
        table.add(line3);
        //set table xy width height - centred
        this.table.setBounds((Gdx.graphics.getWidth() / 2) - (table.getPrefWidth() / 2), (Gdx.graphics.getHeight() / 2) - (table.getPrefHeight()/2),
                table.getPrefWidth(), table.getPrefHeight());
        //set labels xywidth height
        line1.setBounds((table.getWidth() / 2) - (line1.getPrefWidth() / 2), (table.getHeight() / 2) - (line1.getPrefHeight()),
                line1.getPrefWidth(), line1.getPrefHeight());
        line2.setBounds((table.getWidth() / 2) - (line2.getPrefWidth() / 2), (table.getHeight() / 2) - (line2.getPrefHeight()),
                line2.getPrefWidth(), line2.getPrefHeight());
        line3.setBounds((table.getWidth() / 2) - (line3.getPrefWidth() / 2), (table.getHeight() / 2) - (line3.getPrefHeight()),
                line3.getPrefWidth(), line3.getPrefHeight());

    }

    /**
     * get about table to display main screen
     * @return 
     */
    public Table getTable() {
        return table; //shows all text in the screen
    }
}
