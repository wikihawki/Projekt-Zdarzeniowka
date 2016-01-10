import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class DatabaseConnection {

	public ArrayList<objCard> importCards()
	{
		ArrayList<objCard> wlasciciele = new ArrayList<objCard>();

		final String wezStrone = "SELECT IDKarty,Nazwa, Opis,Typ,Efekt,DrugiEfekt,Poziom_Bonus,Nagroda_Cena,Skarby,Tag FROM Karta";

		DatabaseEngine e_id = new DatabaseEngine();

		try {
			ResultSet result = e_id.getStatement().executeQuery(wezStrone);
			while (result.next()) {
				/*
				objCard w = new objCard();
				w.setImie(result.getString("Imie"));
				w.setNazwisko(result.getString("Nazwisko"));
				w.setID(result.getInt("Id"));
				w.setIdDokumentu(result.getInt("IdDokumentu"));
				w.setPESEL(result.getInt("PESEL"));
				w.setDataUrodzenia(result.getString("DataUrodzenia"));
				w.setAdres(result.getString("Adres"));
				wlasciciele.add(w);
				*/
				System.out.println(result.getString("Nazwa"));
			}
		} catch (SQLException e) {
			System.out.println("Error when executing SQLite query: " + wezStrone);
			e.printStackTrace();
		} finally {
			e_id.dispose();
		}

		return wlasciciele;
	}
}