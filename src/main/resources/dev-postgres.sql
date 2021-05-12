insert into items (id, title, cost, description) values
(1, 'Чёрный кофе', 90, 'Молотый');

insert into users (id, email, password, role) values
(1, 'ashot@cofi.ru', '$2y$12$6G7v5bApu2ZRH7W.HV8NHuJs0W4pDQ3xMmXZSW1k03Dfvzk78lkaO', 'ROLE_ADMIN'),
(2, 'j_smith@rosatom.ru', '$2y$12$6G7v5bApu2ZRH7W.HV8NHuJs0W4pDQ3xMmXZSW1k03Dfvzk78lkaO', 'ROLE_MANAGER'),
(3, 'user@kot.ru', '$2y$12$6G7v5bApu2ZRH7W.HV8NHuJs0W4pDQ3xMmXZSW1k03Dfvzk78lkaO', 'ROLE_USER');

insert into cafe (id, adress) values
(1, 'Пр-т Братьев Коростелёвых д.45'),
(2, 'Ул. Школьная д.89');