package database;

import gameLogic.Player;
import networkControllerServer.TCPServer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final String FILE_PATH = "users.txt";
    private static Map<String, Player> users = new HashMap<>();


static {
    users.put("jan", new Player("jan","123"));
}
    // Login-Validierung
    public static boolean validateLogin(String username, String password) {
        if (!users.containsKey(username)) {
            Player player = new Player(username, password);
            users.put(username, player);
            saveUsers();
            return username != null && password != null;
        }
        Player player = users.get(username);
        return player != null && player.getPassword().equals(password);
    }

    // Spieler anhand des Benutzernamens abrufen
    public static Player getPlayer(String username) {
        return users.get(username);
    }

    // Benutzer speichern
    private static boolean saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            // Temporäre Map ohne TCPServer-Referenzen
            Map<String, Player> tempUsers = new HashMap<>();
            for (Map.Entry<String, Player> entry : users.entrySet()) {
                Player player = entry.getValue();
                tempUsers.put(entry.getKey(), new Player(player.getName(), player.getPassword(), player.getBalance()));
            }
            oos.writeObject(tempUsers);
            return true;
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Benutzer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Benutzer laden
    @SuppressWarnings("unchecked")
    public static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (Map<String, Player>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Keine Benutzerdaten gefunden, neue Datenbank wird erstellt.");
            users = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Fehler beim Laden der Benutzer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Benutzer ausgeben (Debugging-Zwecke)
    public static void printUsers() {
        users.forEach((username, player) -> {
            System.out.println("Benutzername: " + username + ", Balance: " + player.getBalance());
        });
    }
}
