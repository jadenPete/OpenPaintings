package me.jadenPete.OpenPaintings;

import java.util.ArrayList;

import org.bukkit.Art;

public class ArtQueue {

  ArrayList<Art> paintings = new ArrayList<Art>();
  
  // Add art to the queue
  public void add(Art art) {
    paintings.add(art);
  }
  
  // Get the next art selection
  public Art getNext() {
    if(paintings.size() == 0) return null;
    Art nextPainting = paintings.get(0);
    return nextPainting;
  }
  
  // See whether or not there exists another art selection
  public boolean hasNext() {
    return paintings.size() > 0;
  }
  
  // Delete the oldest art selection
  public void discardOldest() {
    if(paintings.size() > 0) paintings.remove(0);
  }
  
  // Delete the newest art selection
  public void discardNewest() {
    if(paintings.size() > 0) paintings.remove(paintings.size() - 1);
  }
  
}
