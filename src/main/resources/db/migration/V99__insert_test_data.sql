INSERT INTO user_data.users(id, firstname, lastname, email, password, is_locked, is_enabled, role_id)
VALUES ('71119396-8694-11ed-9ef6-77042ee83937', 'Микола', 'Вишиванка', 'zlodiy123@gmail.com', '$2a$12$rmi83nAZKjrOwL/9JSIspuLpDshxr9BQkLJZeSJu1f6J0YEMfXjrO', 'false', 'true', 1),
('9a87155e-7401-11ee-b962-0242ac120002', 'Георгій', 'Вишенський', 'washington111@gmail.com', '$2a$12$HpXzPZ2C66a1r2CDUgY3QeruTDRJFboE0ebT9NO/lNjEB7AnkeU6a', 'false', 'true', 1),
('e2cb927c-7401-11ee-b962-0242ac120002', 'Володя', 'Осташин', 'vovka1992_ost@gmail.com', '$2a$12$aZVMQ0wTIweamdoqGxKF1eRS7QJfxy.2N7R89XKyuwMbYO4iuUVGG', 'false', 'true', 1),
('2781a503-7dda-4389-80fd-c627777ec0f3', 'Сергій', 'Камишин', 'kamishinsergey123@gmail.com', '$2a$12$0cai5HiyVT0mR6iWqb/1PunobDeAhGFNSBkc1CNHfqsFiUnhkVh76', 'false', 'true', 1),
('1e4edaf2-5e57-4753-840d-a6c97f860067', 'Юлія', 'Сверидова', 'julia_sveridova_work@gmail.com', '$2a$12$Y5zIBa49N4lWl37jJaKSNecRPd1MzbSJ8iejcJeenyTcT3mYUe8Q.', 'false', 'true', 1),
('90cdbeed-0898-480a-bbd5-43042885ceb3', 'Макар', 'Вишневецький', 'vishnya1976@gmail.com', '$2a$12$i3f0tALYX8pMjYa/SYadJek5FxqWnwofa6cPi1yAoT1QWTq6YZP2G', 'false', 'true', 1),
('a48c5d51-11d3-47cd-8705-dd53df55f845', 'Євгеній', 'Сошкін', 'zhenya1990@gmail.com', '$2a$12$VTtj8eiBMF9MZMycthaF6urVE39D8Wlsa/v9Mdq4BOG5.rEbUTuZC', 'false', 'true', 1),
('46bb1ff3-e05d-481c-b435-960dfc1f933d', 'Марк', 'Балтишев', 'baltishevmark123@gmail.com', '$2a$12$ZVG4OzDa/l5Z2HUaNMxBfOd0TYBrvPDw7oxRkUOEyJnzsZPtVOHx2', 'false', 'true', 1),
('b1a163be-3913-4299-8403-35785977b768', 'Оксана', 'Зиноївна', 'zenoivnazitomyr@gmail.com', '$2a$12$eHmZ.QUluAUVk7s40WPktukPv.PGw0oz8gT0IY60dgqN.9QSmsa0e', 'false', 'true', 1),
('dba68dc0-d1ba-4df8-9399-7ab09b071560', 'Довженко', 'Марія', 'dovzhenko.m.1978@gmail.com', '$2a$12$T89M9KeZJ2oE.we1V60UbOcaFdulAtjz8/sNS.VBkRJRcRrZPHPYS', 'false', 'true', 1);

INSERT INTO item_data.inventories(id)
VALUES ('02ab61b1-ac27-4f32-9bc9-3e40341b4699'), ('710c2e63-641e-42c3-a306-9e3088b8fda8'),
('9785b0c7-93e3-4da1-b8c8-2809c2065146'), ('672c6a46-0ee6-4817-bd0b-0d27231daf1f'),
('345d947e-7b65-44b4-9431-8b4254769469'), ('aee71174-f86d-4828-a290-4409550e1b10'),
('29823253-599a-43bb-8e85-97e36c7ba429'), ('539d6275-39b8-46cf-af78-9b815cc670e9'),
('755748a4-3d57-42a3-9af6-2661c021681b'), ('006ea5bb-ec91-4026-a62c-ea4469f5276e'),
('8ee6f894-0bfb-4716-9264-54e9aee6ba61'), ('97095135-7388-4fbc-9e79-e642fa11c5ea'),
('0e612ea1-32de-4776-b1f1-412fd482fd70'), ('50a638ab-06c0-4493-89a2-5e3ada61ea44'),
('80134b52-1fdf-4f48-8c97-404948f08ef8');

