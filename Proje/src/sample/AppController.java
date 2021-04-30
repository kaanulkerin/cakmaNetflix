package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Time;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    String url = "jdbc:sqlite:proje_database.db";
    Main mainobject= new Main();
    String user= mainobject.user;
    String password= mainobject.password;
    static int seconds = 0;
    static int minutes = 0;
    static int milliseconds = 0;
    static int hours = 0;
    static boolean isStart = true;
    int programid=0;
    int userid=0;
    Timeline timeline;
    LocalTime time = LocalTime.parse("00:00:00");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    int episodenumber=1;
    int oldepisodenumbref=100;

    @FXML
    private ImageView Backgroundid;
    @FXML
    private Button watch;
    @FXML
    private Label Lastepisodetext;
    @FXML
    private TextField Textfield;
    @FXML
    private Button checktype;
    @FXML
    private Button checkname;
    @FXML
    private ListView<String> listview;
    @FXML
    private ChoiceBox<Integer> takePoint;
    @FXML
    private Button GivePoint;
    @FXML
    private Label episodenum;
    @FXML
    private Label timeminute;
    @FXML
    private ImageView tv;
    @FXML
    private Button Stop;
    @FXML
    private Button Menu;
    @FXML
    private Label episodenumtext;
    @FXML
    private Label timetext;
    @FXML
    private Label Pointtext;
    @FXML
    private Button Alllist;
    @FXML
    private Label Episodestext;
    @FXML
    private ChoiceBox<Integer> Episodes;
    String ProgramName;
    @FXML
    private Button Chooseepisode;
