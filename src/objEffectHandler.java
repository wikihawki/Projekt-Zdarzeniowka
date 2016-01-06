import java.util.Random;
import java.util.Vector;

public class objEffectHandler
{
	private objGameLogic environment;
	public objEffectHandler(objGameLogic envi)
	{
		environment=envi;
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
			case 1:

				break;
			}
			break;
		case OTHER:

			break;
		default:
			break;
		}
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
}
