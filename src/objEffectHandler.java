
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.sun.prism.impl.Disposer.Target;

import javafx.util.Pair;


public class objEffectHandler implements GameEventListener
{
	private objGameLogic environment;
	private Vector<Pair<Integer,objEntity>> continuousEffects;
	public objEffectHandler(objGameLogic envi)
	{
		environment=envi;
		continuousEffects=new Vector<Pair<Integer, objEntity>>();
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
			{
				int temp=JOptionPane.showConfirmDialog(null, "Otworzyc pieczec?", "Otworzyc pieczec", JOptionPane.YES_NO_OPTION);
				System.out.print("wybral opcje ");
				System.out.print(temp);
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
				// TODO: poprawic ten efekt bo bedzie dzialal Ÿle//wstepnie zrobione, ale przydalo by sie jeszcze poprawic
				handleEffect(objCard.SecondaryType.MONSTER, 42, target);
				for(int i=0; i<environment.getPlayersNumber();i++)if(!environment.getPlayer(i).equals(environment.getCurrentFight().getMainPlayer())&&!environment.getPlayer(i).equals(environment.getCurrentFight().getMainPlayer()))environment.getPlayer(i).levelUp(1);
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
			case 8:
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
			case 1:
			{
				String[] array={"Otwórz","Zamknij"};
				int temp=JOptionPane.showInternalOptionDialog(null, "Wybierz czy chcesz otworzyc czy zamkn¹c pieczec", null, 0, JOptionPane.QUESTION_MESSAGE, null, array, 0);
				System.out.print("wybral opcje ");
				System.out.println(temp);
				if(temp==0) environment.openSeal();
				else environment.closeSeal();
				break;
			}
			case 2:
				environment.getCurrentPlayer().getCardsInPlay().addCard(environment.getCurrentPlayer().getHand().removeCard(environment.getCurrentPlayer().getHand().getCardIndex((objCard)target)));
				switch(((objCard)target).getSecondaryType())
				{
				case ARMOR:
					environment.getCurrentPlayer().setArmorCounter(environment.getCurrentPlayer().getArmorCounter()+1);
					break;
				case BOOTS:
					environment.getCurrentPlayer().setFootgearCounter(environment.getCurrentPlayer().getFootgearCounter()+1);
					break;
				case ONEHANDWEAPON:
					environment.getCurrentPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()+1);
					break;
				case TWOHANDWEAPON:
					environment.getCurrentPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()+2);
					break;
				case HAT:
					environment.getCurrentPlayer().setHeadgearCounter(environment.getCurrentPlayer().getHeadgearCounter()+1);
					break;
				default:
					break;

				}
				break;
			case 3:
				addContinuousEffect(35,null);
				break;
			case 4:
				addContinuousEffect(30, target);
				break;
			case 5:
				((objMonster)target).increaseStrength(10);
				((objMonster)target).setName("Giant Atomic"+((objMonster)target).getName());
				((objMonster)target).increaseTreasures(2);
				break;
			case 6:
			{
				int temp=JOptionPane.showOptionDialog(null, "Wybierz pierwsz¹ karte do odrzucenia", null, 0, JOptionPane.QUESTION_MESSAGE, null, environment.getPlayer(environment.getPlayingPlayer()).getHand().toArray(), 0);
				if(temp==JOptionPane.CLOSED_OPTION);
				else
				{
					environment.getPlayer(environment.getPlayingPlayer()).discardCardfromHand(temp);
					temp=JOptionPane.showOptionDialog(null, "Wybierz drug¹ karte do odrzucenia", null, 0, JOptionPane.QUESTION_MESSAGE, null, environment.getPlayer(environment.getPlayingPlayer()).getHand().toArray(), 0);
					if(temp==JOptionPane.CLOSED_OPTION);
					else
					{
						environment.getPlayer(environment.getPlayingPlayer()).discardCardfromHand(temp);

					}
				}
				break;
			}
			case 7:
				addContinuousEffect(31, target);
				break;
			case 9:
			{
				environment.getCurrentPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()+1);
				addContinuousEffect(32, target);
				break;
			}
			case 10:

				if(target.getClass()==objPlayer.class)environment.getCurrentFight().addBonus(3);
				else if(target.getClass()==objMonster.class)environment.getCurrentFight().addBonus(3);
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
			}
			break;
		case SEAL:
			switch(effectNr)
			{
				case 1:
					for(int i=0;i<environment.getPlayersNumber();i++)if(environment.getPlayer(i).getCardsInPlay().findCards("Scientist", objCard.SecondaryType.CLASS).size()>0)environment.getPlayer(i).levelUp(-1);
					addContinuousEffect(40, null);
					break;
				case 2:
					for(int i=0;i<environment.getPlayersNumber();i++)if(environment.getPlayer(i).getCardsInPlay().findCards("Militia", objCard.SecondaryType.CLASS).size()>0)environment.getPlayer(i).levelUp(-1);
					addContinuousEffect(41, null);
					break;
			}
			break;
		default:
			switch(effectNr)
			{
			case 1:
				if(!continuousEffectContains(20))environment.openSeal();
				addContinuousEffect(20, target);
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
				if(!changeCard((objCard) target, environment.getCurrentPlayer(), "Militia", true))addContinuousEffect(26, target);
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
			}
			break;
		}
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
		continuousEffects.add(new Pair<Integer, objEntity>(i, target));
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
	private boolean monsterBonus(objMonster target, String playerClass, int bonus)
	{
		objPlayer player1=environment.getCurrentFight().getMainPlayer();
		objPlayer player2=environment.getCurrentFight().getHelperPlayer();
		Vector<objCard>classes=new Vector<objCard>();
		if(player1.getClassCounter()==classes.size()) classes=player1.getCardsInPlay().findCards(playerClass, objCard.SecondaryType.CLASS);
		if(player2!=null)if(player2.getClassCounter()==0)classes.addAll(player2.getCardsInPlay().findCards(playerClass, objCard.SecondaryType.CLASS));
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
		if(player1.getClassCounter()==classes.size())for(int i=0;i<playerClass.length;i++)classes=player1.getCardsInPlay().findCards(playerClass[i], objCard.SecondaryType.CLASS);
		if(player2!=null)if(player2.getClassCounter()==0)for(int i=0;i<playerClass.length;i++)classes.addAll(player2.getCardsInPlay().findCards(playerClass[i], objCard.SecondaryType.CLASS));
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
		case MONSTER:
			return null;

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
			if(card.getEffect(0)==13)return null;
			else
			{
				for(int i=0; i<environment.getPlayersNumber();i++)temp.add(environment.getPlayer(i));
				return temp;
			}
		case ITEMENCHANTER:
		{
			Vector<Integer>temp2=environment.getCurrentPlayer().findItemsInPlay();
			if(temp2.size()>0)
			{
				for(int i=0;i<temp2.size();i++) temp.add(environment.getCurrentPlayer().getCardsInPlay().getCard(temp2.get(i)));
			}
			return temp;
		}
		case OTHER:
			switch(card.getEffect(card.getEffect(0)))
			{
			case 1:
			case 3:
				return null;
			case 2:
			{
				Vector<Integer>temp2=environment.getCurrentPlayer().findItemsInPlay();
				if(temp2.size()>0)
				{
					for(int i=0;i<temp2.size();i++) temp.add(environment.getCurrentPlayer().getCardsInPlay().getCard(temp2.get(i)));
				}
			}
			case 4:
				for(int i=0; i<environment.getPlayersNumber();i++)temp.add(environment.getPlayer(i));
				return temp;
			case 5:
				for(int i=0;i<environment.getCurrentFight().getMonsters().size();i++)temp.add(environment.getCurrentFight().getMonsters().get(i));
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
			case 12:
				return null;

			}
			return temp;

		}
		throw new IllegalArgumentException();
	}

	@Override
	public void gameEventOccurred(GameEvent evt)
	{
		GameEvent.EventType eventType=evt.getEventType();
		Iterator<Pair<Integer, objEntity>> iter=continuousEffects.iterator();
		while(iter.hasNext())
		{
			Pair<Integer, objEntity> pair = iter.next();
			switch(pair.getKey())
			{
			case 1:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					if(environment.getCurrentFight().getHelperPlayer()==null)((objMonster)pair.getValue()).setBonus(-1);
					else ((objMonster)pair.getValue()).setBonus(0);
				}
				break;
			case 2:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					if(!((objMonster)pair.getValue()).didEffectTookPlace())monsterBonus(((objMonster)pair.getValue()), "Militia",3);
					else if(monsterBonus(((objMonster)pair.getValue()), "Militia",0)==false)
					{
						((objMonster)pair.getValue()).increaseStrength(-3);
					}
					if(((objMonster)pair.getValue()).getBonus()%5==0||(((objMonster)pair.getValue()).didEffectTookPlace()&&((objMonster)pair.getValue()).getBonus()%5==3))monsterBonus(((objMonster)pair.getValue()),true,3);
					else monsterBonus(((objMonster)pair.getValue()), false,-3);

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
			case 5:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					objPlayer temp =environment.getCurrentFight().getMainPlayer();
					temp.moveFromPlayToCarried(temp.getCardsInPlay().findCardsIndex(null, objCard.Tag.FLAME));
				}
			case 9:
				if(eventType==GameEvent.EventType.TURNSTARTED)
				{
					if(environment.getCurrentPlayer()==pair.getValue())
					{
						((objPlayer)pair.getValue()).endImmediately();
						removeContinuousEffect(pair.getKey());
						removeContinuousEffect(-pair.getKey());
					}
				}
				break;
			case 16:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)
				{
					if(!((objMonster)pair.getValue()).didEffectTookPlace())monsterBonus(((objMonster)pair.getValue()),objCard.SecondaryType.ARMOR,5);
					else if(monsterBonus(((objMonster)pair.getValue()), "Militia",0)==false)
					{
						((objMonster)pair.getValue()).increaseStrength(-3);
					}
					if(((objMonster)pair.getValue()).getBonus()%5==0)monsterBonus(((objMonster)pair.getValue()),false,3);
					else monsterBonus(((objMonster)pair.getValue()), true,-3);
				}
				break;
			case 22:
				if(eventType==GameEvent.EventType.DSICARD)
				{
					if(evt.getTarget()==pair.getValue())
					{
						String massage="Wybierz jedn¹ z poni¿szych:\n";
						for(int i=0;i<environment.getSealDeck().size();i++)massage+=environment.getSealDeck().getCard(i)+"   "+environment.getSealDeck().getCard(i)+"\n";
						int temp=JOptionPane.showOptionDialog(null, massage, "Wybierz pieczec do otwarcia", 0, JOptionPane.QUESTION_MESSAGE, null, environment.getSealDeck().toArray(), 0);
						environment.openSeal(temp);
					}
				}
				break;
			case 23:
				if(eventType==GameEvent.EventType.INVENTORYCHANGED)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Kid", true);
				break;
			case 24:
				if(eventType==GameEvent.EventType.INVENTORYCHANGED)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Militia", false);
				break;
			case 25:
				if(eventType==GameEvent.EventType.INVENTORYCHANGED)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Kid", false);
				break;
			case 26:
				if(eventType==GameEvent.EventType.INVENTORYCHANGED)changeCard((objCard)pair.getValue(), environment.getCurrentPlayer(), "Militia", true);
				break;
			case 27:
			if(eventType==GameEvent.EventType.SEVENTHSEAL){((objCard)pair.getValue()).setBonus(3);

			}
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
								addContinuousEffect(28, null);
								environment.getCurrentFight().addBonus(-3);
							}
						}
					}
				}
			case 30:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)if(!continuousEffectContains(29))
				{
					if(environment.getCurrentFight().getHelperPlayer()!=null)environment.getCurrentFight().addBonus(-5);
					else environment.getCurrentFight().addBonus(5);
					addContinuousEffect(29, null);
				}
				break;
			case 31:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)if(!continuousEffectContains(28))
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
				if(eventType==GameEvent.EventType.DSICARD)
				{
					if(evt.getTarget()==pair.getValue())environment.getCurrentPlayer().setFreeHandCounter(environment.getCurrentPlayer().getFreeHandCounter()-1);
				}
				break;
			case 33:
				break;
			case 34:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)if(continuousEffectContains(33))
				{
					environment.getCurrentFight().addLevelBonus(1);
				}
			case 40:
				break;
			case 41:
				break;
			case -1:
			case -2:
				if(eventType==GameEvent.EventType.FIGHTOVER||(eventType==GameEvent.EventType.FIGHTCHANGED&&evt.getTarget()==pair.getValue()))
				{
					removeContinuousEffect(pair.getKey());
					removeContinuousEffect(-pair.getKey());
				}
			}
		}
	}
}
