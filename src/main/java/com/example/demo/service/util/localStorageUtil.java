package com.example.demo.service.util;

import com.example.demo.service.dto.cart.CartItemDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.prefs.Preferences;

public class localStorageUtil {
    private static final String LOCAL_STORAGE_KEY = "cartItems";

    public static void saveToLocalStorage(List<CartItemDTO> cartItems) {
        try {
            Preferences preferences = Preferences.userRoot().node(localStorageUtil.class.getName());
            ObjectMapper mapper = new ObjectMapper();
            String cartItemsJson = mapper.writeValueAsString(cartItems);
            preferences.put(LOCAL_STORAGE_KEY, cartItemsJson);
            System.out.println("Data is saved to local storage.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<CartItemDTO> readFromLocalStorage() {
        try {
            Preferences preferences = Preferences.userRoot().node(localStorageUtil.class.getName());
            String cartItemsJson = preferences.get(LOCAL_STORAGE_KEY, "");
            if (!cartItemsJson.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(cartItemsJson, new TypeReference<List<CartItemDTO>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}