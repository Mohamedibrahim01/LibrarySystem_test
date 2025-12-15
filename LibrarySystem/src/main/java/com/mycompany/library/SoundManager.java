/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library;

import javafx.scene.media.AudioClip;
import java.net.URL;

/**
 *
 * @author Hassan
 */
public class SoundManager {
    private static final String SUCCESS_SOUND = "/com/mycompany/library/sounds/success.wav";
    private static final String ERROR_SOUND = "/com/mycompany/library/sounds/error.wav";
    private static final String RETURN_SOUND = "/com/mycompany/library/sounds/return.wav";

    // دالة عامة لتشغيل أي صوت
    private static void playSound(String fileName) {
        try {
            URL resource = SoundManager.class.getResource(fileName);
            if (resource != null) {
                AudioClip clip = new AudioClip(resource.toString());
                clip.play();
            } else {
                System.out.println("⚠️ Sound file not found: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    // دوال جاهزة للاستدعاء
    public static void playSuccess() {
        playSound(SUCCESS_SOUND);
    }

    public static void playError() {
        playSound(ERROR_SOUND);
    }
    
    public static void playReturn() {
        playSound(RETURN_SOUND);
    }
}
