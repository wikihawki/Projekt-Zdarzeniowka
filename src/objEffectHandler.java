
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.sun.prism.impl.Disposer.Target;

import javafx.util.Pair;


public class objEffectHandler implements GameEventListener
{
	private int rotation;
	private objGameLogic environment;
	private Vector<Pair<Integer,objEntity>> continuousEffects;
	public objEffectHandler(objGameLogic envi)
	{
		rotation=0;
		environment=envi;
		continuousEffects=new Vector<Pair<Integer, objEntity>>();
	}
	public void handleEffect(objCard.SecondaryType type,int effectNr, objEntity target)
	{
		rotation=0;
		handleEffectRec(type, effectNr, target);
	}
	private void handleEffectRec(objCard.SecondaryType type,int effectNr, objEntity target)
	{

	switch (type)
	{
	case DISASTER:
		switch (effectNr)
		{

		case 1:
			((objPlayer)target).levelUp(-1);

			break;
		case 2:
		{
			Vector<Integer> temp=((objPlayer)target).findHat();
			for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
			handleEffectRec(type, 1, target);
			break;
		}
		case 3:
			environment.openSeal();
			break;
		case 4:
		{
			handleEffectRec(type, 3, target);
			Random gen=new Random();
			int temp=((objPlayer)target).getCardsInPlay().size(),temp2=((objPlayer)target).getCarriedCards().size();
			if(temp+temp2>0){
				int help=gen.nextInt(temp+temp2);
				if(help<temp)((objPlayer)target).discardCardFromPlay(help);
				else((objPlayer)target).discardCarriedCard(help-temp);
			}
			break;
		}
		case 5:
		{
			Random gen=new Random();
			int n=gen.nextInt(6)+1;
			for(int i=0;i<n&&((objPlayer)target).getHand().size()>0;i++)
			{
				int temp=((objPlayer)target).getHand().size();
				int help=gen.nextInt(temp);
				((objPlayer)target).discardCardfromHand(help);
			}
			break;
		}
		case 6:
		{
			Vector<Integer> temp=((objPlayer)target).findArmor();
			for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
			handleEffectRec(type, 1, target);
			break;
		}
		case 7:
		{
			Vector<Integer> temp=((objPlayer)target).findClass();
			for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
			break;
		}
		case 8:
		{
			if(target.equals(environment.getCurrentPlayer()))((objPlayer)target).endImmediately();
			else addContinuousEffect(9, target);
			break;
		}
		case 9:
		{
			Vector<Integer> temp=((objPlayer)target).getCardsInPlay().findCardsIndex(null, objCard.Tag.FLAME);
			for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
			break;
		}
		case 10:
		{
			Vector<objCard> temp=((objPlayer)target).getCardsInPlay().getStack(0);
			temp.addAll(((objPlayer)target).getCarriedCards().getStack(0));
			int help=0, index=0;
			for(int i=0;i<temp.size();i++)if(temp.get(i).getValue()>help)index=i;
			if(index<((objPlayer)target).getCardsInPlay().size())((objPlayer)target).getCardsInPlay().removeCard(index);
			else((objPlayer)target).getCarriedCards().removeCard(index-((objPlayer)target).getCardsInPlay().size());
			break;
		}
		case 11:
		{
			Vector<Integer> temp=((objPlayer)target).findBoots();
			for(int i=0;i<temp.size();i++)if(temp.size()>1&&((objPlayer)target).getCardsInPlay().getCard(i).getEffect(1)==7)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
			break;
		}
		case 12:
		{
			((objPlayer)target).changeSex();
			break;
		}
		case 13:
		{
			environment.closeSeal();
			break;
		}
		case 0:
			break;
		default:

			throw new IllegalArgumentException();
		}
		break;
	case MONSTER:
		switch (effectNr)
		{
		case 0:
			break;
		case 1:
			environment.openSeal();
			break;
		case 2:
			if(environment.getCurrentFight().getHelperPlayer()==null)((objMonster)target).setLevel(999);
			addContinuousEffect(1,target);
			break;
		case 3:
			monsterBonus((objMonster)target, "Militia", 3);
			monsterBonus((objMonster)target, true, 3);
			addContinuousEffect(2,target);
			break;
		case 4:
			environment.getCurrentFight().setHelperEscape(0);
			environment.getCurrentFight().setMainPlayerEscape(0);
			environment.getCurrentFight().setEscapeBonus(environment.getCurrentFight().getEscapeBonus()-1);
			addContinuousEffect(3,target);
			break;
		case 5:
			monsterBonus((objMonster)target, "Scientist", 4);
			addContinuousEffect(4,target);
			break;
		case 6:
		{
			objPlayer temp =environment.getCurrentFight().getMainPlayer();
			temp.moveFromPlayToCarried(temp.getCardsInPlay().findCardsIndex(null, objCard.Tag.FLAME));
			addContinuousEffect(5, target);
			break;
		}
		case 7:
			monsterBonus((objMonster)target, "Kid", -3);
			addContinuousEffect(6,target);
			break;
		case 8:
			addContinuousEffect(7,target);
			break;
		case 9:
		{
			String[]temp=new String[2];
			temp[0]="Scientist";
			temp[1]="Militia";
			monsterBonus((objMonster)target, temp, -3);
			addContinuousEffect(8,target);
			break;
		}
		case 10:
		{
			int temp=JOptionPane.showConfirmDialog(null, "Otworzyc pieczec?", "Otworzyc pieczec", JOptionPane.YES_NO_OPTION);

			if(temp==0)
			{
				environment.openSeal(environment.getCurrentPlayer());
				environment.getCurrentFight().addBonus(10);
			}
			break;
		}
		case 11:
			environment.getCurrentPlayer().moveFromPlayToCarried(environment.getCurrentPlayer().findClass());
			addContinuousEffect(10, target);
			break;
		case 12:
			//zaimplementowane w objPlayer.playCard
			break;
		case 13:
			addContinuousEffect(11, target);
			break;
		case 14:
		{
			objPlayer temp =environment.getCurrentFight().getMainPlayer();
			boolean flag=false;
			temp.moveFromPlayToCarried(temp.getCardsInPlay().findCardsIndex(null, objCard.Tag.FLAME));
			addContinuousEffect(5, target);
			monsterBonus((objMonster)target, "Scientist", -3);
			addContinuousEffect(17,target);
			for(int i=0; i<environment.getPlayersNumber();i++)if(environment.getPlayer(i).getCardsInPlay().findCards("Scientist", objCard.SecondaryType.CLASS).size()>0)flag=true;
			if(!flag)((objMonster)target).increaseStrength(5);
			addContinuousEffect(18, target);
			break;
		}
		case 15:
			monsterBonus((objMonster)target, "Blogger", -4);
			addContinuousEffect(12,target);
			break;
		case 16:
			monsterBonus((objMonster)target, objCard.SecondaryType.BOOTS, 5);
			addContinuousEffect(13,target);
			break;
		case 17:
			//zaimplementowane w objPlayer.playCard
			break;
		case 18:
			((objMonster)target).increaseStrength(5);
			addContinuousEffect(14, target);
			break;
		case 19:
			monsterBonus((objMonster)target, "Kid", -4);
			addContinuousEffect(15,target);


			break;
		case 20:
			monsterBonus((objMonster)target, "Militia", -3);
			addContinuousEffect(59,target);
			break;
		case 21:
			monsterBonus((objMonster)target, "Blogger", -3);
			addContinuousEffect(58,target);
			break;
		case 22:
			break;
		case 23:
			break;
		case 24:
			break;
		case 25:
			break;
		case 26:
			break;
		case 27:
			break;
		case 28:
			break;
		case 36:
			monsterBonus((objMonster)target, objCard.SecondaryType.ARMOR, 5);
			monsterBonus((objMonster)target, false, 3);
			addContinuousEffect(16,target);
			break;
		case 37:
			handleEffectRec(objCard.SecondaryType.MONSTER, 42, target);
			addContinuousEffect(17, target);
			break;
		case 38:
			((objPlayer)target).levelUp(-1);
		case 39:
		{
			Vector<objCard> temp=((objPlayer)target).getCardsInPlay().getStack(0);
			temp.addAll(((objPlayer)target).getCarriedCards().getStack(0));
			int help=0, index=0;
			if(temp.size()>0){
				for(int i=0;i<temp.size();i++)if(temp.get(i).getBonus()>help)
					{
						help=temp.get(i).getBonus();
						index=i;
					}
				if(index<((objPlayer)target).getCardsInPlay().size())((objPlayer)target).getCardsInPlay().removeCard(index);
				else((objPlayer)target).getCarriedCards().removeCard(index-((objPlayer)target).getCardsInPlay().size());
			}
			break;
		}
		case 40:
		{
			handleEffectRec(objCard.SecondaryType.MONSTER, 1, target);
			objPlayer player=environment.getPlayer(0);
			for(int i=1;i<4;i++)if(environment.getPlayer(i).getLevel()<player.getLevel())player=environment.getPlayer(i);
			if(player!=target){
				String massage="Niech gracz "+player+" wybierze jedn� z poni�szych:\n";
				for(int i=0;i<((objPlayer)target).getHand().size();i++)massage+=((objPlayer)target).getHand().getCard(i)+"   "+((objPlayer)target).getHand().getCard(i).getDiscription()+"\n";
				int temp=JOptionPane.showOptionDialog(null, massage, "Wybierz kt�r� karte chcesz zabrac", 0, JOptionPane.QUESTION_MESSAGE, null, ((objPlayer)target).getHand().toArray(), 0);
				player.getHand().addCard(((objPlayer)target).getHand().removeCard(temp));
			}
		}
			break;
		case 41:
			handleEffectRec(objCard.SecondaryType.MONSTER, 38, target);
			handleEffectRec(objCard.SecondaryType.MONSTER, 1, target);
			break;
		case 42:
			((objPlayer)target).die();
			break;
		case 43:
			for(int i=0; i<environment.getPlayersNumber();i++)if(!environment.getPlayer(i).equals(target))environment.getPlayer(i).drawTreasure(1);
			break;
		case 44:
		{
			Vector<Integer> temp=((objPlayer)target).findBoots();
			for(int i=0;i<temp.size();i++)if(temp.size()==1||((objPlayer)target).getCardsInPlay().getCard(i).getEffect(1)!=7)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
			if(temp.size()==0)((objPlayer)target).levelUp(-1);
			break;
		}
		case 45:
		{
			Vector<Integer> temp=((objPlayer)target).findArmor();
			for(int i=0;i<temp.size();i++)if(temp.size()==1||((objPlayer)target).getCardsInPlay().getCard(i).getEffect(1)!=7)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
			if(temp.size()==0)((objPlayer)target).levelUp(-1);
			break;
		}
		case 46:
			((objPlayer)target).levelUp(-2);
			break;
		case 47:
			handleEffectRec(objCard.SecondaryType.MONSTER, 1, target);
			Vector<Integer> temp=((objPlayer)target).findHat();
			for(int i=0;i<temp.size();i++)if(temp.size()==1||((objPlayer)target).getCardsInPlay().getCard(i).getEffect(1)!=7)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
			break;
		default:
			throw new IllegalArgumentException();
		}
		break;
	case OTHER:
	case CLASS:
		switch(effectNr)
		{
		case 8:
			if(target!=null)
			{
				if(((objCard)((objPlayedCard)target).getPlayedCard()).getSecondaryType()==objCard.SecondaryType.DISASTER)
				{
					if(environment.getPlayedCards().lastElement().getPlayedCard()==target)
					{
						environment.getPlayedCards().remove(environment.getPlayedCards().size()-1);
					}
				}
				else throw new IllegalArgumentException();
			}
			else environment.closeSeal();
			environment.discardCard(((objPlayedCard)target).getPlayedCard());
			break;
		case 1:
		{
			String[] array={"Otw�rz","Zamknij"};
			int temp=JOptionPane.showOptionDialog(null, "Wybierz czy chcesz otworzyc czy zamkn�c pieczec", null, 0, JOptionPane.QUESTION_MESSAGE, null, array, 0);

			if(temp==0) environment.openSeal();
			else environment.closeSeal();
			environment.discardCard(((objPlayedCard)target).getPlayedCard());
			break;
		}
		case 2:
		{
			objPlayedCard temp=(objPlayedCard)target;
			temp.getPlayer().getCardsInPlay().addCard(temp.getPlayer().getHand().removeCard(temp.getPlayer().getHand().getCardIndex((objCard) temp.getTarget())));
			switch(((objCard)temp.getTarget()).getSecondaryType())
			{
			case ARMOR:
				temp.getPlayer().setArmorCounter(environment.getCurrentPlayer().getArmorCounter()+1);
				break;
			case BOOTS:
				temp.getPlayer().setFootgearCounter(environment.getCurrentPlayer().getFootgearCounter()+1);
				break;
			case ONEHANDWEAPON:
				temp.getPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()+1);
				break;
			case TWOHANDWEAPON:
				temp.getPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()+2);
				break;
			case HAT:
				temp.getPlayer().setHeadgearCounter(environment.getCurrentPlayer().getHeadgearCounter()+1);
				break;
			default:
				break;
			}
			addContinuousEffect(50, target);
			break;
		}
		case 3:
			addContinuousEffect(35,((objPlayedCard)target).getPlayedCard());
			environment.discardCard(((objPlayedCard)target).getPlayedCard());

			break;
		case 4:
		{
			objPlayedCard temp=((objPlayedCard)target);
			temp.getPlayer().getCardsInPlay().addCard(temp.getPlayedCard());
			addContinuousEffect(30, target);
			break;
		}
		case 5:
			((objMonster)((objPlayedCard)target).getTarget()).increaseStrength(10);
			((objMonster)((objPlayedCard)target).getTarget()).setName("Giant Atomic"+((objMonster)((objPlayedCard)target).getTarget()).getName());
			((objMonster)((objPlayedCard)target).getTarget()).increaseTreasures(2);
			break;
		case 6:
		{
			addContinuousEffect(37, target);
			break;
		}
		case 7:
			addContinuousEffect(31, target);
			break;
		case 9:
		{
			((objMonster)((objPlayedCard)target).getTarget()).increaseStrength(-5);
			((objMonster)((objPlayedCard)target).getTarget()).setName("Shell-Socked"+((objMonster)((objPlayedCard)target).getTarget()).getName());
			((objMonster)((objPlayedCard)target).getTarget()).increaseTreasures(-1);
			break;
		}
		case 10:

			if(target.getClass()==objPlayer.class)environment.getCurrentFight().addBonus(3);
			else if(target.getClass()==objMonster.class)environment.getCurrentFight().addBonus(-3);
			else throw new IllegalArgumentException();
			break;
		case 11:
			addContinuousEffect(33, target);
			break;
		case 12:
			addContinuousEffect(34, target);
			break;
		case 13:
			((objPlayer)target).levelUp(1);
			break;
		case 14:
			//kid
			break;
		case 15:
			addContinuousEffect(60, target);
			break;
		case 16:
			addContinuousEffect(61, target);
			break;
		case 17:
		{
			((objMonster)((objPlayedCard)target).getTarget()).increaseStrength(5);
			((objMonster)((objPlayedCard)target).getTarget()).setName("Undead"+((objMonster)((objPlayedCard)target).getTarget()).getName());
			((objMonster)((objPlayedCard)target).getTarget()).increaseTreasures(1);
			break;
		}
		case 18:
		{
			((objMonster)((objPlayedCard)target).getTarget()).increaseStrength(5);
			((objMonster)((objPlayedCard)target).getTarget()).setName("Commie"+((objMonster)((objPlayedCard)target).getTarget()).getName());
			((objMonster)((objPlayedCard)target).getTarget()).increaseTreasures(1);
			break;
		}
		case 19:
		{
			((objMonster)((objPlayedCard)target).getTarget()).increaseStrength(-5);
			((objMonster)((objPlayedCard)target).getTarget()).setName("Mutated"+((objMonster)((objPlayedCard)target).getTarget()).getName());
			((objMonster)((objPlayedCard)target).getTarget()).increaseTreasures(-1);
			break;
		}
		}
		break;
	case SEAL:
		switch(effectNr)
		{
			case 1:
				for(int i=0;i<environment.getPlayersNumber();i++)if(environment.getPlayer(i).getCardsInPlay().findCards("Scientist", objCard.SecondaryType.CLASS).size()>0)environment.getPlayer(i).levelUp(-1);
				break;
			case 2:
				addContinuousEffect(40, null);
				break;
			case 3:
				for(int i=0;i<environment.getPlayersNumber();i++)if(environment.getPlayer(i).getCardsInPlay().findCards("Militia", objCard.SecondaryType.CLASS).size()>0)environment.getPlayer(i).levelUp(-1);
				break;
			case 4:
				addContinuousEffect(41, null);
				break;
			case 5:
				for(int i=0;i<environment.getPlayersNumber();i++)if(environment.getPlayer(i).getCardsInPlay().findCards("Blogger", objCard.SecondaryType.CLASS).size()>0)environment.getPlayer(i).levelUp(-1);
				break;
			case 6:
				addContinuousEffect(43, null);
				break;
			case 7:
				environment.openSeal();
				break;
		}
		break;
	default:
		switch(effectNr)
		{
		case 1:
			if(!continuousEffectContains(120))environment.openSeal();
			addContinuousEffect(120, target);
			break;
		case 2:
			if(!changeCard((objCard) target, environment.getCurrentPlayer(), "Kid", true))addContinuousEffect(23, target);
			break;
		case 3:
			if(!changeCard((objCard) target, environment.getCurrentPlayer(), "Militia", false))addContinuousEffect(24, target);
			break;
		case 4:
			//urzycie procy
			break;
		case 5:
			if(target==null) environment.closeSeal();
			else for(int i=0;i<environment.getPlayedCards().size();i++)if(environment.getPlayedCards().get(i).getPlayedCard()==target)environment.getPlayedCards().remove(i);
			break;
		case 6:
			addContinuousEffect(27, target);
			break;
		case 7:
			//zaimplementowane przy odrzucaniu
			break;
		case 8:
			//zaimplementowane przy dodawaniu
			break;
		case 9:
			if(!continuousEffectContains(35))if(!changeCard((objCard) target, environment.getCurrentPlayer(), "Militia", true))addContinuousEffect(26, target);
			break;
		case 10:
		case 11:
			//zaimplementowane w ucieczce
		case 12:
			if(!changeCard((objCard) target, environment.getCurrentPlayer(), "Kid", false))addContinuousEffect(25, target);
			break;
		case 13:
			addContinuousEffect(22, target);
			break;
		case 14:
			environment.getCurrentPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()+1);
			addContinuousEffect(32, target);
			break;
		case 15:
			addContinuousEffect(36, target);
			break;
		case 16:
			if(!changeCard((objCard) target, environment.getCurrentPlayer(), "Blogger", true))addContinuousEffect(56, target);
			break;
		case 17:
			if(!changeCard((objCard) target, environment.getCurrentPlayer(), "Blogger", false))addContinuousEffect(57, target);
			break;
		}
		break;

	}
	rotation++;
}
	private boolean changeCard(objCard target, objPlayer player, String playerClass, boolean flag)// false --->nie dla klasy true ---> tylko dla klsy
	{
		if(flag==(player.getCardsInPlay().findCards(playerClass, objCard.SecondaryType.CLASS).size()==0))
		{
			player.moveFromPlayToCarried(environment.getCurrentPlayer().getCardsInPlay().getCardIndex((objCard)target));
			return true;
		}
		return false;
	}
	private void removeCardFromStack()
	{
		environment.discardCard(environment.getPlayedCards().remove(environment.getPlayedCards().size()-1).getPlayedCard());
	}
	public void addContinuousEffect(int i, objEntity target)
	{
		continuousEffects.add(new Pair<Integer, objEntity>(i, target));
		continuousEffects.add(new Pair<Integer, objEntity>(-i, target));
	}
	private void removeContinuousEffect(int k)
	{
		for(int i=0;i<continuousEffects.size();i++)
		{
			if(continuousEffects.get(i).getKey()==k||continuousEffects.get(i).getKey()==-k)
			{
				continuousEffects.remove(i);
				i--;
			}
		}
	}
	private boolean continuousEffectContains(int k)
	{
		for(int i=0;i<continuousEffects.size();i++)
		{
			if(continuousEffects.get(i).getKey()==k)
			{
				return true;
			}
		}
		return false;
	}
	private boolean continuousEffectContains(int k, objEntity target)
	{
		for(int i=0;i<continuousEffects.size();i++)
		{
			if(continuousEffects.get(i).getKey()==k&&continuousEffects.get(i).getValue()==target)
			{
				return true;
			}
		}
		return false;
	}
	private boolean monsterBonus(objMonster target, String playerClass, int bonus)
	{
		objPlayer player1=environment.getCurrentFight().getMainPlayer();
		objPlayer player2=environment.getCurrentFight().getHelperPlayer();
		Vector<objCard>classes=new Vector<objCard>();
		classes=player1.getCardsInPlay().findCards(playerClass, objCard.SecondaryType.CLASS);
		if(player2!=null)classes.addAll(player2.getCardsInPlay().findCards(playerClass, objCard.SecondaryType.CLASS));
		if(classes.size()>0)
		{
			target.increaseStrength(bonus);
			target.setEffectTookPlace(true);
			return true;
		}
		return false;
	}
	private boolean monsterBonus(objMonster target, String[] playerClass, int bonus)
	{
		objPlayer player1=environment.getCurrentFight().getMainPlayer();
		objPlayer player2=environment.getCurrentFight().getHelperPlayer();
		Vector<objCard>classes=new Vector<objCard>();
		for(int i=0;i<playerClass.length;i++)classes=player1.getCardsInPlay().findCards(playerClass[i], objCard.SecondaryType.CLASS);
		if(player2!=null)for(int i=0;i<playerClass.length;i++)classes.addAll(player2.getCardsInPlay().findCards(playerClass[i], objCard.SecondaryType.CLASS));
		if(classes.size()>0)
		{
			target.increaseStrength(bonus);
			target.setEffectTookPlace(true);
			return true;
		}
		return false;
	}
	private boolean monsterBonus(objMonster target, objCard.SecondaryType type, int bonus)
	{
		objPlayer player1=environment.getCurrentFight().getMainPlayer();
		objPlayer player2=environment.getCurrentFight().getHelperPlayer();
		Vector<objCard>classes=new Vector<objCard>();
		classes=player1.getCardsInPlay().findCards(null, type);
		if(player2!=null)classes.addAll(player2.getCardsInPlay().findCards(null, type));
		if(classes.size()==0)
		{
			target.increaseStrength(bonus);
			target.setEffectTookPlace(true);
			return true;
		}
		return false;
	}
	private boolean monsterBonus(objMonster target, boolean sex, int bonus)
	{
		objPlayer player2=environment.getCurrentFight().getHelperPlayer();
		boolean temp=false;
		if(environment.getCurrentFight().getMainPlayer().getSex()==sex)temp=true;
		if(player2!=null)if(player2.getSex()==sex)temp=true;
		if(temp){
			target.increaseStrength(bonus);
			return true;
		}
		return false;
	}
	public Class<?> getTargetClass(objCard.SecondaryType cardType, int effectNr)
	{
		switch (cardType)
		{
		case DISASTER:
			switch (effectNr)
			{
			case 1:
			case 2:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
				return objPlayer.class;
			case 3:
			case 13:
				return null;
			default:
				break;
			}
			break;
		case MONSTER:
			break;
		case OTHER:
		case CLASS:
			switch (effectNr)
			{
			case 6:
			case 7:
			case 15:
			case 16:
				return objPlayer.class;
			case 3:
			case 13:
				return null;


			default:
				break;
			}
			break;
		default:
			break;
		}
		return null;
	}
	public boolean isLegalToPlay(objCard.SecondaryType cardType, int effectNr)
	{
		switch (cardType)
		{
		case DISASTER:
			switch (effectNr)
			{
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
				return true;
			case 13:
				if(environment.getCurrentPlayer().getMyTurnPhase()==objPlayer.TurnPhase.FIGHT)return false;
				else return true;
			default:
				break;
			}
		case ARMOR:
			break;
		case BOOTS:
			break;
		case CLASS:
			break;
		case HAT:
			break;
		case ITEMENCHANTER:
			break;
		case MONSTER:
			break;
		case ONEHANDWEAPON:
			break;
		case OTHER:
			break;
		case OTHERITEM:
			break;
		case TWOHANDWEAPON:
			break;
		default:
			break;
		}
		return false;
	}

	public Vector<objEntity> getValidTargets(objCard card)
	{
		Vector<objEntity> temp=new Vector<objEntity>();
		switch(card.getSecondaryType())
		{
		case ARMOR:
		case BOOTS:
		case HAT:
		case OTHERITEM:
		case SEAL:
		case TWOHANDWEAPON:
		case ONEHANDWEAPON:
			return null;
		case MONSTER:
			if(environment.getCurrentFight()!=null)return null;
			else return temp;

		case CLASS:
		{
			Vector<Integer>temp2=environment.getCurrentPlayer().findClass();
			if(temp2.size()>0)
			{
				for(int i=0;i<temp2.size();i++) temp.add(environment.getCurrentPlayer().getCardsInPlay().getCard(temp2.get(i)));
				if(temp2.size()<environment.getCurrentPlayer().getClassCounter())temp.add(null);
				return temp;
			}
			else return null;
		}
		case DISASTER:
			if(card.getEffect(0)==13||card.getEffect(0)==3)return null;
			else
			{
				for(int i=0; i<environment.getPlayersNumber();i++)temp.add(environment.getPlayer(i));
				return temp;
			}
		case ITEMENCHANTER:
		{
			MunchkinHand temp2=environment.getCurrentPlayer().getHand();
			if(temp2.size()>0)
			{
				for(int i=0;i<temp2.size();i++)if(temp2.getCard(i).getType()==objCard.Type.TREASURE&&temp2.getCard(i).getSecondaryType()!=objCard.SecondaryType.OTHER&&temp2.getCard(i).getSecondaryType()!=objCard.SecondaryType.ITEMENCHANTER) temp.add(temp2.getCard(i));
			}
			return temp;
		}
		case OTHER:
			switch(card.getEffect(0))
			{
			case 1:
			case 3:
				return null;
			case 2:
			{
				Vector<objCard> temp2=environment.getCurrentPlayer().getHand().getStack(0);
				for(int i=0;i<temp2.size();i++) if(((objCard)temp2.get(i)).getSecondaryType()==objCard.SecondaryType.ARMOR||((objCard)temp2.get(i)).getSecondaryType()==objCard.SecondaryType.OTHERITEM||((objCard)temp2.get(i)).getSecondaryType()==objCard.SecondaryType.HAT||((objCard)temp2.get(i)).getSecondaryType()==objCard.SecondaryType.BOOTS||((objCard)temp2.get(i)).getSecondaryType()==objCard.SecondaryType.ONEHANDWEAPON||((objCard)temp2.get(i)).getSecondaryType()==objCard.SecondaryType.TWOHANDWEAPON)temp.add(temp2.elementAt(i));
				return temp;
			}
			case 4:
				for(int i=0; i<environment.getPlayersNumber();i++)temp.add(environment.getPlayer(i));
				return temp;
			case 5:
				if(environment.getCurrentFight()!=null)for(int i=0;i<environment.getCurrentFight().getMonsters().size();i++)temp.add(environment.getCurrentFight().getMonsters().get(i));
				return temp;
			case 8:
				{
					Vector<objPlayedCard> temp2=environment.getPlayedCards();
					if(temp2.size()>0)
					{
						for(int i=0;i<temp2.size();i++) temp.add(temp2.get(i).getPlayedCard());
						temp.add(null);
						return temp;
					}
					else return null;
				}
			case 10:
				if(environment.getCurrentFight()!=null){
				temp.add(environment.getCurrentFight().getMainPlayer());
				temp.add(environment.getCurrentFight().getMonsters().get(0));
				}
				break;
			case 12:
				return null;
			case 13:
				for(int i=0; i<environment.getPlayersNumber();i++)temp.add(environment.getPlayer(i));
				return temp;
			}
			return temp;

		}
		throw new IllegalArgumentException();
	}

	@Override
	public void gameEventOccurred(GameEvent evt)
	{
		GameEvent.EventType eventType=evt.getEventType();

		for(int k=0;k<continuousEffects.size();k++)
		{
			Pair<Integer, objEntity> pair = continuousEffects.get(k);
			switch(pair.getKey())
			{
			case 1:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					if(environment.getCurrentFight().getHelperPlayer()==null)((objMonster)pair.getValue()).setLevel(999);
					else ((objMonster)pair.getValue()).setLevel(1);
				}
				break;
			case 2:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					if(!((objMonster)pair.getValue()).didEffectTookPlace())monsterBonus(((objMonster)pair.getValue()), "Militia",3);
					else if(monsterBonus(((objMonster)pair.getValue()), "Militia",0)==false)
					{
						((objMonster)pair.getValue()).increaseStrength(-3);
						((objMonster)pair.getValue()).setEffectTookPlace(false);
					}
					if(((objMonster)pair.getValue()).getBonus()%5==0||(((objMonster)pair.getValue()).didEffectTookPlace()&&((objMonster)pair.getValue()).getBonus()%5==3))monsterBonus(((objMonster)pair.getValue()),true,3);
					if(((objMonster)pair.getValue()).getBonus()%5==1||(!((objMonster)pair.getValue()).didEffectTookPlace()&&((objMonster)pair.getValue()).getBonus()%5==3))
					{
						objPlayer player2=environment.getCurrentFight().getHelperPlayer();
						boolean temp=true;
						if(environment.getCurrentFight().getMainPlayer().getSex()!=false)temp=false;
						if(player2!=null)if(player2.getSex()!=false)temp=false;
						if(temp)((objMonster)pair.getValue()).increaseStrength(-3);
					}

				}
				break;
			case 3:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					environment.getCurrentFight().setHelperEscape(0);
					environment.getCurrentFight().setMainPlayerEscape(0);
					environment.getCurrentFight().setEscapeBonus(environment.getCurrentFight().getEscapeBonus()-1);
				}
				break;
			case 4:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					if(!((objMonster)pair.getValue()).didEffectTookPlace())monsterBonus(((objMonster)pair.getValue()), "scientist",4);
					else if(monsterBonus(((objMonster)pair.getValue()), "scientist",0)==false)
					{
						((objMonster)pair.getValue()).increaseStrength(-4);
					}
				}
				break;
			case 5:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					objPlayer temp =environment.getCurrentFight().getMainPlayer();
					temp.moveFromPlayToCarried(temp.getCardsInPlay().findCardsIndex(null, objCard.Tag.FLAME));
					temp =environment.getCurrentFight().getHelperPlayer();
					if(temp!=null)temp.moveFromPlayToCarried(temp.getCardsInPlay().findCardsIndex(null, objCard.Tag.FLAME));
				}
				break;
			case 9:
				if(eventType==GameEvent.EventType.TURNSTARTED)
				{
					if(environment.getCurrentPlayer()==pair.getValue())
					{
						((objPlayer)pair.getValue()).endImmediately();
						removeContinuousEffect(pair.getKey());
						removeContinuousEffect(-pair.getKey());
						if(k>2)k-=2;
						else k=0;
					}
				}
				break;
			case 16:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					if(!((objMonster)pair.getValue()).didEffectTookPlace())monsterBonus(((objMonster)pair.getValue()),objCard.SecondaryType.ARMOR,5);
					else if(monsterBonus(((objMonster)pair.getValue()), objCard.SecondaryType.ARMOR,0)==false)
					{
						((objMonster)pair.getValue()).increaseStrength(-5);
					}
					if(((objMonster)pair.getValue()).getBonus()%5==0)monsterBonus(((objMonster)pair.getValue()),false,3);
					else
					{
						objPlayer player2=environment.getCurrentFight().getHelperPlayer();
						boolean temp=true;
						if(environment.getCurrentFight().getMainPlayer().getSex()!=true)temp=false;
						if(player2!=null)if(player2.getSex()!=true)temp=false;
						if(temp)((objMonster)pair.getValue()).increaseStrength(-3);
					}
				}
				break;
			case 17:
				if(eventType==GameEvent.EventType.FIGHTOVER)
				{
					for(int i=0; i<environment.getPlayersNumber();i++)if(!continuousEffectContains(17, environment.getPlayer(i)))environment.getPlayer(i).levelUp(1);
					removeContinuousEffect(17);
					removeContinuousEffect(-17);
					if(k>2)k-=2;
					else k=0;
					if(continuousEffectContains(17))
					{
						removeContinuousEffect(-17);
						removeContinuousEffect(17);
						k-=2;
					}

				}
				break;
			case 22:
				if(eventType==GameEvent.EventType.DSICARD)
				{
					if(evt.getTarget()==pair.getValue())
					{
						String massage="Wybierz jedn� z poni�szych:\n";
						for(int i=0;i<environment.getSealDeck().size();i++)massage+=environment.getSealDeck().getCard(i)+"   "+environment.getSealDeck().getCard(i).getDiscription()+"\n";
						int temp=JOptionPane.showOptionDialog(null, massage, "Wybierz pieczec do otwarcia", 0, JOptionPane.QUESTION_MESSAGE, null, environment.getSealDeck().toArray(), 0);
						environment.openSeal(temp);
						removeContinuousEffect(pair.getKey());
						removeContinuousEffect(-pair.getKey());
						if(k>2)k-=2;
						else k=0;
					}
				}
				break;
			case 23:
				if(eventType==GameEvent.EventType.CARDPLAYED)if(evt.getTarget()==pair.getValue()||((objCard)evt.getTarget()).getSecondaryType()==objCard.SecondaryType.CLASS)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Kid", true);
				break;
			case 24:
				if(eventType==GameEvent.EventType.CARDPLAYED)if(evt.getTarget()==pair.getValue()||((objCard)evt.getTarget()).getSecondaryType()==objCard.SecondaryType.CLASS)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Militia", false);
				break;
			case 25:
				if(eventType==GameEvent.EventType.CARDPLAYED)if(evt.getTarget()==pair.getValue()||((objCard)evt.getTarget()).getSecondaryType()==objCard.SecondaryType.CLASS)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Kid", false);
				break;
			case 26:
				if(eventType==GameEvent.EventType.CARDPLAYED)if(evt.getTarget()==pair.getValue()||((objCard)evt.getTarget()).getSecondaryType()==objCard.SecondaryType.CLASS)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Militia", true);
				break;
			case 27:
				if(eventType==GameEvent.EventType.SEVENTHSEAL)((objCard)pair.getValue()).setBonus(3);
				break;
			case 28:
				if(eventType==GameEvent.EventType.INVENTORYCHANGED)
				{
					{
						boolean flag=false;
						Vector<Integer>temp=((objPlayer)pair.getValue()).findWeapon();
						if(temp.size()<((objPlayer)pair.getValue()).getFreeHandCounter())
						{
							for(int i=0;i<temp.size();i++)if(((objPlayer)pair.getValue()).getCardsInPlay().getCard(temp.get(i)).getSecondaryType()==objCard.SecondaryType.ONEHANDWEAPON) flag=true;
							if(!flag)
							{
								removeContinuousEffect(pair.getKey());
								removeContinuousEffect(-pair.getKey());
								if(k>2)k-=2;
								else k=0;
								environment.getCurrentFight().addBonus(-3);
							}
						}
						else environment.getCurrentFight().addBonus(-3);
					}
				}
				break;
			case 30:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)if(!continuousEffectContains(129))
				{

					if(environment.getCurrentFight().getHelperPlayer()!=null)
					{
						addContinuousEffect(129, null);
						environment.getCurrentFight().addBonus(-10);
					}
				}
				if(eventType==GameEvent.EventType.FIGHTSTARTED)
				{
					environment.getCurrentFight().addBonus(5);
				}
				if(eventType==GameEvent.EventType.DSICARD)if(evt.getTarget()==((objPlayedCard)pair.getValue()).getPlayedCard())
				{
					if(!continuousEffectContains(129))environment.getCurrentFight().addBonus(5);
					else environment.getCurrentFight().addBonus(-5);
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
				break;
			case 31:
				if(eventType==GameEvent.EventType.FIGHTCHANGED||eventType==GameEvent.EventType.FIGHTSTARTED)if(!continuousEffectContains(28))if(environment.getCurrentFight().getMainPlayer()==(objPlayer)pair.getValue()||environment.getCurrentFight().getMainPlayer()==(objPlayer)pair.getValue())
				{
					boolean flag=false;
					Vector<Integer>temp=((objPlayer)pair.getValue()).findWeapon();
					if(temp.size()<((objPlayer)pair.getValue()).getFreeHandCounter())
					{
						for(int i=0;i<temp.size();i++)if(((objPlayer)pair.getValue()).getCardsInPlay().getCard(temp.get(i)).getSecondaryType()==objCard.SecondaryType.ONEHANDWEAPON) flag=true;
						if(flag)
						{
							addContinuousEffect(28, null);
							environment.getCurrentFight().addBonus(3);
						}
					}
				}
				break;
			case 32:
				if(eventType==GameEvent.EventType.DSICARD)if(evt.getTarget()==((objPlayedCard)pair.getValue()).getTarget())
				{
					if(((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard())!=-1)environment.getCurrentPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()-1);
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
				if(eventType==GameEvent.EventType.INVENTORYCHANGED)if(evt.getTarget()==((objPlayedCard)pair.getValue()).getTarget())
				{
					if(((objPlayedCard)pair.getValue()).getPlayer().getCarriedCards().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard())!=-1)environment.getCurrentPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()-1);
					if(((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard())!=-1)environment.getCurrentPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()+1);
				}
				break;
			case 33:
				break;
			case 34:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)if(continuousEffectContains(33))
				{
					environment.getCurrentFight().addLevelBonus(1);
					removeContinuousEffect(33);
					removeContinuousEffect(-33);
					removeContinuousEffect(34);
					removeContinuousEffect(-34);
					if(k>4)k-=4;
					else k=4;
				}
				break;
			case 35:
				if(eventType==GameEvent.EventType.FIGHTSTARTED||eventType==GameEvent.EventType.FIGHTCHANGED)if(!continuousEffectContains(150))
				{
					addContinuousEffect(150, null);
					environment.getCurrentFight().addBonus(5);
					for(int i=0;i<environment.getCurrentFight().getMonsters().size();i++)if(!continuousEffectContains(151,environment.getCurrentFight().getMonsters().elementAt(i)))
					{
						environment.getCurrentFight().getMonsters().elementAt(i).increaseTreasures(-1);
						addContinuousEffect(151, environment.getCurrentFight().getMonsters().elementAt(i));
					}
				}
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					for(int i=0;i<environment.getCurrentFight().getMonsters().size();i++)if(!continuousEffectContains(151,environment.getCurrentFight().getMonsters().elementAt(i)))
					{
						environment.getCurrentFight().getMonsters().elementAt(i).increaseTreasures(-1);
						addContinuousEffect(151, environment.getCurrentFight().getMonsters().elementAt(i));
					}
				}
				break;
			case 36:
				if(eventType==GameEvent.EventType.INVENTORYCHANGED)if(evt.getTarget()==((objPlayedCard)pair.getValue()).getTarget())
				{
					if(((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard())!=-1)
					{
						((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().removeCard(((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard()));
						((objPlayedCard)pair.getValue()).getPlayer().getCarriedCards().addCard(((objPlayedCard)pair.getValue()).getPlayedCard());
						gameEventOccurred(new GameEvent(evt.getSource(), evt.getEventType(), ((objPlayedCard)pair.getValue()).getPlayedCard()));
					}else if(((objPlayedCard)pair.getValue()).getPlayer().getCarriedCards().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard())!=-1)
					{
						((objPlayedCard)pair.getValue()).getPlayer().getCarriedCards().removeCard(((objPlayedCard)pair.getValue()).getPlayer().getCarriedCards().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard()));
						((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().addCard(((objPlayedCard)pair.getValue()).getPlayedCard());
						gameEventOccurred(new GameEvent(evt.getSource(), evt.getEventType(), ((objPlayedCard)pair.getValue()).getPlayedCard()));
					}
				}
				if(eventType==GameEvent.EventType.DSICARD)if(evt.getTarget()==((objPlayedCard)pair.getValue()).getTarget())
				{
					if(((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard())!=-1)
					{
						((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().removeCard(((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard()));
						environment.discardCard(((objPlayedCard)pair.getValue()).getPlayedCard());
					}else if(((objPlayedCard)pair.getValue()).getPlayer().getCarriedCards().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard())!=-1)
					{
						((objPlayedCard)pair.getValue()).getPlayer().getCarriedCards().removeCard(((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard()));
						environment.discardCard(((objPlayedCard)pair.getValue()).getPlayedCard());
					}
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
				break;
			case 37:
				if(eventType==GameEvent.EventType.DISASTER)
				{
					if(((objPlayer)pair.getValue()).getCardsInPlay().findCards("Militia",objCard.SecondaryType.CLASS).size()>0)
				{
				int temp=JOptionPane.showOptionDialog(null, "Wybierz pierwsz� karte do odrzucenia", "Militia - I was ready for that", 0, JOptionPane.QUESTION_MESSAGE, null, environment.getPlayer(environment.getPlayingPlayer()).getHand().toArray(), 0);
				if(temp==JOptionPane.CLOSED_OPTION);
				else
				{
					environment.getPlayer(environment.getPlayingPlayer()).discardCardfromHand(temp);
					temp=JOptionPane.showOptionDialog(null, "Wybierz drug� karte do odrzucenia", "Militia - I was ready for that", 0, JOptionPane.QUESTION_MESSAGE, null, environment.getPlayer(environment.getPlayingPlayer()).getHand().toArray(), 0);
					if(temp==JOptionPane.CLOSED_OPTION);
					else
					{
						environment.getPlayer(environment.getPlayingPlayer()).discardCardfromHand(temp);
						environment.getPlayedCards().remove(environment.getPlayedCards().size()-1);
					}
				}
				}
					else
					{
						removeContinuousEffect(pair.getKey());
						removeContinuousEffect(-pair.getKey());
						if(k>2)k-=2;
						else k=0;
					}
				}
				break;
			case 40:
				if(eventType==GameEvent.EventType.FIGHTCHANGED||eventType==GameEvent.EventType.FIGHTSTARTED)
				{
					if(environment.getCurrentFight().getMainPlayer().getCardsInPlay().findCards("Scientist", objCard.SecondaryType.CLASS).size()>0)if(!continuousEffectContains(140,environment.getCurrentFight().getMainPlayer()))
					{
						addContinuousEffect(140, environment.getCurrentFight().getMainPlayer());
						environment.getCurrentFight().addBonus(-3);
						
					}
					if(environment.getCurrentFight().getHelperPlayer()!=null)if(environment.getCurrentFight().getHelperPlayer().getCardsInPlay().findCards("Scientist", objCard.SecondaryType.CLASS).size()>0)if(!continuousEffectContains(140,environment.getCurrentFight().getHelperPlayer()))
					{
						addContinuousEffect(140, environment.getCurrentFight().getHelperPlayer());
						environment.getCurrentFight().addBonus(-3);
						
					}
				}
				break;
			case 41:
				if(eventType==GameEvent.EventType.FIGHTCHANGED||eventType==GameEvent.EventType.FIGHTSTARTED)
				{
					if(environment.getCurrentFight().getMainPlayer().getCardsInPlay().findCards("Militia", objCard.SecondaryType.CLASS).size()>0)if(!continuousEffectContains(140,environment.getCurrentFight().getMainPlayer()))
					{
						addContinuousEffect(140, environment.getCurrentFight().getMainPlayer());
						environment.getCurrentFight().addBonus(-3);
						
					}
					if(environment.getCurrentFight().getHelperPlayer()!=null)if(environment.getCurrentFight().getHelperPlayer().getCardsInPlay().findCards("Militia", objCard.SecondaryType.CLASS).size()>0)if(!continuousEffectContains(140,environment.getCurrentFight().getHelperPlayer()))
					{
						addContinuousEffect(140, environment.getCurrentFight().getHelperPlayer());
						environment.getCurrentFight().addBonus(-3);
						
					}
				}
				break;
			case 42:
				if(eventType==GameEvent.EventType.TURNSTARTED)if(pair.getValue()==evt.getTarget())
				{
					((objPlayer)pair.getValue()).drawDoor(4);
					((objPlayer)pair.getValue()).drawTreasure(4);
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
				break;
			case 43:
				if(eventType==GameEvent.EventType.FIGHTCHANGED||eventType==GameEvent.EventType.FIGHTSTARTED)
				{
					if(environment.getCurrentFight().getMainPlayer().getCardsInPlay().findCards("Blogger", objCard.SecondaryType.CLASS).size()>0)if(!continuousEffectContains(140,environment.getCurrentFight().getMainPlayer()))
					{
						addContinuousEffect(140, environment.getCurrentFight().getMainPlayer());
						environment.getCurrentFight().addBonus(-3);
						
					}
					if(environment.getCurrentFight().getHelperPlayer()!=null)if(environment.getCurrentFight().getHelperPlayer().getCardsInPlay().findCards("Blogger", objCard.SecondaryType.CLASS).size()>0)if(!continuousEffectContains(140,environment.getCurrentFight().getHelperPlayer()))
					{
						addContinuousEffect(140, environment.getCurrentFight().getHelperPlayer());
						environment.getCurrentFight().addBonus(-3);
						
					}
				}
			case 50:
			if(eventType==GameEvent.EventType.INVENTORYCHANGED)if(evt.getTarget()==((objPlayedCard)pair.getValue()).getTarget())
			{
				int n=((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().getCardIndex(((objPlayedCard)pair.getValue()).getPlayedCard());
				if(n!=-1)
				{
					environment.discardCard(((objPlayedCard)pair.getValue()).getPlayer().getCardsInPlay().removeCard(n));
				}
				removeContinuousEffect(pair.getKey());
				removeContinuousEffect(-pair.getKey());
				if(k>2)k-=2;
				else k=0;
			}
			case 56:
				if(eventType==GameEvent.EventType.CARDPLAYED)if(evt.getTarget()==pair.getValue()||((objCard)evt.getTarget()).getSecondaryType()==objCard.SecondaryType.CLASS)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Blogger", true);
				break;
			case 57:
				if(eventType==GameEvent.EventType.CARDPLAYED)if(evt.getTarget()==pair.getValue()||((objCard)evt.getTarget()).getSecondaryType()==objCard.SecondaryType.CLASS)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Blogger", false);
				break;
			case 58:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					if(!((objMonster)pair.getValue()).didEffectTookPlace())monsterBonus(((objMonster)pair.getValue()), "Blogger",-3);
					else if(monsterBonus(((objMonster)pair.getValue()), "Blogger",0)==false)
					{
						((objMonster)pair.getValue()).increaseStrength(3);
					}
				}
				break;
			case 59:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					if(!((objMonster)pair.getValue()).didEffectTookPlace())monsterBonus(((objMonster)pair.getValue()), "Militia",-3);
					else if(monsterBonus(((objMonster)pair.getValue()), "Militia",0)==false)
					{
						((objMonster)pair.getValue()).increaseStrength(3);
					}
				}
				break;
			case 60:
				if(eventType==GameEvent.EventType.SEALOPEN)((objPlayer)pair.getValue()).drawDoor(1);
				break;
			case 61:
				if(eventType==GameEvent.EventType.LEVELDOWN)if(evt.getTarget()==pair.getValue())((objPlayer)pair.getValue()).drawTreasure(1);
				break;
			case -1:
			case -2:
			case -3:
			case -4:
			case -5:
			case -16:
			case -59:
				if(eventType==GameEvent.EventType.FIGHTOVER||(eventType==GameEvent.EventType.FIGHTCHANGED&&evt.getTarget()==pair.getValue()))
				{
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
				break;
			case -23:
			case -24:
			case -25:
			case -26:
			case -60:
			case -61:
			case -56:
			case -58:
			case -57:
				if((eventType==GameEvent.EventType.DSICARD&&evt.getTarget()==pair.getValue()))
				{
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
			break;
			case -35:
				if(eventType==GameEvent.EventType.SEALOPEN&&eventType==GameEvent.EventType.SEALOPEN)
				{
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
				break;
			case -28:
			case -31:
				if(eventType==GameEvent.EventType.FIGHTOVER||(eventType==GameEvent.EventType.DSICARD&&evt.getTarget()==pair.getValue()))
				{
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
				break;
			case -33:
			case -34:
				if(eventType==GameEvent.EventType.FIGHTOVER)
				{
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
				break;
			case -40:
			case -41:
				if(eventType==GameEvent.EventType.SEALOPEN&&eventType==GameEvent.EventType.SEALOPEN)
				{
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
					if(k>2)k-=2;
					else k=0;
				}
			}
		}
	}
}
