package screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 *
 * @author User
 */
public class Credits {

    private Label programmer, name1, name2, name3, name4, name5, name6, name7, name8, name9, name10;
    Stage stage;
    Table table;
    Label.LabelStyle style;

    public Credits(Stage stage, Label.LabelStyle style, Table table) {
        this.style = style;
        this.stage = stage;
        this.table = new Table();

        table.debug();
        programmer = new Label("Programmer", style);
        name1 = new Label("ZETH OSHARODE", style);
        name2 = new Label("JACOB WILLIAMS", style);
        name3 = new Label("TAHER AHMED", style);
        name4 = new Label("REBECCA BARRETT", style);
        name5 = new Label("BHAVEN PATEL", style);
        name6 = new Label("NISHA KHATRI", style);
        name7 = new Label("AVNI SOLANKI", style);
        name8 = new Label("AREEB MOHAMMAD", style);
        name9 = new Label("VIVIAN KNIGHT", style);
        name10 = new Label("KUNAL AGARWALA", style);
        addToTable();
        this.table.setBounds((Gdx.graphics.getWidth() / 2) - (table.getPrefWidth()), (Gdx.graphics.getHeight() / 2) - (table.getPrefHeight()),
                table.getPrefWidth(), table.getPrefHeight());
    }

    private void addToTable() {
        table.add(new Label("Programmer", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name1);
        table.row();
        table.add(new Label("Programmer", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name2);
        table.row();
        table.add(new Label("Programmer", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name3);
        table.row();
        table.add(new Label("Map Designer", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name4);
        table.row();
        table.add(new Label("Map Designer", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name5);
        table.row();
        table.add(new Label("Story Line", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name6);
        table.row();
        table.add(new Label("Story Line", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name7);
        table.row();
        table.add(new Label("Coordinator", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name8);
        table.row();
        table.add(new Label("Designer", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name9);
        table.row();
        table.add(new Label("Voice Actor", style)).width(programmer.getPrefWidth() + (programmer.getPrefWidth() / 2));
        table.add(name10);
        table.row();
        table.setSize(table.getPrefWidth(), table.getPrefHeight());
    }

    public Table getTable() {
        return table;
    }
}
