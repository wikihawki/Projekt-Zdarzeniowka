import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;




public class DatabaseConnection {

	public Vector<objCard> importCards()
	{
		Vector<objCard> cards = new Vector<objCard>();

		final String query = "SELECT IDKarty,Nazwa, Opis,Typ,Efekt,DrugiEfekt,Poziom_Bonus,Nagroda_Cena,Skarby,Tag FROM Karta";

		DatabaseEngine e_id = new DatabaseEngine();

		try {
			ResultSet result = e_id.getStatement().executeQuery(query);
			while (result.next()) {
				objCard.SecondaryType type2 = null;
				objCard.Type type = null;
				objCard.Tag tag = null;
				switch(result.getInt("Typ"))
				{
				case 1:
					type2=objCard.SecondaryType.MONSTER;
					type=objCard.Type.DOOR;
					break;
				case 2:
					type2=objCard.SecondaryType.SEAL;
					type=objCard.Type.SEAL;
					break;
				case 3:
					type2=objCard.SecondaryType.CLASS;
					type=objCard.Type.DOOR;
					break;
				case 4:
					type2=objCard.SecondaryType.ITEMENCHANTER;
					type=objCard.Type.TREASURE;
					break;
				case 5:
					type2=objCard.SecondaryType.OTHERITEM;
					type=objCard.Type.TREASURE;
					break;
				case 6:
					type2=objCard.SecondaryType.OTHER;
					type=objCard.Type.TREASURE;
					break;
				case 7:
					type2=objCard.SecondaryType.DISASTER;
					type=objCard.Type.DOOR;
					break;
				case 8:
					type2=objCard.SecondaryType.HAT;
					type=objCard.Type.TREASURE;
					break;
				case 9:
					type2=objCard.SecondaryType.BOOTS;
					type=objCard.Type.TREASURE;
					break;
				case 10:
					type2=objCard.SecondaryType.ARMOR;
					type=objCard.Type.TREASURE;
					break;
				case 11:
					type2=objCard.SecondaryType.ONEHANDWEAPON;
					type=objCard.Type.TREASURE;
					break;
				case 12:
					type2=objCard.SecondaryType.TWOHANDWEAPON;
					type=objCard.Type.TREASURE;
					break;
				case 13:
					type2=objCard.SecondaryType.OTHER;
					type=objCard.Type.DOOR;
					break;
				}
				switch(result.getInt("Tag"))
				{
				case 0:
					tag=objCard.Tag.NULL;
					break;
				case 1:
					tag=objCard.Tag.SHARK;
					break;
				case 2:
					tag=objCard.Tag.UNDEAD;
					break;
				case 3:
					tag=objCard.Tag.BIG;
					break;
				case 4:
					tag=objCard.Tag.FLAME;
					break;
				}
				objCard temp = new objCard(result.getInt("IDKarty"), type, type2, tag, result.getString("Nazwa"), result.getString("Opis"), result.getInt("Poziom_Bonus"), result.getInt("Efekt"),result.getInt("DrugiEfekt"), result.getInt("Nagroda_Cena"),result.getInt("Skarby"), 0);

				cards.add(temp);


			}
		} catch (SQLException e) {
			System.out.println("Error when executing SQLite query: " + query);
			e.printStackTrace();
		} finally {
			e_id.dispose();
		}

		return cards;
	}
	public objCard importCard(int idNr)
	{

		final String query = "SELECT IDKarty,Nazwa, Opis,Typ,Efekt,DrugiEfekt,Poziom_Bonus,Nagroda_Cena,Skarby,Tag FROM Karta WHERE IDKarty="+idNr;

		DatabaseEngine e_id = new DatabaseEngine();
		try {
			ResultSet result = e_id.getStatement().executeQuery(query);
			result.next();
			objCard.SecondaryType type2 = null;
			objCard.Type type = null;
			objCard.Tag tag = null;
			switch(result.getInt("Typ"))
			{
			case 1:
				type2=objCard.SecondaryType.MONSTER;
				type=objCard.Type.DOOR;
				break;
			case 2:
				type2=objCard.SecondaryType.SEAL;
				type=objCard.Type.SEAL;
				break;
			case 3:
				type2=objCard.SecondaryType.CLASS;
				type=objCard.Type.DOOR;
				break;
			case 4:
				type2=objCard.SecondaryType.ITEMENCHANTER;
				type=objCard.Type.TREASURE;
				break;
			case 5:
				type2=objCard.SecondaryType.OTHERITEM;
				type=objCard.Type.TREASURE;
				break;
			case 6:
				type2=objCard.SecondaryType.OTHER;
				type=objCard.Type.TREASURE;
				break;
			case 7:
				type2=objCard.SecondaryType.DISASTER;
				type=objCard.Type.DOOR;
				break;
			case 8:
				type2=objCard.SecondaryType.HAT;
				type=objCard.Type.TREASURE;
				break;
			case 9:
				type2=objCard.SecondaryType.BOOTS;
				type=objCard.Type.TREASURE;
				break;
			case 10:
				type2=objCard.SecondaryType.ARMOR;
				type=objCard.Type.TREASURE;
				break;
			case 11:
				type2=objCard.SecondaryType.ONEHANDWEAPON;
				type=objCard.Type.TREASURE;
				break;
			case 12:
				type2=objCard.SecondaryType.TWOHANDWEAPON;
				type=objCard.Type.TREASURE;
				break;
			case 13:
				type2=objCard.SecondaryType.OTHER;
				type=objCard.Type.DOOR;
				break;
			}
			switch(result.getInt("Tag"))
			{
			case 0:
				tag=objCard.Tag.NULL;
				break;
			case 1:
				tag=objCard.Tag.SHARK;
				break;
			case 2:
				tag=objCard.Tag.UNDEAD;
				break;
			case 3:
				tag=objCard.Tag.BIG;
				break;
			case 4:
				tag=objCard.Tag.FLAME;
				break;
			}
			objCard temp = new objCard(result.getInt("IDKarty"), type, type2, tag, result.getString("Nazwa"), result.getString("Opis"), result.getInt("Poziom_Bonus"), result.getInt("Efekt"),result.getInt("DrugiEfekt"), result.getInt("Nagroda_Cena"),result.getInt("Skarby"), 0);
			return temp;


		} catch (SQLException e) {
			System.out.println("Error when executing SQLite query: " + query);
			e.printStackTrace();
		} finally {
			e_id.dispose();
		}
		return null;

	}
	public void saveGame(objGameLogic game, int slot)
	{
		cleanSlot(slot);
		saveStackState(game.getDoorDeck().getStack(0), slot, 1, -1);
		saveStackState(game.getDoorDiscard().getStack(0), slot, 2, -1);
		saveStackState(game.getTreasureDeck().getStack(0), slot, 3, -1);
		saveStackState(game.getTreasureDiscard().getStack(0), slot, 4, -1);
		saveStackState(game.getSealDeck().getStack(0), slot, 5, -1);
		saveStackState(game.getOpenedSeals().getStack(0), slot, 6, -1);
		int[] temp=game.getNextPlayerId(game.getPlayerIndex(game.getCurrentPlayer()));
		for(int i=0;i<4;i++)
		{
			savePlayer(game.getPlayer(temp[i]), slot, i);
			saveStackState(game.getPlayer(temp[i]).getHand().getStack(0), slot, 1, temp[i]);
			saveStackState(game.getPlayer(temp[i]).getCardsInPlay().getStack(0), slot, 2, temp[i]);
			saveStackState(game.getPlayer(temp[i]).getCarriedCards().getStack(0), slot, 3, temp[i]);
		}
	}
	public boolean savePlayer(objPlayer player, int slot, int nr)
	{
		boolean finalized = true;
		String flag;
		if(player.getSex())flag="'M'";
		else flag="'K'";
		final String query ="INSERT INTO Gracz(IDGracza, IDGry, Nazwa, Poziom, Plec)VALUES ("+nr+","+slot+",'"+player.getName()+"',"+player.getLevel()+","+flag+")";
		DatabaseEngine e_id = new DatabaseEngine();

		try {
			e_id.getConnection().setAutoCommit(false);
			e_id.getStatement().executeUpdate(query);
			e_id.getConnection().commit();
		} catch (SQLException e) {
			finalized = false;
			System.out.println("Error when executing SQLite query: " + query);
			e.printStackTrace();
		} finally {
			e_id.dispose();
		}

		return finalized;
	}
	private boolean cleanSlot(int slot)
	{
		boolean finalized = true;

		final String query ="DELETE FROM StanGry WHERE NrGry="+slot;
		final String query2 ="DELETE FROM Gracz WHERE IDGry="+slot;

		DatabaseEngine e_id = new DatabaseEngine();

		try {
			e_id.getConnection().setAutoCommit(false);
			e_id.getStatement().executeUpdate(query);
			e_id.getStatement().executeUpdate(query2);
			e_id.getConnection().commit();
		} catch (SQLException e) {
			finalized = false;
			System.out.println("Error when executing SQLite query: " + query);
			e.printStackTrace();
		} finally {
			e_id.dispose();
		}

		return finalized;
	}
	public void saveStackState(Vector<objCard> stack, int slot, int type,int IDGracza)
	{
		for(int i=0;i<stack.size();i++)
		{
			saveCardState(stack.get(i),slot,type, IDGracza);
		}
	}
	private boolean saveCardState(objCard card, int slot, int type, int IDGracza)
	{
		boolean finalized = true;

		final String query ="INSERT INTO StanGry(NrGry, IDGracza, IDKarty, Polorzenie)VALUES ("+slot+","+IDGracza+","+card.getIdNr()+","+type+")";

		DatabaseEngine e_id = new DatabaseEngine();

		try {
			e_id.getConnection().setAutoCommit(false);
			e_id.getStatement().executeUpdate(query);
			e_id.getConnection().commit();
		} catch (SQLException e) {
			finalized = false;
			System.out.println("Error when executing SQLite query: " + query);
			e.printStackTrace();
		} finally {
			e_id.dispose();
		}

		return finalized;
	}
	public objPlayer loadPlayer(int slot, int playerNr, objGameLogic game)
	{

		final String query = "SELECT Nazwa,Plec FROM Gracz WHERE IDGracza="+playerNr+" AND IDGry="+slot;

		DatabaseEngine e_id = new DatabaseEngine();
		try {
			ResultSet result = e_id.getStatement().executeQuery(query);
			result.next();
			boolean flag;
			String help=result.getString("Plec");
			flag=(help=="K");
			objPlayer temp = new objPlayer(result.getString("Nazwa"),flag, game, playerNr);
			return temp;


		} catch (SQLException e) {
			System.out.println("Error when executing SQLite query: " + query);
			e.printStackTrace();
		} finally {
			e_id.dispose();
		}
		return null;
	}
	public Vector<objCard> loadStack(int slot, int type, int playerNr)
	{
		final String query = "SELECT IDKarty FROM StanGry WHERE Polorzenie="+type+" AND NrGry="+slot+" AND IDGracza="+playerNr;

		DatabaseEngine e_id = new DatabaseEngine();
		try {
			ResultSet result = e_id.getStatement().executeQuery(query);
			Vector<objCard> temp=new Vector<objCard>();
			while (result.next())
			{
				temp.add(importCard(result.getInt("IDKarty")));
			}
			return temp;


		} catch (SQLException e) {
			System.out.println("Error when executing SQLite query: " + query);
			e.printStackTrace();
		} finally {
			e_id.dispose();
		}
		return null;
	}
}