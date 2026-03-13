#!/bin/bash

echo "=== Testing Smart Farming Advisory System ==="

echo "1. Creating Farmer..."
curl -X POST "http://localhost:8080/api/farmers" \
  -H "Content-Type: application/json" \
  -d '{"name":"John Farmer","location":"Kigali","phoneNumber":"+250780001002"}'

echo -e "\n\n2. Getting All Farmers..."
curl -X GET "http://localhost:8080/api/farmers"

echo -e "\n\n3. Creating Crop..."
curl -X POST "http://localhost:8080/api/crops" \
  -H "Content-Type: application/json" \
  -d '{"cropName":"Maize","idealSoil":"Loam","idealSeason":"Rainy"}'

echo -e "\n\n4. Getting Crop Recommendations..."
curl -X GET "http://localhost:8080/api/crops/recommend?soil=Loam&season=Rainy"

echo -e "\n\n5. Getting Crop Advice..."
curl -X POST "http://localhost:8080/api/recommendations/crop-advice" \
  -H "Content-Type: application/json" \
  -d '{"farmerId":1,"soilType":"Loam","season":"Rainy"}'

echo -e "\n\n=== Test Complete ==="
