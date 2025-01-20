# Cho-Han

Cho Han ist ein Glücks-Würfelspiel aus Japan

## Dokumentation

Die ganze Dokumentation ist in dem Cho-Han Doku Ordner

## Installation 

1. Repository clonen
2. Run Configuration Server: connection.Server
3. Run Configuration Client: connection.Client    Modify options -> allow multiple instances

## Regeln

Die Teilnehmer wetten auf die Augenzahl der 2 Würfel, dabei kann man entweder auf eine 
gerade Würfelzahl, eine ungerade Würfelzahl oder auf eine exakte Würfelzahl, zum Beispiel 
“8”, wetten. Dann würfelt der Dealer. Falls man richtig getippt hat, kriegt man seinen 
Einsatz und noch mehr zurück, je nachdem auf was man gewettet hat (beim Wetten auf 
eine genaue Zahl bekommt man natürlich mehr zurück), ansonsten verliert man seinen 
Einsatz. 

## User Guide

1. Beim Öffnen des Programmes kann man sich mit einem Server zum Spielen verbinden 
indem man sich einloggt oder registriert (bei einem nicht vorhandenen Username wird 
automatisch ein neuer Spieler registriert, man muss also nicht extra zwischen einer 
einloggen oder registrieren Option auswählen), man hat auch die Auswahl das Programm 
wieder zu beenden. 

2. Nachdem man sich mit einem “Username” und “Password” eingeloggt oder registriert 
hat wird man mit dem Server verbunden und automatisch einer Lobby hinzugefügt, 
existiert zu dem Zeitpunkt keine geeignete Lobby wird eine neue Lobby erstellt. Man 
bekommt dann seine aktuelle Balance zurückgegeben und eine Liste an 
Auswahlmöglichkeiten:

        1)Bet odd
        2)Bet Even
        3)Bet die count
        4)Skip round
        5)Exit lobby


3. Bei bet odd/even/die count muss man anschließend seinen Betrag angeben mit dem 
man wettet, bei bet die count muss man anschließen angeben auf welche Zahl man wettet 
(2-12) 

4. Nach einem festen Zeitintervall würfelt der Server und informiert die Nutzer über den 
Wurf, dazu wird zurückgegeben ob es ein even oder odd Wurf war, die genaue Würfelzahl 
und die aktuelle Balance des Spielers nach Abziehen/Überweisen des Einsatzes. 
Anschließend bekommt man die Auswahlmöglichkeiten wieder ausgegeben und das 
Ganze wiederholt sich. 
