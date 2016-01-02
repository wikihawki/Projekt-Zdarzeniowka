import java.awt.Image;


public class objSealCard extends objCard {
	
	private int effect;
	public objSealCard(Type type, Image imgCard, String name, String discription, int effect) {
		super(type, imgCard, name, discription);
		this.effect=effect;
	}
	public int getEffect() {
		return effect;
	}

}
