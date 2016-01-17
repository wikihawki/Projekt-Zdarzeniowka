public class objPlayedCard extends objEntity
{
	private objCard playedCard;
	private objEntity target;
	private objPlayer player;
	public objPlayedCard(objCard card, objEntity target, objPlayer player)
	{
		playedCard=card;
		this.target=target;
		this.player=player;
	}
	public objCard getPlayedCard() {
		return playedCard;
	}
	public objEntity getTarget()
	{
		return target;
	}
	public objPlayer getPlayer() {
		return player;
	}
	@Override
	public String getName() {
		return playedCard.getName()+" zagrane na "+target.getName()+" przez "+player.getName();
	}
}
