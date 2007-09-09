import javax.microedition.rms.*;
import java.io.*;
import javax.microedition.io.*;


public class Settings
{
private RecordStore rs;
private int calibValue,hemisphereValue;

public Settings() {
		calibValue=1;
		hemisphereValue=1;
		this.getSettings();
		

}

public  void saveSettings(boolean init,int calibValue, int hemisphereValue)
{
	try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);
		
			try { 
					this.calibValue = calibValue;
					this.hemisphereValue = hemisphereValue;
					dout.writeInt(this.calibValue);
					dout.writeInt(this.hemisphereValue);
					
					
					byte[] data = bout.toByteArray();
					
					if (init) {
						rs.addRecord(data,0,data.length);
					} else {
						rs.setRecord(1,data,0,data.length);
					}
					dout.close();
					bout.close();
					
			} catch (Exception ex) {
				//this.showAlert(ex.toString());
			}
			
	} catch (Exception ex){
			//this.showAlert(ex.toString());
	}

}

private void getSettings() {
	DataInputStream din;
	ByteArrayInputStream bin;
	try {
			rs = RecordStore.openRecordStore("setup",true);
			
			if (rs.getNumRecords() == 0)
			{
				this.saveSettings(true,1,1);
			}
			
			byte[] data = rs.getRecord(1);
			bin = new ByteArrayInputStream(data);
			din = new DataInputStream(bin);
			
			this.calibValue = din.readInt();
			this.hemisphereValue = din.readInt();
			
/*			cgCalibrate.setSelectedIndex(this.calibValue,true);
			cgHemisphere.setSelectedIndex(this.hemisphereValue,true);
*/		
			din.close();
			bin.close();
			
		} catch (RecordStoreException rse) {
			//this.showAlert(rse.toString());
			rse.printStackTrace();
		} catch (Exception ex) {
			//this.showAlert(ex.toString());
	
		}
}
	
public int getCalib()
{
	return this.calibValue;
	
}

public int getHemisphere() {
	return this.hemisphereValue;
}

public int getDaysCalib() {
	return this.getCalib()-1;
}

public boolean isNorthern() {
	if (this.hemisphereValue == 0) {
		return true;
	} 
		return false;
	
}

public void cleanUp()
{
		try {
			rs.closeRecordStore();
			rs = null;
		} catch (Exception ex){
		
		}
	
}








}
