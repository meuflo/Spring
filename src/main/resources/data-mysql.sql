INSERT INTO `role` (`id`, `name`)
VALUES (1, 'ROLE_UTILISATEUR'),
       (2, 'ROLE_ADMINISTRATEUR'),
       (3, 'ROLE_SUPER_ADMINISTRATEUR');

INSERT INTO pays(name)
VALUES ('France'),
       ('Allemagne'),
       ('Espagne');

INSERT INTO `entreprise` (`nom`)
VALUES ('Amazon'),
       ('Google'),
       ('Red Hat');

INSERT INTO user(first_name, name, age, pays_id, entreprise_id, email, password, created_at, updated_at)
VALUES ('Toto', 'TUTU', 5, 1, 1, 'a@a.com', '$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q',
        UTC_TIMESTAMP(), UTC_TIMESTAMP()),
       ('Titi', 'SMITH', 10, 2, 2, 'b@b.com', '$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q',
        UTC_TIMESTAMP(), UTC_TIMESTAMP()),
       ('Tata', 'SMITH', 15, 3, 3, 'c@c.com', '$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q',
        UTC_TIMESTAMP(), UTC_TIMESTAMP());

INSERT INTO role_users (user_id, role_id) VALUES
                                                           (1, 1),
                                                           (2, 1),
                                                           (2, 2),
                                                           (3, 3);

INSERT INTO `contrat` (`id`, `date_de_creation`, `date_de_retour`)
VALUES (1, '2023-05-09', '2023-05-11'),
       (2, '2023-05-10', NULL);

INSERT INTO `materiel` (`id`, `nom`, `numero`)
VALUES (1, 'Ecran 30\"', 123),
       (2, 'Ecran 30\"', 456),
       (3, 'Clavier', 789);

INSERT INTO `ligne_de_contrat` (`contrat_id`, `materiel_id`, `date_de_retour_anticipe`)
VALUES (1, 1, NULL),
       (1, 3, '2023-05-10'),
       (2, 2, NULL);



INSERT INTO emploi(name)
VALUES ('Developpeur'),
       ('Testeur'),
       ('Chef de projet');

INSERT INTO `recherche_emploi_utilisateur` (`user_id`, `emploi_id`)
VALUES (1, 1),
       (1, 2),
       (2, 2);