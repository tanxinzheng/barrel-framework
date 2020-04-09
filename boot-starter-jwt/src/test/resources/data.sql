DELETE FROM xmo_user;
INSERT INTO xmo_user (id, username, password, salt, name, age, email, disable) VALUES
(1, 'admin', '939e3704e692394a35f64638b5ff08d7', '3242343', 'admin', 18, 'admin@test.com', 0),
(2, 'tester', '939e3704e692394a35f64638b5ff08d7', '3242343', 'Jone', 18, 'test1@test.com', 0),
(3, 'developer', '939e3704e692394a35f64638b5ff08d7', '3242343', '老D', 20, 'test2@test.com', 0),
(4, 'Tom', '939e3704e692394a35f64638b5ff08d7', '3242343', 'Tom', 28, 'test3@test.com', 0),
(5, 'Sandy', '123456', '42321',  'Sandy', 21, 'test4@test.com', 0),
(6, 'Billie', '123456', '42321', 'Billie', 24, 'test5@test.com', 0);

DELETE FROM xmo_group;
INSERT INTO xmo_group (id, group_code, group_name, group_type, description, active) VALUES
(1, 'ADMIN', '管理员', 'SYSTEM', '系统管理员', '1'),
(2, 'TESTER', '测试人员', 'SYSTEM', '测试人员', '1'),
(3, 'DEVELOPER', '开发人员', 'SYSTEM', '开发人员', '0');

INSERT INTO xmo_permission
(ID, PERMISSION_NAME, PERMISSION_URL, PERMISSION_KEY, PERMISSION_ACTION, DESCRIPTION, ACTIVE, CREATED_USER_ID, CREATED_TIME, UPDATED_USER_ID, UPDATED_TIME)
VALUES 
('1c5505edff35469885432536fdcb8fdb', '测试用户受保护访问资源', '/test/protect/tester', 'GET:/test/protect/tester', 'VIEW', '已保护[tester]资源', '1', 'TEST', '2019-07-28 23:21:10', 'TEST', '2019-07-28 23:21:10'),
('1c5505edff35469885432536fdcb8fda', '管理员用户受保护访问资源', '/test/protect/admin', 'GET:/test/protect/admin', 'VIEW', '已保护[admin]资源', '1', 'TEST', '2019-07-28 23:21:10', 'TEST', '2019-07-28 23:21:10');

INSERT INTO xmo_group_permission (ID, GROUP_ID, PERMISSION_ID)
VALUES
('1', '2', '1c5505edff35469885432536fdcb8fdb'),
('2', '1', '1c5505edff35469885432536fdcb8fdb'),
('3', '1', '1c5505edff35469885432536fdcb8fda');

INSERT INTO xmo_user_group (ID, GROUP_ID, USER_ID)
VALUES
('1', '1', '1'),
('2', '2', '2'),
('3', '3', '3');

