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

				System.out.println(result.getString("Nazwa"));
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
}