# Smart Farming Advisory System

A digital application designed to help farmers make better farming decisions. It provides guidance on crop selection based on their regions, fertilizer usage, and seasonal planning based on available data.

## Features

- **Crop Recommendations**: Get suitable crop suggestions based on soil type and season
- **Disease Management**: Identify common crop diseases and get prevention/treatment guidance
- **Fertilizer Advice**: Receive appropriate fertilizer usage recommendations
- **User Management**: Support for Farmers, Agronomists, Agro-dealers, and System Administrators
- **Data Storage**: Efficient management of agricultural data

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: MySQL
- **Java Version**: 17
- **Build Tool**: Maven

## Project Structure

```
src/main/java/com/smartfarming/
├── SmartFarmingAdvisoryApplication.java
├── controller/          # REST API endpoints
├── service/            # Business logic layer
├── repository/         # Data access layer
└── entity/             # JPA entities

src/main/resources/
├── application.properties
└── schema.sql          # Database schema
```

## API Endpoints

### Farmers
- `GET /api/farmers` - Get all farmers
- `GET /api/farmers/{id}` - Get farmer by ID
- `POST /api/farmers` - Create new farmer
- `PUT /api/farmers/{id}` - Update farmer
- `DELETE /api/farmers/{id}` - Delete farmer

### Crops
- `GET /api/crops` - Get all crops
- `GET /api/crops/recommend?soil={soilType}&season={season}` - Get crop recommendations
- `POST /api/crops` - Add new crop
- `PUT /api/crops/{id}` - Update crop
- `DELETE /api/crops/{id}` - Delete crop

### Recommendations
- `GET /api/recommendations` - Get all recommendations
- `POST /api/recommendations/crop-advice` - Get crop recommendation
- `POST /api/recommendations/disease-advice` - Get disease treatment advice
- `POST /api/recommendations/fertilizer-advice` - Get fertilizer advice

### Diseases
- `GET /api/diseases` - Get all diseases
- `GET /api/diseases/search?symptom={symptom}` - Search diseases by symptoms
- `POST /api/diseases` - Add new disease

### Fertilizers
- `GET /api/fertilizers` - Get all fertilizers
- `GET /api/fertilizers/search?nutrient={nutrient}` - Search by nutrient content
- `POST /api/fertilizers` - Add new fertilizer

### Agronomists
- `GET /api/agronomists` - Get all agronomists
- `POST /api/agronomists` - Add new agronomist

### Agro-dealers
- `GET /api/agro-dealers` - Get all agro-dealers
- `GET /api/agro-dealers/location/{location}` - Get dealers by location
- `POST /api/agro-dealers` - Add new agro-dealer

## Database Setup

1. Create MySQL database named `smart_farming_db`
2. Update database credentials in `application.properties`
3. The application will auto-create tables using the provided schema

## Running the Application

1. Clone the repository
2. Navigate to project directory
3. Run: `mvn spring-boot:run`
4. Application will start on `http://localhost:8080`

## Sample API Usage

### Get Crop Recommendations
```bash
curl -X GET "http://localhost:8080/api/crops/recommend?soil=Loam&season=Rainy"
```

### Create Farmer
```bash
curl -X POST "http://localhost:8080/api/farmers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Farmer",
    "location": "Kigali, Rwanda",
    "phoneNumber": "+250780001001"
  }'
```

### Get Crop Advice
```bash
curl -X POST "http://localhost:8080/api/recommendations/crop-advice" \
  -H "Content-Type: application/json" \
  -d '{
    "farmerId": 1,
    "soilType": "Loam",
    "season": "Rainy"
  }'
```

## Contributing

This project follows the standard Spring Boot conventions and includes:
- Proper validation using Jakarta Bean Validation
- RESTful API design
- Comprehensive error handling
- Cross-origin resource sharing (CORS) support

## Future Enhancements

- Mobile application development
- Weather API integration
- AI-based crop prediction
- Real-time sensor integration
- Advanced analytics dashboard
