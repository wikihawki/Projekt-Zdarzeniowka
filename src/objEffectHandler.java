
public class objEffectHandler
{
	private objGameLogic environment;
	public objEffectHandler(objGameLogic envi)
	{
		environment=envi;
	}
	public void handleEffect(objCard.Type cardType, int effectNr, objEntity target)
	{
		switch (cardType)
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
		case SEAL:
			break;
		case WEAPON:
			break;
		default:
			break;
		}
	}
}
