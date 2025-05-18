
DROP TABLE IF EXISTS job_required_skills CASCADE;
DROP TABLE IF EXISTS applications CASCADE;
DROP TABLE IF EXISTS job_categories CASCADE;
DROP TABLE IF EXISTS jobs CASCADE;
DROP TABLE IF EXISTS companies CASCADE;
DROP TABLE IF EXISTS skills CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- USERS table
CREATE TABLE users (
                       user_id UUID PRIMARY KEY,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       phone VARCHAR(20),
                       user_type VARCHAR(10) NOT NULL CHECK (user_type IN ('JOB_SEEKER', 'EMPLOYER', 'ADMIN')),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- COMPANIES table
-- COMPANIES table (sans owner_id)
CREATE TABLE companies (
                           company_id UUID PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           description TEXT,
                           location VARCHAR(100),
                           website VARCHAR(100),
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- JOB CATEGORIES table
CREATE TABLE job_categories (
                                category_id UUID PRIMARY KEY,
                                name VARCHAR(50) NOT NULL UNIQUE
);

-- JOBS table
-- JOBS table (ajout de owner_id)
CREATE TABLE jobs (
                      job_id UUID PRIMARY KEY,
                      owner_id UUID NOT NULL REFERENCES users(user_id),
                      company_id UUID NOT NULL REFERENCES companies(company_id),
                      category_id UUID REFERENCES job_categories(category_id),
                      title VARCHAR(100) NOT NULL,
                      description TEXT NOT NULL,
                      job_type VARCHAR(15) CHECK (job_type IN ('full-time', 'part-time', 'internship')),
                      salary VARCHAR(50),
                      location VARCHAR(100),
                      status VARCHAR(10) DEFAULT 'active' CHECK (status IN ('active', 'closed')),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      expiry_date DATE
);


-- APPLICATIONS table
CREATE TABLE applications (
                              application_id UUID PRIMARY KEY,
                              job_id UUID NOT NULL REFERENCES jobs(job_id),
                              user_id UUID NOT NULL REFERENCES users(user_id),
                              cover_letter TEXT,
                              resume_url VARCHAR(255),
                              status VARCHAR(10) DEFAULT 'pending' CHECK (status IN ('pending', 'accepted', 'rejected')),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- SKILLS table
CREATE TABLE skills (
                        skill_id UUID PRIMARY KEY,
                        name VARCHAR(50) NOT NULL UNIQUE
);

-- JOB_REQUIRED_SKILLS table
CREATE TABLE job_required_skills (
                                     job_id UUID NOT NULL REFERENCES jobs(job_id),
                                     skill_id UUID NOT NULL REFERENCES skills(skill_id),
                                     PRIMARY KEY (job_id, skill_id)
);

-- Initial data: Categories
INSERT INTO job_categories (category_id, name) VALUES
                                                   (gen_random_uuid(), 'Développement Web'),
                                                   (gen_random_uuid(), 'Data Science'),
                                                   (gen_random_uuid(), 'Cybersécurité'),
                                                   (gen_random_uuid(), 'Design');

-- Initial data: Skills
INSERT INTO skills (skill_id, name) VALUES
                                        (gen_random_uuid(), 'Java'),
                                        (gen_random_uuid(), 'Python'),
                                        (gen_random_uuid(), 'React'),
                                        (gen_random_uuid(), 'SQL'),
                                        (gen_random_uuid(), 'UX Design');


select * from users

    INSERT INTO users (user_id, email, password_hash, first_name, last_name, phone, user_type, created_at) VALUES
    ('11111111-1111-1111-1111-111111111111', 'alice@example.com', 'hash1', 'Alice', 'Dupont', '0601020304', 'JOB_SEEKER', NOW()),
    ('22222222-2222-2222-2222-222222222222', 'bob@example.com', 'hash2', 'Bob', 'Martin', '0605060708', 'EMPLOYER', NOW()),
    ('33333333-3333-3333-3333-333333333333', 'charlie@example.com', 'hash3', 'Charlie', 'Durand', NULL, 'ADMIN', NOW());
INSERT INTO companies (company_id, name, description, location, website, created_at) VALUES
                                                                                         ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'TechCorp', 'Entreprise innovante dans la tech.', 'Paris', 'https://techcorp.example.com', NOW()),
                                                                                         ('bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'DesignStudio', 'Agence de design créatif.', 'Lyon', 'https://designstudio.example.com', NOW());
INSERT INTO jobs (job_id, owner_id, company_id, category_id, title, description, job_type, salary, location, status, created_at, expiry_date) VALUES
                                                                                                                                                  ('dddddddd-dddd-dddd-dddd-dddddddddddd', '22222222-2222-2222-2222-222222222222', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', NULL, 'Développeur Java', 'Développement backend en Java.', 'full-time', '40000€', 'Paris', 'active', NOW(), CURRENT_DATE + INTERVAL '30 days'),
                                                                                                                                                  ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '22222222-2222-2222-2222-222222222222', 'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', NULL, 'Designer UX', 'Création d''interfaces utilisateurs.', 'part-time', '25000€', 'Lyon', 'active', NOW(), CURRENT_DATE + INTERVAL '45 days');
INSERT INTO applications (application_id, job_id, user_id, cover_letter, resume_url, status, created_at) VALUES
                                                                                                             ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '11111111-1111-1111-1111-111111111111', 'Je suis très motivée pour ce poste.', 'https://resume.example.com/alice.pdf', 'pending', NOW()),
                                                                                                             ('99999999-9999-9999-9999-999999999999', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '11111111-1111-1111-1111-111111111111', 'Passionnée par le design UX.', NULL, 'pending', NOW());

