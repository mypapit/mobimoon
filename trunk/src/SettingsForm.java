import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.microedition.midlet.*;
import javax.microedition.io.*;
import java.io.*;

public class SettingsForm extends Form implements CommandListener
{

private ChoiceGroup cgCalibrate,cgHemisphere;

public Command DISMISS_COMMAND;
public Command cmdSave;
private Display display;
private String calibrateValue[] = {"-1","0","+1"};
private String hemisphere[] = {"Northern","Southern"};
private MobiMoon midlet;
private Settings rsettings;
//private RecordStore rs;
//private int calibValue,hemisphereValue;

public SettingsForm(MobiMoon midlet)
{
	super("Settings");
	this.midlet= midlet;
	
	this.display=midlet.display;
	
	DISMISS_COMMAND = new Command("Back",Command.BACK,5);
	//cmdSave = new Command("Save",Command.OK,6);
	
	cgCalibrate = new ChoiceGroup("Calibrate days",Choice.POPUP,calibrateValue,null);
	cgCalibrate.setSelectedIndex(1,true);
	cgHemisphere = new ChoiceGroup("Hemisphere", Choice.POPUP,hemisphere,null);
	cgHemisphere.setSelectedIndex(1,true);
	
	rsettings = new Settings();
	
	//rsettings.getSettings();
	cgHemisphere.setSelectedIndex(rsettings.getHemisphere(),true);
	cgCalibrate.setSelectedIndex(rsettings.getCalib(),true);
	
	
	
	this.append("Settings to calibrate Hijri date\n");
	this.append(cgCalibrate);
	this.append(cgHemisphere);
	this.addCommand(DISMISS_COMMAND);
	//this.addCommand(cmdSave);
	this.setCommandListener(this);
	

}

public void commandAction(Command c, Displayable d)
{
	if (c == DISMISS_COMMAND) {
		rsettings.saveSettings(false,cgCalibrate.getSelectedIndex(),cgHemisphere.getSelectedIndex());
		
		midlet.moonitem.setNorthern(rsettings.isNorthern());
		midlet.datefield.calibrate(rsettings.getDaysCalib());
		midlet.updateDate();
		rsettings.cleanUp();
		rsettings = null;
		display.setCurrent(midlet.form);

	} 
	/*else if (c == cmdSave) {
	}*/

}


public void showAlert(String text)
{
	Alert a = new Alert("Error",text,null, AlertType.ERROR);
	a.setTimeout(Alert.FOREVER);
	display.setCurrent(a,this);
	
}




}
