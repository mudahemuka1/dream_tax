-- Verify Database Tables and Data
USE smart_farming_db;

-- Check if tables exist
SHOW TABLES;

-- Check farmers table
SELECT * FROM Farmer;

-- Check crops table  
SELECT * FROM Crop;

-- Check recommendations table
SELECT * FROM Recommendation;

-- Test crop recommendation logic
SELECT c.Crop_Name, c.Ideal_Soil, c.Ideal_Season 
FROM Crop c 
WHERE c.Ideal_Soil = 'Loam' AND c.Ideal_Season = 'Rainy';

-- Check relationships
SELECT 
    f.Name as FarmerName,
    c.Crop_Name,
    r.Advice_Details,
    r.Date
FROM Recommendation r
JOIN Farmer f ON r.Farmer_ID = f.Farmer_ID
JOIN Crop c ON r.Crop_ID = c.Crop_ID;
