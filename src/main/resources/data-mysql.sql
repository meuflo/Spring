INSERT INTO `role` (`id`,`name`)
VALUES (1,'ROLE_UTILISATEUR'),
       (2,'ROLE_ADMINISTRATEUR');

INSERT INTO pays(name)
VALUES ('France'),
       ('Allemagne'),
       ('Espagne');

INSERT INTO `entreprise` (`nom`)
VALUES ('Amazon'),
       ('Google'),
       ('Red Hat');

INSERT INTO user(first_name, name, age, pays_id, entreprise_id, email, password, role_id, created_at, updated_at)
VALUES ('Toto', 'TUTU', 5, 1, 1, 'a@a.com','$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q', 2, UTC_TIMESTAMP(), UTC_TIMESTAMP()),
       ('Titi', 'SMITH', 10, 2, 2,'b@b.com','$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q', 1, UTC_TIMESTAMP(), UTC_TIMESTAMP()),
       ('Tata', 'SMITH', 15, 3, 3, 'c@c.com','$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q', 1, UTC_TIMESTAMP(), UTC_TIMESTAMP());







INSERT INTO emploi(name)
VALUES ('Developpeur'),
       ('Testeur'),
       ('Chef de projet');

INSERT INTO `recherche_emploi_utilisateur` (`user_id`, `emploi_id`) VALUES
                                                                               (1, 1),
                                                                               (1, 2),
                                                                               (2, 2);