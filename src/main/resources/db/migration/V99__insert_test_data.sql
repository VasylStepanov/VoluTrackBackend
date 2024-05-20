INSERT INTO user_data.users(id, firstname, lastname, email, password, is_locked, is_enabled, role_id)
VALUES ('71119396-8694-11ed-9ef6-77042ee83937', 'Микола', 'Вишиванка', 'zlodiy123@gmail.com', '$2a$12$rmi83nAZKjrOwL/9JSIspuLpDshxr9BQkLJZeSJu1f6J0YEMfXjrO', 'false', 'true', 1),
('9a87155e-7401-11ee-b962-0242ac120002', 'Георгій', 'Вишенський', 'washington111@gmail.com', '$2a$12$HpXzPZ2C66a1r2CDUgY3QeruTDRJFboE0ebT9NO/lNjEB7AnkeU6a', 'false', 'true', 1),
('e2cb927c-7401-11ee-b962-0242ac120002', 'Володя', 'Осташин', 'vovka1992_ost@gmail.com', '$2a$12$aZVMQ0wTIweamdoqGxKF1eRS7QJfxy.2N7R89XKyuwMbYO4iuUVGG', 'false', 'true', 1),
('2781a503-7dda-4389-80fd-c627777ec0f3', 'Сергій', 'Камишин', 'kamishinsergey123@gmail.com', '$2a$12$0cai5HiyVT0mR6iWqb/1PunobDeAhGFNSBkc1CNHfqsFiUnhkVh76', 'false', 'true', 1),
('1e4edaf2-5e57-4753-840d-a6c97f860067', 'Юлія', 'Сверидова', 'julia_sveridova_work@gmail.com', '$2a$12$Y5zIBa49N4lWl37jJaKSNecRPd1MzbSJ8iejcJeenyTcT3mYUe8Q.', 'false', 'true', 1),
('90cdbeed-0898-480a-bbd5-43042885ceb3', 'Макар', 'Вишневецький', 'vishnya1976@gmail.com', '$2a$12$i3f0tALYX8pMjYa/SYadJek5FxqWnwofa6cPi1yAoT1QWTq6YZP2G', 'false', 'true', 1);

INSERT INTO item_data.inventories(id)
VALUES ('02ab61b1-ac27-4f32-9bc9-3e40341b4699'), ('710c2e63-641e-42c3-a306-9e3088b8fda8'),
('9785b0c7-93e3-4da1-b8c8-2809c2065146'), ('672c6a46-0ee6-4817-bd0b-0d27231daf1f'),
('345d947e-7b65-44b4-9431-8b4254769469'), ('aee71174-f86d-4828-a290-4409550e1b10'),
('29823253-599a-43bb-8e85-97e36c7ba429'), ('539d6275-39b8-46cf-af78-9b815cc670e9'),
('755748a4-3d57-42a3-9af6-2661c021681b');

INSERT INTO item_data.requests(id)
VALUES ('abfe376e-809e-40df-8b64-3aff45ff2e3c'), ('dbe9c523-44fb-47fb-9830-0b0f51bf36b7'),
('59da3984-f587-4d51-9994-0276deaba65a'), ('ce699406-2ab7-4928-b8c9-1e84a1ca61ec'),
('19f87a6a-79df-4c14-823d-2ac36c1d57ad'), ('473fc3a3-edfc-407a-8898-b856281f8baa'),
('f7d0158b-fd25-4a46-ae62-7b4f70435773'), ('29ae3f8a-5439-425a-98a0-12d17e09530c'),
('8d756d5c-d6fe-41c6-847f-e33d6cc18eae');

INSERT INTO general_data.addresses(id, address, coordinates_latitude, coordinates_longitude)
VALUES ('e17d83d8-771e-4902-954c-04550fcabe60', 'Львівська область, Львів, Рівна вулиця 6', 23.9761968, 49.8314759),
('c3880914-311c-40af-bb57-d0540933d3cd', 'Львівська область, Львів', NULL, NULL),
('9cb02340-08ad-446e-a80c-55e217721675', 'Rivnens''ka oblast, Rivne, 24 Fabrychna St', 50.641917, 26.269554),
('98083222-fba4-4c7b-8d77-43774966795f', 'Івано-Франківська область, Івано-Франківськ, 118-В, Halytska Street', NULL, NULL),
('4fdbfff0-908b-44c9-89cb-61a37ab20df5', 'Ternopil''s''ka oblast, Berezhany, Vulytsya Lepkykh, 10', 24.93536, 49.44342),
('769f21a1-5901-4f6b-90d4-a4b539f83b34', 'Donets''ka oblast, Kramators''k, Oleksy Tykhoho St, 1', 37.560764, 48.718680),
('0dcd3aa9-0a10-4054-b5e4-213c59fe5db0', 'Kyivs''ka oblast, Bila Tserkva, Pryvokzal''na St, 10', 30.117936, 49.807795),
('c4d96896-fe86-4998-b019-1b4564be1eff', 'Kyivs''ka oblast, Bila Tserkva', NULL, NULL);