INSERT INTO item_data.requests(id)
VALUES ('abfe376e-809e-40df-8b64-3aff45ff2e3c'), ('dbe9c523-44fb-47fb-9830-0b0f51bf36b7'),
('59da3984-f587-4d51-9994-0276deaba65a'), ('ce699406-2ab7-4928-b8c9-1e84a1ca61ec'),
('19f87a6a-79df-4c14-823d-2ac36c1d57ad'), ('473fc3a3-edfc-407a-8898-b856281f8baa'),
('f7d0158b-fd25-4a46-ae62-7b4f70435773'), ('29ae3f8a-5439-425a-98a0-12d17e09530c'),
('8d756d5c-d6fe-41c6-847f-e33d6cc18eae'), ('e9c45510-0f52-4523-9ef0-cdf80192fbf9'),
('21874d6e-e21b-4f43-8ef3-d0dabccc1095'), ('05faca4f-245c-4f91-b937-4be606520f9f'),
('96696d5e-d513-49e9-b752-d5f0a9284e1b'), ('0120d5d8-cd90-4edb-a929-d33c8982878f'),
('6093c1b1-94d1-43ba-9474-06a3a36bf175');

INSERT INTO general_data.addresses(id, address, coordinates_longitude, coordinates_latitude)
VALUES ('e17d83d8-771e-4902-954c-04550fcabe60', 'Львівська область, Львів, Рівна вулиця 6', 49.8314759, 23.9761968),
('c3880914-311c-40af-bb57-d0540933d3cd', 'Knyahyni Ol''hy St, 18, L''viv, L''vivs''ka oblast, 79000', 49.824465315800936, 24.007322888416407),
('9cb02340-08ad-446e-a80c-55e217721675', 'Rivnens''ka oblast, Rivne, 24 Fabrychna St', 50.641917, 26.269554),
('98083222-fba4-4c7b-8d77-43774966795f', 'Vulytsya Yevhena Konovalʹtsya, 305', 48.885189882170096, 24.71090984728424),
('4fdbfff0-908b-44c9-89cb-61a37ab20df5', 'Ternopil''s''ka oblast, Berezhany, Vulytsya Lepkykh, 10', 49.44342, 24.93536),
('769f21a1-5901-4f6b-90d4-a4b539f83b34', 'Donets''ka oblast, Kramators''k, Oleksy Tykhoho St, 1', 48.718680, 37.560764),
('0dcd3aa9-0a10-4054-b5e4-213c59fe5db0', 'Kyivs''ka oblast, Bila Tserkva, Pryvokzal''na St, 10', 49.80787253425913, 30.11794946833953),
('c4d96896-fe86-4998-b019-1b4564be1eff', 'Shevchenka St, 15, Bila Tserkva, Kyivs''ka oblast, 09100', 49.79388248173281, 30.118627837456383),
('4d05e7fe-de5c-4b98-b506-48b3ed170038', 'Konstyantynovycha St, 39, Vinnytsia, Vinnyts''ka oblast, 21000', 49.230370, 28.438717),
('649daf96-6513-41f6-b5d8-688c3eae8fc9', NULL, 50.51613490390936, 30.773822268373745),
('0198c2dd-39a7-4629-9bbe-aa584ca5943e', 'Boryslavska St, 59, Kyiv, 02000', 50.43111486792335, 30.433654781339076),
('16b262e2-4207-4602-b699-5353350d51fd', 'Bilopil''s''kyi Shlyakh St, 53, Sumy, Sums''ka oblast, 40000', 50.929899459924336, 34.74157271215379),
('70d90328-33ea-4369-acc0-aae94d91772a', 'Lyapunova St, 33, Kharkiv, Kharkivs''ka oblast, 61000', 50.019895, 36.230942);

