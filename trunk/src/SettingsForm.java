import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.microedition.midlet.*;
import javax.microedition.io.*;
import java.io.*;

public class SettingsForm extends Form implements CommandListener
{

private ChoiceGroup cgCalibrate;
public Command DISMISS_COMMAND;
public Command cmdSave;
private Display display;
private String calibrateValue[] = {"-1","0","+1"};
private MobiMoon midlet;
private RecordStore rs;
private int calibValue;

public SettingsForm(MobiMoon midlet)
{
	super("Settings");
	this.midlet= midlet;
	
	display=Display.getDisplay(midlet);
	
	DISMISS_COMMAND = new Command("Back",Command.BACK,5);
	cmdSave = new Command("Save",Command.OK,6);
	
	cgCalibrate = new ChoiceGroup("Calibrate days",Choice.POPUP,calibrateValue,null);
	cgCalibrate.setSelectedIndex(1,true);
	this.getSettings();
	
	
	
	this.append("Settings to calibrate Hijri date\n");
	this.append(cgCalibrate);
	this.addCommand(DISMISS_COMMAND);
	this.addCommand(cmdSave);
	this.setCommandListener(this);
	

}

public void commandAction(Command c, Displayable d)
{
	if (c == DISMISS_COMMAND) {
		this.cleanUp();
		display.setCurrent(midlet.form);
	} else if (c == cmdSave) {
		this.saveSettings(false);
		this.cleanUp();
		midlet.updateDate();
		display.setCurrent(midlet.form);
	}

}

private void saveSettings(boolean init)
{
	try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);
		
			try { 
					this.calibValue = cgCalibrate.getSelectedIndex();
					dout.writeInt(this.calibValue);
					
					byte[] data = bout.toByteArray();
					
					if (init) {
						rs.addRecord(data,0,data.length);
					} else {
						rs.setRecord(1,data,0,data.length);
					}
					dout.close();
					bout.close();
					
			} catch (Exception ex) {
				this.showAlert(ex.toString());
			}
			
	} catch (Exception ex){
			this.showAlert(ex.toString());
	}

}

private void getSettings() {
	DataInputStream din;
	ByteArrayInputStream bin;
	try {
			rs = RecordStore.openRecordStore("setup",true);
			
			if (rs.getNumRecords() == 0)
			{
				this.saveSettings(true);
			}
			
			byte[] data = rs.getRecord(1);
			bin = new ByteArrayInputStream(data);
			din = new DataInputStream(bin);
			
			this.calibValue = din.readInt();
			cgCalibrate.setSelectedIndex(this.calibValue,true);
			
			din.close();
			bin.close();
			
		} catch (RecordStoreException rse) {
			this.showAlert(rse.toString());
			rse.printStackTrace();
		} catch (Exception ex) {
			this.showAlert(ex.toString());
	
	}

}

public void showAlert(String text)
{
	Alert a = new Alert("Error",text,null, AlertType.ERROR);
	a.setTimeout(Alert.FOREVER);
	display.setCurrent(a,this);
	
}

private void cleanUp()
{
		try {
			rs.closeRecordStore();
			rs = null;
		} catch (Exception ex){
		
		}
	
}

public int getValue()
{
	return this.calibValue-1;
}


}
