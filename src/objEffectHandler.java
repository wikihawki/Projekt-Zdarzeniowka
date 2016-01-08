import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;


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
				for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardfromPlay(temp.elementAt(i));
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
				if(help<temp)((objPlayer)target).discardCardfromPlay(help);
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
					if(help<temp)((objPlayer)target).discardCardfromPlay(help);
					else((objPlayer)target).discardCarriedCard(help-temp);
				}
				break;
			}
			case 6:
			{
				Vector<Integer> temp=((objPlayer)target).findArmor();
				for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardfromPlay(temp.elementAt(i));
				break;
			}
			case 7:
			{
				Vector<Integer> temp=((objPlayer)target).findClass();
				for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardfromPlay(temp.elementAt(i));
				break;
			}
			case 8:
			{
				//lose turn
				break;
			}
			case 9:
			{
				Vector<Integer> temp=((objPlayer)target).getCardsInPlay().findCardsID(null, objCard.Tag.FLAME);
				for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardfromPlay(temp.elementAt(i));
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
				for(int i=0;i<temp.size();i++)((objPlayer)target).discardCardfromPlay(temp.elementAt(i));
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
				temp.moveFromPlayToCarried(temp.getCardsInPlay().findCardsID(null, objCard.Tag.FLAME));
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
				String[]temp=new String[2];
				temp[0]="Scientist";
				temp[1]="Militia";
				monsterBonus((objMonster)target, temp, -3);
				addContinuousEffect(8,target);
				break;
			case 10:

				break;
			case 11:

				break;

			default:
				throw new IllegalArgumentException();
			}
			break;
		case OTHER:

			break;
		default:
			break;
		}
	}
	private void addContinuousEffect(int i, objEntity target)
	{
		continuousEffects.put(i, target);
		continuousEffects.put(-i, null);
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

	@Override
	public void gameEventOccurred(GameEvent evt)
	{
		GameEvent.EventType eventType=evt.getEventType();
		Iterator<Entry<Integer, objEntity>> iter=continuousEffects.entrySet().iterator();
		while(iter.hasNext())
		{
			Entry<Integer, objEntity> pair = iter.next();

			if(iter.next().getKey()>0)switch(iter.next().getKey())
			{
			case 1:
				if(eventType==GameEvent.EventType.FIGHTCHANGED)if(environment.getCurrentFight().getHelperPlayer()==null)((objMonster)pair.getValue()).setBonus(-1);
				break;
			}
			else
			{
				continuousEffects.remove(iter.next().getKey());
				continuousEffects.remove(-iter.next().getKey());
			}
		}
	}
}
