/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.deserializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.compucom.serviceops.tsheetsapi.json.TimesheetJson;
import com.compucom.serviceops.tsheetsapi.model.Timesheet;
import com.compucom.serviceops.tsheetsapi.service.TimesheetService;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * @author Mark Gottschling on Apr 10, 2017
 *
 */
public class TimesheetJsonDeserializer implements JsonDeserializer<TimesheetJson> {
	private static final Logger logger = LogManager.getLogger(TimesheetJsonDeserializer.class);
	
    @Override
    public TimesheetJson deserialize(JsonElement json, Type paramType,
            JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {

    	/*
    	 *  deserialize the timesheet according to it's annotation mappings
    	 *  NOTE "customfields" is not mapped by annotations. it is deserialized below.
    	 *  it's value may be a map(json array) or an empty string
    	 */    	
        TimesheetJson timesheet = new Gson().fromJson(json.getAsJsonObject(), TimesheetJson.class);

        try {
        	// convert json into an object
        	JsonObject mainObj = json.getAsJsonObject();

        	// if the customfields property exists
            if (mainObj.get("customfields") != null) {            	
            	// attempt to get the customfields element as an object
            	JsonObject obj = null;
            	try {
            		obj = mainObj.get("customfields").getAsJsonObject();
            	}
                catch(IllegalStateException ie) {
                	// attempt to get as a string
                	String fieldAsString = mainObj.get("customfields").getAsString();     
                	// set the map to null
                    timesheet.setCustomFields(null); 
                    return timesheet;
                }
            	
            	// if it is an object, process it like a map
                if (obj != null) {
                	// deserialize the custom fields map
                    Map<String, String> map = new HashMap<String, String>();
                    for (Map.Entry<String, JsonElement> entry : obj.entrySet())  {
                        String value = entry.getValue().getAsString();
                        map.put(entry.getKey(), value);
                    }
                    // update the timesheet custom fields field
                    timesheet.setCustomFields(map);
                }
            }
        }
        catch (IllegalArgumentException ie) {
            logger.error("Custom Fields cannot be deserialized ...", ie);
            System.out.println(ie.getMessage());
            System.out.println("Custom Fields cannot be deserialized ...");
            timesheet.setCustomFields(null);            
        }
        return timesheet;
    }
}