INSERT INTO volunteer_data.volunteers(id, phone_number, user_id, address_id, inventory_id, request_id)
VALUES ('ea5353ba-7df5-44dd-a85c-5c011580f9aa', '+380123456781', '71119396-8694-11ed-9ef6-77042ee83937', 'e17d83d8-771e-4902-954c-04550fcabe60', '02ab61b1-ac27-4f32-9bc9-3e40341b4699', 'abfe376e-809e-40df-8b64-3aff45ff2e3c'),
('c852891b-3f67-424d-97ef-194da575d403', '+380123456782','9a87155e-7401-11ee-b962-0242ac120002', 'c3880914-311c-40af-bb57-d0540933d3cd', '710c2e63-641e-42c3-a306-9e3088b8fda8', 'dbe9c523-44fb-47fb-9830-0b0f51bf36b7'),
('f061ad57-879c-446d-8bd4-b64452243bae', NULL, 'e2cb927c-7401-11ee-b962-0242ac120002', NULL, '9785b0c7-93e3-4da1-b8c8-2809c2065146', '59da3984-f587-4d51-9994-0276deaba65a'),
('f0330f60-0438-490f-97e8-d7330f73e3f4', '+380123456783', '2781a503-7dda-4389-80fd-c627777ec0f3', NULL, '672c6a46-0ee6-4817-bd0b-0d27231daf1f', 'ce699406-2ab7-4928-b8c9-1e84a1ca61ec'),
('329a7ba0-f10b-4996-8624-cfb32ff24c38', '+380123456784', '1e4edaf2-5e57-4753-840d-a6c97f860067', NULL, '345d947e-7b65-44b4-9431-8b4254769469', '19f87a6a-79df-4c14-823d-2ac36c1d57ad'),
('f042b766-57c5-4e54-a742-0678693e8fbe', NULL, '90cdbeed-0898-480a-bbd5-43042885ceb3', 'c4d96896-fe86-4998-b019-1b4564be1eff', 'aee71174-f86d-4828-a290-4409550e1b10', '473fc3a3-edfc-407a-8898-b856281f8baa'),
('f841617c-fa16-464f-9efc-71f3731e8f52', '+380123456785', 'a48c5d51-11d3-47cd-8705-dd53df55f845', NULL, '006ea5bb-ec91-4026-a62c-ea4469f5276e', 'e9c45510-0f52-4523-9ef0-cdf80192fbf9'),
('d7b05a64-e7f6-4059-b41d-69672dfdec1e', NULL, '46bb1ff3-e05d-481c-b435-960dfc1f933d', NULL, '8ee6f894-0bfb-4716-9264-54e9aee6ba61', '21874d6e-e21b-4f43-8ef3-d0dabccc1095'),
('a0c04e2a-bb44-4e39-abd2-dda0d9417f74', NULL, 'b1a163be-3913-4299-8403-35785977b768', NULL, '0e612ea1-32de-4776-b1f1-412fd482fd70', '96696d5e-d513-49e9-b752-d5f0a9284e1b'),
('7de01b75-f9cc-46a9-b995-f2e42cdc424e', NULL, 'dba68dc0-d1ba-4df8-9399-7ab09b071560', NULL, '50a638ab-06c0-4493-89a2-5e3ada61ea44', '0120d5d8-cd90-4edb-a929-d33c8982878f');

