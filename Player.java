public class Player{
    private char team;
    private String name;
    private String code;
    private int hits;
    private int outs;
    private int strike;
    private int walks;
    private int sacrifice;
    private int hbp;
    private int errors;


    //constructor
    Player(){
        this.team = 0;
        this.name = null;
        this.code = null;
        this.hits = 0;
        this.outs = 0;
        this.strike = 0;
        this.walks = 0;
        this.sacrifice = 0;
        this.hbp = 0;
        this.errors = 0;
    }

    //overloaded constructor
    Player(char t, String n, String k){
        this.team = t;
        this.name = n;
        this.code = k;
    }

    //accessor
    public char getTeam(){return team;}
    public String getName(){return name;}
    public String getCode(){return code;}
    public int getHits(){return hits;}
    public int getOuts(){return outs;}
    public int getStrike(){return strike;}
    public int getWalks(){return walks;}
    public int getSacrifice(){return sacrifice;}
    public int getHbp(){return hbp;}

    //mutators
    public void setTeam(char t){this.team = t;}
    public void setName(String n){this.name = n;}
    public void setCode(String k){this.code = k;}
    public void setHits(int h){this.hits = h;}
    public void setOuts(int o){this.outs = o;}
    public void setStrike(int s){this.strike = s;}
    public void setWalks(int w){this.walks = w;}
    public void setSacrifice(int s){this.sacrifice = s;}
    public void setHbp(int h){this.hbp = h;}

    //increment everything by 1
    public void incrementHits(){this.hits++;}
    public void incrementOuts(){this.outs++;}
    public void incrementStrike(){this.strike++;}
    public void incrementWalks(){this.walks++;}
    public void incrementSacrifice(){this.sacrifice++;}
    public void incrementHbp(){this.hbp++;}
    public void incrementPA(){this.errors++;}

    public Player add(Player p){
        this.hits += p.hits;
        this.outs += p.outs;
        this.strike += p.strike;
        this.walks += p.walks;
        this.sacrifice += p.sacrifice;
        this.hbp += p.hbp;

        return this;
    }

    	/*
	 * A function that calculates the at bats of the player object
	 * 
	 * parameters: none
	 * return: double - the at bats
	 */
	public double atBats() {
		return (this.hits + this.outs + this.strike);
	}
	
	/*
	 * A function that calculates the batting average of the player object
	 * 
	 * parameters: none
	 * return: double - 0 if function is dividing by 0
	 * 				  - the batting average if the function is not dividing by 0
	 */
	public double battingAverage() {
		double atBats = atBats();
		
		if (atBats == 0)
			return 0;
		else {
			return (this.hits / atBats);
		}
	}
	
	/*
	 * A function that calculates the on-base percentage of the player object
	 * 
	 * parameters: none
	 * return: double - 0 if function is dividing by 0
	 * 				  - the on-base percentage if the function is not dividing by 0
	 */
	public double onBasePercentage() { //errors are plate apperances
		double plateApperances = atBats() + this.walks + this.hbp + this.sacrifice + this.errors;
		
		if(plateApperances == 0)
			return 0;
		else {
			return ((this.hits + this.walks + this.hbp) / plateApperances);
		}
	}
}