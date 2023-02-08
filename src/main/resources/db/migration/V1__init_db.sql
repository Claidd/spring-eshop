create sequence bucket_seq start with 1 increment by 1;
create sequence category_seq start with 1 increment by 1;
create sequence order_detail_seq start with 1 increment by 1;
create sequence order_seq start with 1 increment by 1;
create sequence product_seq start with 1 increment by 1;
create sequence user_seq start with 1 increment by 1;
create table buckets (id bigint not null, user_id bigint, primary key (id));
create table buckets_products (bucket_id bigint not null, product_id bigint not null);
create table category (id bigint not null, title varchar(255), primary key (id));
create table order_details (id bigint not null, amount numeric(38,2), price numeric(38,2), order_id bigint, product_id bigint, primary key (id));
create table orders (id bigint not null, address varchar(255), created timestamp(6), status varchar(255), sum numeric(38,2), updated timestamp(6), user_id bigint, primary key (id));
create table orders_details (order_id bigint not null, details_id bigint not null);
create table products (id bigint not null, price numeric(38,2), title varchar(255), primary key (id));
create table products_categories (product_id bigint not null, category_id bigint not null);
create table users (id bigint not null, archive boolean not null, email varchar(255), name varchar(255), password varchar(255), role varchar(255), primary key (id));
alter table if exists orders_details add constraint order_details_fk unique (details_id);
alter table if exists buckets add constraint buckets_user_fk foreign key (user_id) references users;
alter table if exists buckets_products add constraint buckets_products_fk foreign key (product_id) references products;
alter table if exists buckets_products add constraint buckets_bucket_fk foreign key (bucket_id) references buckets;
alter table if exists order_details add constraint orders_order_fk foreign key (order_id) references orders;
alter table if exists order_details add constraint orders_product_fk foreign key (product_id) references products;
alter table if exists orders add constraint orders_user_fk foreign key (user_id) references users;
alter table if exists orders_details add constraint orders_details_fk foreign key (details_id) references order_details;
alter table if exists orders_details add constraint orders_order_fk foreign key (order_id) references orders;
alter table if exists products_categories add constraint products_category_fk foreign key (category_id) references category;
alter table if exists products_categories add constraint products_product_fk foreign key (product_id) references products;






























-- create sequence bucket_seq start with 1 increment by 1;
-- create sequence category_seq start with 1 increment by 1;
-- create sequence order_details_seq start with 1 increment by 1;
-- create sequence order_seq start with 1 increment by 1;
-- create sequence product_seq start with 1 increment by 1;
-- create sequence user_seq start with 1 increment by 1;
-- create table category (id bigint not null, title varchar(255), primary key (id));
-- create table order_item (id bigint not null, address varchar(255), created timestamp(6), status varchar(255), sum numeric(38,2), updated timestamp(6), user_id bigint, primary key (id));
-- create table order1_details (order_id bigint not null, details_id bigint not null);
-- create table orders_details (id bigint not null, amount numeric(38,2), price numeric(38,2), order_id bigint, product_id bigint, primary key (id));
-- create table products_categories (product_id bigint not null, category_id bigint not null);
-- create table users (id bigint not null, archive boolean not null, email varchar(255), name varchar(255), password varchar(255), role varchar(255), bucket_id bigint, primary key (id));
-- create table buckets (id bigint not null, price numeric(38,2), title varchar(255), user_id bigint, primary key (id));
-- create table buckets_products (bucket_id bigint not null, product_id bigint not null);
-- alter table if exists order1_details add constraint UK_jl6j57knpxg53vvturqjobmo1 unique (details_id);
-- alter table if exists buckets add constraint FKnl0ltaj67xhydcrfbq8401nvj foreign key (user_id) references users;
-- alter table if exists buckets_products add constraint FKf88d1m7fbp1epxj80rjhoaphp foreign key (product_id) references buckets;
-- alter table if exists buckets_products add constraint FKc49ah45o66gy2f2f4c3os3149 foreign key (bucket_id) references buckets;
-- alter table if exists order_item add constraint FKagt0gwlb3exgij1th9mxw8a6g foreign key (user_id) references users;
-- alter table if exists order1_details add constraint FKar5ix1jp6dxubq0ymf1l051xi foreign key (details_id) references orders_details;
-- alter table if exists order1_details add constraint FKk1lmtfuqwf6wdlougdf5cw13q foreign key (order_id) references order1;
-- alter table if exists orders_details add constraint FKtq2p60h2d2y8ui6op1pr0sit3 foreign key (order_id) references order1;
-- alter table if exists orders_details add constraint FK4mley88iksv8t8eo4b75v90xd foreign key (product_id) references buckets;
-- alter table if exists products_categories add constraint FKfdcalyppphnbbrsktuqd46c9 foreign key (category_id) references category;
-- alter table if exists products_categories add constraint FK2889pshutw1n4kptl2o59wsy4 foreign key (product_id) references buckets;
-- alter table if exists users add constraint FK8l2qc4c6gihjdyoch727guci foreign key (bucket_id) references buckets;


