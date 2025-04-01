-- USERS
INSERT INTO users (id, name, email, password, role) VALUES
('f50cb8e6-9b56-4023-987d-f74ce9a9a8d5', 'User', 'user@test.com', '$2a$10$/V63J4a13sNAhaNbtaiqlOGfcJFVjkqwleDvYkJiI3sN9pTGwaEoe', 'USER'),
('ac3dc77b-5a2f-453c-aef3-cc75e92c4fd2', 'Admin', 'admin@test.com', '$2a$10$/V63J4a13sNAhaNbtaiqlOGfcJFVjkqwleDvYkJiI3sN9pTGwaEoe', 'ADMIN');

-- CATEGORIES
INSERT INTO categories (id, name, description) VALUES
('fcd3a663-b73a-4d0e-8018-0beb8c740487', 'Bikes', NULL),
('749df635-b751-4588-b18c-28ca7263756b', 'Surfboards', NULL);

-- PRODUCTS
INSERT INTO products (id, name, description, category_id) VALUES
('a0b745a0-7e8b-4308-8587-e1a22ab358ed', 'Bike', 'High-quality bicycles designed for comfort, performance, and durability. Whether you''re a casual rider or a cycling enthusiast, we have the perfect bike to suit your needs.', 'fcd3a663-b73a-4d0e-8018-0beb8c740487');

-- CONFIGURATORS
INSERT INTO configurators (id, product_id, price_strategy_type) VALUES
('cd3aeaba-2a25-4c1b-bd4b-0c3fc2a6beac', 'a0b745a0-7e8b-4308-8587-e1a22ab358ed', 'SIMPLE');

-- PARTS
INSERT INTO parts (id, name, product_id) VALUES
('4aa6bb0c-4721-450d-9a15-de6cf3b8c28d', 'Frame Type', 'a0b745a0-7e8b-4308-8587-e1a22ab358ed'),
('4e7cb976-1bc5-409c-acdc-93fbadbd3dd2', 'Frame Finish', 'a0b745a0-7e8b-4308-8587-e1a22ab358ed'),
('23402b99-84e3-438e-bd38-952b0e14facb', 'Wheels', 'a0b745a0-7e8b-4308-8587-e1a22ab358ed'),
('bb54a545-016e-473b-9845-e4a05a876faa', 'Rim Color', 'a0b745a0-7e8b-4308-8587-e1a22ab358ed'),
('f286f930-e418-401d-a1bf-9387d375a53f', 'Chain', 'a0b745a0-7e8b-4308-8587-e1a22ab358ed');

-- PART OPTIONS
INSERT INTO part_options (id, name, price, in_stock, part_id) VALUES
('3f56936a-12b2-4c64-a6e9-36b28c4ed9de', 'Diamond', 100.0, true, '4aa6bb0c-4721-450d-9a15-de6cf3b8c28d'),
('8a1f83d7-505b-4082-a451-e6e49641fdaf', 'Step-through', 90.0, false, '4aa6bb0c-4721-450d-9a15-de6cf3b8c28d'),
('15b92658-dd3d-486a-b3cf-dfaa5042ce66', 'Full-suspension', 130.0, true, '4aa6bb0c-4721-450d-9a15-de6cf3b8c28d'),

('7bd35ece-5c8a-4d75-9738-64578cde7927', 'Matte', 50.0, true, '4e7cb976-1bc5-409c-acdc-93fbadbd3dd2'),
('45a9cefb-6d9c-4b59-b44c-00df0d1af468', 'Shiny', 30.0, true, '4e7cb976-1bc5-409c-acdc-93fbadbd3dd2'),

('dfa8e81e-1359-40d9-a48a-4ea19cbd6786', 'Road wheels', 80.0, true, '23402b99-84e3-438e-bd38-952b0e14facb'),
('f405014b-cdd9-41a8-b1af-b34c5423cbad', 'Mountain wheels', 100.0, true, '23402b99-84e3-438e-bd38-952b0e14facb'),
('91f0253e-cb11-4c89-82fe-6a35a533139a', 'Fat bike wheels', 120.0, true, '23402b99-84e3-438e-bd38-952b0e14facb'),

('0149eff7-c06c-4dae-a76e-32653d4f304a', 'Red', 10.0, false, 'bb54a545-016e-473b-9845-e4a05a876faa'),
('2ca03531-51e9-4703-b384-26ab7f544492', 'Black', 10.0, true, 'bb54a545-016e-473b-9845-e4a05a876faa'),
('2dfbfa43-b13f-48d4-8bff-03f1b6221f29', 'Blue', 20.0, true, 'bb54a545-016e-473b-9845-e4a05a876faa'),

('eb89f4a6-8c95-4180-a2bf-4ea29f3b305b', 'Single-speed chain', 43.0, true, 'f286f930-e418-401d-a1bf-9387d375a53f'),
('bb6970e9-fd35-4232-a516-d7619d0b49f7', '8-speed chain', 60.0, true, 'f286f930-e418-401d-a1bf-9387d375a53f');

