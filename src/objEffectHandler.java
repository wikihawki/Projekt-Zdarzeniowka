
public class objEffectHandler
{
	private objGameLogic environment;
	public objEffectHandler(objGameLogic envi)
	{
		environment=envi;
	}
	public void handleEffect(objCard.Type cardType, int effectNr)
	{
		switch (cardType)
		{
		case ARMOR:
			break;
		case BOOTS:
			break;
		case DISASTER:
			break;
		case HEADGEAR:
			break;
		case MONSTER:
			break;
		case OTHER:
			break;
		case OTHERITEM:
			break;
		case SEAL:
			break;
		case WEAPON:
			break;
		default:
			break;
		}
	}
}
