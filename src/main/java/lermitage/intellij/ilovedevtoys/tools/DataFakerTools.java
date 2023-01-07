package lermitage.intellij.ilovedevtoys.tools;

import fr.marcwrobel.jbanking.IsoCountry;
import fr.marcwrobel.jbanking.iban.BbanStructure;
import fr.marcwrobel.jbanking.iban.RandomIban;
import net.datafaker.Faker;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.Ancient;
import net.datafaker.providers.base.Animal;
import net.datafaker.providers.base.Appliance;
import net.datafaker.providers.base.Artist;
import net.datafaker.providers.base.Barcode;
import net.datafaker.providers.base.Book;
import net.datafaker.providers.base.Company;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.Job;
import net.datafaker.providers.base.Music;
import net.datafaker.providers.base.Twitter;
import net.datafaker.providers.base.University;
import net.datafaker.providers.food.Food;
import net.datafaker.providers.movie.Babylon5;
import net.datafaker.providers.movie.BackToTheFuture;
import net.datafaker.providers.movie.Buffy;
import net.datafaker.providers.movie.ChuckNorris;
import net.datafaker.providers.movie.Ghostbusters;
import net.datafaker.providers.movie.Movie;
import net.datafaker.providers.movie.Pokemon;
import net.datafaker.providers.movie.RickAndMorty;
import net.datafaker.providers.movie.TheItCrowd;
import net.datafaker.providers.videogame.SuperMario;

import java.util.List;
import java.util.Locale;

public class DataFakerTools {

    public static final List<String> FAKER_LOCALES = List.of(
        "en",
        "en-CA",
        "en-US",
        "fr",
        "fr-CA",
        "fr-CH",
        "ar",
        "bg",
        "by",
        "ca",
        "cs",
        "cs-CZ",
        "da-DK",
        "de",
        "de-AT",
        "de-CH",
        "dk",
        "ee",
        "es",
        "es-AR",
        "es-MX",
        "es-PY",
        "fa",
        "fi-FI",
        "he",
        "hr",
        "hu",
        "hy",
        "id",
        "in-ID",
        "it",
        "ja",
        "ko",
        "lv",
        "nb-NO",
        "nl",
        "no-NO",
        "pl",
        "pt",
        "pt-BR",
        "ru",
        "sk",
        "sv",
        "sv-SE",
        "th",
        "tr",
        "uk",
        "vi",
        "zh-CN",
        "zh-TW");

    public static final List<String> FAKER_GENERATORS = List.of(
        "Address",
        "Ancient god",
        "Ancient hero",
        "Ancient primordial",
        "Ancient titan",
        "Animal",
        "Appliance brand",
        "Appliance equipment",
        "Artist",
        "Babylon5 character",
        "Babylon5 quote",
        "Back To The Future character",
        "Back To The Future quote",
        "Banking - IBAN",
        "Bar code",
        "Book author",
        "Book title",
        "Buffy character",
        "Buffy episode",
        "Buffy quote",
        "Chuck Norris fact",
        "Company buzzword",
        "Company name",
        "Food",
        "GhostBusters actor",
        "GhostBusters character",
        "GhostBusters quote",
        "Id number",
        "Job key skill",
        "Job position",
        "Job title",
        "Movie quote",
        "Music instrument",
        "Pokemon location",
        "Pokemon name",
        "Super Mario character",
        "Super Mario game",
        "Rick and Morty character",
        "Rick and Morty quote",
        "The It Crowd actor",
        "The It Crowd character",
        "The It Crowd email",
        "The It Crowd quote",
        "Twitter user id",
        "Twitter username",
        "University name");

