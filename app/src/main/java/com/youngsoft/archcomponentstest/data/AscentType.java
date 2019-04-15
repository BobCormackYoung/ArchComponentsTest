package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "AscentType_Table")
public class AscentType {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String ascentName;

    private String description;

    public AscentType(String ascentName, String description) {
        this.ascentName = ascentName;
        this.description = description;
    }

    public static AscentType[] populateAscentTypeData() {
        return new AscentType[]{
                new AscentType("Flash", "To successfully and cleanly complete a climbing route on the first attempt after having received beta of some form."),
                new AscentType("Free Solo", "Climbing without aid or protection."),
                new AscentType("Headpoint", "The practice of top-roping a hard trad route before leading it cleanly."),
                new AscentType("Pinkpoint", "To complete a lead climb without falling or resting on the rope (hangdogging), but with pre-placed protection and carabiners."),
                new AscentType("Aid-Climb", "A style of climbing in which standing on or pulling oneself up via devices attached to fixed or placed protection is used to make upward progress."),
                new AscentType("Free Base", "Climbing with your only protection being a parachute that is deployed in the event of a fall. A combination of free soloing, and BASE jumping."),
                new AscentType("On-Sight", "A clean ascent, with no prior practice or beta."),
                new AscentType("Top Rope", "A style in climbing in which the climber is securely attached to a rope which then passes up, through an anchor system at the top of the climb, and down to a belayer at the foot of the climb."),
                new AscentType("Redpoint", "Redpointing a route means free-climbing it by leading, after having practiced the route beforehand.")
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAscentName() {
        return ascentName;
    }

    public String getDescription() {
        return description;
    }

}
