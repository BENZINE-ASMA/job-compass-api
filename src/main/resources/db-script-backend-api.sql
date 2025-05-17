-- Script de suppression de toutes les tables
DROP TABLE IF EXISTS job_skills CASCADE;
DROP TABLE IF EXISTS user_skills CASCADE;
DROP TABLE IF EXISTS applications CASCADE;
DROP TABLE IF EXISTS job_categories CASCADE;
DROP TABLE IF EXISTS jobs CASCADE;
DROP TABLE IF EXISTS work_experiences CASCADE;
DROP TABLE IF EXISTS companies CASCADE;
DROP TABLE IF EXISTS skills CASCADE;
DROP TABLE IF EXISTS users CASCADE;
Drop table if exists job_required_skills cascade;

-- Tables principales simplifiées mais fonctionnelles
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       phone VARCHAR(20),
                       user_type VARCHAR(10) NOT NULL CHECK (user_type IN ('job_seeker', 'employer', 'admin')),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE companies (
                           company_id BIGSERIAL PRIMARY KEY,
                           owner_id INTEGER NOT NULL REFERENCES users(user_id),
                           name VARCHAR(100) NOT NULL,
                           description TEXT,
                           location VARCHAR(100),
                           website VARCHAR(100),
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE job_categories (
                                category_id BIGSERIAL PRIMARY KEY,
                                name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE jobs (
                      job_id SERIAL PRIMARY KEY,
                      company_id INTEGER NOT NULL REFERENCES companies(company_id),
                      category_id INTEGER REFERENCES job_categories(category_id),
                      title VARCHAR(100) NOT NULL,
                      description TEXT NOT NULL,
                      job_type VARCHAR(15) CHECK (job_type IN ('full-time', 'part-time', 'internship')),
                      salary VARCHAR(50), -- Ex: "45000-60000" ou "Compétitif"
                      location VARCHAR(100),
                      status VARCHAR(10) DEFAULT 'active' CHECK (status IN ('active', 'closed')),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      expiry_date DATE
);

CREATE TABLE applications (
                              application_id SERIAL PRIMARY KEY,
                              job_id INTEGER NOT NULL REFERENCES jobs(job_id),
                              user_id INTEGER NOT NULL REFERENCES users(user_id),
                              cover_letter TEXT,
                              resume_url VARCHAR(255),
                              status VARCHAR(10) DEFAULT 'pending' CHECK (status IN ('pending', 'accepted', 'rejected')),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tables pour les compétences (version simplifiée)
CREATE TABLE skills (
                        skill_id SERIAL PRIMARY KEY,
                        name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE job_required_skills (
                                     job_id INTEGER NOT NULL REFERENCES jobs(job_id),
                                     skill_id INTEGER NOT NULL REFERENCES skills(skill_id),
                                     PRIMARY KEY (job_id, skill_id)
);

-- Données essentielles
INSERT INTO job_categories (name) VALUES
                                      ('Développement Web'), ('Data Science'), ('Cybersécurité'), ('Design');

INSERT INTO skills (name) VALUES
                              ('Java'), ('Python'), ('React'), ('SQL'), ('UX Design');

-- Insertion d'exemple utilisateur
INSERT INTO users (email, password_hash, first_name, last_name, user_type) VALUES
                                                                               ('employer@company.com', 'hash123', 'Jean', 'Dupont', 'employer'),
                                                                               ('candidate@mail.com', 'hash456', 'Marie', 'Martin', 'job_seeker');

-- Insertion d'exemple entreprise
INSERT INTO companies (owner_id, name, location) VALUES
    (1, 'Tech Solutions', 'Paris');

-- Insertion d'exemple job
INSERT INTO jobs (company_id, category_id, title, description, job_type, salary, location) VALUES
    (1, 1, 'Développeur Fullstack', 'Description du poste...', 'full-time', '50000-60000', 'Paris');