INSERT INTO volunteer_data.volunteers(id, user_id, address_id, inventory_id, request_id)
VALUES ('ea5353ba-7df5-44dd-a85c-5c011580f9aa', '71119396-8694-11ed-9ef6-77042ee83937', 'e17d83d8-771e-4902-954c-04550fcabe60', '02ab61b1-ac27-4f32-9bc9-3e40341b4699', 'abfe376e-809e-40df-8b64-3aff45ff2e3c'),
('c852891b-3f67-424d-97ef-194da575d403', '9a87155e-7401-11ee-b962-0242ac120002', 'c3880914-311c-40af-bb57-d0540933d3cd', '710c2e63-641e-42c3-a306-9e3088b8fda8', 'dbe9c523-44fb-47fb-9830-0b0f51bf36b7'),
('f061ad57-879c-446d-8bd4-b64452243bae', 'e2cb927c-7401-11ee-b962-0242ac120002', NULL, '9785b0c7-93e3-4da1-b8c8-2809c2065146', '59da3984-f587-4d51-9994-0276deaba65a'),
('f0330f60-0438-490f-97e8-d7330f73e3f4', '2781a503-7dda-4389-80fd-c627777ec0f3', NULL, '672c6a46-0ee6-4817-bd0b-0d27231daf1f', 'ce699406-2ab7-4928-b8c9-1e84a1ca61ec'),
('329a7ba0-f10b-4996-8624-cfb32ff24c38', '1e4edaf2-5e57-4753-840d-a6c97f860067', NULL, '345d947e-7b65-44b4-9431-8b4254769469', '19f87a6a-79df-4c14-823d-2ac36c1d57ad'),
('f042b766-57c5-4e54-a742-0678693e8fbe', '90cdbeed-0898-480a-bbd5-43042885ceb3', 'c4d96896-fe86-4998-b019-1b4564be1eff', 'aee71174-f86d-4828-a290-4409550e1b10', '473fc3a3-edfc-407a-8898-b856281f8baa');

INSERT INTO group_data.groups(id, name, description, volunteer_id, address_id, inventory_id, request_id)
VALUES ('360ee69b-0f6b-48d7-a40b-db0cd537afda', 'Робимо окопні свічки', NULL, 'f061ad57-879c-446d-8bd4-b64452243bae', '4fdbfff0-908b-44c9-89cb-61a37ab20df5', '29823253-599a-43bb-8e85-97e36c7ba429', 'f7d0158b-fd25-4a46-ae62-7b4f70435773'),
('a79da60e-86b8-4659-a293-7defccbb5c00', 'Збір та видача гуманітарної допомоги', NULL, '329a7ba0-f10b-4996-8624-cfb32ff24c38', '769f21a1-5901-4f6b-90d4-a4b539f83b34', '539d6275-39b8-46cf-af78-9b815cc670e9', '29ae3f8a-5439-425a-98a0-12d17e09530c'),
('3478e89e-862b-455a-8eae-857ee190aa99', 'Сітки біля вокзалу', 'Плетимо сітки по вулиці Привокзальна 10. Контактний номер: +380123456789', 'f042b766-57c5-4e54-a742-0678693e8fbe', '0dcd3aa9-0a10-4054-b5e4-213c59fe5db0', '755748a4-3d57-42a3-9af6-2661c021681b', '8d756d5c-d6fe-41c6-847f-e33d6cc18eae');

