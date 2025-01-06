package database;

import gameLogic.Player;
import networkControllerServer.TCPServer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final String FILE_PATH = "server/src/database/users.txt";


    // Login-Validierung
    public static boolean validateLogin(String username, String password) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String fileUsername = parts[0]; //Username ist erster Teil in txt Datei
                    String filePassword = parts[1]; //Passwort ist zweiter Teil in txt Datei

                    if (fileUsername.equals(username)) {
                        return filePassword.equals(password);
                    }
                }
            }

            // Benutzer nicht vorhanden, erstelle neuen Eintrag
            System.out.println("User doesnt exist, creating new entry");
            addUser(username, password);
            return true;

        } catch (IOException e) {
            System.err.println("Error validating login: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Spieler erstellen und speichern
    private static void addUser(String username, String password) {
        Player player = new Player(username, password);
        saveUser(player);
    }

    // Spieler anhand des Benutzernamens abrufen
    public static Player getPlayer(String username) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String fileUsername = parts[0];
                    if (fileUsername.equals(username)) {
                        String filePassword = parts[1];
                        double balance = Double.parseDouble(parts[2]);
                        return new Player(fileUsername, filePassword, balance);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Benutzer speichern
    public static void saveUser(Player player) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, true), StandardCharsets.UTF_8))) {
            String line = player.getName() + ";" + player.getPassword() + ";" + player.getBalance();
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
        }
    }
/*
    // Benutzer laden
    public static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    users.put(username, new Player(username, password, balance));
                } else {
                    System.err.println("Ungültige Zeile in der Datei: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Keine Benutzerdaten gefunden, neue Datenbank wird erstellt.");
            users = new HashMap<>();
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Benutzer: " + e.getMessage());
            e.printStackTrace();
        }
    }

 */

    // Benutzer ausgeben (Debugging-Zwecke)
    public static void printUsers() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String username = parts[0];
                    double balance = Double.parseDouble(parts[2]);
                    System.out.println("Username: " + username + ", Balance: " + balance);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error rendering users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Balance eines Benutzers aktualisieren
    public static void updateBalance(String username, double newBalance) {
        File userFile = new File(FILE_PATH);
        StringBuilder fileContent = new StringBuilder();
        boolean userFound = false;

        try {
            // Datei Zeile für Zeile lesen
            if (userFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(userFile), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (parts.length == 3) {
                            String fileUsername = parts[0];
                            if (fileUsername.equals(username)) {
                                // Balance aktualisieren
                                line = username + ";" + parts[1] + ";" + newBalance;
                                userFound = true;
                            }
                        }
                        fileContent.append(line).append(System.lineSeparator());
                    }
                }
            }

            // Falls Benutzer nicht gefunden, Nachricht ausgeben
            if (!userFound) {
                System.out.println("Username not found: " + username);
                return;
            }

            // Datei mit aktualisiertem Inhalt neu schreiben
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userFile), StandardCharsets.UTF_8))) {
                writer.write(fileContent.toString());
            }

        } catch (IOException e) {
            System.err.println("Error updating balance: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
