package com.hendersonkleber.weatherforecast.util;

import java.text.Normalizer;

public class TextUtil {
    public static String normalize(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
    }
}
