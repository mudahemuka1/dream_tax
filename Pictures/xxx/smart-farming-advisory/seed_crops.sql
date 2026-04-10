USE smartfarming;

-- Create default Admin if not exists
-- (Wait, users have roles, need to see the User structure once more)

-- Add some Crops for Loam/Summer
INSERT INTO crops (name, description, suitable_soil_type, suitable_season, growing_duration_days) 
VALUES ('Hybrid Maize', 'High-yielding drought-tolerant maize variety perfect for Rwandan hills.', 'Loam', 'Summer', 120)
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO crops (name, description, suitable_soil_type, suitable_season, growing_duration_days) 
VALUES ('Bush Beans', 'Nutrient-rich beans suitable for mid-altitude areas.', 'Loam', 'Summer', 90)
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO crops (name, description, suitable_soil_type, suitable_season, growing_duration_days) 
VALUES ('Roma Tomatoes', 'Commercial grade tomatoes with high resistance to blight. kwitabhwaho cyane harimo kuhira (irrigation), kurwanya indwara nudukoko. Gisarurwa mu minsi 90-120 bitewe nubwoko bwimbuto.', 'Loamy soil (ubutaka bwiza buvanze neza, butumisha amazi)', 'Season B: Feb – June (Summer Season)', 120)
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO crops (name, description, suitable_soil_type, suitable_season, growing_duration_days) 
VALUES ('Pavuro', 'Pavuro (beans) ni igihingwa cyingenzi mu Rwanda gitanga proteine nyinshi. Gikura neza mu butaka bwa loam bufite ifumbire ihagije kandi gikenera imvura ihagije mu bihe byiza.', 'Loamy soil (ivangavanga: umusenyi + ibumba + ifumbire)', 'Season A: Sept – Jan (Winter Season)', 95)
ON DUPLICATE KEY UPDATE name=name;