-- alter table if exists buckets drop constraint if exists FKnl0ltaj67xhydcrfbq8401nvj;
-- alter table if exists buckets_products drop constraint if exists FKf88d1m7fbp1epxj80rjhoaphp;
-- alter table if exists buckets_products drop constraint if exists FKc49ah45o66gy2f2f4c3os3149;
-- alter table if exists order1 drop constraint if exists FKagt0gwlb3exgij1th9mxw8a6g;
-- alter table if exists order1_details drop constraint if exists FKk1lmtfuqwf6wdlougdf5cw13q;
-- alter table if exists order1_details drop constraint if exists FKar5ix1jp6dxubq0ymf1l051xi;
-- alter table if exists orders_details drop constraint if exists FKtq2p60h2d2y8ui6op1pr0sit3;
-- alter table if exists orders_details drop constraint if exists FK4mley88iksv8t8eo4b75v90xd;
-- alter table if exists products_categories drop constraint if exists FKfdcalyppphnbbrsktuqd46c9;
-- alter table if exists products_categories drop constraint if exists FK2889pshutw1n4kptl2o59wsy4;
-- alter table if exists users drop constraint if exists FK8l2qc4c6gihjdyoch727guci;


-- Hibernate: create sequence bucket_seq start with 1 increment by 1
--     Hibernate: create sequence category_seq start with 1 increment by 1
--     Hibernate: create sequence order_details_seq start with 1 increment by 1
--     Hibernate: create sequence order_seq start with 1 increment by 1
--     Hibernate: create sequence product_seq start with 1 increment by 1
--     Hibernate: create sequence user_seq start with 1 increment by 1
--     Hibernate: create table buckets (id bigint not null, price numeric(38,2), title varchar(255), user_id bigint, primary key (id))
--     Hibernate: create table buckets_products (bucket_id bigint not null, product_id bigint not null)
--     Hibernate: create table category (id bigint not null, title varchar(255), primary key (id))
--     Hibernate: create table order1 (id bigint not null, address varchar(255), created timestamp(6), status varchar(255), sum numeric(38,2), updated timestamp(6), user_id bigint, primary key (id))
--     Hibernate: create table order1_details (order_id bigint not null, details_id bigint not null)
--     Hibernate: create table orders_details (id bigint not null, amount numeric(38,2), price numeric(38,2), order_id bigint, product_id bigint, primary key (id))
--     Hibernate: create table products_categories (product_id bigint not null, category_id bigint not null)
--     Hibernate: create table users (id bigint not null, archive boolean not null, email varchar(255), name varchar(255), password varchar(255), role varchar(255), bucket_id bigint, primary key (id))
--     Hibernate: alter table if exists order1_details add constraint UK_jl6j57knpxg53vvturqjobmo1 unique (details_id)
--     Hibernate: alter table if exists buckets add constraint FKnl0ltaj67xhydcrfbq8401nvj foreign key (user_id) references users
--     Hibernate: alter table if exists buckets_products add constraint FKf88d1m7fbp1epxj80rjhoaphp foreign key (product_id) references buckets
--     Hibernate: alter table if exists buckets_products add constraint FKc49ah45o66gy2f2f4c3os3149 foreign key (bucket_id) references buckets
--     Hibernate: alter table if exists order1 add constraint FKagt0gwlb3exgij1th9mxw8a6g foreign key (user_id) references users
--     Hibernate: alter table if exists order1_details add constraint FKar5ix1jp6dxubq0ymf1l051xi foreign key (details_id) references orders_details
--     Hibernate: alter table if exists order1_details add constraint FKk1lmtfuqwf6wdlougdf5cw13q foreign key (order_id) references order1
--     Hibernate: alter table if exists orders_details add constraint FKtq2p60h2d2y8ui6op1pr0sit3 foreign key (order_id) references order1
--     Hibernate: alter table if exists orders_details add constraint FK4mley88iksv8t8eo4b75v90xd foreign key (product_id) references buckets
--     Hibernate: alter table if exists products_categories add constraint FKfdcalyppphnbbrsktuqd46c9 foreign key (category_id) references category
--     Hibernate: alter table if exists products_categories add constraint FK2889pshutw1n4kptl2o59wsy4 foreign key (product_id) references buckets
--     Hibernate: alter table if exists users add constraint FK8l2qc4c6gihjdyoch727guci foreign key (bucket_id) references buckets