    public static String generateFakeData(String generator, String locale, int howMany) {
        if (howMany < 1) {
            return "";
        }
        try {
            Faker faker = new Faker(Locale.forLanguageTag(locale));
            StringBuilder sb = new StringBuilder();

            switch (generator) {
                case "Address" -> {
                    Address address = faker.address();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(address.fullAddress()).append("\n");
                    }
                }
                case "Ancient god" -> {
                    Ancient ancient = faker.ancient();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(ancient.god()).append("\n");
                    }
                }
                case "Ancient hero" -> {
                    Ancient ancient = faker.ancient();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(ancient.hero()).append("\n");
                    }
                }
                case "Ancient titan" -> {
                    Ancient ancient = faker.ancient();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(ancient.titan()).append("\n");
                    }
                }
                case "Ancient primordial" -> {
                    Ancient ancient = faker.ancient();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(ancient.primordial()).append("\n");
                    }
                }
                case "Animal" -> {
                    Animal animal = faker.animal();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(animal.name())
                            .append(" (")
                            .append(animal.scientificName())
                            .append(")\n");
                    }
                }
                case "Appliance brand" -> {
                    Appliance appliance = faker.appliance();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(appliance.brand()).append("\n");
                    }
                }
                case "Appliance equipment" -> {
                    Appliance appliance = faker.appliance();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(appliance.equipment()).append("\n");
                    }
                }
                case "Artist" -> {
                    Artist artist = faker.artist();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(artist.name()).append("\n");
                    }
                }
                case "Babylon5 character" -> {
                    Babylon5 babylon5 = faker.babylon5();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(babylon5.character()).append("\n");
                    }
                }
                case "Babylon5 quote" -> {
                    Babylon5 babylon5 = faker.babylon5();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(babylon5.quote()).append("\n");
                    }
                }
                case "Back To The Future character" -> {
                    BackToTheFuture backToTheFuture = faker.backToTheFuture();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(backToTheFuture.character()).append("\n");
                    }
                }
                case "Back To The Future quote" -> {
                    BackToTheFuture backToTheFuture = faker.backToTheFuture();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(backToTheFuture.quote()).append("\n");
                    }
                }
                case "Banking - IBAN" -> {
                    String country = locale.toUpperCase();
                    if (country.contains("-")) {
                        country = country.substring(0, country.indexOf("-"));
                    }
                    IsoCountry isoCountry;
                    try {
                        isoCountry = IsoCountry.valueOf(country);
                    } catch (IllegalArgumentException e) {
                        isoCountry = IsoCountry.FR;
                    }
                    BbanStructure bbanStructure = BbanStructure.forCountry(isoCountry).orElse(BbanStructure.FR);
                    sb.append("=== ").append(bbanStructure).append(" IBAN codes ===\n");
                    for (int i = 0; i < howMany; i++) {
                        sb.append(new RandomIban().next(bbanStructure).toPrintableString()).append("\n");
                    }
                }
                case "Bar code" -> {
                    Barcode barcode = faker.barcode();
                    sb.append("=== ean8 bar codes ===\n");
                    for (int i = 0; i < howMany; i++) {
                        sb.append(barcode.ean8()).append("\n");
                    }
                    sb.append("\n=== ean13 bar codes ===\n");
                    for (int i = 0; i < howMany; i++) {
                        sb.append(barcode.ean13()).append("\n");
                    }
                    sb.append("\n=== gtin8 bar codes ===\n");
                    for (int i = 0; i < howMany; i++) {
                        sb.append(barcode.gtin8()).append("\n");
                    }
                    sb.append("\n=== gtin12 bar codes ===\n");
                    for (int i = 0; i < howMany; i++) {
                        sb.append(barcode.gtin12()).append("\n");
                    }
                    sb.append("\n=== gtin13 bar codes ===\n");
                    for (int i = 0; i < howMany; i++) {
                        sb.append(barcode.gtin13()).append("\n");
                    }
                    sb.append("\n=== gtin14 bar codes ===\n");
                    for (int i = 0; i < howMany; i++) {
                        sb.append(barcode.gtin14()).append("\n");
                    }
                }
                case "Book author" -> {
                    Book book = faker.book();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(book.author()).append("\n");
                    }
                }
                case "Book title" -> {
                    Book book = faker.book();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(book.title()).append("\n");
                    }
                }
                case "Buffy character" -> {
                    Buffy buffy = faker.buffy();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(buffy.characters()).append("\n");
                    }
                }
                case "Buffy episode" -> {
                    Buffy buffy = faker.buffy();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(buffy.episodes()).append("\n");
                    }
                }
                case "Buffy quote" -> {
                    Buffy buffy = faker.buffy();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(buffy.quotes()).append("\n");
                    }
                }
                case "Chuck Norris fact" -> {
                    ChuckNorris chuckNorris = faker.chuckNorris();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(chuckNorris.fact()).append("\n");
                    }
                }
                case "Company name" -> {
                    Company company = faker.company();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(company.name()).append("\n");
                    }
                }
                case "Company buzzword" -> {
                    Company company = faker.company();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(company.buzzword()).append("\n");
                    }
                }
                case "Food" -> {
                    Food food = faker.food();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(food.ingredient()).append("\n");
                    }
                }
                case "GhostBusters actor" -> {
                    Ghostbusters ghostbusters = faker.ghostbusters();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(ghostbusters.actor()).append("\n");
                    }
                }
                case "GhostBusters character" -> {
                    Ghostbusters ghostbusters = faker.ghostbusters();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(ghostbusters.character()).append("\n");
                    }
                }
                case "GhostBusters quote" -> {
                    Ghostbusters ghostbusters = faker.ghostbusters();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(ghostbusters.quote()).append("\n");
                    }
                }
                case "Id number" -> {
                    IdNumber idNumber = faker.idNumber();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(idNumber.valid()).append("\n");
                    }
                }
                case "Job title" -> {
                    Job job = faker.job();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(job.title()).append("\n");
                    }
                }
                case "Job position" -> {
                    Job job = faker.job();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(job.position()).append("\n");
                    }
                }
                case "Job key skill" -> {
                    Job job = faker.job();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(job.keySkills()).append("\n");
                    }
                }
                case "Movie quote" -> {
                    Movie movie = faker.movie();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(movie.quote()).append("\n");
                    }
                }
                case "Music instrument" -> {
                    Music music = faker.music();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(music.instrument()).append("\n");
                    }
                }
                case "Pokemon location" -> {
                    Pokemon pokemon = faker.pokemon();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(pokemon.location()).append("\n");
                    }
                }
                case "Pokemon name" -> {
                    Pokemon pokemon = faker.pokemon();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(pokemon.name()).append("\n");
                    }
                }
                case "Super Mario game" -> {
                    SuperMario superMario = faker.superMario();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(superMario.games()).append("\n");
                    }
                }
                case "Super Mario character" -> {
                    SuperMario superMario = faker.superMario();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(superMario.characters()).append("\n");
                    }
                }
                case "Rick and Morty character" -> {
                    RickAndMorty rickAndMorty = faker.rickAndMorty();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(rickAndMorty.character()).append("\n");
                    }
                }
                case "Rick and Morty quote" -> {
                    RickAndMorty rickAndMorty = faker.rickAndMorty();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(rickAndMorty.quote()).append("\n");
                    }
                }
                case "The It Crowd character" -> {
                    TheItCrowd theItCrowd = faker.theItCrowd();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(theItCrowd.characters()).append("\n");
                    }
                }
                case "The It Crowd quote" -> {
                    TheItCrowd theItCrowd = faker.theItCrowd();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(theItCrowd.quotes()).append("\n");
                    }
                }
                case "The It Crowd actor" -> {
                    TheItCrowd theItCrowd = faker.theItCrowd();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(theItCrowd.actors()).append("\n");
                    }
                }
                case "The It Crowd email" -> {
                    TheItCrowd theItCrowd = faker.theItCrowd();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(theItCrowd.emails()).append("\n");
                    }
                }
                case "Twitter username" -> {
                    Twitter twitter = faker.twitter();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(twitter.userName()).append("\n");
                    }
                }
                case "Twitter user id" -> {
                    Twitter twitter = faker.twitter();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(twitter.userId()).append("\n");
                    }
                }
                case "University name" -> {
                    University university = faker.university();
                    for (int i = 0; i < howMany; i++) {
                        sb.append(university.name()).append("\n");
                    }
                }
            }

            return sb.toString().trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
