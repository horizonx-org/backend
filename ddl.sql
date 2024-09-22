CREATE TABLE developer(
    id uuid PRIMARY KEY,
    name varchar(200) NOT NULL UNIQUE,
    title text NOT NULL,
    approval varchar(30) NOT NULL
);

CREATE TABLE app(
    id uuid PRIMARY KEY,
    name varchar(200) NOT NULL UNIQUE,
    developer_id uuid NOT NULL REFERENCES developer(id),

    title text NOT NULL,
    icon_file text,
    banner_file text,
    screenshot_files text[] NOT NULL,
    description text NOT NULL,
    tags text[] NOT NULL,

    published boolean NOT NULL
);

CREATE TABLE app_version(
    id uuid PRIMARY KEY,
    app_id uuid NOT NULL REFERENCES app(id),
    version_code varchar(30) NOT NULL,
    updated_at timestamptz NOT NULL,
    published boolean NOT NULL,

    UNIQUE (app_id, version_code)
)
