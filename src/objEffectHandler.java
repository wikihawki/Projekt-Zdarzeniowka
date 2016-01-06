
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
				break;
			case 3:
				break;
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
			case 3:
				return objPlayer.class;
			case 12:
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
}
