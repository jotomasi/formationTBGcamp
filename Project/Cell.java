package Project;

public class Cell {
    //attribute
    private boolean liveHere;
    private int lifeTime;

    //Contructors
    public Cell(){
        this.liveHere=false;
        this.lifeTime = 0;
    }

    //getters and setters
    public boolean getLive(){return this.liveHere;}
    public int getLifeTime(){return this.lifeTime;}

    public void setLive(boolean b){this.liveHere = b;}
    public void setLifeTime(boolean survive ){
        if(survive) this.lifeTime++;
        else this.lifeTime=0;
    }




}