INSERT INTO group_data.members(id, volunteer_id, group_id, member_role)
VALUES ('10fca4f1-2854-4f0b-b816-6a90298a4904', 'f061ad57-879c-446d-8bd4-b64452243bae', '360ee69b-0f6b-48d7-a40b-db0cd537afda', 2),
('d22228e0-81f6-4c41-9f90-dde189f60f3c', '329a7ba0-f10b-4996-8624-cfb32ff24c38', 'a79da60e-86b8-4659-a293-7defccbb5c00', 2),
('7504f077-f3f0-4018-b2c6-6f518aedf3ed', 'f042b766-57c5-4e54-a742-0678693e8fbe', '3478e89e-862b-455a-8eae-857ee190aa99', 2),
('9648b6ab-42b5-4757-9934-a7f9986dfb49', 'f0330f60-0438-490f-97e8-d7330f73e3f4', '3478e89e-862b-455a-8eae-857ee190aa99', 0);

INSERT INTO volunteer_data.cars(id, number, description, carrying_kg, type, volunteer_id)
VALUES ('3ff0b760-5cd1-418e-877e-705c18a3bcd5', 'АО 1234 ВВ', 'Чорна шкода', 60, 0, 'ea5353ba-7df5-44dd-a85c-5c011580f9aa'),
('c852891b-3f67-424d-97ef-194da575d403', 'АВ 6523 АК', 'Біла хонда', 100, 1, 'c852891b-3f67-424d-97ef-194da575d403');

INSERT INTO item_data.items(id, name, description, amount, item_measurement, item_type)
VALUES ('d20bfdaf-2334-4005-8fff-d447935d6cf2', '6 пар військових берц', NULL, 6, 2, 2),
('3e145743-21fb-4721-97ce-7d735f25ff62', 'Харчі', 'Будь-які харчі, тушонка, каші, сухе печиво, чай', 0, 0, 3),
('e5913d2e-d5c7-4e73-aae2-03fe0c647867', '5 кг тушонки', 'Тушонка домашня в 1л банках', 5, 0, 3),
('558fc402-b492-4574-8d15-8c73ad19fc86', '10 FPV дронів', '10 Звичайних FPV дронів', 10, 2, 5),
('dbd2aba0-7026-46ca-976b-76b75fb24efc', 'Потрібно зимовий одяг', 'Будь-який одяг для дітей та дорослих у прифронтові селища', 0, 2, 1),
('f783f548-86c7-47af-9521-46f026dcb1d3', '4 штуки маскувальних сіток', NULL, 4, 2, 6),
('a3eec7b1-16a8-42d7-ba28-1b53882a251b', 'Одяг', 'Різний дитячий одяг', 4, 0, 1),
('94300a39-e561-4e57-a13b-fdcb90fa9bf3', 'Окопні свічки', '40 кг окопних свічок', 40, 0, 7);

INSERT INTO item_data.inventory_items(id, inventory_id, item_id)
VALUES ('04498a3a-f704-4e70-b197-1a76c29f9e85', '755748a4-3d57-42a3-9af6-2661c021681b', 'f783f548-86c7-47af-9521-46f026dcb1d3'),
('6fa8667b-1b88-48ff-badb-89701fcf1a82', '755748a4-3d57-42a3-9af6-2661c021681b', 'd20bfdaf-2334-4005-8fff-d447935d6cf2'),
('a3eec7b1-16a8-42d7-ba28-1b53882a251b', '29823253-599a-43bb-8e85-97e36c7ba429', '94300a39-e561-4e57-a13b-fdcb90fa9bf3'),
('d210e4d4-6164-403f-9205-7871d145df08', '02ab61b1-ac27-4f32-9bc9-3e40341b4699', 'a3eec7b1-16a8-42d7-ba28-1b53882a251b'),
('8c3fbc57-b555-4c1c-ae4c-9d632c953633', '710c2e63-641e-42c3-a306-9e3088b8fda8', 'e5913d2e-d5c7-4e73-aae2-03fe0c647867'),
('ab25a8af-17ed-42cb-a8c0-78cff5563dde', '539d6275-39b8-46cf-af78-9b815cc670e9', '558fc402-b492-4574-8d15-8c73ad19fc86');

INSERT INTO item_data.request_items(id, request_id, item_id)
VALUES ('0b222c33-dafa-40aa-b583-7ee67f8fe02e', '29ae3f8a-5439-425a-98a0-12d17e09530c', 'dbd2aba0-7026-46ca-976b-76b75fb24efc'),
('749d1a05-39db-4610-a4ac-365e05cdd1fd', '29ae3f8a-5439-425a-98a0-12d17e09530c', '3e145743-21fb-4721-97ce-7d735f25ff62');