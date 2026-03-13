# How to Run Smart Farming Advisory System

## Method 1: Using Command Prompt/Terminal

1. **Open Command Prompt**
   - Press `Win + R`, type `cmd`, press Enter
   - OR Open PowerShell

2. **Navigate to Project Directory**
   ```bash
   cd c:\Users\user\Music\farming_advesory
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

## Expected Output:
```
[INFO] Scanning for projects...
[INFO] 
[INFO] -------------------< com.smartfarming:smart-farming-advisory >-------------------
[INFO] Building smart-farming-advisory 1.0.0
[INFO]   ....
[INFO] --- spring-boot-maven-plugin:3.2.0:run (default-cli) @ smart-farming-advisory ---
[INFO] Attaching agents: []
[INFO] 
[INFO]   .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-03-11 22:10:15.123  INFO 12345 --- [           main] com.smartfarming.SmartFarmingAdvisoryApplication : Starting SmartFarmingAdvisoryApplication using Java 17.0.2
2024-03-11 22:10:15.456  INFO 12345 --- [           main] com.smartfarming.SmartFarmingAdvisoryApplication : No active profile set, falling back to 1 default profile: "default"
2024-03-11 22:10:16.789  INFO 12345 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2024-03-11 22:10:17.012  INFO 12345 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 23 ms. Found 7 repository interfaces.
2024-03-11 22:10:17.890  INFO 12345 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080
2024-03-11 22:10:17.891  INFO 12345 --- [           main] com.smartfarming.SmartFarmingAdvisoryApplication : Started SmartFarmingAdvisoryApplication in 3.234 seconds (process running for 4.567)
```

## Method 2: Using IDE (IntelliJ/Eclipse)

1. **Open Project in IDE**
   - File → Open → Select `farming_advesory` folder

2. **Run Main Class**
   - Right-click on `SmartFarmingAdvisoryApplication.java`
   - Select "Run 'SmartFarmingAdvisoryApplication.main()'"

## Method 3: Using Maven Wrapper

1. **Run with Maven Wrapper**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## Step 2: Test the Application

Once you see "Tomcat started on port 8080", the application is running!

### Test in Browser:
Open these URLs in your browser:

1. **Test Farmers API:**
   - Open: `http://localhost:8080/api/farmers`
   - Expected Output: `[]` (empty array - no farmers yet)

2. **Test Crops API:**
   - Open: `http://localhost:8080/api/crops`
   - Expected Output: `[]` (empty array - no crops yet)

### Test with cURL (in new terminal):
```bash
# Create a farmer
curl -X POST "http://localhost:8080/api/farmers" \
  -H "Content-Type: application/json" \
  -d '{"name":"John Farmer","location":"Kigali","phoneNumber":"+250780001001"}'

# Get all farmers
curl -X GET "http://localhost:8080/api/farmers"
```

### Expected API Response:
```json
[
  {
    "farmerId": 1,
    "name": "John Farmer",
    "location": "Kigali",
    "phoneNumber": "+250780001001",
    "createdAt": "2024-03-11T22:15:30.123"
  }
]
```

---

## Step 3: Verify Database

1. **Open MySQL Workbench or MySQL Command Line**
2. **Check if data was saved:**
   ```sql
   USE smart_farming_db;
   SELECT * FROM Farmer;
   ```

### Expected Database Output:
```
+-----------+--------------+----------------+----------------+---------------------+
| Farmer_ID | Name         | Location       | Phone_Number   | Created_At          |
+-----------+--------------+----------------+----------------+---------------------+
|         1 | John Farmer  | Kigali         | +250780001001  | 2024-03-11 22:15:30 |
+-----------+--------------+----------------+----------------+---------------------+
```

---

## Troubleshooting:

### If you get "Connection refused":
- Make sure MySQL is running
- Check database credentials in `application.properties`

### If you get "Build failed":
- Run `mvn clean install` first
- Check Java version (should be 17+)

### If you get "Port 8080 already in use":
- Change port in `application.properties`: `server.port=8081`

---

## Success Indicators:
✅ Application starts without errors
✅ Tomcat starts on port 8080
✅ APIs return responses
✅ Data appears in database
✅ No error messages in console
