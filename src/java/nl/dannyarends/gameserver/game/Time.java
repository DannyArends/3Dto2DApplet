package nl.dannyarends.gameserver.game;

import nl.dannyarends.generic.Utils;

public class Time {
  int day;
  int month;
  int year;
  int hours;
  int min;
  int secs;
  
  public Time(){
    hours=0;
    min=0;
    secs=0;
    day=1;
    month=1;
    year=1;
  }
  
  Time(String[] t) {
    hours=Integer.parseInt(t[0]);
    min=Integer.parseInt(t[1]);
    secs=Integer.parseInt(t[2]);
    day=Integer.parseInt(t[3]);
    month=Integer.parseInt(t[4]);
    year=Integer.parseInt(t[5]);
  }
  
  public String getTime(){
    return toTens(hours)+":"+toTens(min)+":"+toTens(secs);
  }
  
  String toTens(int i){
    if(i < 10){
      return "0"+i;
    }else{
      return String.valueOf(i);
    }
  }
  
  String toThousands(int i){
    if(i < 10){
      return "000"+i;
    }else if(i < 100){
      return "00"+i;
    }else if(i < 1000){
      return "0"+i;
    }else{
      return String.valueOf(i);
    }
  }
  
  public String getDate(){
    return toTens(day)+":"+toTens(month)+":"+toThousands(year);
  }
  
  public void addSecond(){
    if(secs < 60){
      secs++;
    }else{
      secs=0;
      if(min < 60){
      min++;
      }else{
        min=0;
        if(hours < 6){
          hours++;
        }else{
          hours=0;
          if(day < 15){
            day++;
          }else{
            day=1;
            if(month < 15){
              month++;
            }else{
              month=1;
              year++;
            }
          }
        }
      }
    }
  }

  public void set(String[] t) {
    hours=Integer.parseInt(t[0]);
    min=Integer.parseInt(t[1]);
    secs=Integer.parseInt(t[2]);
    day=Integer.parseInt(t[3]);
    month=Integer.parseInt(t[4]);
    year=Integer.parseInt(t[5]);
  }
  
}
