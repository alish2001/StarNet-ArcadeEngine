package com.starnetmc.core.modules.scoreboards;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Splitter;
 
public class StarBoard {
 
    private Player player;
    private Scoreboard scoreboard;
    private Map<Integer, String> scores = new HashMap<Integer, String>();
    private List<Team> teams = new ArrayList<Team>();
    private String displayName;
    private Objective objective;
 
    public StarBoard(Player player, String displayName) {
        this.player = player;
        this.displayName = displayName;
 
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("Starboard","dummy");
 
        objective.setDisplayName(this.displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
 
    public void setLine(int line, String text){
        scores.put(line, fixText(text));
    }
    
    public void setBlank(int line){
        setLine(line, "");
    }
 
    private String fixText(String text){
        if(text.length() > 48){
            text = text.substring(0, 47);
        }
        return text;
    }
 
    private Map.Entry<Team, String> createTeam(String text) {
        String result = "";
        if (text.length() <= 16)
            return new AbstractMap.SimpleEntry<>(null, text);
        Team team = scoreboard.registerNewTeam("text-" + scoreboard.getTeams().size());
        Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
        team.setPrefix(iterator.next());
        result = iterator.next();
        if (text.length() > 32)
            team.setSuffix(iterator.next());
        teams.add(team);
        return new AbstractMap.SimpleEntry<>(team, result);
    }
 
    public void build() {
        for(int i : scores.keySet()){
            String text = scores.get(i);
            Map.Entry<Team, String> team = createTeam(text);
            String value = team.getValue();
            if(team.getKey() != null){
                team.getKey().addEntry(value);
            }
            objective.getScore(value).setScore(i);
        }
    }
    
    public void send(){
        player.setScoreboard(scoreboard);
    }
    
    public void update(){
        if(player.getScoreboard() != null){
            for(String s : player.getScoreboard().getEntries()){
                player.getScoreboard().resetScores(s);
            }
            
            for(int i : scores.keySet()){
                String text = scores.get(i);
                Map.Entry<Team, String> team = createTeam(text);
                String value = team.getValue();
                if(team.getKey() != null){
                    team.getKey().addEntry(value);
                }
                objective.getScore(value).setScore(i);
                player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(value).setScore(i);
            }
        }
    }
    
    public void resetScores(){
        if(player.getScoreboard() !=null ) {
            for (String s : player.getScoreboard().getEntries()) {
                player.getScoreboard().resetScores(s);
            }
        }
        
        for(String s : scoreboard.getEntries()){
            scoreboard.resetScores(s);
        }
    }
 
    public void clearScores(){
        scores.clear();
        teams.clear();
    }
    
    public String getDisplayName() {
        return displayName;
    }
 
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
 
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}