package co.edu.uptc.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private Properties props;
    private final String CONFIG_PATH = "config.properties";

    private void loadCongfig(){
        try (FileInputStream input = new FileInputStream(CONFIG_PATH)){
            props = new Properties();
            props.load(input);

        } catch (Exception e){
        }
    }
}
