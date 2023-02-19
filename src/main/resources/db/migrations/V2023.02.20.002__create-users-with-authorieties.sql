INSERT INTO users (username, password, enabled)
VALUES('admin', '{bcrypt}$2a$12$LLVZkBCL206iGBqZJkyI5uCa57sieSe3okNkoVh47wDDkyXlzsSnC', 1);
INSERT INTO users (username, password, enabled)
VALUES('user', '{bcrypt}$2a$12$KZMvZ0sinjiSiJ3EBJiIZuB9/wWbiIp6zXiRH00Qd4GTqrTETQ9AC', 1);

INSERT INTO authorities (username, authority)
VALUES('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority)
VALUES('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority)
VALUES('user', 'ROLE_USER');