Controller control = new Controller();
    private static final String SELECT_QUERYwatchmovie = "insert into kullanıcıprogramtablosu (userID,programID)"+ "values( ? , ?) ;";
    private static final String SELECT_QUERYgetid = "select userID from usertablosu where userMail = ? ;";
    private static final String SELECT_QUERYgetid2 = "select programID from programtablosu where programAdı= ? ;";
    private static final String SELECT_QUERYsetdatenow = "update kullanıcıprogramtablosu\n" +
            "set sonİzlemeTarihi = date('now')\n" +
            "where userID=? and programID=?;";
    private static final String SELECT_QUERYepisode = "update kullanıcıprogramtablosu \n" +
            "set sonİzlenenBölüm = ?  where userID = ? and programID = ? ;";
    private static final String SELECT_QUERYizlendi = "update kullanıcıprogramtablosu \n" +
            "set İzlendi = ?  where userID = ? and programID = ? ;";
    private static final String SELECT_QUERYizlendimi = "select  İzlendi FROM kullanıcıprogramtablosu where userID=? and programID=?;";
    private static final String SELECT_QUERYlastepisode = "SELECT sonİzlenenBölüm FROM kullanıcıprogramtablosu where userID=? and programID=?;";
    private static final String SELECT_QUERYoldtime = "select sonKalınanYer from kullanıcıprogramtablosu where userID =? and programID=?";
    @FXML
    void GoMovie(MouseEvent event) {
        System.out.println(control.Passwordnow);
        System.out.println(control.nickname);
        String Choosen = listview.getSelectionModel().getSelectedItem().toString();
        ProgramName=Choosen;
        watch.setVisible(true);
        timeminute.setVisible(false);
        timetext.setVisible(false);
        Menu.setVisible(true);
        episodenum.setVisible(false);
        episodenumtext.setVisible(false);
        Episodes.setVisible(false);
        GivePoint.setVisible(false);
        takePoint.setVisible(false);
        Pointtext.setVisible(false);
        Stop.setVisible(false);
        Episodestext.setVisible(false);
        Chooseepisode.setVisible(false);
        episodenum.setText("1");
        timeminute.setText("");
        int oldepisodenumb=0;
        Lastepisodetext.setVisible(false);
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYgetid);){

            preparedStatement.setString(1, control.nickname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userid=resultSet.getInt(1);
            }
        } catch (SQLException e) {
        }
        try (  Connection conn = DriverManager.getConnection(url);
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYgetid2);){

            preparedStatement.setString(1, ProgramName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                programid=resultSet.getInt(1);
            }
        } catch (SQLException e) {
        }
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYlastepisode);){

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, programid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 System.out.println(resultSet.getString("sonİzlenenBölüm"));
                 Lastepisodetext.setText("En son izlenen bölüm: "+resultSet.getString("sonİzlenenBölüm"));
                oldepisodenumb=resultSet.getInt(1);
                oldepisodenumbref=oldepisodenumb;
                System.out.println(oldepisodenumb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
if(oldepisodenumb==2|| oldepisodenumb==3||oldepisodenumb==4||oldepisodenumb==5||oldepisodenumb==6||oldepisodenumb==7||oldepisodenumb==8||oldepisodenumb==9||oldepisodenumb==10)
{
    Lastepisodetext.setVisible(true);
}
        if (Choosen.compareTo("Recep İvedik 6") == 0) {
            System.out.println(Choosen);
            File file1 = new File("recep ivedik.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Assasins Creed ") == 0) {
            System.out.println(Choosen);
            File file1 = new File("assasıns creed.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Alaca Karanlık") == 0) {
            System.out.println(Choosen);
            File file1 = new File("alacakaranlık.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Yüzüklerin Efendisi İki Kule") == 0) {
            System.out.println(Choosen);
            File file1 = new File("yüzüklerin efendisi iki kule.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Maske") == 0) {
            System.out.println(Choosen);
            File file1 = new File("maske.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Kara Şövalye") == 0) {
            System.out.println(Choosen);
            File file1 = new File("kara şövalye.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Sherlock Holmes") == 0) {
            System.out.println(Choosen);
            File file1 = new File("sherlock.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Yüzüklerin Efendisi Kralın Dönüşü") == 0) {
            System.out.println(Choosen);
            File file1 = new File("Yüzüklerin efendisi kralın dönüşü.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Transformers Kayıp Çağ") == 0) {
            System.out.println(Choosen);
            File file1 = new File("transformers kayıp çağ.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Başlangıç") == 0) {
            System.out.println(Choosen);
            File file1 = new File("başlangıç.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Interstellar") == 0) {
            System.out.println(Choosen);
            File file1 = new File("ınterstellar.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Harry Potter Ölüm Yadigarları") == 0) {
            System.out.println(Choosen);
            File file1 = new File("hp ölüm yadigarları.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Jurassic World") == 0) {
            System.out.println(Choosen);
            File file1 = new File("jurrasik world.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Fantastik Canavarlar") == 0) {
            System.out.println(Choosen);
            File file1 = new File("fantastik canavarlar.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Ninja Kaplumbağalar") == 0) {
            System.out.println(Choosen);
            File file1 = new File("ninja turtles.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Kuşlarla Dans") == 0) {
            System.out.println(Choosen);
            File file1 = new File("kuşlarla dans.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Mission Blue") == 0) {
            System.out.println(Choosen);
            File file1 = new File("mission blue.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Mercan Peşinde") == 0) {
            System.out.println(Choosen);
            File file1 = new File("mercan  peşinde.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Dream Big") == 0) {
            System.out.println(Choosen);
            File file1 = new File("dream big.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Aydaki Son Adam") == 0) {
            System.out.println(Choosen);
            File file1 = new File("aydaki son adam.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Plastik Okyanus") == 0)
        {
            System.out.println(Choosen);
            File file1 = new File("plastik okyanus.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Rakamlarla Tahmin") == 0) {
            System.out.println(Choosen);
            File file1 = new File("rakamlarla tahmin.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Ben Efsaneyim") == 0) {
            System.out.println(Choosen);
            File file1 = new File("ben efsaneyim.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Arif V 216") == 0) {
            System.out.println(Choosen);
            File file1 = new File("arif v 216.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("pk") == 0) {
            System.out.println(Choosen);
            File file1 = new File("pk.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Örümcek Adam") == 0) {
            System.out.println(Choosen);
            File file1 = new File("örümcek adam.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Jurassic Park") == 0) {
            System.out.println(Choosen);
            File file1 = new File("jurassıc park.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Frankestein") == 0) {
            System.out.println(Choosen);
            File file1 = new File("frankeştayn.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Gezegenimiz") == 0) {
            System.out.println(Choosen);
            File file1 = new File("gezegenimiz.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("72 Sevimli Hayvan") == 0) {
            System.out.println(Choosen);
            File file1 = new File("72 sevimli.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Kuşçular") == 0) {
            System.out.println(Choosen);
            File file1 = new File("kuşçular.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Marsta Keşif") == 0) {
            System.out.println(Choosen);
            File file1 = new File("marsta keşif.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Pandemic") == 0) {
            System.out.println(Choosen);
            File file1 = new File("pandemic.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Pokemon") == 0) {
            System.out.println(Choosen);
            File file1 = new File("pokemon.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Şirinler") == 0) {
            System.out.println(Choosen);
            File file1 = new File("şirinler asıl.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Charlienin Çikolata Fabrikası") == 0) {
            System.out.println(Choosen);
            File file1 = new File("çarli.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Alvin ve Sincaplar") == 0) {
            System.out.println(Choosen);
            File file1 = new File("alvin ve sincaplar.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Scooby-Doo") == 0) {
            System.out.println(Choosen);
            File file1 = new File("scooby.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Kung Fu Panda") == 0) {
            System.out.println(Choosen);
            File file1 = new File("kung fu panda.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Mr.Bean Tatilde") == 0) {
            System.out.println(Choosen);
            File file1 = new File("mr bean tatil.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Shrek") == 0) {
            System.out.println(Choosen);
            File file1 = new File("shrek.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Mega Zeka") == 0) {
            System.out.println(Choosen);
            File file1 = new File("megamind.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Bizi Hatırla") == 0) {
            System.out.println(Choosen);
            File file1 = new File("bizi hatırla.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Delibal") == 0) {
            System.out.println(Choosen);
            File file1 = new File("delibal.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Kardeşim Benim") == 0) {
            System.out.println(Choosen);
            File file1 = new File("kardeşim benim.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Dangal") == 0) {
            System.out.println(Choosen);
            File file1 = new File("dangal.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Yerçekimi") == 0) {
            System.out.println(Choosen);
            File file1 = new File("YERÇEKİMİ.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Jaws") == 0) {
            System.out.println(Choosen);
            File file1 = new File("JAWS.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Da Vinci Şifresi") == 0) {
            System.out.println(Choosen);
            File file1 = new File("davinci.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);

        }
        if (Choosen.compareTo("Marvels Iron Fist") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("ironfist.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Ejderhalar") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("ejderhalar.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Diriliş Ertuğrul") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("diriliş.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Trol Avcıları:Arcadia Hikayeleri") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("Trollhunters.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("How I Met Your Mother") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("how ı met your mother.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Leyla ile Mecnun") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("leyla ile mecnun.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Beni Böyle Sev") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("beni böyle sev.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Patron Bebek Yine İş Başında") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("patron bebek.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Atiye") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("atiye.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Maşa ve Koca Ayı") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("maşa.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Sünger Bob") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("sünger.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Stranger Things") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("st.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("The Originals") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("the originals.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Angry Birds") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("angry.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Criminal") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("criminal.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Beyblade") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("beyblade.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Sonic X") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("sonic x.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Kung Fu Panda Muhteşem Sırlar") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("kung fu muhteşem sırlar.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("The Blacklist") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("blacklist.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Dünyanın En Sıra Dışı Evleri") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("dünyanın en sıra dışı evleri.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Car Masters") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("car masters.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Büyük Tasarımlar") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("büyük tasarımlar.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Basketball or Nothing") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("basketball.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("The Big Family Cooking") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("the big family.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("Sıradışı Kulübeler") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("sıradışı kulübeler.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
        if (Choosen.compareTo("The Witcher") == 0) {
            episodenum.setVisible(true);
            episodenumtext.setVisible(true);
            Episodes.setVisible(true);
            Episodestext.setVisible(true);
            Chooseepisode.setVisible(true);
            watch.setVisible(false);
            System.out.println(Choosen);
            File file1 = new File("witcher.jpg");
            Image image1 = new Image(file1.toURI().toString());
            Backgroundid.setImage(image1);
        }
    }
    private static final String SELECT_QUERYpoint = "update kullanıcıprogramtablosu \n" +
            "set Puan = ? where userID = ? and programID = ?;";
    @FXML
    void PointAction(ActionEvent event) {
        System.out.println(takePoint.getValue().toString());
        Pointtext.setVisible(true);
        takePoint.setVisible(false);
        GivePoint.setVisible(false);
        Pointtext.setText("Verdiğiniz Puan: " + takePoint.getValue().toString());
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYpoint);){
            preparedStatement.setInt(1,takePoint.getValue().intValue());
            preparedStatement.setInt(2, userid);
            preparedStatement.setInt(3, programid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }
    private static final String SELECT_QUERY = "select * from programtablosu inner join programtürtablosu\n" +
            "on programtablosu.programID = programtürtablosu.programID inner join türtablosu\n" +
            "on programtürtablosu.türID=türtablosu.türID where türtablosu.türAdı = ?;";
    private static final String SELECT_QUERY2 = "select * from programtablosu";
    private static final String SELECT_QUERY3 = "select * from programtablosu where programAdı= ?;";
    @FXML
    void nameaction(ActionEvent event) {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY3);){

            preparedStatement.setString(1, Textfield.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            listview.getItems().clear();
            while (resultSet.next()) {
                listview.getItems().add(resultSet.getString("programAdı"));
            }
        } catch (SQLException e) {
        }
    }
    @FXML
    void typeaction(ActionEvent event) {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);){

            preparedStatement.setString(1, Textfield.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            listview.getItems().clear();
            while (resultSet.next()) {
                listview.getItems().add(resultSet.getString("programAdı"));
            }
        } catch (SQLException e) {
        }
    }
    @FXML
    void Allaction(ActionEvent event) {
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY2);) {

            ResultSet resultSet = preparedStatement.executeQuery();
            listview.getItems().clear();
            while (resultSet.next()) {
                listview.getItems().add(resultSet.getString("programAdı"));
            }
        } catch (SQLException e) {
        }
    }
    @FXML
    void returnmenu(ActionEvent event) {
        watch.setVisible(false);
        Stop.setVisible(false);
        timeminute.setVisible(false);
        timetext.setVisible(false);
        episodenum.setVisible(false);
        episodenumtext.setVisible(false);
        Menu.setVisible(false);
        GivePoint.setVisible(false);
        takePoint.setVisible(false);
        Episodes.setVisible(false);
        tv.setVisible(false);
        Alllist.setVisible(false);
        Episodestext.setVisible(false);
        Chooseepisode.setVisible(false);
        Pointtext.setVisible(false);
        listview.setVisible(true);
        Textfield.setVisible(true);
        checktype.setVisible(true);
        checkname.setVisible(true);
        Alllist.setVisible(true);
        Lastepisodetext.setVisible(false);
        File file1 = new File("default.jpg");
        Image image1 = new Image(file1.toURI().toString());
        Backgroundid.setImage(image1);
    }
    int bölümsayısı;
    @FXML
    void Goepisode(ActionEvent event) {
        episodenum.setText(Episodes.getValue().toString());
        watch.setVisible(true);
        episodenumber=Episodes.getValue().intValue();
        bölümsayısı=episodenumber;
    }
    public void initialize(URL url, ResourceBundle rb) {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    private void incrementTime() {
        time = time.plusSeconds(1);
        timeminute.setText(time.format(dtf));
    }
    private static final String SELECT_QUERYtime = "update kullanıcıprogramtablosu \n" +
            "set sonKalınanYer = ?  where userID = ? and programID = ? ;";
    private static final String SELECT_QUERYisShow = "SELECT programtipi from programtablosu where programID=?;";
    @FXML
    void Stopaction(ActionEvent event) {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYisShow);){
            preparedStatement.setInt(1, programid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(resultSet.getString(1).compareTo("Film")==0)
                {
                    watch.setVisible(true);
                }
                else
                {
                    watch.setVisible(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Stop.setVisible(false);
        tv.setVisible(false);
        listview.setVisible(true);
        Textfield.setVisible(true);
        checktype.setVisible(true);
        checkname.setVisible(true);
        Alllist.setVisible(true);
        Menu.setVisible(true);
        timetext.setVisible(false);
        timeminute.setVisible(false);
        GivePoint.setVisible(false);
        takePoint.setVisible(false);



        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYisShow);){
            preparedStatement.setInt(1, programid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(resultSet.getString(1).compareTo("Film")==0)
                {

                }
                else
                {
                    Episodestext.setVisible(true);
                    Episodes.setVisible(true);
                    Chooseepisode.setVisible(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Time sentime= Time.valueOf(time);
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYtime);){
            System.out.println(sentime.toString());
            preparedStatement.setString(1,sentime.toString());
            preparedStatement.setInt(2, userid);
            preparedStatement.setInt(3, programid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
        if (isStart==true) {
            timeline.stop();
            isStart=false;
            time = LocalTime.parse("00:00:00");
            timeminute.setText(time.format(dtf));
        }
    }
    @FXML
    void watchAction(ActionEvent event) {
        Episodestext.setVisible(false);
        Chooseepisode.setVisible(false);
        Episodes.setVisible(false);
        GivePoint.setVisible(true);
        takePoint.setVisible(true);
        Stop.setVisible(true);
        watch.setVisible(false);
        tv.setVisible(true);
        listview.setVisible(false);
        Textfield.setVisible(false);
        checkname.setVisible(false);
        checktype.setVisible(false);
        Alllist.setVisible(false);
        Menu.setVisible(false);
        Pointtext.setVisible(false);
        timeline.play();
        isStart = true;
        timeminute.setVisible(true);
        timetext.setVisible(true);
        Lastepisodetext.setVisible(false);

        try (  Connection conn = DriverManager.getConnection(url);
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYgetid);){

            preparedStatement.setString(1, control.nickname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userid = resultSet.getInt(1);
            }
        } catch (SQLException e) {
        }

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYgetid2);){

            preparedStatement.setString(1, ProgramName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                   programid = resultSet.getInt(1);
            }
        } catch (SQLException e) {
        }

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYwatchmovie);){
            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, programid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYsetdatenow);) {

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, programid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       int izlendimi=0;
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYizlendimi);){

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, programid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                izlendimi=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try ( Connection conn = DriverManager.getConnection(url);
              PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYizlendi);){
            preparedStatement.setInt(1,1);
            preparedStatement.setInt(2, userid);
            preparedStatement.setInt(3, programid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
         Boolean isShow =false;
        Boolean isMovie= false;
         try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYisShow);){
            preparedStatement.setInt(1, programid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(resultSet.getString(1).compareTo("Film")==0)
                {
                    isShow=false;
                    isMovie=true;
                }
                else
                {
                    isShow=true;
                    isMovie=false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYlastepisode);){

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, programid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("sonİzlenenBölüm"));
                Lastepisodetext.setText("En son izlenen bölüm: "+resultSet.getString("sonİzlenenBölüm"));
                oldepisodenumbref=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if( isShow== true) {

            try(Connection conn = DriverManager.getConnection(url);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYepisode);) {

                preparedStatement.setInt(1, episodenumber);
                preparedStatement.setInt(2, userid);
                preparedStatement.setInt(3, programid);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
            }
        }
        else{
            try(Connection conn = DriverManager.getConnection(url);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYepisode);) {
                episodenumber=1;
                preparedStatement.setInt(1, episodenumber);
                preparedStatement.setInt(2, userid);
                preparedStatement.setInt(3, programid);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
            }
        }

        if (episodenumber==oldepisodenumbref ) {
            try(Connection conn = DriverManager.getConnection(url);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYoldtime);) {

                preparedStatement.setInt(1, userid);
                preparedStatement.setInt(2, programid);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    time =time.parse(resultSet.getString(1));
                }
            } catch (SQLException  e) {
                e.printStackTrace();
            }
        }
    }
}