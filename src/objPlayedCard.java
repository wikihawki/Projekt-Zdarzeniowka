public class objPlayedCard
{
	private objCard playedCard;
	private objEntity target;
	public objPlayedCard(objCard card, objEntity target)
	{
		playedCard=card;
		this.target=target;
	}
	public objCard getPlayedCard() {
		return playedCard;
	}
	public objEntity getTarget()
	{
		return target;
	}
}
