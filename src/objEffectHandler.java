import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;


public class objEffectHandler implements GameEventListener
{
	private objGameLogic environment;
	private Map<Integer, objEntity> continuousEffects;
	public objEffectHandler(objGameLogic envi)
	{
		environment=envi;
		continuousEffects=new HashMap<Integer, objEntity>();
	}

	public void handleEffect(objCard.SecondaryType type,int effectNr, objEntity target)
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
				break;
			}
			case 3:
				environment.openSeal();
				break;
			case 4:
			{
				Random gen=new Random();
				int temp=((objPlayer)target).getCardsInPlay().size(),temp2=((objPlayer)target).getCarriedCards().size();
				int help=gen.nextInt(temp+temp2);
				if(help<temp)((objPlayer)target).discardCardFromPlay(help);
				else((objPlayer)target).discardCarriedCard(help-temp);
				break;
			}
			case 5:
			{
				Random gen=new Random();
				int n=gen.nextInt(6)+1;
				for(int i=0;i<n;i++)
				{
					int temp=((objPlayer)target).getCardsInPlay().size(),temp2=((objPlayer)target).getCarriedCards().size();
					int help=gen.nextInt(temp+temp2);
					if(help<temp)((objPlayer)target).discardCardFromPlay(help);
					else((objPlayer)target).discardCarriedCard(help-temp);
				}
				break;
			}
			case 6:
			{
				Vector<Integer> temp=((objPlayer)target).findArmor();
				for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
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
			default:
				throw new IllegalArgumentException();
			}
			removeCardFromStack();
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
				if(environment.getCurrentFight().getHelperPlayer()==null)((objMonster)target).setBonus(-1);
				addContinuousEffect(1,target);
				break;
			case 3:
				monsterBonus((objMonster)target, "Militia", -3);
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
				JOptionPane.showConfirmDialog(null, "Otworzyc pieczec?", "Otworzyc pieczec", JOptionPane.YES_NO_OPTION);
				break;
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
				addContinuousEffect(14,target);
				for(int i=0; i<environment.getPlayersNumber();i++)if(environment.getPlayer(i).getCardsInPlay().findCards("Scientist", objCard.SecondaryType.CLASS).size()>0)flag=true;
				if(!flag)((objMonster)target).increaseStrength(5);
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
				break;
			case 21:
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
				monsterBonus((objMonster)target, objCard.SecondaryType.ARMOR, -3);
				monsterBonus((objMonster)target, false, 3);
				addContinuousEffect(16,target);
				break;
			case 37:
				handleEffect(objCard.SecondaryType.MONSTER, 42, target);
				for(int i=0; i<environment.getPlayersNumber();i++)if(!environment.getPlayer(i).equals(target))environment.getPlayer(i).levelUp(1);
			case 38:
				((objPlayer)target).levelUp(-1);
			case 39:
			{
				Vector<objCard> temp=((objPlayer)target).getCardsInPlay().getStack(0);
				temp.addAll(((objPlayer)target).getCarriedCards().getStack(0));
				int help=0, index=0;
				for(int i=0;i<temp.size();i++)if(temp.get(i).getBonus()>help)index=i;
				if(index<((objPlayer)target).getCardsInPlay().size())((objPlayer)target).getCardsInPlay().removeCard(index);
				else((objPlayer)target).getCarriedCards().removeCard(index-((objPlayer)target).getCardsInPlay().size());
				break;
			}
			case 40:
				//potem
				break;
			case 41:
				handleEffect(objCard.SecondaryType.MONSTER, 38, target);
				handleEffect(objCard.SecondaryType.MONSTER, 1, target);
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
				for(int i=0;i<temp.size();i++)if(temp.size()>1&&((objPlayer)target).getCardsInPlay().getCard(i).getEffect(1)==7)((objPlayer)target).discardCardFromPlay(temp.elementAt(i));
				break;
			}
			default:
				throw new IllegalArgumentException();
			}
			break;
		case OTHER:
			switch(effectNr)
			{
			case 1:
				if(target!=null)
				{
					if(((objCard)target).getSecondaryType()==objCard.SecondaryType.DISASTER)
					{
						if(environment.getPlayedCards().lastElement().getPlayedCard()==target)
						{
							environment.getPlayedCards().remove(environment.getPlayedCards().size()-1);
						}
					}
					else throw new IllegalArgumentException();
				}
				else environment.closeSeal();
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			case 5:

				break;
			}
			break;
		case SEAL:
			break;
		default:
			switch(effectNr)
			{
			case 1:
				if(!continuousEffects.containsKey(20))environment.openSeal();
				addContinuousEffect(20, target);
				break;
			case 2:
				changeCard((objCard) target, environment.getCurrentPlayer(), "Kid", true);
				break;
			case 3:
				changeCard((objCard) target, environment.getCurrentPlayer(), "Militia", false);
				break;
			case 4:
				//urzycie procy
				break;
			case 5:
				if(target==null) environment.closeSeal();
				else for(int i=0;i<environment.getPlayedCards().size();i++)if(environment.getPlayedCards().get(i).getPlayedCard()==target)environment.getPlayedCards().remove(i);
				break;
			case 6:
				addContinuousEffect(21, target);
				break;
			case 7:
				//zaimplementowane przy odrzucaniu
				break;
			case 8:
				//zaimplementowane przy dodawaniu
			}
			break;
		}
	}
	private void changeCard(objCard target, objPlayer player, String playerClass, boolean flag)// false --->nie dla klasy true ---> tylko dla klsy
	{
		if(flag==(player.getCardsInPlay().findCards("Kid", objCard.SecondaryType.CLASS).size()==0))player.moveFromPlayToCarried(environment.getCurrentPlayer().getCardsInPlay().getCardIndex((objCard)target));
	}
	private void removeCardFromStack()
	{
		environment.discardCard(environment.getPlayedCards().remove(environment.getPlayedCards().size()-1).getPlayedCard());
	}
	public void addContinuousEffect(int i, objEntity target)
	{
		continuousEffects.put(i, target);
		continuousEffects.put(-i, null);
	}
	private void removeContinuousEffect(int i)
	{
		continuousEffects.remove(i);
		continuousEffects.remove(-i);
	}
	private void monsterBonus(objMonster target, String playerClass, int bonus)
	{
		objPlayer player1=environment.getCurrentFight().getMainPlayer();
		objPlayer player2=environment.getCurrentFight().getHelperPlayer();
		Vector<objCard>classes=new Vector<objCard>();
		if(player1.getClassCounter()==0) classes=player1.getCardsInPlay().findCards(playerClass, objCard.SecondaryType.CLASS);
		if(player2!=null)if(player2.getClassCounter()==0)classes.addAll(player2.getCardsInPlay().findCards(playerClass, objCard.SecondaryType.CLASS));
		if(classes.size()>0)
		{
			target.increaseStrength(bonus);
			target.setEffectTookPlace(true);
		}
	}
	private void monsterBonus(objMonster target, String[] playerClass, int bonus)
	{
		objPlayer player1=environment.getCurrentFight().getMainPlayer();
		objPlayer player2=environment.getCurrentFight().getHelperPlayer();
		Vector<objCard>classes=new Vector<objCard>();
		if(player1.getClassCounter()==0)for(int i=0;i<playerClass.length;i++)classes=player1.getCardsInPlay().findCards(playerClass[i], objCard.SecondaryType.CLASS);
		if(player2!=null)if(player2.getClassCounter()==0)for(int i=0;i<playerClass.length;i++)classes.addAll(player2.getCardsInPlay().findCards(playerClass[i], objCard.SecondaryType.CLASS));
		if(classes.size()>0)
		{
			target.increaseStrength(bonus);
			target.setEffectTookPlace(true);
		}
	}
	private void monsterBonus(objMonster target, objCard.SecondaryType type, int bonus)
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
		}
	}
	private void monsterBonus(objMonster target, boolean sex, int bonus)
	{
		objPlayer player2=environment.getCurrentFight().getHelperPlayer();
		boolean temp=false;
		if(environment.getCurrentFight().getMainPlayer().getSex()==sex)temp=true;
		if(player2!=null)if(player2.getSex()==sex)temp=true;
		if(temp)target.increaseStrength(bonus);
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
		case ITEMENCHANCER:
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
		case MONSTER:
			return null;

		case CLASS:
		{
			Vector<Integer>temp2=environment.getCurrentPlayer().findClass();
			if(temp2.size()>0)
			{
				for(int i=0;i<temp2.size();i++) temp.add(environment.getCurrentPlayer().getCardsInPlay().getCard(temp2.get(i)));
				return temp;
			}
			else return null;
		}
		case DISASTER:
			if(card.getEffect(0)==13)return null;
			else
			{
				for(int i=0; i<environment.getPlayersNumber();i++)temp.add(environment.getPlayer(i));
				return temp;
			}
		case ITEMENCHANCER:
			Vector<Integer>temp2=environment.getCurrentPlayer().findItemsInPlay();
			if(temp2.size()>0)
			{
				for(int i=0;i<temp2.size();i++) temp.add(environment.getCurrentPlayer().getCardsInPlay().getCard(temp2.get(i)));
			}
			return temp;
		case OTHER:
			switch(card.getEffect(card.getEffect(0)))
			{

			}
			switch(card.getEffect(card.getEffect(1)))
			{

			}
			return temp;

		}
		throw new IllegalArgumentException();
	}

	@Override
	public void gameEventOccurred(GameEvent evt)
	{
		GameEvent.EventType eventType=evt.getEventType();
		Iterator<Entry<Integer, objEntity>> iter=continuousEffects.entrySet().iterator();
		while(iter.hasNext())
		{
			Entry<Integer, objEntity> pair = iter.next();
			switch(pair.getKey())
			{
			case 1:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)if(environment.getCurrentFight().getHelperPlayer()==null)((objMonster)pair.getValue()).setBonus(-1);
				break;
			case -1:
				if(eventType==GameEvent.EventType.FIGHTOVER)
				{
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
				}
			}
		}
	}
}
