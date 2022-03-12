INSERT INTO user (id, username, password, enabled)
VALUES (1, 'akif', '$2a$10$UhZGye5Tg/ZJY/RBycY3/eI1xnbx/f4AN.OmDZ3KRUIUJc/07Bdx.', true);

INSERT INTO user_role(user_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO user_role(user_id, role) VALUES (1, 'ROLE_ADMIN');