INSERT INTO group_data.groups(id, name, description, volunteer_id, address_id, inventory_id, request_id)
VALUES ('360ee69b-0f6b-48d7-a40b-db0cd537afda', 'Робимо окопні свічки', NULL, 'f061ad57-879c-446d-8bd4-b64452243bae', '4fdbfff0-908b-44c9-89cb-61a37ab20df5', '29823253-599a-43bb-8e85-97e36c7ba429', 'f7d0158b-fd25-4a46-ae62-7b4f70435773'),
('a79da60e-86b8-4659-a293-7defccbb5c00', 'Збір та видача гуманітарної допомоги', NULL, '329a7ba0-f10b-4996-8624-cfb32ff24c38', '769f21a1-5901-4f6b-90d4-a4b539f83b34', '539d6275-39b8-46cf-af78-9b815cc670e9', '29ae3f8a-5439-425a-98a0-12d17e09530c'),
('3478e89e-862b-455a-8eae-857ee190aa99', 'Сітки біля вокзалу', 'Плетимо сітки по вулиці Привокзальна 10. Контактний номер: +380123456789', 'f042b766-57c5-4e54-a742-0678693e8fbe', '0dcd3aa9-0a10-4054-b5e4-213c59fe5db0', '755748a4-3d57-42a3-9af6-2661c021681b', '8d756d5c-d6fe-41c6-847f-e33d6cc18eae'),
('5845f7bf-3d43-4122-abb2-6a8194482abf', 'Робимо дрони', 'Робимо FPV дрони', 'f042b766-57c5-4e54-a742-0678693e8fbe', 'c4d96896-fe86-4998-b019-1b4564be1eff', '97095135-7388-4fbc-9e79-e642fa11c5ea', '05faca4f-245c-4f91-b937-4be606520f9f'),
('495eb0be-5dad-4499-a738-f87ab9d1ca63', 'Готуємо для ЗСУ', 'Готуємо тушонку та каші на фронт', 'a0c04e2a-bb44-4e39-abd2-dda0d9417f74', '16b262e2-4207-4602-b699-5353350d51fd', '0e612ea1-32de-4776-b1f1-412fd482fd70', '96696d5e-d513-49e9-b752-d5f0a9284e1b'),
('b5d1f4bd-c079-41cd-94b6-b7086b4ee19c', 'Збір допомоги на Харківщині', 'Збираємо допомогу військовим на Харківському напрямку', '7de01b75-f9cc-46a9-b995-f2e42cdc424e', '70d90328-33ea-4369-acc0-aae94d91772a', '80134b52-1fdf-4f48-8c97-404948f08ef8', '6093c1b1-94d1-43ba-9474-06a3a36bf175');

INSERT INTO group_data.members(id, volunteer_id, group_id, member_role)
VALUES ('10fca4f1-2854-4f0b-b816-6a90298a4904', 'f061ad57-879c-446d-8bd4-b64452243bae', '360ee69b-0f6b-48d7-a40b-db0cd537afda', 2),
('d22228e0-81f6-4c41-9f90-dde189f60f3c', '329a7ba0-f10b-4996-8624-cfb32ff24c38', 'a79da60e-86b8-4659-a293-7defccbb5c00', 2),
('7504f077-f3f0-4018-b2c6-6f518aedf3ed', 'f042b766-57c5-4e54-a742-0678693e8fbe', '3478e89e-862b-455a-8eae-857ee190aa99', 2),
('9648b6ab-42b5-4757-9934-a7f9986dfb49', 'f0330f60-0438-490f-97e8-d7330f73e3f4', '3478e89e-862b-455a-8eae-857ee190aa99', 0),
('dd01b536-2aea-4889-b20d-5dfe5f3326b3', 'f042b766-57c5-4e54-a742-0678693e8fbe', '5845f7bf-3d43-4122-abb2-6a8194482abf', 2),
('115c4c24-37bc-4392-b69b-abec8eb5f0a0', 'a0c04e2a-bb44-4e39-abd2-dda0d9417f74', '495eb0be-5dad-4499-a738-f87ab9d1ca63', 2),
('b2b5075a-cd9d-4fc9-a97a-9c353803da3c', '7de01b75-f9cc-46a9-b995-f2e42cdc424e', 'b5d1f4bd-c079-41cd-94b6-b7086b4ee19c', 2);

INSERT INTO volunteer_data.cars(id, number, description, carrying_kg, type, volunteer_id)
VALUES ('3ff0b760-5cd1-418e-877e-705c18a3bcd5', 'АО 1234 ВВ', 'Чорна шкода', 60, 0, 'ea5353ba-7df5-44dd-a85c-5c011580f9aa'),
('c852891b-3f67-424d-97ef-194da575d403', 'АВ 6523 АК', 'Біла хонда', 100, 1, 'c852891b-3f67-424d-97ef-194da575d403'),
('17255247-e4a5-4edb-a446-74b7e0f2e9a9', 'ВА 1234 ОО', 'Бус мерседес', 400, 2, 'f841617c-fa16-464f-9efc-71f3731e8f52'),
('b8d3c4f3-8bcd-4d7b-8cd5-167f1b61f3e3', 'ВХ 0000 НХ', 'Форд фокус синій', 80, 0, 'd7b05a64-e7f6-4059-b41d-69672dfdec1e'),
('f374ea06-680c-46b9-91ad-2dc9b08f5fca', 'АК 1234 АО', 'Лада калина біла', 100, 0, 'd7b05a64-e7f6-4059-b41d-69672dfdec1e');

