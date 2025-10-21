# References – Question 4 (Smart-Campus Device System)

**Name:** Manushi Khatri  
**Course:** Large Scale Programming (Fall 2025)  
**Midterm Programming – Question 4**

---

### Primary References
1. Oracle Java Tutorials – [Abstract Classes and Methods](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)     
2. GeeksforGeeks – [Polymorphism in Java](https://www.geeksforgeeks.org/polymorphism-in-java/)  
3. Java SE API – [IllegalArgumentException](https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalArgumentException.html)

---

### AI / Internet Source Disclosure
- **Tool used:** ChatGPT (GPT-5, OpenAI, October 2025)  
- **Purpose:** To double-check that my class hierarchy (`Device`, `DoorLock`, `Thermostat`, `Camera`) and interface implementations (`Networked`, `BatteryPowered`) followed Java’s abstract and interface principles exactly as described in the midterm instructions.  
- **Prompt:** “How do I design a Java system with an abstract parent class and multiple interfaces where subclasses combine behaviors like networking and battery management?”  
- **AI Answer (Excerpt):** 
``` 
Here’s a clean, flexible design that mixes an abstract parent class with multiple small interfaces so concrete classes can “compose” behaviors like networking and battery management.

Design idea (quick map)

Device (abstract): common identity + power lifecycle.

Small, focused interfaces (mix-ins):

WifiCapable, CellularCapable, EthernetCapable (network behaviors)

BatteryManaged (battery state + rules)

Concrete classes combine what they need:

Laptop = Device + WifiCapable + EthernetCapable + BatteryManaged

Router = Device + WifiCapable + EthernetCapable

Drone = Device + CellularCapable + BatteryManaged

This follows SRP and interface segregation: classes only implement what they actually use.

Java code
// ---------- Behavior Interfaces ----------

interface WifiCapable {
    void connectWifi(String ssid, String password);
    void disconnectWifi();
    boolean isWifiConnected();
}

interface CellularCapable {
    void attachCellular(String apn);
    void detachCellular();
    boolean isCellularAttached();
}

interface EthernetCapable {
    void plugEthernet(String macAddress);
    void unplugEthernet();
    boolean isEthernetLinked();
}

interface BatteryManaged {
    int getBatteryPercent();
    void chargePercent(int percent);  // +percent (clamped 0..100)
    void consumePercent(int percent); // -percent (clamped 0..100)
    boolean isLowBattery();           // typically < 20%
}

// ---------- Abstract Parent ----------

abstract class Device {
    private final String id;
    private String name;
    private boolean poweredOn;

    protected Device(String id, String name) {
        this.id = id;
        this.name = name;
        this.poweredOn = false;
    }

    public final void powerOn() {
        if (!poweredOn) {
            poweredOn = true;
            onPowerOn();        // hook for subclasses
        }
    }

    public final void powerOff() {
        if (poweredOn) {
            onPowerOff();       // hook for subclasses
            poweredOn = false;
        }
    }

    protected void onPowerOn() {}   // default no-op
    protected void onPowerOff() {}  // default no-op

    public boolean isPoweredOn() { return poweredOn; }
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /** Each device reports a concise status string */
    public abstract String status();
}

// ---------- A reusable Portable base (with battery) ----------

abstract class PortableDevice extends Device implements BatteryManaged {
    private int batteryPercent; // 0..100

    protected PortableDevice(String id, String name, int initialBattery) {
        super(id, name);
        this.batteryPercent = clamp(initialBattery, 0, 100);
    }

    @Override
    public int getBatteryPercent() { return batteryPercent; }

    @Override
    public void chargePercent(int percent) {
        if (percent <= 0) return;
        batteryPercent = clamp(batteryPercent + percent, 0, 100);
    }

    @Override
    public void consumePercent(int percent) {
        if (percent <= 0) return;
        batteryPercent = clamp(batteryPercent - percent, 0, 100);
    }

    @Override
    public boolean isLowBattery() { return batteryPercent < 20; }

    protected static int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    @Override
    protected void onPowerOn() {
        // Example: wake radios, start sensors, etc.
        if (isLowBattery()) {
            System.out.println(getName() + ": Low battery on startup.");
        }
    }
}

// ---------- Concrete Devices ----------

class Laptop extends PortableDevice implements WifiCapable, EthernetCapable {
    private boolean wifi;
    private boolean eth;

    public Laptop(String id, String name, int initialBattery) {
        super(id, name, initialBattery);
    }

    // WifiCapable
    @Override public void connectWifi(String ssid, String password) {
        if (!isPoweredOn()) { System.out.println("Laptop: power on first."); return; }
        wifi = true; // pretend to auth/associate
        System.out.println("Laptop Wi-Fi connected to " + ssid);
    }
    @Override public void disconnectWifi() { wifi = false; }
    @Override public boolean isWifiConnected() { return wifi; }

    // EthernetCapable
    @Override public void plugEthernet(String macAddress) {
        if (!isPoweredOn()) { System.out.println("Laptop: power on first."); return; }
        eth = true;
        System.out.println("Laptop Ethernet up with MAC " + macAddress);
    }
    @Override public void unplugEthernet() { eth = false; }
    @Override public boolean isEthernetLinked() { return eth; }

    @Override
    public String status() {
        return String.format(
            "Laptop[%s]: power=%s, batt=%d%%, wifi=%s, eth=%s",
            getName(),
            isPoweredOn(), getBatteryPercent(),
            isWifiConnected(), isEthernetLinked()
        );
    }
}

class Router extends Device implements WifiCapable, EthernetCapable {
    private boolean wifi;
    private boolean eth;

    public Router(String id, String name) { super(id, name); }

    // WifiCapable
    @Override public void connectWifi(String ssid, String password) {
        if (!isPoweredOn()) { System.out.println("Router: power on first."); return; }
        wifi = true;
        System.out.println("Router AP up on SSID " + ssid);
    }
    @Override public void disconnectWifi() { wifi = false; }
    @Override public boolean isWifiConnected() { return wifi; }

    // EthernetCapable
    @Override public void plugEthernet(String macAddress) {
        if (!isPoweredOn()) { System.out.println("Router: power on first."); return; }
        eth = true;
        System.out.println("Router WAN linked.");
    }
    @Override public void unplugEthernet() { eth = false; }
    @Override public boolean isEthernetLinked() { return eth; }

    @Override
    public String status() {
        return String.format(
            "Router[%s]: power=%s, wifi=%s, eth=%s",
            getName(),
            isPoweredOn(), isWifiConnected(), isEthernetLinked()
        );
    }
}

class Drone extends PortableDevice implements CellularCapable {
    private boolean cellular;

    public Drone(String id, String name, int initialBattery) {
        super(id, name, initialBattery);
    }

    // CellularCapable
    @Override public void attachCellular(String apn) {
        if (!isPoweredOn()) { System.out.println("Drone: power on first."); return; }
        if (isLowBattery()) {
            System.out.println("Drone: refusing cellular attach on low battery.");
            return;
        }
        cellular = true;
        System.out.println("Drone attached to cellular APN " + apn);
    }
    @Override public void detachCellular() { cellular = false; }
    @Override public boolean isCellularAttached() { return cellular; }

    @Override
    public String status() {
        return String.format(
            "Drone[%s]: power=%s, batt=%d%%, LTE=%s",
            getName(),
            isPoweredOn(), getBatteryPercent(),
            isCellularAttached()
        );
    }
}

// ---------- Demo ----------

public class Main {
    public static void main(String[] args) {
        Laptop laptop = new Laptop("L1", "DevLaptop", 75);
        Router router = new Router("R1", "HomeRouter");
        Drone drone = new Drone("D1", "SurveyDrone", 18);

        laptop.powerOn();
        laptop.connectWifi("CampusNet", "secret");
        laptop.plugEthernet("00:11:22:33:44:55");
        laptop.consumePercent(10);

        router.powerOn();
        router.plugEthernet("AA:BB:CC:DD:EE:FF");
        router.connectWifi("MySSID", "pass123");

        drone.powerOn();
        drone.attachCellular("iot.example"); // will warn/refuse due to low battery
        drone.chargePercent(5);
        drone.attachCellular("iot.example"); // tries again

        System.out.println(laptop.status());
        System.out.println(router.status());
        System.out.println(drone.status());
    }
}

Why this scales well

Abstract parent (Device) centralizes lifecycle (power on/off, identity), so all devices behave consistently.

Small interfaces keep responsibilities focused; classes only implement what they need (high cohesion).

PortableDevice gives a reusable battery implementation (validation + clamping), so anything portable gets battery rules “for free.”

Easy to extend: Want a Smartphone? Extend PortableDevice, implement WifiCapable + CellularCapable. Want a UPS-powered router? Implement BatteryManaged in Router or subclass it. 
```  
- **Adaptation:** This helped me verify my design structure before compiling and testing my final code in Eclipse.

---
