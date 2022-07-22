package top.ycao.enp;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ENPInitializer implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "enp";
    public static final String MOD_NAME = "EndermanNoPickup";

    @Override
    public void onInitialize() {
        log(Level.INFO, "ENP loaded.");
        //TODO: Initializer
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}