-- RULE ENTITY (FATHER)
INSERT INTO rule_entity (id, configurator_id, rule_type) VALUES
-- Rules for 'Bike'
('a809ea53-8c86-4f62-b1a3-2c20a46b5f2f', 'cd3aeaba-2a25-4c1b-bd4b-0c3fc2a6beac', 'RESTRICTION'),
('c52e48cb-21e1-4c39-a5c3-c2481573bacc', 'cd3aeaba-2a25-4c1b-bd4b-0c3fc2a6beac', 'RESTRICTION'),
('b6e8d7e7-97e6-4e75-b127-24a4fc09e15d', 'cd3aeaba-2a25-4c1b-bd4b-0c3fc2a6beac', 'PRICE_CONDITION');


-- RESTRICTION RULE ENTITY
INSERT INTO restriction_rule_entity (id, if_option_id, operator) VALUES
('a809ea53-8c86-4f62-b1a3-2c20a46b5f2f', 'f405014b-cdd9-41a8-b1af-b34c5423cbad', 'REQUIRES'),
('c52e48cb-21e1-4c39-a5c3-c2481573bacc', '91f0253e-cb11-4c89-82fe-6a35a533139a', 'EXCLUDES');

-- PRICE CONDITION RULE ENTITY
INSERT INTO price_condition_rule_entity (id, if_option_id, price) VALUES
('b6e8d7e7-97e6-4e75-b127-24a4fc09e15d', '15b92658-dd3d-486a-b3cf-dfaa5042ce66', 10.0);

-- RULE REQUIRED OPTIONS
INSERT INTO rule_required_options (rule_id, option_id) VALUES
('b6e8d7e7-97e6-4e75-b127-24a4fc09e15d', '2dfbfa43-b13f-48d4-8bff-03f1b6221f29');

-- RULE TARGET OPTIONS
INSERT INTO rule_target_options (rule_id, target_option_id) VALUES
('a809ea53-8c86-4f62-b1a3-2c20a46b5f2f', '15b92658-dd3d-486a-b3cf-dfaa5042ce66'),
('c52e48cb-21e1-4c39-a5c3-c2481573bacc', '0149eff7-c06c-4dae-a76e-32653d4f304a');

-- PRESET CONFIGURATIONS
INSERT INTO preset_configurations (id, name, price, is_active, product_id) VALUES
('ba2c9ecb-328c-46c3-a432-a704f5eeafad', 'Shadow Beast', 340, true,	'a0b745a0-7e8b-4308-8587-e1a22ab358ed'),
('0e1a8e1e-f0d5-48ae-9f25-1b08d6485d43', 'Classic Commuter', 290, true, 'a0b745a0-7e8b-4308-8587-e1a22ab358ed'),
('1ff93b0f-d632-4741-8c91-967f1f09a991', 'Trail Blazer', 370, true, 'a0b745a0-7e8b-4308-8587-e1a22ab358ed'),
('6a8cf228-65b1-4d89-9826-e8141447a621', 'Urban Cruiser', 320, true, 'a0b745a0-7e8b-4308-8587-e1a22ab358ed');

-- PRESET SELECTED OPTIONS
INSERT INTO preset_selected_options (preset_id, option_name, part_name) VALUES
-- Shadow Beast
('ba2c9ecb-328c-46c3-a432-a704f5eeafad', 'Single-speed chain', 'Chain'),
('ba2c9ecb-328c-46c3-a432-a704f5eeafad', 'Shiny', 'Frame Finish'),
('ba2c9ecb-328c-46c3-a432-a704f5eeafad', 'Full-suspension', 'Frame Type'),
('ba2c9ecb-328c-46c3-a432-a704f5eeafad',	'Black', 'Rim Color'),
('ba2c9ecb-328c-46c3-a432-a704f5eeafad',	'Fat bike wheels', 'Wheels'),
-- Classic Commuter
('0e1a8e1e-f0d5-48ae-9f25-1b08d6485d43', 'Diamond', 'Frame Type'),
('0e1a8e1e-f0d5-48ae-9f25-1b08d6485d43', 'Shiny', 'Frame Finish'),
('0e1a8e1e-f0d5-48ae-9f25-1b08d6485d43', 'Road wheels', 'Wheels'),
('0e1a8e1e-f0d5-48ae-9f25-1b08d6485d43', 'Black', 'Rim Color'),
('0e1a8e1e-f0d5-48ae-9f25-1b08d6485d43', 'Single-speed chain', 'Chain'),
-- Trail Blazer
('1ff93b0f-d632-4741-8c91-967f1f09a991', 'Diamond', 'Frame Type'),
('1ff93b0f-d632-4741-8c91-967f1f09a991', 'Matte', 'Frame Finish'),
('1ff93b0f-d632-4741-8c91-967f1f09a991', 'Mountain wheels', 'Wheels'),
('1ff93b0f-d632-4741-8c91-967f1f09a991', 'Blue', 'Rim Color'),
('1ff93b0f-d632-4741-8c91-967f1f09a991', '8-speed chain', 'Chain'),

('6a8cf228-65b1-4d89-9826-e8141447a621', 'Step-through', 'Frame Type'),
('6a8cf228-65b1-4d89-9826-e8141447a621', 'Matte', 'Frame Finish'),
('6a8cf228-65b1-4d89-9826-e8141447a621', 'Road wheels', 'Wheels'),
('6a8cf228-65b1-4d89-9826-e8141447a621', 'Blue', 'Rim Color'),
('6a8cf228-65b1-4d89-9826-e8141447a621', '8-speed chain', 'Chain');
