import java.awt.*;

public class objCard extends objEntity
{
	public enum Type{DOOR, TREASURE, SEAL}
	public enum SecondaryType{TWOHANDWEAPON,ONEHANDWEAPON, ARMOR, BOOTS, HAT, MONSTER,DISASTER,OTHER,OTHERITEM,CLASS,SEAL, ITEMENCHANTER}
	public enum Tag{SHARK, UNDEAD, BIG, NULL,FLAME}
	private String name="";
	private String discription="";
	private Type type;
	private SecondaryType secondaryType;
	private int effect[];
	private int levelBonus=0;
	private int rewardValue=0;
	private int treasures=0;
	private boolean isFaceDown = true;
	private Image imgCard = null;
	private int idNr;
	private Tag tag;
	private int whenToPlay;
	public objCard (int id,Type type,SecondaryType type2,Tag tag, String name, String discription,int levelBonus, int effect, int effect2,int reward, int treasures,int whenTo)
	{
		this.idNr=id;
		this.effect=new int[2];
		this.effect[0]=effect;
		this.name=name;
		this.imgCard = null;
		this.discription=discription;
		this.levelBonus=levelBonus;
		this.tag=tag;
		this.effect[1]=effect2;
		this.rewardValue=reward;
		this.treasures=treasures;
		if((type==Type.DOOR&&(type2==SecondaryType.DISASTER||type2==SecondaryType.MONSTER||type2==SecondaryType.OTHER||type2==SecondaryType.CLASS))||(type==Type.TREASURE&&type2!=SecondaryType.DISASTER&&type2!=SecondaryType.MONSTER&&type2!=SecondaryType.CLASS)||(type==Type.SEAL&&type2==SecondaryType.SEAL))
		{
			this.type=type;
			secondaryType=type2;
		}
		else throw new IllegalArgumentException("Zly typ glowny i wtórny");
	}
	public void setImgCard(Image card)
	{
		imgCard=card;
	}
	public boolean isFaceDown ()
	{
		return isFaceDown;
	}

	public void setFaceDown (boolean isFaceDown)
	{
		this.isFaceDown = isFaceDown;
	}

	public Image getImg ()
	{
		return imgCard;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String getDiscription() {
		return discription;
	}

	public int getEffect(int i) {
		return effect[i];
	}
	public String toString()
	{
		return name;
	}


	public int getBonus() {
		if(type==Type.TREASURE)
		return levelBonus;
		else throw new IllegalStateException();
	}
	public int getValue() {
		if(type==Type.TREASURE)
		return rewardValue;
		else throw new IllegalStateException();
	}
	public int getLevel() {
		if(type==Type.DOOR&&secondaryType==SecondaryType.MONSTER)
		return levelBonus;
		else throw new IllegalStateException();
	}
	public int getReward() {
		if(type==Type.DOOR&&secondaryType==SecondaryType.MONSTER)
		return rewardValue;
		else throw new IllegalStateException();
	}
	public int getTreasures() {
		if(type==Type.DOOR&&secondaryType==SecondaryType.MONSTER)
		return treasures;
		else throw new IllegalStateException();
	}

	public SecondaryType getSecondaryType() {
		return secondaryType;
	}

	public int getIdNr() {
		return idNr;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public int getWhenToPlay() {
		return whenToPlay;
	}




}