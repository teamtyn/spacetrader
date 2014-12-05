/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader.voice;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import javafx.concurrent.Task;
import spacetrader.ComputerController;

public class VoiceRecognizer {
    private static Microphone microphone;
    private static edu.cmu.sphinx.recognizer.Recognizer recognizer;
    private static boolean listen = true;
    private static boolean timer = true;
    private static Thread voiceThread;
    public static void load() {
        
        ConfigurationManager cm = new ConfigurationManager(VoiceRecognizer.class.getResource("spacetrader.config.xml"));

        recognizer = (edu.cmu.sphinx.recognizer.Recognizer) cm.lookup("recognizer");
        recognizer.allocate();
        microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            return;
//        } else { microphone.stopRecording();}
        }
        //voiceThread = createThreadToRecognizeVoice();

    }
    
    public static void prepare() {
        ConfigurationManager cm = new ConfigurationManager(VoiceRecognizer.class.getResource("spacetrader.config.xml"));

        recognizer = (edu.cmu.sphinx.recognizer.Recognizer) cm.lookup("recognizer");
        recognizer.allocate();
        microphone = (Microphone) cm.lookup("microphone");
//        if (!microphone.startRecording()) {
//            System.out.println("Cannot start microphone.");
//            recognizer.deallocate();
//            return;
//        } else { microphone.stopRecording();}
//        }
    }
    
    public static void setListen(boolean newListen) {
        listen = newListen;
    }
    
    public static String recognizeVoice() {
        String stringResult = "";


            timer = true;

                while(timer) {

                    Result result = recognizer.recognize();
                    if(result != null) {
                        timer = false;
                        stringResult = result.getBestFinalResultNoFiller();
                    }
                }
 
            
            return stringResult;
    }
    
    public static boolean isVoiceRecognitionRunning() {
        return recognizer.getState() == Recognizer.State.RECOGNIZING;
    }

    public static void quitVoiceRecognition() {
        if(recognizer.getState() == Recognizer.State.ALLOCATED) {
            recognizer.deallocate();
        }
        if(microphone.isRecording()) {
            microphone.stopRecording();
        }
    }
    
    public static void startThreadToRecognizeVoice() {
        if(!voiceThread.isAlive()) {
            voiceThread.start();
        }
    }
    
    public static void stopThreadToRecognizeVoice() {
        voiceThread.stop();
    }
    
    public static Thread createThreadToRecognizeVoice() {
                Task<String> recognize = new Task<String>() {
            
            @Override protected String call() throws Exception {
                String string = recognizeVoice();
                System.out.println(string);
                //System.out.println()
                return recognizeVoice();
            }
        };

        Thread thread = new Thread(recognize);
        thread.setDaemon(true);
        return thread;
    }
}