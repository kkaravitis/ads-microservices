create table Customer
(
    id  bigint not null,
    name   varchar(120),
    role    varchar(100),
    primary key (id)
);

create table Category
(
    identifier varchar(120) not null,
    name varchar(120),
    description varchar(120),
    primary key (identifier)
);

create table Ad
(
    id varchar(120) not null,
    short_description varchar(120),
    description varchar(120),
    customer_id bigint,
    category_id varchar(120),
    created_at timestamp,
    price double,

    primary key (id),
    FOREIGN KEY (customer_id) REFERENCES Customer(id),
    FOREIGN KEY (category_id) REFERENCES Category(identifier)
);
