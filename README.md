ASK RFID reader on C-One
========================

In this article, we will explain how to manage power state of the ASK RFID reader, and access the firmware version of the reader.

Add CpcPowerMgmt and CpcAsk to your project
-------------------------------------------

1. Specify the Coppernic Maven repository in the project-level build.gradle file:

```groovy
allprojects {
    repositories {
        maven { url 'http://arti-01:8081/artifactory/plugins-release'}
        maven { url 'http://arti-01:8081/artifactory/libs-release'}
    }
}
```

2. Add the libraries as dependencies in your module-level build.gradle file:

```groovy
dependencies {
    compile 'fr.coppernic.cpcframework.cpcpowermgmt:CpcPowerMgmt:3.6.0'
    compile 'fr.coppernic.cpcframework.cpcask:CpcAsk:2.0.4'
}
```

Power management
----------------
For fine power management, it is mandatory to manage the power state of the reader before being able to use it. CpcPowerMgmt library must be used for it.

### Imports needed

```groovy
import fr.coppernic.cpcframework.cpcpowermgmt.cone.PowerMgmt;
import fr.coppernic.cpcframework.cpcpowermgmt.cone.PowerMgmt.InterfacesCone;
import fr.coppernic.cpcframework.cpcpowermgmt.cone.PowerMgmt.ManufacturersCone;
import fr.coppernic.cpcframework.cpcpowermgmt.cone.PowerMgmt.ModelsCone;
import fr.coppernic.cpcframework.cpcpowermgmt.cone.PowerMgmt.PeripheralTypesCone;
```

### Declare a PowerMgmt object

```groovy
private PowerMgmt mPowerMgmt;
```

### Instantiate the PowerMgmt object

```groovy
mPowerMgmt = new PowerMgmt(this);
```

### Power on RFID reader and wait 500ms for the reader to be initialized

```groovy
mPowerMgmt.setPower(PowerMgmt.PeripheralTypesCone.RfidSc,
                PowerMgmt.ManufacturersCone.Ask,
                PowerMgmt.ModelsCone.Ucm108,
                PowerMgmt.InterfacesCone.ExpansionPort,
                true);

SystemClock.sleep(500);
```
RFID reader 
-----------

1. Declare a Reader object

```groovy
private Reader mReader;
```

2. Instantiate the Reader object

```groovy
Reader.getInstance(this, new OnGetReaderInstanceListener() {
            @Override
            public void OnGetReaderInstance(Reader reader) {
                mReader = reader;
            }
        });
```

3. Open communication with reader

```groovy
mReader.cscOpen(CpcDefinitions.ASK_READER_PORT, 115200, false);
```

4. Gets the firmware version of the reader

```groovy
StringBuilder sb = new StringBuilder();
mReader.cscVersionCsc(sb);
```
Reader is now ready for action!
