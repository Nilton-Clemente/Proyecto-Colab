-- V5: Datos semilla de catalogos (habilidades y tecnologias)
-- Permite que el perfil de habilidades y la creacion de proyectos funcionen
-- sin necesidad de cargar los catalogos de forma manual.

INSERT INTO usuario.habilidad (nombre, area) VALUES
    ('Java', 'Backend'),
    ('Spring Boot', 'Backend'),
    ('Kotlin', 'Movil'),
    ('Jetpack Compose', 'Movil'),
    ('React', 'Frontend'),
    ('Django', 'Backend'),
    ('Python', 'Backend'),
    ('PostgreSQL', 'Base de datos'),
    ('Analisis', 'Gestion'),
    ('Requerimientos', 'Gestion'),
    ('Gestion de proyectos', 'Gestion'),
    ('Pruebas', 'Calidad')
ON CONFLICT (nombre) DO NOTHING;

INSERT INTO usuario.tecnologia (nombre) VALUES
    ('Java'),
    ('Spring Boot'),
    ('Kotlin'),
    ('Jetpack Compose'),
    ('React'),
    ('Django'),
    ('Python'),
    ('PostgreSQL'),
    ('Flutter'),
    ('Node.js')
ON CONFLICT (nombre) DO NOTHING;
