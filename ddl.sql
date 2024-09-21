CREATE TABLE developer(
    id uuid PRIMARY KEY,
    name varchar(200) NOT NULL UNIQUE,
    title text NOT NULL,
    imageUrl text NOT NULL,
    approval varchar(30) NOT NULL
);

CREATE TABLE app(
    id uuid PRIMARY KEY,
    name varchar(200) NOT NULL UNIQUE,
    developer_id uuid NOT NULL REFERENCES developer(id),

    title text NOT NULL,
    image_url text NOT NULL,
    description text NOT NULL,

    published boolean NOT NULL
);

CREATE TABLE app_version(
    id uuid PRIMARY KEY,
    app_id uuid NOT NULL REFERENCES app(id),
    version_string varchar(30) NOT NULL UNIQUE
)