INSERT INTO item_data.inventory_items(id, ready_to_send, end_product, name, description, weight, amount, item_type, inventory_id)
VALUES ('04498a3a-f704-4e70-b197-1a76c29f9e85', true, true, '6 пар військових берц', NULL, 6, 2, 2, '755748a4-3d57-42a3-9af6-2661c021681b'),
('6fa8667b-1b88-48ff-badb-89701fcf1a82', true, true, '5 кг тушонки', 'Тушонка домашня в 1л банках', 5, 5, 3, '0e612ea1-32de-4776-b1f1-412fd482fd70'),
('a3eec7b1-16a8-42d7-ba28-1b53882a251b', true, true, '10 FPV дронів', '10 Звичайних FPV дронів', 6, 5, 5, '29823253-599a-43bb-8e85-97e36c7ba429'),
('d210e4d4-6164-403f-9205-7871d145df08', false, true, '4 штуки маскувальних сіток', NULL, 8, 4, 6, '02ab61b1-ac27-4f32-9bc9-3e40341b4699'),
('8c3fbc57-b555-4c1c-ae4c-9d632c953633', false, true, 'Одяг', 'Різний дитячий одяг', 4, 0, 1, '710c2e63-641e-42c3-a306-9e3088b8fda8'),
('ab25a8af-17ed-42cb-a8c0-78cff5563dde', true, true, 'Окопні свічки', '40 кг окопних свічок', 40, 0, 7, '539d6275-39b8-46cf-af78-9b815cc670e9'),
('388835f9-19c3-49b7-945f-8cd7fc216f51', true, true, 'Дрони', 'FPV дрони', 6, 4, 4, '97095135-7388-4fbc-9e79-e642fa11c5ea'),
('56a492f7-9117-405a-bac6-31dc4f1405f4', false, false, 'Запчастини для дронів', NULL, 20, 0, 0, '97095135-7388-4fbc-9e79-e642fa11c5ea');

INSERT INTO item_data.request_items(id, request_status, end_product, weight, amount, item_type, request_id)
VALUES ('0b222c33-dafa-40aa-b583-7ee67f8fe02e', 0, true, 0, 0, 3, '29ae3f8a-5439-425a-98a0-12d17e09530c'),
('749d1a05-39db-4610-a4ac-365e05cdd1fd', 0, true, 0, 0, 1, '29ae3f8a-5439-425a-98a0-12d17e09530c'),
('03867446-1f50-45d7-9146-51258955f076', 1, false, 100, 0, 0, '05faca4f-245c-4f91-b937-4be606520f9f'),
('f32ecc47-b82d-40a9-b901-6a1b7fbdce52', 0, false, 0, 0, 3, '96696d5e-d513-49e9-b752-d5f0a9284e1b');

INSERT INTO general_data.routes(id, start_at, status, driver_id, car_id, from_address_id, to_address_id, inventory_item_id, request_item_id, volunteer_giver_id, volunteer_taker_id)
VALUES ('effd3f58-5f6b-4cbd-8f00-f056e0edffbe', '2024-06-22 09:15:00.000', 0, 'c852891b-3f67-424d-97ef-194da575d403', 'c852891b-3f67-424d-97ef-194da575d403', 'c3880914-311c-40af-bb57-d0540933d3cd', '4d05e7fe-de5c-4b98-b506-48b3ed170038', NULL, NULL, NULL, NULL),
('07a0fd7f-53f4-4868-a985-946fa4930c98', '2024-06-13 19:15:00.000', 0, 'd7b05a64-e7f6-4059-b41d-69672dfdec1e', 'b8d3c4f3-8bcd-4d7b-8cd5-167f1b61f3e3', '649daf96-6513-41f6-b5d8-688c3eae8fc9', '16b262e2-4207-4602-b699-5353350d51fd', NULL, NULL, NULL, NULL);