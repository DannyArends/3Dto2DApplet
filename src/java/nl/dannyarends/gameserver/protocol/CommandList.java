package nl.dannyarends.gameserver.protocol;

public class CommandList {

  public enum toServer{
    heatbeat("H|",""),
    login("L|",":"),
    chat("C|",""),
    movement("M|",":");
    
    String pre;
    String sep;
    
    toServer(String s,String t){
      pre=s;
      sep=t;
    }
    
    public String toString(){
      return pre;
    }
  }

  public enum fromServer{
    timestamp("T|",":"),
    login("L|",":"),
    chat("C|",""),
    players("P|",":"),
    items("I|",":"),
    objects("O|",":"),
    movement("M|",":");
    
    String pre;
    public String sep;
    fromServer(String s,String t){
      pre=s;
      sep=t;
    }
    
    public String toString(){
      return pre;
    }
  }
  
}
