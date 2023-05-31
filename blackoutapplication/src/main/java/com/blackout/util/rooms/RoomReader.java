package com.blackout.util.rooms;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomReader {

  public static ObservableList<Room> readRoomsFromFile(String filename) {
    ArrayList<Room> rooms = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      StringBuilder jsonContent = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        jsonContent.append(line);
      }

      String jsonString = jsonContent.toString();
      JsonParser parser = new JsonParser();
      JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
      JsonArray roomArray = jsonObject.getAsJsonArray("rooms");

      Gson gson = new Gson();
      for (int i = 0; i < roomArray.size(); i++) {
        JsonObject roomObject = roomArray.get(i).getAsJsonObject();
        Room room = gson.fromJson(roomObject, Room.class);
        rooms.add(room);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return FXCollections.observableArrayList(rooms);
  }
}

