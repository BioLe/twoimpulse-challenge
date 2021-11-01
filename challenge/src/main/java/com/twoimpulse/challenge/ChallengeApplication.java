package com.twoimpulse.challenge;

import com.twoimpulse.challenge.book.Book;
import com.twoimpulse.challenge.book.BookRepository;
import com.twoimpulse.challenge.campaign.Campaign;
import com.twoimpulse.challenge.campaign.CampaignRepository;
import com.twoimpulse.challenge.campaignsubscriber.CampaignSubscriber;
import com.twoimpulse.challenge.campaignsubscriber.CampaignSubscriberRepository;
import com.twoimpulse.challenge.inventory.Inventory;
import com.twoimpulse.challenge.inventory.InventoryRepository;
import com.twoimpulse.challenge.library.Library;
import com.twoimpulse.challenge.library.LibraryRepository;
import com.twoimpulse.challenge.librarycard.LibraryCard;
import com.twoimpulse.challenge.librarycard.LibraryCardRepository;
import com.twoimpulse.challenge.librarycardsubscription.LibraryCardSubscription;
import com.twoimpulse.challenge.librarycardsubscription.LibraryCardSubscriptionRepository;
import com.twoimpulse.challenge.loan.Loan;
import com.twoimpulse.challenge.loan.LoanRepository;
import com.twoimpulse.challenge.loanstate.LoanState;
import com.twoimpulse.challenge.loanstate.LoanStateEnum;
import com.twoimpulse.challenge.loanstate.LoanStateRepository;
import com.twoimpulse.challenge.person.Person;
import com.twoimpulse.challenge.person.PersonRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.util.ResourceUtils.getURL;

