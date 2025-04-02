CREATE TABLE categories (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    description VARCHAR(255),
    category_id UUID,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE configurators (
    id UUID PRIMARY KEY,
    product_id UUID UNIQUE,
    price_strategy_type VARCHAR(255),
    CONSTRAINT fk_configurators_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    CONSTRAINT chk_users_role CHECK (role IN ('USER', 'ADMIN'))
);

CREATE TABLE orders (
    id UUID PRIMARY KEY,
    user_id UUID,
    total_price REAL NOT NULL,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_configurations (
    order_id UUID NOT NULL,
    product_id UUID,
    product_name VARCHAR(255),
    selected_options_json TEXT,
    quantity INTEGER,
    preset BOOLEAN,
    price REAL,
    CONSTRAINT fk_order_configurations_order FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE parts (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    product_id UUID,
    CONSTRAINT fk_parts_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE part_options (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    price REAL NOT NULL,
    in_stock BOOLEAN NOT NULL,
    part_id UUID,
    CONSTRAINT fk_part_options_part FOREIGN KEY (part_id) REFERENCES parts(id)
);

CREATE TABLE preset_configurations (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    price REAL NOT NULL,
    is_active BOOLEAN NOT NULL,
    product_id UUID,
    CONSTRAINT fk_preset_configurations_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE preset_selected_options (
    preset_id UUID NOT NULL,
    option_name VARCHAR(255),
    part_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (preset_id, part_name),
    CONSTRAINT fk_preset_selected_options_preset FOREIGN KEY (preset_id) REFERENCES preset_configurations(id)
);

CREATE TABLE rule_entity (
    id UUID PRIMARY KEY,
    rule_type VARCHAR(31) NOT NULL,
    configurator_id UUID,
    CONSTRAINT fk_rule_entity_configurator FOREIGN KEY (configurator_id) REFERENCES configurators(id)
);

CREATE TABLE price_condition_rule_entity (
    id UUID PRIMARY KEY,
    price REAL NOT NULL,
    if_option_id UUID NOT NULL,
    CONSTRAINT fk_price_condition_rule_entity_id FOREIGN KEY (id) REFERENCES rule_entity(id),
    CONSTRAINT fk_price_condition_rule_if_option FOREIGN KEY (if_option_id) REFERENCES part_options(id)
);

CREATE TABLE rule_required_options (
    rule_id UUID NOT NULL,
    option_id UUID NOT NULL,
    PRIMARY KEY (rule_id, option_id),
    CONSTRAINT fk_rule_required_rule FOREIGN KEY (rule_id) REFERENCES price_condition_rule_entity(id),
    CONSTRAINT fk_rule_required_option FOREIGN KEY (option_id) REFERENCES part_options(id)
);

CREATE TABLE restriction_rule_entity (
    id UUID PRIMARY KEY,
    operator VARCHAR(255) NOT NULL,
    if_option_id UUID NOT NULL,
    CONSTRAINT fk_restriction_rule_entity_id FOREIGN KEY (id) REFERENCES rule_entity(id),
    CONSTRAINT fk_restriction_rule_if_option FOREIGN KEY (if_option_id) REFERENCES part_options(id)
);

CREATE TABLE rule_target_options (
    rule_id UUID NOT NULL,
    target_option_id UUID NOT NULL,
    PRIMARY KEY (rule_id, target_option_id),
    CONSTRAINT fk_rule_target_rule FOREIGN KEY (rule_id) REFERENCES restriction_rule_entity(id),
    CONSTRAINT fk_rule_target_option FOREIGN KEY (target_option_id) REFERENCES part_options(id)
);
