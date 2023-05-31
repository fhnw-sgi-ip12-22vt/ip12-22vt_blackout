package com.blackout;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


// Neuer Test mit template "unittest" erstellen
public class DummyTest {
  // Test Methode mit "utest" erstellen einfach in Klasse schreiben und Intellij schlägt das template vor
  @Test
  public void runDummyTest() {
    // assert methode immer statisch importieren
    assertTrue(true);
  }
}

/*
Unittests
=========

Unittest sollen kleine Komponenten testen (bsp. Methoden). Anhand des Features werden alle
Komponenten des Features separat in eine Unittest überprüft. Diese Tests werden automatisch
durch eine Pipeline ausgeführt (Eine Pipeline ist eine fest definiert Reihenfolgen von Operationen,
welche nach eine Event bspw. einem Push ausgeführt wird, Siehe mehr bei der Dev Dokument) oder
können manuell von einem Entwickler lokal angestossen werden um frühzeitig Regressionen
oder Fehler zu entdecken.
Dafür sollen sie folgendermassen aufgebaut sein:
Sprechender und sinnvollen Namen (der Name soll beschreiben, was der Testfall macht)
Für Argumente sollen Mocks oder kontrollierte Daten übergeben werden
Einheiten oder Methoden, welche getestet werden müssen
Der Testfall soll nach dem "Given When Then Pattern" aufgebaut sein
Eine Behauptung anwenden
Sie müssen isoliert durchführbar sein
Die Vor- und Nachbedingungen sollen aus dem Kommentar der zu testenden Methoden abgeleitet werden

Beispiel Java
public class Tests {
  @Test
  public void testisOverdrawnBalance500AssertTrue() throws Throwable {
    // Given
    Customer customer = mockCustomer();
    int initialBalance = 0;
    Account underTest = new Account(customer, initial_balance);

    // When
    int balance = -500;
    boolean result = underTest.isOverdrawn(balance);

    // Then
    assertTrue(result);
  }
}

Hilfe Unittests
---------------
Damit das Schreiben der Test einfacher ist und der Norm entspricht, gibt es im Entwickler Projekt ein Template dazu.
Dateien → UnitTest (Erstellt eine Unittestdatei)
Methode → uTest (Erstellt ein Testmethode)

mehr unter:
https://gitlab.fhnw.ch/ip12-22vt/ip12-22vt_wechselspiel/docu/-/blob/main/04_testing/testing.adoc
*/
