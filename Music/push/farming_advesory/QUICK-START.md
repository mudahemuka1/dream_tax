# Quick Start Links - Smart Farming Advisory System

## 🚀 Run Application Now

### Method 1: Direct Run Command
Copy and paste this command in terminal:
```bash
cd c:\Users\user\Music\farming_advesory && mvn spring-boot:run
```

### Method 2: Step by Step
1. **Open Command Prompt** (Win + R → cmd)
2. **Navigate:** `cd c:\Users\user\Music\farming_advesory`
3. **Run:** `mvn spring-boot:run`

---

## 🌐 Application Links (After Starting)

### Main API Endpoints:
- **Farmers:** http://localhost:8080/api/farmers
- **Crops:** http://localhost:8080/api/crops  
- **Diseases:** http://localhost:8080/api/diseases
- **Fertilizers:** http://localhost:8080/api/fertilizers
- **Recommendations:** http://localhost:8080/api/recommendations

### Special Functions:
- **Crop Recommendations:** http://localhost:8080/api/crops/recommend?soil=Loam&season=Rainy
- **Disease Search:** http://localhost:8080/api/diseases/search?symptom=yellow

---

## 🧪 Quick Test Links

### Test Create Farmer (POST):
```bash
curl -X POST "http://localhost:8080/api/farmers" -H "Content-Type: application/json" -d "{\"name\":\"Test Farmer\",\"location\":\"Kigali\",\"phoneNumber\":\"+250780001001\"}"
```

### Test Get Farmers (GET):
```bash
curl -X GET "http://localhost:8080/api/farmers"
```

### Test Crop Recommendation:
```bash
curl -X GET "http://localhost:8080/api/crops/recommend?soil=Loam&season=Rainy"
```

---

## 📱 Browser Testing

Open these directly in your browser after starting:

1. **View All Farmers:** http://localhost:8080/api/farmers
2. **View All Crops:** http://localhost:8080/api/crops
3. **Get Recommendations:** http://localhost:8080/api/crops/recommend?soil=Loam&season=Rainy

---

## 🔧 Setup Requirements

### Prerequisites:
1. **Java 17+** installed
2. **Maven** installed  
3. **MySQL** running
4. **Database created:** `smart_farming_db`

### Database Setup:
```sql
CREATE DATABASE smart_farming_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Update Database Credentials:
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

---

## ✅ Success Check

When you see this output, it's working:
```
Tomcat started on port 8080
Started SmartFarmingAdvisoryApplication
```

Then open: http://localhost:8080/api/farmers

---

## 🚨 Common Issues

### Port 8080 in use?
Change in `application.properties`:
```properties
server.port=8081
```

### Database connection error?
Check MySQL is running and credentials are correct.

### Build failed?
Run: `mvn clean install` first

---

## 🎯 One-Click Test

After starting, click these links:
- [Test Farmers API](http://localhost:8080/api/farmers)
- [Test Crops API](http://localhost:8080/api/crops)
- [Test Crop Recommendations](http://localhost:8080/api/crops/recommend?soil=Loam&season=Rainy)
