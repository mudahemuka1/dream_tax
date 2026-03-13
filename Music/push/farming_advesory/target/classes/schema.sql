-- ============================================================
-- Smart Farming Advisory System Database Schema
-- ============================================================

CREATE DATABASE IF NOT EXISTS smart_farming_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE smart_farming_db;

-- ============================================================
-- TABLE 1: Farmer
-- Stores registered farmer profiles.
-- ============================================================
CREATE TABLE IF NOT EXISTS Farmer (
    Farmer_ID    INT           NOT NULL AUTO_INCREMENT,
    Name         VARCHAR(100)  NOT NULL,
    Location     VARCHAR(150)  NOT NULL,
    Phone_Number VARCHAR(20)   NOT NULL UNIQUE,
    Created_At   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_farmer PRIMARY KEY (Farmer_ID)
);

-- ============================================================
-- TABLE 2: Crop
-- Catalogue of crops with ideal growing conditions.
-- ============================================================
CREATE TABLE IF NOT EXISTS Crop (
    Crop_ID      INT           NOT NULL AUTO_INCREMENT,
    Crop_Name    VARCHAR(100)  NOT NULL UNIQUE,
    Ideal_Soil   VARCHAR(100)  NOT NULL,
    Ideal_Season VARCHAR(50)   NOT NULL,

    CONSTRAINT pk_crop PRIMARY KEY (Crop_ID)
);

-- ============================================================
-- TABLE 3: Disease
-- Disease library with symptoms, prevention, and treatment.
-- ============================================================
CREATE TABLE IF NOT EXISTS Disease (
    Disease_ID        INT          NOT NULL AUTO_INCREMENT,
    Symptoms          TEXT         NOT NULL,
    Prevention_Method TEXT,
    Medicine_Type     VARCHAR(100),

    CONSTRAINT pk_disease PRIMARY KEY (Disease_ID)
);

-- ============================================================
-- TABLE 4: Fertilizer
-- Fertilizer catalogue with nutrient content and dosage.
-- ============================================================
CREATE TABLE IF NOT EXISTS Fertilizer (
    Fertilizer_ID    INT           NOT NULL AUTO_INCREMENT,
    Nutrient_Content VARCHAR(150)  NOT NULL,
    Recommended_Dose VARCHAR(100)  NOT NULL,

    CONSTRAINT pk_fertilizer PRIMARY KEY (Fertilizer_ID)
);

-- ============================================================
-- TABLE 5: Agronomist
-- Agronomist profiles for expert validation of recommendations.
-- ============================================================
CREATE TABLE IF NOT EXISTS Agronomist (
    Agronomist_ID INT           NOT NULL AUTO_INCREMENT,
    Name          VARCHAR(100)  NOT NULL,
    Qualification VARCHAR(150)  NOT NULL,
    Phone         VARCHAR(20)   NOT NULL,

    CONSTRAINT pk_agronomist PRIMARY KEY (Agronomist_ID)
);

-- ============================================================
-- TABLE 6: AgroDealer
-- Agro-dealer profiles who supply fertilizers and medicines.
-- ============================================================
CREATE TABLE IF NOT EXISTS AgroDealer (
    Dealer_ID     INT           NOT NULL AUTO_INCREMENT,
    Name          VARCHAR(100)  NOT NULL,
    Shop_Location VARCHAR(150)  NOT NULL,
    Phone         VARCHAR(20)   NOT NULL,

    CONSTRAINT pk_agrodealer PRIMARY KEY (Dealer_ID)
);

-- ============================================================
-- TABLE 7: Recommendation  (Central / Bridge Table)
-- Links farmers to crop, disease, and fertilizer advice.
-- ============================================================
CREATE TABLE IF NOT EXISTS Recommendation (
    Recommendation_ID INT          NOT NULL AUTO_INCREMENT,
    Farmer_ID         INT          NOT NULL,
    Crop_ID           INT          NOT NULL,
    Fertilizer_ID     INT,
    Disease_ID        INT,
    Advice_Details    TEXT         NOT NULL,
    Date              DATE         NOT NULL DEFAULT (CURRENT_DATE),
    Created_At        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_recommendation
        PRIMARY KEY (Recommendation_ID),

    -- Foreign Keys
    CONSTRAINT fk_rec_farmer
        FOREIGN KEY (Farmer_ID)
        REFERENCES Farmer(Farmer_ID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_rec_crop
        FOREIGN KEY (Crop_ID)
        REFERENCES Crop(Crop_ID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_rec_fertilizer
        FOREIGN KEY (Fertilizer_ID)
        REFERENCES Fertilizer(Fertilizer_ID)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT fk_rec_disease
        FOREIGN KEY (Disease_ID)
        REFERENCES Disease(Disease_ID)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- ============================================================
-- INDEXES  (for query performance)
-- ============================================================
CREATE INDEX IF NOT EXISTS idx_recommendation_farmer
    ON Recommendation(Farmer_ID);

CREATE INDEX IF NOT EXISTS idx_recommendation_crop
    ON Recommendation(Crop_ID);

CREATE INDEX IF NOT EXISTS idx_recommendation_date
    ON Recommendation(Date);

CREATE INDEX IF NOT EXISTS idx_farmer_location
    ON Farmer(Location);

CREATE INDEX IF NOT EXISTS idx_crop_season
    ON Crop(Ideal_Season);
