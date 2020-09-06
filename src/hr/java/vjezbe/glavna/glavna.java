package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;

public class glavna {

	public static void main(String[] args) {
		
		Scanner unos = new Scanner(System.in);
		
		//Inicijaliziranje korisnika
		System.out.print("Unesite broj korisnika koje zelite unjeti");
		Integer numKorisnika=unos.nextInt();
		Korisnik[] poljeKorisnika = new Korisnik [numKorisnika];
		unesiKorisnika(unos,poljeKorisnika);
		
		//Inicijaliziranje Kategorija i Artikala
		System.out.print("Unesite broj kategorija koje zelite unjeti");
		Integer brojKategorija = unos.nextInt();
		Kategorija[] poljeKategorije = new Kategorija[brojKategorija];
		unesiKategorije(unos,poljeKategorije);
		
		//Inicijaliziranje prodaja
		System.out.println("Unesite broj artikala koji su aktivno na prodaju");
		Integer pomBrojAktivnih = unos.nextInt();
		Prodaja[] poljeProdaje = new Prodaja[pomBrojAktivnih];
		unos.nextLine();
		unesiProdaju(unos,poljeKorisnika,poljeKategorije,poljeProdaje);
		
		
		//Ispis rjesenja
		ispisi(poljeProdaje);
		
		unos.close();
		
	}
	
	//Metoda za unosenje korisnika
	private static  void unesiKorisnika(Scanner unos,Korisnik[] poljeKorisnika) {
		unos.nextLine();
		for (int i = 0; i<poljeKorisnika.length;i++) {
			
			System.out.println("Unesite ime "+ (i +1) +". Korisnika");
			
			String pomIme = unos.nextLine();
			System.out.println("Unesite prezime "+ (i +1) +". Korisnika");
			String pomPrezime = unos.nextLine();
			System.out.println("Unesite email "+ (i +1) +". Korisnika");
			String pomEmail = unos.nextLine();
			System.out.println("Unesite telefon "+ (i +1) +". Korisnika");
			String pomTelefon = unos.nextLine();
			
			poljeKorisnika[i] = new Korisnik(pomIme,pomPrezime,pomEmail,pomTelefon);
		}
	}
	
	//Unosi kategoriju i artikle koji su dio kategorija
	private static void unesiKategorije(Scanner unos,Kategorija[] poljeKategorije) {
		unos.nextLine();
		for (int i = 0; i<poljeKategorije.length;i++) {
			System.out.println("Unesite naziv "+ (i +1) +". kategorije");
			String pomNaziv = unos.nextLine();
			System.out.println("Unesite broj artikla koji zelite unijeti za kategoriju");
			Integer pomBrojArtikla = unos.nextInt();
			Artikl[] poljeArtikla = new Artikl[pomBrojArtikla];
			unos.nextLine();
			for (int j = 0;j<pomBrojArtikla;j++) {
				System.out.println("Unesite naslov "+ (j +1) +". artikla");
				String pomNaslov = unos.nextLine();
				System.out.println("Unesite opis "+ (j +1) +". artikla");
				String pomOpis = unos.nextLine();
				System.out.println("Unesite cijenu "+ (j +1) +". artikla");
				//pretvaram string sa "," u big decimal sa "."
				String pomStringCijena = unos.nextLine();
				BigDecimal pomCijena = new BigDecimal(pomStringCijena.replaceAll(",", "."));
				poljeArtikla[j] = new Artikl(pomNaslov,pomOpis,pomCijena);
			}
			poljeKategorije [i] = new Kategorija(pomNaziv,poljeArtikla);
			
		}
	}
	
	
	//Metoda za unos prodaje
	private static void unesiProdaju(Scanner unos , Korisnik[] poljeKorisnika,Kategorija[] poljeKategorije,Prodaja[] poljeProdaje) {
		for (int i = 0;i<poljeProdaje.length;i++) {
			System.out.println("Odaberite korisnika: \n");
			
			for (int j = 0; j<poljeKorisnika.length;j++) {
				System.out.println((j+1) + ". " +poljeKorisnika[j].getIme()+ " "+ poljeKorisnika[j].getPrezime()+"\n");
			}
			
			Integer pomKorisnik = unos.nextInt();
			unos.nextLine();
			System.out.println("Odaberite kategoriju:\n");
			
			for (int j = 0;j<poljeKategorije.length;j++) {
				System.out.println((j+1)+ ". "+poljeKategorije[j].getNaziv());
			}
			
			Integer pomKategorija = unos.nextInt();
			unos.nextLine();
			System.out.println("Odaberite artikl:\n");
			
			for (int j = 0;j<poljeKategorije[pomKategorija-1].getArtikli().length;j++) {
				System.out.println((j+1)+ ". " + poljeKategorije[pomKategorija-1].getArtikli()[j].getNaslov());
			}
			Integer pomArtiklKategorije = unos.nextInt();
			unos.nextLine();
			
			LocalDate datum = LocalDate.now();
			poljeProdaje[i] = new Prodaja(poljeKategorije[pomKategorija-1].getArtikli()[pomArtiklKategorije-1],poljeKorisnika[pomKorisnik-1],datum);
		}
		
	}
	
	
	//Metoda za ispis
	private static void ispisi(Prodaja[] poljeProdaje) {
		System.out.println("Trenutni su oglasi na prodaju: \n");
		for (int i = 0; i< poljeProdaje.length;i++) {
			System.out.println("Naslov: " + poljeProdaje[i].getArtikl().getNaslov()+"\n");
			System.out.println("Opis: "+ poljeProdaje[i].getArtikl().getOpis()+"\n");
			System.out.println("Cijena: "+ poljeProdaje[i].getArtikl().getCijena()+"\n");
			//Datum objave nije u Stringu i tu ga moramo pretvorit u string
			LocalDate datum = poljeProdaje[i].getDatumObjave();
			String stringDatum = datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			System.out.println("Datum: " + stringDatum +"\n" );
			//finalni ispis
			System.out.println(poljeProdaje[i].getKorisnik().getIme() + ", "+poljeProdaje[i].getKorisnik().getPrezime() + ", Mail: " 
			+poljeProdaje[i].getKorisnik().getEmail() + ", tel:" + poljeProdaje[i].getKorisnik().getTelefon());
		}
	}

}