@SpringBootApplication
public class ChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner loadData(BookRepository bookRepository,
                                      LibraryRepository libraryRepository,
                                      CampaignRepository campaignRepository,
                                      InventoryRepository inventoryRepository,
                                      PersonRepository personRepository,
                                      LoanStateRepository loanStateRepository,
                                      LibraryCardRepository libraryCardRepository,
                                      LibraryCardSubscriptionRepository libraryCardSubscriptionRepository,
                                      LoanRepository loanRepository,
                                      CampaignSubscriberRepository campaignSubscriberRepository){

        ClassLoader classLoader = getClass().getClassLoader();


        return (args) -> {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String salt = "twoimpulsechallengepasswordencryptionsecretphrase";
            String hashedPassword = passwordEncoder.encode(salt+"password");

            // People
            Person Leo = new Person("Leo", "leo@gmail.com", hashedPassword, LocalDateTime.now().minusMonths(1));
            Person Marg = new Person("Margarida","marg@gmail.com", hashedPassword, LocalDateTime.now().minusWeeks(2));
            Person Joao = new Person("João", "joao@gmail.com", hashedPassword, LocalDateTime.now());
            personRepository.saveAll(List.of(Leo, Marg, Joao));

            // Libraries
            Library lisboaLib = new Library("Biblioteca Nacional de Portugal","Campo Grande 83");
            Library evoraLib = new Library("Biblioteca Pública de Évora","Largo do Conde de Vila Flor 4");
            Library portoLib = new Library("Biblioteca Pública Municipal do Porto", "R. de Dom João IV 17");
            Library faroLib = new Library("Biblioteca Municipal de Faro", "R. Pintor Carlos Porfirio 35");
            libraryRepository.saveAll(List.of(lisboaLib, evoraLib, portoLib, faroLib));

            // Books



            Book hobbit = new Book(
                    "9780553471076",
                    "The Hobbit",
                    "The Hobbit tells the famous story of Bilbo Baggins...",
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/hobbit.jpg"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images\\hobbit.jpg").getFile()).toPath()
//
//                    )
            );
            Book harryPotter = new Book(
                    "9789722325331",
                    "Harry Potter and the Philosopher's Stone",
                    "Harry Potter a young wizard..",
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/harry_potter.jpg"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images/harry_potter.jpg").getFile()).toPath())
            );
            Book harryPotter2 = new Book(
                    "9789722325332",
                    "Harry Potter and the Chamber of Secrets",
                    "Harry fights a giant basilisk.",
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/harry_potter_2.jpg"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images/harry_potter_2.jpg").getFile()).toPath())
            );

            Book harryPotter3 = new Book(
                    "9789722325333",
                    "Harry Potter and the Prisoner of Azkaban",
                    "Harry fights the dementors.",
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/harry_potter_3.jpg"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images/harry_potter_3.jpg").getFile()).toPath())
            );
            //

            Book harryPotter4 = new Book(
                    "9789722325334",
                    "Harry Potter and Goblet of Fire",
                    "Harry plays the world cup.",
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/harry_potter_4.jpg"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images/harry_potter_4.jpg").getFile()).toPath())
            );

            Book harryPotter5 = new Book(
                    "9789722325335",
                    "Harry Potter and Order of Phoenix",
                    "Harry forms a gang.",
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/harry_potter_5.jpg"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images/harry_potter_5.jpg").getFile()).toPath())
            );

            Book harryPotter6 = new Book(
                    "9789722325336",
                    "Harry Potter and Half Blood Prince",
                    "Harry discovers a mysterious book.",
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/harry_potter_6.jpg"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images/harry_potter_6.jpg").getFile()).toPath())
            );

            Book harryPotter7 = new Book(
                    "9789722325337",
                    "Harry Potter and Deathly Hallows",
                    "Harry kills voldmort.",
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/harry_potter_7.jpg"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images/harry_potter_7.jpg").getFile()).toPath())
            );
            bookRepository.saveAll(List.of(hobbit, harryPotter, harryPotter2, harryPotter3,
                    harryPotter4, harryPotter5, harryPotter6, harryPotter7));

            //BookStock
            Inventory hobbitBS = new Inventory(hobbit, lisboaLib);
            Inventory hobbitBS2 = new Inventory(hobbit, lisboaLib);
            Inventory harryPotterBS = new Inventory(harryPotter, lisboaLib);
            Inventory harryPotterBS2 = new Inventory(harryPotter2, lisboaLib);
            Inventory harryPotterBS3 = new Inventory(harryPotter3, lisboaLib);
            Inventory harryPotterBS4 = new Inventory(harryPotter4, lisboaLib);
            Inventory harryPotterBS5 = new Inventory(harryPotter5, lisboaLib);
            Inventory harryPotterBS6 = new Inventory(harryPotter6, lisboaLib);
            Inventory harryPotterBS7 = new Inventory(harryPotter7, lisboaLib);
            inventoryRepository.saveAll(List.of(hobbitBS, hobbitBS2, harryPotterBS, harryPotterBS2,
                    harryPotterBS3, harryPotterBS4, harryPotterBS5, harryPotterBS6, harryPotterBS7));

            //LoanState
            LoanState borrowed = new LoanState(LoanStateEnum.BORROWED);
            LoanState returned = new LoanState(LoanStateEnum.RETURNED);

            loanStateRepository.saveAll(List.of(borrowed, returned));

            //LibraryCard
            LibraryCard libraryCardLeoLisboa = new LibraryCard(lisboaLib, Leo, LocalDateTime.now().minusDays(6).minusHours(23).minusMinutes(59));
            LibraryCard libraryCardMargLisboa = new LibraryCard(lisboaLib, Marg, LocalDateTime.now().minusDays(3));
            LibraryCard libraryCardLeoEvora = new LibraryCard(evoraLib, Leo);
            libraryCardRepository.saveAll(List.of(libraryCardLeoLisboa, libraryCardLeoEvora, libraryCardMargLisboa));

            //LibraryCardSubscription
            LibraryCardSubscription libCardSub = new LibraryCardSubscription(libraryCardLeoLisboa, LocalDateTime.now());
            LibraryCardSubscription libCardSub2 = new LibraryCardSubscription(libraryCardLeoLisboa, LocalDateTime.now().minusMonths(2));
            LibraryCardSubscription libCardSubEvora = new LibraryCardSubscription(libraryCardLeoEvora, LocalDateTime.now());
            LibraryCardSubscription libCardSubLisboaMarg = new LibraryCardSubscription(libraryCardMargLisboa, LocalDateTime.now());
            libraryCardSubscriptionRepository.saveAll(List.of( libCardSubEvora, libCardSubLisboaMarg));

            //Loans
            Loan loanHobbitB = new Loan(hobbitBS, borrowed, libraryCardLeoLisboa, LocalDateTime.now().minusDays(30));
            Loan loanHarryPotterB = new Loan(harryPotterBS, borrowed, libraryCardLeoLisboa, LocalDateTime.now().minusDays(28));
            Loan loanHobbitR = new Loan(hobbitBS, returned, libraryCardLeoLisboa, LocalDateTime.now().minusDays(26));
            Loan loanHarryPotterR = new Loan(harryPotterBS, returned, libraryCardLeoLisboa, LocalDateTime.now().minusDays(25));
            Loan loanHarryPotter2B = new Loan(harryPotterBS, borrowed, libraryCardLeoLisboa, LocalDateTime.now().minusDays(24));
            Loan loanHarryPotter3B = new Loan(harryPotterBS3, borrowed, libraryCardLeoLisboa, LocalDateTime.now().minusDays(20));
            Loan loanHarryPotter3R = new Loan(harryPotterBS3, returned, libraryCardLeoLisboa, LocalDateTime.now().minusDays(15));
            Loan loanHarryPotter4B = new Loan(harryPotterBS4, borrowed, libraryCardLeoLisboa, LocalDateTime.now().minusDays(10));
            Loan loanHarryPotter5B = new Loan(harryPotterBS5, borrowed, libraryCardMargLisboa, LocalDateTime.now().minusDays(5));

            loanRepository.saveAll(List.of(loanHobbitB, loanHarryPotterB, loanHobbitR, loanHarryPotterR,
                    loanHarryPotter2B, loanHarryPotter3B, loanHarryPotter3R, loanHarryPotter4B,
                    loanHarryPotter5B));

            System.out.println("LibraryCardId: " + libraryCardLeoLisboa.getLibraryCardId());
            System.out.println("Hobbit1 " + hobbitBS.getInventoryId());
            System.out.println("Hobbit2 " + hobbitBS2.getInventoryId());
            System.out.println("HarryPotter " + harryPotterBS.getInventoryId());

            // Campaigns

            Campaign harryPotterFans = new Campaign(
                    "HarryPotterCon",
                    "Meet and greet for harry potter fans and newcomers to the series",
                    lisboaLib,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMonths(1),
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/harry-con.png"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images/harry-con.png").getFile()).toPath())
            );

            Campaign tolkienMeeting = new Campaign(
                    "Tolkien Meeting",
                    "Join us to discuss all of tolkiens work, a good chance to read them if you haven't!",
                    lisboaLib,
                    LocalDateTime.now().plusMonths(1),
                    LocalDateTime.now().plusMonths(2),
                    IOUtils.toByteArray(classLoader.getResourceAsStream("images/tolkien.jpg"))
//                    Files.readAllBytes(
//                            new File(classLoader.getResource("images/tolkien.jpg").getFile()).toPath())
            );

            campaignRepository.saveAll(List.of(harryPotterFans, tolkienMeeting));

            // Campaign Subscriptions

            CampaignSubscriber hpFanLeo = new CampaignSubscriber(harryPotterFans, libraryCardLeoLisboa);
            CampaignSubscriber hpFanMarg = new CampaignSubscriber(harryPotterFans, libraryCardMargLisboa);

            campaignSubscriberRepository.saveAll(List.of(hpFanLeo, hpFanMarg));
        };



    }
}
